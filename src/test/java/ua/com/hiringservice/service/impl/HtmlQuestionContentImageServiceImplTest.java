package ua.com.hiringservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.hiringservice.IntegrationTestBase;
import ua.com.hiringservice.exception.ImageNotFoundException;
import ua.com.hiringservice.model.entity.task.HtmlQuestionContentImage;
import ua.com.hiringservice.service.task.ImageService;

/** Test class for image. */
class HtmlQuestionContentImageServiceImplTest extends IntegrationTestBase {
  private final byte[] testData = "Test Image Data".getBytes();
  @Autowired private ImageService imageService;

  @Test
  @Order(1)
  void saveImageAndDeleteById() {
    final HtmlQuestionContentImage savedHtmlQuestionContentImage = imageService.saveImage(testData);

    Assertions.assertNotNull(imageService.getImageById(savedHtmlQuestionContentImage.getId()));
    imageService.deleteImageById(savedHtmlQuestionContentImage.getId());
    Assertions.assertThrows(
        ImageNotFoundException.class,
        () -> imageService.getImageById(savedHtmlQuestionContentImage.getId()));
  }

  @Test
  @Order(2)
  void saveImageAndRetrieveById() {

    final HtmlQuestionContentImage savedHtmlQuestionContentImage = imageService.saveImage(testData);

    Assertions.assertNotNull(savedHtmlQuestionContentImage.getId());
    Assertions.assertArrayEquals(testData, savedHtmlQuestionContentImage.getData());

    final HtmlQuestionContentImage retrievedHtmlQuestionContentImage =
        imageService.getImageById(savedHtmlQuestionContentImage.getId());
    Assertions.assertEquals(
        savedHtmlQuestionContentImage.getId(), retrievedHtmlQuestionContentImage.getId());
    Assertions.assertArrayEquals(
        savedHtmlQuestionContentImage.getData(), retrievedHtmlQuestionContentImage.getData());
  }
}
