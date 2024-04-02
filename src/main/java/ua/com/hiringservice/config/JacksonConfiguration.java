package ua.com.hiringservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Objects;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.QuestionContentType;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/**
 * Configuration class for customizing Jackson to enable the usage of custom class annotations, such
 * as {@link QuestionContentType}. This class registers subtypes based on the annotations found in
 * the specified package.
 *
 * @apiNote This configuration allows Jackson to recognize and handle custom content types specified
 *     by the {@link QuestionContentType} annotation. The subtypes are determined by scanning
 *     classes within the package defined by {@link JacksonConfiguration#PACKAGE_NAME_OF_CONTENT}.
 * @author Zakhar Kuropiatnyk
 * @since 06/01/2024
 */
@Configuration
public class JacksonConfiguration {

  /** The base package for scanning classes annotated with {@link QuestionContentType}. */
  private static final String PACKAGE_NAME_OF_CONTENT = Content.class.getPackageName();

  /**
   * Provides a customized ObjectMapper bean with registered subtypes based on annotations.
   *
   * @return The customized ObjectMapper bean.
   */
  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    registerContentSubtypesByAnnotations(objectMapper);
    return objectMapper;
  }

  /**
   * Registers Jackson subtypes based on classes annotated with {@link QuestionContentType}.
   *
   * @param objectMapper The ObjectMapper to configure with subtypes.
   */
  private void registerContentSubtypesByAnnotations(ObjectMapper objectMapper) {
    final Reflections reflections = new Reflections(PACKAGE_NAME_OF_CONTENT);
    final Set<Class<?>> subtypes = reflections.getTypesAnnotatedWith(QuestionContentType.class);

    subtypes.stream()
        .map(
            subType -> {
              final QuestionContentType annotation =
                  subType.getAnnotation(QuestionContentType.class);
              if (annotation != null) {
                final QuestionType typeName = annotation.value();
                return new NamedType(subType, typeName.name());
              }
              return null; // Filter out classes without @QuestionContentType annotation
            })
        .filter(Objects::nonNull)
        .forEach(objectMapper::registerSubtypes);
  }
}
