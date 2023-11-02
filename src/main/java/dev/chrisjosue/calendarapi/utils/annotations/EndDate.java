package dev.chrisjosue.calendarapi.utils.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Document
@Constraint(validatedBy = EndDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDate {
    String message() default "Date range is incorrect.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
