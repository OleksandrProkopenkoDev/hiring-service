package ua.com.hiringservice.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.springframework.stereotype.Component;
import ua.com.hiringservice.exception.TextImageWrapperKeyDeserializeException;
import ua.com.hiringservice.model.content.TextImageWrapper;

/**
 * Custom key deserializer for {@link TextImageWrapper}.
 *
 * <p>This deserializer is used by Jackson to convert a key representation into a {@link
 * TextImageWrapper} object. It specifically handles deserialization for keys formatted as
 * "markdown=value,image=value".
 *
 * @implNote This deserializer assumes a key format like "markdown=value,image=value".
 * @implSpec This deserializer extracts "markdown" and "image" values from the key and constructs a
 *     {@link TextImageWrapper} object.
 * @author Zakhar Kuropiatnyk
 */
@Component
public class TextImageWrapperMapKeyDeserializer extends KeyDeserializer {

  /**
   * Deserializes the key into a {@link TextImageWrapper} object.
   *
   * @param key The key in the format "markdown=value,image=value".
   * @param ctxt The deserialization context.
   * @return The deserialized {@link TextImageWrapper} object.
   * @throws TextImageWrapperKeyDeserializeException If the key format is invalid. Or If an I/O
   *     error occurs during deserialization.
   */
  @Override
  public Object deserializeKey(String key, DeserializationContext ctxt) {

    @SuppressWarnings("PMD.CloseResource")
    final JsonParser jsonParser = ctxt.getParser();
    /* Ensure that the key is not null */
    if (key == null) {
      throw new TextImageWrapperKeyDeserializeException(
          new JsonMappingException(jsonParser, "Key cannot be null"));
    }
    try {
      /* Extract information from the key */
      final String[] parts = key.split(",");
      final String text = extractValue(parts[0], jsonParser);
      final String image = extractValue(parts[1], jsonParser);
      final String nullPmd = null;
      return TextImageWrapper.builder()
          .html(text)
          .image("null".equals(image) ? nullPmd : image)
          .build();
    } catch (JsonMappingException e) {
      throw new TextImageWrapperKeyDeserializeException(e);
    }
  }

  /**
   * Extracts the value from a key part formatted as "name=value".
   *
   * @param part The key part in the format "name=value".
   * @param jsonParser json parser.
   * @return The extracted value.
   * @throws JsonMappingException If the key part format is invalid.
   */
  private String extractValue(String part, JsonParser jsonParser) throws JsonMappingException {
    final int keyLength = 2;
    final String[] keyValue = part.split("=");
    if (keyValue.length == keyLength) {
      String value = keyValue[1];
      if (value.endsWith(")")) {
        // Remove the last character
        value = value.substring(0, value.length() - 1);
      }
      return value;
    } else {
      throw new JsonMappingException(jsonParser, "Invalid key format: " + part);
    }
  }
}
