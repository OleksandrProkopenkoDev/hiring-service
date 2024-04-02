package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.UserApplicationDto;
import ua.com.hiringservice.model.entity.user.UserApplication;

/**
 * Mapper interface for converting between UserApplication entity and UserApplication Dto.
 *
 * @author Yevhen Melnyk
 */
@Mapper(config = MapperConfig.class)
public interface UserApplicationMapper {

  /**
   * Converts a UserApplication entity to a UserApplication Dto.
   *
   * @param userApplication The UserApplication entity to be converted.
   * @return The converted UserApplication Dto.
   */
  @Mapping(target = "keycloakId", source = "userDetails.userKeycloakId")
  @Mapping(target = "vacancyId", source = "vacancy.id")
  UserApplicationDto toDto(UserApplication userApplication);

  /**
   * Converts a UserApplication Dto to a UserApplication entity.
   *
   * @param userApplicationDto The UserApplication Dto to be converted.
   * @return The converted UserApplication entity.
   */
  @Mapping(target = "userDetails.userKeycloakId", source = "keycloakId")
  @Mapping(target = "vacancy.id", source = "vacancyId")
  UserApplication toEntity(UserApplicationDto userApplicationDto);
}
