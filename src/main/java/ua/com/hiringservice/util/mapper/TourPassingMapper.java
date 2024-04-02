package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.tour.TourPassingDto;
import ua.com.hiringservice.model.entity.tour.TourPassing;

/** Mapper interface for mapping between TourPassing entities and TourPassingDto DTOs. */
@Mapper(
    uses = {TourMapper.class},
    config = MapperConfig.class)
public interface TourPassingMapper {

  @Mapping(target = "tourDto", source = "tour")
  TourPassingDto toDto(TourPassing tourPassing);

  @Mapping(target = "tour", source = "tourDto")
  TourPassing toEntity(TourPassingDto tourPassingDto);
}
