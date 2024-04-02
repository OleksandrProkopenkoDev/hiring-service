package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.model.dto.filter.AnswerFilterCriteria;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;

/** Service for {@link AnswerDto} && {@link Answer} */
@Service
public interface AnswerService {

  AnswerDto findById(UUID id);

  AnswerDto create(AnswerDto answerDto);

  void deleteAnswerById(UUID id);

  /**
   * Method return {@link Page<AnswerDto>} with Answers collect by provided criteria in {@link
   * AnswerFilterCriteria}
   *
   * @param filter criteria for Answers collection
   * @param pageable criteria for in what form(how much in what direction) collect answers
   * @return collected answers
   */
  Page<AnswerDto> findAll(AnswerFilterCriteria filter, Pageable pageable);

  AnswerDto provide(UUID id, AnswerDto answer);
}
