package ua.com.hiringservice.util.mapper.task;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.model.entity.task.Task;

/**
 * Mapper interface for converting between {@link Task} entities and {@link TaskDto} DTOs. Uses
 * {@link MapperConfig} for configuration and operates as a Spring component.
 *
 * <p>The methods defined in this interface provide mapping between entity and DTO representations
 * of {@link Task} instances. Implementation is generated by MapStruct during the build process.
 *
 * <p>Additionally, this interface includes default methods for updating the entity and DTO from
 * their corresponding counterparts, considering ignored properties specified in the mappings.
 */
@Mapper(
    uses = {
      TaskQuestionDto.class,
      TaskQuestionMapper.class,
      PracticalTaskDto.class,
      PracticalTaskMapper.class,
      PracticalTaskImageDto.class,
      PracticalTaskImageMapper.class
    },
    config = MapperConfig.class)
public interface TaskMapper {

  @Mapping(target = "practicalTask", source = "practicalTaskDto")
  @Mapping(target = "auditEntity.createdAt", source = "createdAt")
  @Mapping(target = "auditEntity.updatedAt", source = "updatedAt")
  @Mapping(target = "taskQuestions", source = "taskQuestionDtos")
  Task toEntity(TaskDto taskDto);

  @Mapping(target = "practicalTaskDto", source = "practicalTask")
  @Mapping(target = "createdAt", source = "auditEntity.createdAt")
  @Mapping(target = "updatedAt", source = "auditEntity.updatedAt")
  @Mapping(target = "taskQuestionDtos", source = "taskQuestions")
  TaskDto toDto(Task task);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", source = "id", ignore = true)
  @Mapping(target = "practicalTask", source = "practicalTaskDto", ignore = true)
  @Mapping(target = "taskQuestions", source = "taskQuestionDtos", ignore = true)
  @Mapping(target = "totalQuestion", source = "totalQuestion", ignore = true)
  @Mapping(target = "totalDuration", source = "totalDuration", ignore = true)
  @Mapping(target = "totalWeight", source = "totalWeight", ignore = true)
  @Mapping(target = "published", source = "published", ignore = true)
  @Mapping(target = "type", source = "type", ignore = true)
  void updateEntityFromDto(@MappingTarget Task task, TaskDto taskDto);
}