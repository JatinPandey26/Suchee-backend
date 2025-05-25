package com.suchee.app.service;

import com.suchee.app.dto.AuthenticationResponse;
import com.suchee.app.dto.UserLoginRequestDTO;

public interface AuthenticationService {

    AuthenticationResponse login(UserLoginRequestDTO request);
    void logout();

}
