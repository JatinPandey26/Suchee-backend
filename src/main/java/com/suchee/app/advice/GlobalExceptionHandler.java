package com.suchee.app.advice;

import com.suchee.app.dto.BaseResponseDTO;
import com.suchee.app.exception.ApiError;
import com.suchee.app.exception.AppException;
import com.suchee.app.exception.ErrorMessage;
import com.suchee.app.exception.ValidationException;
import com.suchee.app.logging.Trace;
import com.suchee.app.utils.RequestTimingFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleUserNameNotFound(ValidationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiError(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getErrorCode(),
                        ex.getMessage(),
                        request.getRequestURI()
                )
        );
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        String allErrors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(fieldError -> formatFieldError(fieldError))
//                .collect(Collectors.joining("; "));
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(
//                HttpStatus.BAD_REQUEST.value(),
//                AppException.ERROR_CODE.VALIDATION_ERROR.getCode(),
//                allErrors.isEmpty() ? ErrorMessage.METHOD_ARGUMENT_NOT_VALID : allErrors,
//                request.getRequestURI()));
//    }

    private String formatFieldError(FieldError error) {
        return String.format("Field '%s': %s", error.getField(), error.getDefaultMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        BaseResponseDTO<Object> baseResponse = getBaseResponse(ex, HttpStatus.BAD_REQUEST.value(), request);
        baseResponse.setMeta(fieldErrors);
        baseResponse.setFieldLevelError(true);
        baseResponse.setSuccess(false);
        baseResponse.setMessage("Validation failed");
        baseResponse.setError("Field-level validation error");

        return ResponseEntity.badRequest().body(baseResponse);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex,HttpServletRequest request) {
        String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        if (message != null && message.contains("unique constraint")) {
            // Extract the constraint name if you want (optional)
            String constraintName = extractFieldNameFromDetail(message); // implement if needed

            BaseResponseDTO<Object> dto = getBaseResponse(new RuntimeException("Duplicate value violates unique constraint: " + constraintName),500,request);
            dto.setFieldLevelError(true);
            dto.setMeta(Map.of(constraintName,"Duplicate value for this field"));
            return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(getBaseResponse(ex,500,request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String extractFieldNameFromDetail(String message) {
        // Example message: Detail: Key (email)=(value) already exists.
        // We'll extract "email" from between parentheses after "Key"
        try {
            int keyIndex = message.indexOf("Key (");
            if (keyIndex == -1) return "unknown_field";

            int start = keyIndex + 5; // after "Key ("
            int end = message.indexOf(')', start);
            if (end == -1) return "unknown_field";

            return message.substring(start, end);
        } catch (Exception e) {
            return "unknown_field";
        }
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDTO<Object>> handleException(Exception ex, HttpServletRequest request) {

        BaseResponseDTO<Object> dto = getBaseResponse(ex,500,request);

        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private BaseResponseDTO<Object> getBaseResponse(Exception ex,int statusCode,HttpServletRequest request){
        Long startTime = (Long) request.getAttribute(RequestTimingFilter.START_TIME_ATTR);
        long duration = 0L;
        if (startTime != null) {
            duration = System.currentTimeMillis() - startTime;
        } else {
            Trace.log("StartTime is null while calculation api time");
            duration = 0;
        }
        String path = request.getRequestURI();
        String error = ex.getMessage();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        BaseResponseDTO<Object> dto = BaseResponseDTO.failure("Error occurred", error, path, statusCode, duration,stackTrace);
        return  dto;
    }
}
