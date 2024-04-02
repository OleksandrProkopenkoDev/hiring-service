package ua.com.hiringservice.util.mapper.task;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.hiringservice.config.MapperConfig;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;

/**
 * Mapper interface for mapping between DTOs and entities related to practical tasks. Two default
 * methods map List<byte[]> to List<String> and back. Also, they use PracticalTaskImageRepository to
 * get and save images (simple pictures for practical task description)
 */
@Mapper(
    uses = {PracticalTaskImageMapper.class},
    config = MapperConfig.class)
public interface PracticalTaskMapper {

  @Mapping(target = "imageIds", source = "imageDtos")
  PracticalTask toEntity(PracticalTaskDto dto);

  @Mapping(target = "imageDtos", source = "imageDtos")
  PracticalTaskDto toDto(PracticalTask entity, List<PracticalTaskImageDto> imageDtos);

  default List<String> mapDtoToStrings(List<PracticalTaskImageDto> imageDtos) {
    return imageDtos.stream().map(PracticalTaskImageDto::getId).toList();
  }

  @Mapping(target = "imageIds", source = "images")
  PracticalTask toEntityWithImageIds(
      PracticalTaskDto practicalTaskDto, List<PracticalTaskImage> images);

  default List<String> mapEntityToStrings(List<PracticalTaskImage> images) {
    return images.stream().map(PracticalTaskImage::getId).toList();
  }
}
