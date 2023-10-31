package dev.chrisjosue.calendarapi.dto.custom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseHandler(Boolean ok, HttpStatus httpStatus, Object objectResponse) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("ok", ok);
        response.put("httpStatus", httpStatus);
        response.put("data", objectResponse);

        return new ResponseEntity<>(response, httpStatus);
    }
}
