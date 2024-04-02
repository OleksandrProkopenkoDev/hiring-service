package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.vacancy.DepartmentDto;
import ua.com.hiringservice.model.entity.vacancy.Department;

/**
 * Mapper interface for {@link Department}. The interface definition is used by MapStruct processor
 * to generate * DepartmentMapperImpl class under target directory when compiled. Refer <a
 * href="https://mapstruct.org/">mapstruct.org</a> for more details.
 *
 * @author Yevhen Melnyk
 */
@Mapper(config = MapperConfig.class)
public interface DepartmentMapper {

  Department toEntity(DepartmentDto departmentDto);

  DepartmentDto toDto(Department department);
}
