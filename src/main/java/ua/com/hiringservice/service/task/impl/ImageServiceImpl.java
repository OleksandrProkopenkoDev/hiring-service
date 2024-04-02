package ua.com.hiringservice.service.task.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.exception.ImageNotFoundException;
import ua.com.hiringservice.model.entity.task.HtmlQuestionContentImage;
import ua.com.hiringservice.repository.mongo.HtmlQuestionContentImageRepository;
import ua.com.hiringservice.service.task.ImageService;

/** implementation of {@link ImageService} */
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

  private final HtmlQuestionContentImageRepository htmlQuestionContentImageRepository;

  @Override
  public HtmlQuestionContentImage saveImage(byte[] fileImage) {
    final HtmlQuestionContentImage htmlQuestionContentImage = new HtmlQuestionContentImage();
    htmlQuestionContentImage.setData(fileImage);

    return htmlQuestionContentImageRepository.save(htmlQuestionContentImage);
  }

  @Override
  @Cacheable("questionImage")
  public HtmlQuestionContentImage getImageById(String imageId) {
    return htmlQuestionContentImageRepository
        .findById(imageId)
        .orElseThrow(() -> new ImageNotFoundException(imageId));
  }

  @Override
  @CacheEvict(value = "questionImage", key = "#imageId")
  public void deleteImageById(String imageId) {
    htmlQuestionContentImageRepository.deleteById(imageId);
  }
}
