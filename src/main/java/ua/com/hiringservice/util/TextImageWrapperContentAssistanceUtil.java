package ua.com.hiringservice.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.experimental.UtilityClass;
import ua.com.hiringservice.model.content.TextImageWrapper;

/**
 * Class for fight with pmd plugin
 *
 * @author Zakhar Kuropiatnyk
 */
@UtilityClass
@SuppressWarnings("PMD.TooManyMethods")
public class TextImageWrapperContentAssistanceUtil {

  public static void fillInListWithTextImageWrappersWithNullImage(
      final List<TextImageWrapper> wrappers, final Collection<TextImageWrapper> wrappersToCheck) {
    wrappers.addAll(
        wrappersToCheck.stream().filter(wrapper -> wrapper.getImage() == null).toList());
  }

  public static void fillInListWithTextImageWrappersWithNullImage(
      final List<TextImageWrapper> wrappers,
      final Map<TextImageWrapper, TextImageWrapper> wrappersToCheck) {

    wrappers.addAll(
        wrappersToCheck.keySet().stream().filter(wrapper -> wrapper.getImage() == null).toList());
    wrappers.addAll(
        wrappersToCheck.values().stream().filter(wrapper -> wrapper.getImage() == null).toList());
  }

  public static List<TextImageWrapper> getListWithTextImageWrappersWithNullImage(
      final TextImageWrapper wrapperToCheck,
      final Map<TextImageWrapper, TextImageWrapper> wrappersToCheck) {

    final List<TextImageWrapper> wrappers =
        getListWithTextImageWrappersWithNullImage(wrapperToCheck);
    fillInListWithTextImageWrappersWithNullImage(wrappers, wrappersToCheck);
    return wrappers;
  }

  public static List<TextImageWrapper> getListWithTextImageWrappersWithNullImage(
      final TextImageWrapper wrapperToCheck, final Collection<TextImageWrapper> wrappersToCheck) {

    final List<TextImageWrapper> wrappers =
        getListWithTextImageWrappersWithNullImage(wrapperToCheck);

    fillInListWithTextImageWrappersWithNullImage(wrappers, wrappersToCheck);
    return wrappers;
  }

  public static List<TextImageWrapper> getListWithTextImageWrappersWithNullImage(
      final TextImageWrapper wrapperToCheck) {

    final List<TextImageWrapper> wrappers = new ArrayList<>();
    if (wrapperToCheck.getImage() == null) {
      wrappers.add(wrapperToCheck);
    }

    return wrappers;
  }

  public static void excludeTextFromTextImageWrapper(final TextImageWrapper wrapper) {
    wrapper.setNeedHtml(false);
  }

  public static void excludeTextFromTextImageWrapper(
      final TextImageWrapper wrapper, final Map<TextImageWrapper, TextImageWrapper> wrapperMap) {
    excludeTextFromTextImageWrapper(wrapper);
    wrapperMap.forEach(
        (key, value) -> {
          key.setNeedHtml(false);
          value.setNeedHtml(false);
        });
  }

  public static void excludeTextFromTextImageWrapper(
      final TextImageWrapper wrapper, final Set<TextImageWrapper> wrapperSet) {
    excludeTextFromTextImageWrapper(wrapper);
    wrapperSet.forEach(wrap -> wrap.setNeedHtml(false));
  }

  public static void safeUpdateWrapper(
      final TextImageWrapper targetWrapper, final TextImageWrapper sourceWrapper) {
    if (targetWrapper == null || sourceWrapper == null) {
      return;
    }
    if (sourceWrapper.getHtml() != null && !"null".equals(sourceWrapper.getHtml())) {
      targetWrapper.setHtml(sourceWrapper.getHtml());
    }
    if (sourceWrapper.getImage() != null && !"null".equals(sourceWrapper.getImage())) {
      targetWrapper.setImage(sourceWrapper.getImage());
    }
  }

  public static void safeUpdateWrappers(
      final Collection<TextImageWrapper> targetWrappers,
      final Collection<TextImageWrapper> sourceWrappers) {
    if (targetWrappers == null || sourceWrappers == null) {
      return;
    }
    targetWrappers.forEach(
        targetWrapper -> {
          final String targetImage = targetWrapper.getImage();
          final Optional<TextImageWrapper> sourceWrapper =
              sourceWrappers.stream()
                  .filter(source -> source.getImage().equals(targetImage))
                  .findAny();
          sourceWrapper.ifPresent(wrapper -> safeUpdateWrapper(targetWrapper, wrapper));
        });
  }

  public static void safeUpdateWrapperMap(
      final Map<TextImageWrapper, TextImageWrapper> targetWrapperMap,
      final Map<TextImageWrapper, TextImageWrapper> sourceWrapperMap) {
    if (targetWrapperMap == null || sourceWrapperMap == null) {
      return;
    }
    safeUpdateWrappers(targetWrapperMap.keySet(), sourceWrapperMap.keySet());
    safeUpdateWrappers(targetWrapperMap.values(), sourceWrapperMap.values());
  }

  public static Set<TextImageWrapper> getListOfTextImageWrappersFromMap(
      final Map<TextImageWrapper, TextImageWrapper> wrapperMap) {
    final Set<TextImageWrapper> wrappers = new HashSet<>(wrapperMap.keySet());
    wrappers.addAll(wrapperMap.values());
    return wrappers;
  }
}
