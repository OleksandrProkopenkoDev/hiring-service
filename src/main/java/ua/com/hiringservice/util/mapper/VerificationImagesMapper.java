package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.verification.VerificationImagesDto;
import ua.com.hiringservice.model.entity.verification.VerificationImage;

/**
 * Replace this stub by correct Javadoc.
 *
 * @author Lev Kirilenko
 */
@SuppressWarnings("PMD")
@Mapper(config = MapperConfig.class)
public interface VerificationImagesMapper {
  VerificationImagesDto toDto(VerificationImage verificationImage);

  VerificationImage toEntity(VerificationImagesDto verificationImagesDto);
}
