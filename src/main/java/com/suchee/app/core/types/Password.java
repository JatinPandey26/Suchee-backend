package com.suchee.app.core.types;

import com.suchee.app.core.entities.BaseEntity;
import com.suchee.app.core.entities.Validatable;
import com.suchee.app.validation.Validators.CoreValidator;
import com.suchee.app.validation.Validators.PasswordValidator;

public class Password implements BaseEntity, Validatable, TypeWrapper<String>{

    String hashedPassword;

    public Password(String password){
        PasswordValidator validator = (PasswordValidator) getValidator();
        validator.validateValue(password);
        //TODO:hash this password
        this.hashedPassword=password;
    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public CoreValidator getValidator() {
        return new PasswordValidator();
    }
}
