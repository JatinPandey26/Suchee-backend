package com.suchee.app.core.types;

import com.suchee.app.core.entities.BaseEntity;
import com.suchee.app.core.entities.Validatable;
import com.suchee.app.security.PasswordEncoderHolder;
import com.suchee.app.validation.Validators.CoreValidator;
import com.suchee.app.validation.Validators.PasswordValidator;

public class Password implements BaseEntity, Validatable, TypeWrapper<String>{

    private String hashedPassword;

    public Password(String password){
        PasswordValidator validator = (PasswordValidator) getValidator();
        validator.validateValue(password);
        if(PasswordEncoderHolder.isEncoded(password)){
            this.hashedPassword=password;
        }
        else this.hashedPassword= PasswordEncoderHolder.passwordEncoder.encode(password);
    }

    @Override
    public CoreValidator getValidator() {
        return new PasswordValidator();
    }

    @Override
    public String getValue() {
        return this.hashedPassword;
    }
}
