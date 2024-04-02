package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.LocationDto;
import ua.com.hiringservice.model.entity.vacancy.Location;

/** Replace this stub by correct Javadoc. */
@Mapper(config = MapperConfig.class)
public interface LocationMapper {

  @Mapping(target = "id", ignore = true)
  Location toEntity(LocationDto locationDto);

  LocationDto toDto(Location location);
}
