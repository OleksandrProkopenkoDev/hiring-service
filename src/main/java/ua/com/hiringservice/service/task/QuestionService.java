package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.filter.QuestionFilterCriteria;
import ua.com.hiringservice.model.dto.task.QuestionDto;

/** Replace this stub by correct Javadoc. */
public interface QuestionService {

  QuestionDto findById(UUID id);

  Page<QuestionDto> findAllByFilterCriteria(
      Pageable pageable, QuestionFilterCriteria questionFilterCriteria);

  QuestionDto save(QuestionDto questionDto);

  QuestionDto update(UUID questionId, QuestionDto questionDto);

  void deleteById(UUID id);
}
