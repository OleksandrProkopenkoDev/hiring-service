package ua.com.hiringservice.service.task;

/** Replace this stub by correct Javadoc. */
public interface ImageGeneratorService {

  byte[] convertHTMLContentToImageBytes(String htmlContent);

  byte[] convertHTMLContentToImageBytes(String htmlContent, Integer width);
}
