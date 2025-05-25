package com.suchee.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
