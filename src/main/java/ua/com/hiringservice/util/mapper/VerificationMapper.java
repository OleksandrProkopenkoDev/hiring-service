package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.verification.VerificationDto;
import ua.com.hiringservice.model.entity.verification.Verification;

/** Replace this stub by correct Javadoc. */
@Mapper(uses = VerificationMapper.class, config = MapperConfig.class)
public interface VerificationMapper {

  VerificationDto toDto(Verification verification);

  Verification toEntity(VerificationDto verificationDto);
}
