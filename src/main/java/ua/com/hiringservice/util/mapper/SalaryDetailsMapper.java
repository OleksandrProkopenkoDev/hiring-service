package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.SalaryDetailsDto;
import ua.com.hiringservice.model.entity.vacancy.SalaryDetails;

/** Mapper interface for converting between SalaryDetails entity and SalaryDetailsDto Dto. */
@Mapper(config = MapperConfig.class)
public interface SalaryDetailsMapper {
  SalaryDetails toEntity(SalaryDetailsDto salaryDetailsDto);

  SalaryDetailsDto toDto(SalaryDetails salaryDetails);
}
