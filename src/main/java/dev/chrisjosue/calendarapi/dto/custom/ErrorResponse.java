package dev.chrisjosue.calendarapi.dto.custom;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private HttpStatus httpStatus;
    private List<String> errors;
}
