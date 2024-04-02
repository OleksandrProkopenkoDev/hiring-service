package ua.com.hiringservice.model.content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/**
 * Functional interface for target content types. Annotation based on the specified {@link
 * QuestionType}. "questionType" is a "must-have"property for the {@link
 * com.fasterxml.jackson.databind.ObjectMapper}. It marks different {@link Content} implementations
 * to allow correct serialization and deserialization processes during * REST responses with the
 * frontend. It is also used during the save and read operations of {@link Question} and Answer
 *
 * @implNote This annotation is used to specify the content type for classes implementing the {@link
 *     Content} interface.
 * @apiNote The {@link QuestionContentType#value()} method should be used to include the question
 *     type value, {@link QuestionType}.
 * @author Zakhar Kuropiatnyk
 * @since 06/01/2024
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QuestionContentType {

  QuestionType value();
}
