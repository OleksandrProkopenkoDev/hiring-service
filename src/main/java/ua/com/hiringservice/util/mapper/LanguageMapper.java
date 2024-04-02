package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.LanguageDto;
import ua.com.hiringservice.model.entity.vacancy.Language;

/** Replace this stub by correct Javadoc. */
@Mapper(config = MapperConfig.class)
public interface LanguageMapper {

  @Mapping(target = "id", ignore = true)
  Language toEntity(LanguageDto languageDto);

  LanguageDto toDto(Language language);
}
