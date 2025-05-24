package com.suchee.app.core.types;

import com.suchee.app.core.entities.BaseEntity;
import com.suchee.app.core.entities.Validatable;
import com.suchee.app.validation.Validators.CoreValidator;
import com.suchee.app.validation.Validators.EmailValidator;

public final class Email implements BaseEntity, Validatable , TypeWrapper<String> {

    String email;

    public Email(String email){
        this.email=email;
        this.validate(true);
    }

    @Override
    public CoreValidator<Email> getValidator() {
        return new EmailValidator();
    }

    @Override
    public String getValue() {
        return this.email;
    }


}
