package ua.com.hiringservice.util.mapper;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.KeycloakUserDto;

/** Replace this stub by correct Javadoc. */
@Component
@Mapper(config = MapperConfig.class)
public interface KeycloakUserMapper {

  @Mapping(target = "userId", source = "id")
  KeycloakUserDto toDto(UserRepresentation userRepresentation);
}
