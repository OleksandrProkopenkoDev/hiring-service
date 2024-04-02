package ua.com.hiringservice.model.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.hiringservice.config.serializer.TextImageWrapperMapKeyDeserializer;
import ua.com.hiringservice.config.serializer.TextImageWrapperMapKeySerializer;
import ua.com.hiringservice.config.serializer.TextImageWrapperSerializer;

/**
 * Represents a data structure combining text and image information.
 *
 * <p>Additionally, the class specifies a custom key deserializer, {@link
 * TextImageWrapperMapKeyDeserializer}, for deserializing keys in a specific format into instances
 * of this class.
 *
 * @see TextImageWrapperMapKeyDeserializer
 * @author Zakhar Kuropiatnyk, gexter
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(keyUsing = TextImageWrapperMapKeyDeserializer.class)
@JsonSerialize(
    using = TextImageWrapperSerializer.class,
    keyUsing = TextImageWrapperMapKeySerializer.class)
public class TextImageWrapper {

  private String html;
  private String image;

  /**
   * field allow us to exclude {@code text property} from json when we don't need it. default =
   * true;
   */
  @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore @Transient @Builder.Default
  private boolean needHtml = true;
}
