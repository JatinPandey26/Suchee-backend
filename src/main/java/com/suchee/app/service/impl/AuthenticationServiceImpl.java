package com.suchee.app.service.impl;

import com.suchee.app.dto.AuthenticationFailureResponse;
import com.suchee.app.dto.AuthenticationResponse;
import com.suchee.app.dto.AuthenticationSuccessResponse;
import com.suchee.app.dto.UserLoginRequestDTO;
import com.suchee.app.exception.ErrorMessage;
import com.suchee.app.logging.Trace;
import com.suchee.app.security.AuthenticationTokenGenerator;
import com.suchee.app.security.UserAccountDetailService;
import com.suchee.app.service.AuthenticationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    UserAccountDetailService userAccountDetailService;
    PasswordEncoder passwordEncoder;
    AuthenticationTokenGenerator authenticationTokenGenerator;

    AuthenticationServiceImpl(UserAccountDetailService userAccountDetailService,
                              PasswordEncoder passwordEncoder,
                              AuthenticationTokenGenerator authenticationTokenGenerator){
        this.userAccountDetailService=userAccountDetailService;
        this.passwordEncoder=passwordEncoder;
        this.authenticationTokenGenerator=authenticationTokenGenerator;
    }

    @Override
    public AuthenticationResponse login(UserLoginRequestDTO request) {

        if(Trace.auth){
            Trace.log("Login request for user with email : " + request.getEmail());
        }

        try {
            UserDetails userAccountDetails = this.userAccountDetailService.loadUserByUsername(request.getEmail());

            // check Password
            String passwordHave = request.getPassword();
            String passwordWant = userAccountDetails.getPassword();

            if(!passwordEncoder.matches(passwordHave,passwordWant)){

                if(Trace.auth){
                    Trace.log("Login attempt failed , " + ErrorMessage.INVALID_USERNAME_PASSWORD);
                }

                AuthenticationFailureResponse authenticationFailureResponse = new AuthenticationFailureResponse();
                authenticationFailureResponse.setSuccess(Boolean.FALSE);
                authenticationFailureResponse.setTimestamp(LocalDateTime.now());
                authenticationFailureResponse.setErrorCause(ErrorMessage.INVALID_USERNAME_PASSWORD);
                return authenticationFailureResponse;
            }

            /*we are not storing user now in security context , Instead we will send some token to client and
            in next request it will be authenticated and stored in securityContext
            * */

            AuthenticationSuccessResponse authenticationSuccessResponse = new AuthenticationSuccessResponse();
            authenticationSuccessResponse.setSuccess(Boolean.TRUE);
            authenticationSuccessResponse.setTimestamp(LocalDateTime.now());

            String authToken = authenticationTokenGenerator.generateToken(userAccountDetails,passwordHave);
            authenticationSuccessResponse.setAuthToken(authToken);
            authenticationSuccessResponse.setAuthTokenType(authenticationTokenGenerator.getAuthTokenType());

            return authenticationSuccessResponse;
        }
        catch (UsernameNotFoundException exception){
            exception.printStackTrace();
            throw exception;
        }

    }

    @Override
    public void logout() {

    }
}
