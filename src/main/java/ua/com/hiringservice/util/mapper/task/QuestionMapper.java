package ua.com.hiringservice.util.mapper.task;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.task.QuestionDto;
import ua.com.hiringservice.model.entity.task.Question;

/**
 * Mapper interface for converting between {@link Question} entities and {@link QuestionDto} DTOs.
 * Uses {@link MapperConfig} for configuration with a strategy to ignore null values during mapping.
 *
 * <p>The methods defined in this interface provide mapping between entity and DTO representations
 * of {@link Question} instances. Implementation is generated by MapStruct during the build process.
 *
 * <p>Additionally, this interface includes a custom method for updating the entity from its
 * corresponding DTO, considering the configured strategy to ignore null values during mapping.
 */
@Mapper(config = MapperConfig.class)
public interface QuestionMapper {

  Question toEntity(QuestionDto questionDto);

  QuestionDto toDto(Question question);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "id", target = "id", ignore = true)
  void updateEntityFromDto(@MappingTarget Question question, QuestionDto questionDto);
}
