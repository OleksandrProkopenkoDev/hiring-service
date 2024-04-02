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
class ImageServiceImplIntegrationTest extends IntegrationTestBase {
  private final byte[] testData = "Test Image Data".getBytes();
  @Autowired private ImageService imageService;

  @Test
  @Order(1)
  void saveImageAndDeleteById() {
    final HtmlQuestionContentImage savedImage = imageService.saveImage(testData);

    Assertions.assertNotNull(imageService.getImageById(savedImage.getId()));
    imageService.deleteImageById(savedImage.getId());
    Assertions.assertThrows(
        ImageNotFoundException.class, () -> imageService.getImageById(savedImage.getId()));
  }

  @Test
  @Order(2)
  void saveImageAndRetrieveById() {

    final HtmlQuestionContentImage savedImage = imageService.saveImage(testData);

    Assertions.assertNotNull(savedImage.getId());
    Assertions.assertArrayEquals(testData, savedImage.getData());

    final HtmlQuestionContentImage retrievedImage = imageService.getImageById(savedImage.getId());
    Assertions.assertEquals(savedImage.getId(), retrievedImage.getId());
    Assertions.assertArrayEquals(savedImage.getData(), retrievedImage.getData());
  }
}
