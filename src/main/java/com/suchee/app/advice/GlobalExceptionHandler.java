package com.suchee.app.advice;

import com.suchee.app.dto.BaseResponseDTO;
import com.suchee.app.exception.ApiError;
import com.suchee.app.exception.AppException;
import com.suchee.app.exception.ErrorMessage;
import com.suchee.app.exception.ValidationException;
import com.suchee.app.logging.Trace;
import com.suchee.app.utils.RequestTimingFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String allErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> formatFieldError(fieldError))
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                AppException.ERROR_CODE.VALIDATION_ERROR.getCode(),
                allErrors.isEmpty() ? ErrorMessage.METHOD_ARGUMENT_NOT_VALID : allErrors,
                request.getRequestURI()));
    }

    private String formatFieldError(FieldError error) {
        return String.format("Field '%s': %s", error.getField(), error.getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleException(Exception ex, HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute(RequestTimingFilter.START_TIME_ATTR);
        long duration = 0L;
        if (startTime != null) {
            duration = System.currentTimeMillis() - startTime;
        } else {
            Trace.log("StartTime is null while calculation api time");
            duration = 0;
        }
        String path = request.getRequestURI();

        List<String> errors = List.of(ex.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        BaseResponseDTO<Object> dto = BaseResponseDTO.failure("Error occurred", errors, path, 500, duration,stackTrace);

        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
