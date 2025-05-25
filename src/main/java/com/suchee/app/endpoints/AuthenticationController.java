package com.suchee.app.endpoints;

import com.suchee.app.dto.AuthenticationFailureResponse;
import com.suchee.app.dto.AuthenticationResponse;
import com.suchee.app.dto.AuthenticationSuccessResponse;
import com.suchee.app.dto.UserLoginRequestDTO;
import com.suchee.app.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    /**
     * Handles user login requests.
     *
     * <p>This endpoint accepts user credentials (email and password), validates them using the
     * {@code AuthenticationService}, and returns an {@code AuthenticationResponse} indicating
     * whether authentication succeeded or failed.</p>
     *
     * <p>If authentication fails, it returns a {@code 401 Unauthorized} response with the
     * appropriate {@code AuthenticationFailureResponse} body.</p>
     *
     * <p>If authentication is successful, it:
     * <ul>
     *   <li>Returns a {@code 200 OK} response with {@code AuthenticationSuccessResponse}.</li>
     *   <li>Sets an {@code auth_token} HTTP-only cookie containing the authentication token.</li>
     *   <li>Sets an {@code auth_token_type} HTTP-only cookie indicating the token type (e.g., Bearer, Basic).</li>
     * </ul>
     * </p>
     *
     * <p>Cookies are configured with:
     * <ul>
     *   <li>{@code httpOnly = true}</li>
     *   <li>{@code secure = false} (should be {@code true} in production)</li>
     *   <li>{@code path = "/"}</li>
     *   <li>{@code sameSite = "Strict"}</li>
     * </ul>
     * </p>
     *
     * @param userLoginRequestDTO the user's login credentials
     * @return {@code ResponseEntity} containing the authentication result and appropriate headers
     */

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO){

        AuthenticationResponse authenticationResponse = this.authenticationService.login(userLoginRequestDTO);

        if(authenticationResponse instanceof AuthenticationFailureResponse){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(authenticationResponse);
        }

        if(authenticationResponse instanceof AuthenticationSuccessResponse successResponse) {
            ResponseCookie tokenCookie = ResponseCookie.from("auth_token", successResponse.getAuthToken())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Strict")
                    .build();

            ResponseCookie typeCookie = ResponseCookie.from("auth_token_type", successResponse.getAuthTokenType().getType())
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Strict")
                    .build();


            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, tokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, typeCookie.toString())
                    .body(authenticationResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }

}
