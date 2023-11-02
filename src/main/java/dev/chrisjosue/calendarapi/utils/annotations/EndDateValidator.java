package dev.chrisjosue.calendarapi.utils.annotations;

import dev.chrisjosue.calendarapi.dto.event.EventDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateValidator implements ConstraintValidator<EndDate, Object> {
    @Override
    public void initialize(EndDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (obj instanceof EventDto eventDto) {
            return eventDto.getStart().before(eventDto.getEnd());
        }
        return false;
    }
}
