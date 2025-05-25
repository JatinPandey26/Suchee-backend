package com.suchee.app.endpoints;

import com.suchee.app.dto.AuthenticationFailureResponse;
import com.suchee.app.dto.AuthenticationResponse;
import com.suchee.app.dto.UserLoginRequestDTO;
import com.suchee.app.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO){

        AuthenticationResponse authenticationResponse = this.authenticationService.login(userLoginRequestDTO);

        if(authenticationResponse instanceof AuthenticationFailureResponse){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(authenticationResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }

}
