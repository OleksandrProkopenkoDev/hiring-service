package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.tour.TourTaskDto;
import ua.com.hiringservice.model.dto.tour.TourTaskRequestDto;
import ua.com.hiringservice.model.entity.tour.TourTask;
import ua.com.hiringservice.util.mapper.task.TaskMapper;

/** Mapper interface for mapping between TourTask entities and TourTaskDto DTOs. */
@Mapper(
    uses = {TaskMapper.class},
    config = MapperConfig.class)
public interface TourTaskMapper {

  @Mapping(target = "taskDto", source = "task")
  TourTaskDto toDto(TourTask tourTask);

  @Mapping(target = "task", source = "taskDto")
  TourTask toEntity(TourTaskDto tourTaskDto);

  @Mapping(target = "task", ignore = true)
  @Mapping(target = "id", ignore = true)
  TourTask requestToEntity(TourTaskRequestDto requestDto);
}
