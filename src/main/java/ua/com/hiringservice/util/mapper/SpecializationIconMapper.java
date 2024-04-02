package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.SpecializationIconDto;
import ua.com.hiringservice.model.entity.vacancy.SpecializationIcon;

/**
 * Mapper for converting between {@link SpecializationIcon} entities and {@link
 * SpecializationIconDto} DTOs.
 */
@Mapper(config = MapperConfig.class)
public interface SpecializationIconMapper {

  SpecializationIconDto toDto(SpecializationIcon specializationIcon);

  SpecializationIcon toEntity(SpecializationIconDto specializationIconDto);
}
