package com.suchee.app.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * {@code RequestTimingFilter} is a servlet filter that captures the start time of each HTTP request.
 * <p>
 * It sets a request attribute {@link #START_TIME_ATTR} with the current system time in milliseconds
 * at the beginning of the request. This is useful for measuring the total time taken to process a request,
 * typically for logging or performance monitoring purposes.
 * <p>
 * This filter extends {@link OncePerRequestFilter}, ensuring it is executed only once per request dispatch.
 *
 * <p>Example usage:
 * <pre>{@code
 * Long startTime = (Long) request.getAttribute(RequestTimingFilter.START_TIME_ATTR);
 * if (startTime != null) {
 *     long duration = System.currentTimeMillis() - startTime;
 *     // log or record duration
 * }
 * }</pre>
 *
 * @author
 */
@Component
public class RequestTimingFilter extends OncePerRequestFilter {

    /**
     * The attribute name used to store the request start time in milliseconds.
     */
    public static final String START_TIME_ATTR = "startTime";

    /**
     * Sets the {@link #START_TIME_ATTR} attribute on the request to capture the request start time.
     * The attribute can be used later (e.g., in an interceptor or another filter) to calculate the request duration.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to pass the request and response to
     * @throws ServletException if an exception occurs during filtering
     * @throws IOException if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        filterChain.doFilter(request, response);
    }
}
