package ua.com.hiringservice.service.task;

import org.springframework.stereotype.Service;
import ua.com.hiringservice.model.entity.task.HtmlQuestionContentImage;

/** Service interface for {@link HtmlQuestionContentImage} handling. */
@Service
public interface ImageService {
  HtmlQuestionContentImage saveImage(byte[] fileImage);

  HtmlQuestionContentImage getImageById(String id);

  void deleteImageById(String imageId);
}
