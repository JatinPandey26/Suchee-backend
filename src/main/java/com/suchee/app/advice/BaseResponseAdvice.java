package com.suchee.app.advice;

import com.suchee.app.dto.BaseResponseDTO;
import com.suchee.app.utils.RequestTimingFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Global response wrapper that standardizes API responses into a {@link BaseResponseDTO}.
 * Applies to all controller methods returning any response body.
 */
@RestControllerAdvice
public class BaseResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * Indicates whether this advice is applicable to a given controller method return type.
     *
     * @param returnType     the return type of the controller method
     * @param converterType  the selected converter type
     * @return true to apply this advice to all responses
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * Wraps the response body into a {@link BaseResponseDTO} if it's not already wrapped.
     * Also adds metadata like request path, duration, and HTTP status code.
     *
     * @param body                  the body to be written to the response
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter selected to write the response
     * @param serverRequest         the current request
     * @param serverResponse        the current response
     * @return a wrapped response inside {@link BaseResponseDTO}
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest serverRequest,
                                  ServerHttpResponse serverResponse) {

        // Skip wrapping if already in standard format
        if (body instanceof BaseResponseDTO<?>) {
            return body;
        }

        long startTime = (long) request.getAttribute(RequestTimingFilter.START_TIME_ATTR);
        long duration = System.currentTimeMillis() - startTime;
        String path = request.getRequestURI();
        int statusCode = response.getStatus();

        return BaseResponseDTO.success(body, "Request successful", path, statusCode, duration);
    }
}
