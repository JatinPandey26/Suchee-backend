package com.suchee.app.validation.Validators;

import com.suchee.app.core.types.Email;
import com.suchee.app.validation.Errors.SimpleValidationError;

public class EmailValidator extends AbstractCoreValidator<Email,String>{

    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    protected void performValidation(Email email) {
        if (email == null || email.getValue() == null || email.getValue().isBlank()) {
            addError(new SimpleValidationError("Email must not be null or empty."));
            return;
        }

        if (!email.getValue().matches(EMAIL_REGEX)) {
            addError(new SimpleValidationError("Email format is invalid."));
        }
    }

}
