package ua.com.hiringservice.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import ua.com.hiringservice.exception.TextImageWrapperSerializeException;
import ua.com.hiringservice.model.content.TextImageWrapper;

/**
 * Serializer allow us to exclude field "markdown" from {@link TextImageWrapper} during
 * serialization
 *
 * @author Zakhar Kuropiatnyk
 */
public class TextImageWrapperSerializer extends JsonSerializer<TextImageWrapper> {

  @Override
  public void serialize(
      final TextImageWrapper value,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializers) {

    try {
      jsonGenerator.writeStartObject();
      if (value.isNeedHtml()) {
        jsonGenerator.writeStringField("html", value.getHtml());
      } else {
        jsonGenerator.writeStringField("html", null);
      }
      jsonGenerator.writeStringField("image", value.getImage());

      jsonGenerator.writeEndObject();
    } catch (final IOException exc) {
      throw new TextImageWrapperSerializeException(exc);
    }
  }
}
