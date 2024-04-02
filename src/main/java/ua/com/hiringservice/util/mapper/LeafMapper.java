package ua.com.hiringservice.util.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.LeafDetailedDto;
import ua.com.hiringservice.model.entity.Leaf;

/**
 * Mapper interface for {@link Leaf}. The interface definition is used by MapStruct processor to
 * generate * LeafMapperImpl class under target directory when compiled. Refer <a
 * href="https://mapstruct.org/">mapstruct.org</a> for more details.
 *
 * @author gexter
 */
@Mapper(config = MapperConfig.class)
public interface LeafMapper {

  Leaf toEntity(LeafDetailedDto leafDetailedDto);

  LeafDetailedDto toDto(Leaf leaf);

  default Leaf mergeWithDto(Leaf leaf, LeafDetailedDto leafDetailedDto) {
    BeanUtils.copyProperties(this.toEntity(leafDetailedDto), leaf);
    return leaf;
  }
}
