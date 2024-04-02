package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.UserDetailsDto;
import ua.com.hiringservice.model.entity.user.UserDetails;

/** Replace this stub by correct Javadoc. */
@Mapper(uses = UserDetailsMapper.class, config = MapperConfig.class)
public interface UserDetailsMapper {
  UserDetailsDto toDto(UserDetails userDetails);

  UserDetails toEntity(UserDetailsDto userDetailsDto);
}
