package ua.com.hiringservice.model.content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ua.com.hiringservice.model.enums.exam.ContentTypeValue;

/**
 * Functional interface for target content types.
 *
 * @implNote This annotation is used to specify the content type for classes implementing the {@link
 *     Content} interface.
 * @apiNote The {@link ContentType#value()} method should be used to include the content type value,
 *     e.g., "video".
 * @author Zakhar Kuropiatnyk
 * @since 06/01/2024
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentType {

  /**
   * Method to include content type value, e.g., "VIDEO_CONTENT".
   *
   * @return The value of the content type, e.g., "VIDEO_CONTENT".
   */
  ContentTypeValue value();
}
