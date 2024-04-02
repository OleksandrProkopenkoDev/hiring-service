package ua.com.hiringservice.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import ua.com.hiringservice.exception.TextImageWrapperSerializeException;
import ua.com.hiringservice.model.content.TextImageWrapper;

/**
 * Serializer allow us to exclude field "markdown" from {@link TextImageWrapper} during
 * serialization for cases when {@link TextImageWrapper} used like map key
 *
 * @author Zakhar Kuropiatnyk
 */
public class TextImageWrapperMapKeySerializer extends JsonSerializer<TextImageWrapper> {

  @Override
  public void serialize(
      final TextImageWrapper value, final JsonGenerator gen, final SerializerProvider serializers) {
    try {
      final TextImageWrapper valueForSerialization =
          TextImageWrapper.builder()
              .image(value.getImage())
              .html(value.isNeedHtml() ? value.getHtml() : null)
              .build();

      gen.writeFieldName(valueForSerialization.toString());

    } catch (final IOException exc) {
      throw new TextImageWrapperSerializeException(exc);
    }
  }
}
