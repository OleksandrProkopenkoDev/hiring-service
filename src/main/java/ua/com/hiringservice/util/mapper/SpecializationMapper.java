package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.SpecializationCreateDto;
import ua.com.hiringservice.model.dto.vacancy.SpecializationDto;
import ua.com.hiringservice.model.entity.vacancy.Specialization;

/**
 * Mapper for converting between {@link Specialization} entities and {@link SpecializationDto} DTOs.
 */
@Mapper(config = MapperConfig.class)
public interface SpecializationMapper {

  SpecializationDto toDto(Specialization specialization);

  Specialization toEntity(SpecializationDto specializationDto);

  Specialization toEntity(SpecializationCreateDto specializationCreateDto);
}
