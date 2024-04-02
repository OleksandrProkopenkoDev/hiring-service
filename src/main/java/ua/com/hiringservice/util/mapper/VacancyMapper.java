package ua.com.hiringservice.util.mapper;

import java.util.Locale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.VacancyDto;
import ua.com.hiringservice.model.entity.vacancy.Vacancy;
import ua.com.hiringservice.model.enums.vacancy.ExperienceLevel;
import ua.com.hiringservice.model.enums.vacancy.LocationType;
import ua.com.hiringservice.model.enums.vacancy.VacancyStatus;
import ua.com.hiringservice.model.enums.vacancy.WorkArrangement;

/** Mapper interface for converting between Vacancy entity and Vacancy Dto. */
@SuppressWarnings({"PMD", "CPD.Duplications"})
@Mapper(
    config = MapperConfig.class,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {
      LanguageMapper.class,
      LocationMapper.class,
      SalaryDetailsMapper.class,
      DepartmentMapper.class
    })
// CPD-OFF
public interface VacancyMapper {

  @Mapping(target = "experienceLevel", source = "experienceLevel.label")
  @Mapping(target = "locationType", source = "locationType.label")
  @Mapping(target = "workArrangement", source = "workArrangement.label")
  @Mapping(target = "status", source = "status.label")
  VacancyDto toDto(Vacancy vacancy);

  @Mapping(
      target = "workArrangement",
      source = "workArrangement",
      qualifiedByName = "mapWorkArrangement")
  @Mapping(
      target = "experienceLevel",
      source = "experienceLevel",
      qualifiedByName = "mapExperienceLevel")
  @Mapping(target = "locationType", source = "locationType", qualifiedByName = "mapLocationType")
  @Mapping(target = "status", source = "status", qualifiedByName = "mapVacancyStatus")
  Vacancy toEntity(VacancyDto vacancyDto);

  @Mapping(
      target = "workArrangement",
      source = "workArrangement",
      qualifiedByName = "mapWorkArrangement")
  @Mapping(
      target = "experienceLevel",
      source = "experienceLevel",
      qualifiedByName = "mapExperienceLevel")
  @Mapping(target = "locationType", source = "locationType", qualifiedByName = "mapLocationType")
  @Mapping(target = "status", source = "status", qualifiedByName = "mapVacancyStatus")
  void updateEntityFromDto(@MappingTarget Vacancy vacancy, VacancyDto vacancyDto);

  /**
   * there are mappers for enums, from frontend style, like Full-time, Trainee, to java Enum
   * constant format: FULL_TIME, TRAINEE. It necessary because we have validation constraints in
   * Postgres,
   */
  @Named("mapWorkArrangement")
  default WorkArrangement mapWorkArrangement(String workArrangement) {
    return WorkArrangement.valueOf(workArrangement.toUpperCase(Locale.ROOT).replace("-", "_"));
  }

  @Named("mapExperienceLevel")
  default ExperienceLevel mapExperienceLevel(String experienceLevel) {
    return ExperienceLevel.valueOf(experienceLevel.toUpperCase(Locale.ROOT));
  }

  @Named("mapLocationType")
  default LocationType mapLocationType(String locationType) {
    return LocationType.valueOf(locationType.toUpperCase(Locale.ROOT));
  }

  @Named("mapVacancyStatus")
  default VacancyStatus mapVacancyStatus(String vacancyStatus) {
    return VacancyStatus.valueOf(vacancyStatus.toUpperCase(Locale.ROOT));
  }
}
// CPD-ON
