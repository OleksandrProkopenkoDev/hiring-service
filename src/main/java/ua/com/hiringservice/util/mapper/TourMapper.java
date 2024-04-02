package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.tour.TourDto;
import ua.com.hiringservice.model.entity.tour.Tour;
import ua.com.hiringservice.util.mapper.task.TaskMapper;

/** Mapper interface for mapping between Tour entities and TourDto DTOs. */
@Mapper(
    uses = {TaskMapper.class, TourTaskMapper.class},
    config = MapperConfig.class)
public interface TourMapper {

  @Mapping(target = "tourTaskDtos", source = "tourTasks")
  TourDto toDto(Tour tour);

  @Mapping(target = "tourTasks", source = "tourTaskDtos")
  Tour toEntity(TourDto dto);
}
