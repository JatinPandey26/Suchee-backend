package com.suchee.app.validation.Validators;

import com.suchee.app.core.types.Email;
import com.suchee.app.validation.Errors.SimpleValidationError;

public class EmailValidator extends AbstractCoreValidator<Email,String>{

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    protected void performValidation(Email email) {
        if (email == null || email.getValue() == null || email.getValue().isBlank()) {
            addError(new SimpleValidationError("Email must not be null or empty."));
            return;
        }

       try {
           validate(email.getValue());
       }
       catch (Exception e){
           addError(new SimpleValidationError(e.getMessage()));
       }

    }

    public static void validate(String email){
        if (!email.matches(EMAIL_REGEX)) {
            throw new RuntimeException("Email format is invalid.");
        }
    }

}
