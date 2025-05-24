package com.suchee.app.validation.Validators;

import com.suchee.app.core.types.Password;
import com.suchee.app.validation.Errors.SimpleValidationError;

public class PasswordValidator extends AbstractCoreValidator<Password,String> {


    /**
     * @param object
     */
    @Override
    protected void performValidation(Password password) {
        // no validation As of now as it contains hashPassword only
    }

    /**
     * @param object
     */
    @Override
    protected void performValidationForValue(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            addError(new SimpleValidationError("Password must not be null or empty."));
            return;
        }

        if (plainPassword.length() < 8) {
            addError(new SimpleValidationError("Password must be at least 8 characters long."));
        }

        if (!plainPassword.matches(".*[A-Z].*")) {
            addError(new SimpleValidationError("Password must contain at least one uppercase letter."));
        }

        if (!plainPassword.matches(".*[a-z].*")) {
            addError(new SimpleValidationError("Password must contain at least one lowercase letter."));
        }

        if (!plainPassword.matches(".*\\d.*")) {
            addError(new SimpleValidationError("Password must contain at least one digit."));
        }

        if (!plainPassword.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            addError(new SimpleValidationError("Password must contain at least one special character."));
        }
    }

}
