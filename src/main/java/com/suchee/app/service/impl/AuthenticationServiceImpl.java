package com.suchee.app.service.impl;

import com.suchee.app.dto.*;
import com.suchee.app.entity.UserAccount;
import com.suchee.app.exception.ErrorMessage;
import com.suchee.app.logging.Trace;
import com.suchee.app.security.AuthenticationTokenGenerator;
import com.suchee.app.security.UserAccountDetailService;
import com.suchee.app.service.AuthenticationService;
import com.suchee.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserAccountDetailService userAccountDetailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationTokenGenerator authenticationTokenGenerator;
    private final UserService userService;



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


                return generateLoginFailureResponse();
            }

            UserDTO user = this.userService.getUserByEmail(request.getEmail());

            /*we are not storing user now in security context , Instead we will send some token to client and
            in next request it will be authenticated and stored in securityContext
            * */

            AuthenticationSuccessResponse authenticationSuccessResponse = new AuthenticationSuccessResponse();
            authenticationSuccessResponse.setSuccess(Boolean.TRUE);

            String authToken = authenticationTokenGenerator.generateToken(userAccountDetails,passwordHave);
            authenticationSuccessResponse.setAuthToken(authToken);
            authenticationSuccessResponse.setAuthTokenType(authenticationTokenGenerator.getAuthTokenType());
            authenticationSuccessResponse.setUser(user);

            return authenticationSuccessResponse;
        }
        catch (UsernameNotFoundException exception){
            exception.printStackTrace();
            return generateLoginFailureResponse();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    private AuthenticationResponse generateLoginFailureResponse() {
        AuthenticationFailureResponse authenticationFailureResponse = new AuthenticationFailureResponse();
        authenticationFailureResponse.setSuccess(Boolean.FALSE);
        authenticationFailureResponse.setErrorCause(ErrorMessage.INVALID_USERNAME_PASSWORD);
        return authenticationFailureResponse;
    }

    @Override
    public void logout() {

    }
}
