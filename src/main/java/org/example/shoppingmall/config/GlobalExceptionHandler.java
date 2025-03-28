package org.example.shoppingmall.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNumberFormatException(NumberFormatException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "잘못된 숫자 형식이 포함되었습니다.");
        errorMap.put("message", ex.getMessage());
        return errorMap;
    }
}
