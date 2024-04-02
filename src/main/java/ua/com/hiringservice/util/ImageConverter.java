package ua.com.hiringservice.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.experimental.UtilityClass;
import ua.com.hiringservice.model.enums.ImageType;

/**
 * The ImageConverter class used to convert BufferedImage to different types.
 *
 * @author Nikita Diakov
 */
@UtilityClass
public class ImageConverter {

  /**
   * Convert BufferedImage to bytea array.
   *
   * @param image buffer image.
   * @param imageType mime type of image.
   */
  public static byte[] imageToBytesArray(BufferedImage image, ImageType imageType)
      throws IOException {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(image, imageType.name(), outputStream);
    return outputStream.toByteArray();
  }

  /**
   * Convert bytes array to buffer image.
   *
   * @param imageBytes bytes array of buffer image.
   */
  public static BufferedImage bytesArrayToImage(byte[] imageBytes) throws IOException {
    return ImageIO.read(new ByteArrayInputStream(imageBytes));
  }
}
