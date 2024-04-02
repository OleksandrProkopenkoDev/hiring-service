package ua.com.hiringservice.service.task.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xhtmlrenderer.resource.XMLResource;
import org.xhtmlrenderer.swing.Java2DRenderer;
import ua.com.hiringservice.exception.ImageGenerateException;
import ua.com.hiringservice.service.task.ImageGeneratorService;

/**
 * The ImageGeneratorServiceImpl class provides a service for converting HTML content into an image.
 */
@Slf4j
@Service
class ImageGeneratorServiceImpl implements ImageGeneratorService {
  private static final String FORMAT_IMAGE = "png";
  private static final Integer DEFAULT_WIDTH = 800;
  private static final String HTML_START =
      """
            <!DOCTYPE html><html><head>
            </head>
            <body>""";

  private static final String HTML_END = "</body></html>";

  /**
   * Converts the given HTML content into an image and returns it as a byte array. This method first
   * renders the HTML into a BufferedImage, and finally converts the BufferedImage into a byte
   * array.
   *
   * @param htmlContent the html content to be converted into an image
   * @return the resulting image as a byte array
   * @throws RuntimeException if an error occurs during the image processing
   */
  @Override
  @Cacheable("previewImage")
  public byte[] convertHTMLContentToImageBytes(final String htmlContent, final Integer width) {
    log.info("Converting html content to image bytes");

    final String html = HTML_START + htmlContent + HTML_END;

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      final Document doc = convertHtmlStringToDocument(html);
      final BufferedImage image = renderDocumentToBufferedImage(doc, width);

      return convertBufferedImageToByteArray(image, baos);
    } catch (final IOException ioException) {
      log.error("An error occurred while processing the image", ioException);
      throw new ImageGenerateException(ioException);
    }
  }

  @Override
  @Cacheable("previewImage")
  public byte[] convertHTMLContentToImageBytes(final String htmlContent) {
    return convertHTMLContentToImageBytes(htmlContent, DEFAULT_WIDTH);
  }

  private byte[] convertBufferedImageToByteArray(
      final BufferedImage image, final ByteArrayOutputStream baos) throws IOException {
    log.info("Converting BufferedImage to byte array");
    ImageIO.write(image, FORMAT_IMAGE, baos);

    return baos.toByteArray();
  }

  private BufferedImage renderDocumentToBufferedImage(final Document doc, final Integer width) {
    log.info("Rendering Document to BufferedImage");
    final Java2DRenderer imageRenderer = new Java2DRenderer(doc, width);

    return imageRenderer.getImage();
  }

  private Document convertHtmlStringToDocument(final String html) {
    log.info("Converting HTML string to Document");
    try (StringReader reader = new StringReader(html)) {
      return XMLResource.load(reader).getDocument();
    }
  }
}
