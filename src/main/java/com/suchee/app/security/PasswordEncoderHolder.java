package com.suchee.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
* PasswordEncoderHolder will be used to hold encoder which will be used in classes were auto injection
* cant be used like PasswordConverter
* */

@Component
public class PasswordEncoderHolder {

    public static PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        passwordEncoder=encoder;
        return encoder;
    }

    public static boolean isEncoded(String password) {
        return password != null && password.matches("^\\$2[aby]\\$.{56}$");
    }

}
