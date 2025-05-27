package com.suchee.app.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class BaseResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private String api;
    private Instant timestamp;
    private int statusCode;
    private long durationMs;

    public BaseResponseDTO(boolean success, String message, T data, List<String> errors, String api, int statusCode, long durationMs) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.api = api;
        this.timestamp = Instant.now();
        this.statusCode = statusCode;
        this.durationMs = durationMs;
    }

    public static <T> BaseResponseDTO<T> success(T data, String message, String api, int statusCode, long durationMs) {
        return new BaseResponseDTO<>(true, message, data, null, api, statusCode, durationMs);
    }

    public static <T> BaseResponseDTO<T> failure(String message, List<String> errors, String api, int statusCode, long durationMs,T stackTraceElement) {
        return new BaseResponseDTO<>(false, message, stackTraceElement, errors, api, statusCode, durationMs);
    }

}

