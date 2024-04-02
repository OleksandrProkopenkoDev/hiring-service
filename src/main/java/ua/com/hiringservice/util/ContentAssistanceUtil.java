package ua.com.hiringservice.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;
import ua.com.hiringservice.model.content.TextImageWrapper;

/**
 * Class for fight with pmd plugin(again)
 *
 * @author Zakhar Kuropiatnyk
 */
@UtilityClass
public class ContentAssistanceUtil {

  public static String getSafeUpdatedString(final String targetString, final String sourceString) {
    if (!Strings.isBlank(sourceString) && !"null".equals(sourceString)) {
      return sourceString;
    }
    return targetString;
  }

  public static Map<String, String> getSafeUpdatedMap(
      final Map<String, String> targetMap, final Map<String, String> sourceMap) {
    if (sourceMap != null && !sourceMap.isEmpty()) {
      return sourceMap;
    }
    return targetMap;
  }

  public static Set<String> getSafeUpdatedStringSet(
      final Set<String> targetStringSet, final Set<String> sourceStringSet) {
    if (sourceStringSet != null && !sourceStringSet.isEmpty()) {
      return sourceStringSet;
    }
    return targetStringSet;
  }

  public static void imagesToTextProvidedAnswer(
      final Map<String, String> providedAnswer, final Set<TextImageWrapper> wrappers) {
    new HashMap<>(providedAnswer)
        .forEach(
            (key, value) -> {
              final String textKey =
                  wrappers.stream()
                      .filter(wrapper -> wrapper.getImage().equals(key))
                      .map(TextImageWrapper::getHtml)
                      .findAny()
                      .orElse(null);

              final String textValue =
                  wrappers.stream()
                      .filter(wrapper -> wrapper.getImage().equals(value))
                      .map(TextImageWrapper::getHtml)
                      .findAny()
                      .orElse(value);
              if (!Strings.isBlank(textKey)) {
                providedAnswer.remove(key);
                providedAnswer.put(textKey, textValue);
              }
            });
  }

  public static void imagesToTextProvidedAnswer(
      final Set<String> providedAnswer, final Set<TextImageWrapper> answerOptions) {
    new HashSet<>(providedAnswer)
        .forEach(
            provided -> {
              final String text =
                  answerOptions.stream()
                      .filter(wrapper -> provided.equals(wrapper.getImage()))
                      .map(TextImageWrapper::getHtml)
                      .findAny()
                      .orElse(provided);
              providedAnswer.remove(provided);
              providedAnswer.add(text);
            });
  }
}
