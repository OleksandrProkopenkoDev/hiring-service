package ua.com.hiringservice.util.mapper.task;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.task.PracticalTaskPassingResponseDto;
import ua.com.hiringservice.model.dto.task.QuizPassingResponseDto;
import ua.com.hiringservice.model.dto.task.TaskPassingDto;
import ua.com.hiringservice.model.entity.task.TaskPassing;

/** Replace this stub by correct Javadoc. */
@Mapper(
    uses = {TaskMapper.class, AnswerMapper.class},
    config = MapperConfig.class)
public interface TaskPassingMapper {

  TaskPassing toEntity(TaskPassingDto taskPassingDto);

  @Mapping(target = "answers", qualifiedByName = "toDto")
  TaskPassingDto toDto(TaskPassing taskPassing);

  @Mapping(target = "comment", source = "taskPassing.task.comment")
  @Mapping(target = "task", source = "task", ignore = true)
  @Mapping(target = "answers", qualifiedByName = "toDto")
  TaskPassingDto toUserDto(TaskPassing taskPassing);

  @Mapping(target = "type", source = "taskPassing.task.type")
  @Mapping(target = "taskId", source = "taskPassing.task.id")
  @Mapping(target = "passingScore", source = "taskPassing.task.passingScore")
  @Mapping(target = "maxScore", source = "taskPassing.task.maxScore")
  @Mapping(target = "answers", qualifiedByName = "toDto")
  QuizPassingResponseDto toQuizPassingResponseDto(TaskPassing taskPassing);

  @Mapping(target = "comment", source = "taskPassing.task.comment")
  @Mapping(target = "type", source = "taskPassing.task.type")
  @Mapping(target = "taskId", source = "taskPassing.task.id")
  @Mapping(target = "passingScore", source = "taskPassing.task.passingScore")
  @Mapping(target = "maxScore", source = "taskPassing.task.maxScore")
  PracticalTaskPassingResponseDto toPracticalTaskPassingResponseDto(TaskPassing taskPassing);
}
