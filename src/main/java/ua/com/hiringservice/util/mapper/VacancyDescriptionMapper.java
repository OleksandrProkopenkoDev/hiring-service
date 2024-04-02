package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.VacancyDescriptionDto;
import ua.com.hiringservice.model.entity.vacancy.VacancyDescription;

/** Mapper interface for converting between VacancyDescription entity and VacancyDescriptionDto. */
@SuppressWarnings("PMD")
@Mapper(config = MapperConfig.class)
public interface VacancyDescriptionMapper {

  VacancyDescriptionDto toDto(VacancyDescription vacancyDescription);

  VacancyDescription toEntity(VacancyDescriptionDto vacancyDescriptionDto);
}
