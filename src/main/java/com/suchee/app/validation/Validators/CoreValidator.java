package com.suchee.app.validation.Validators;

import com.suchee.app.validation.Errors.ValidationError;

import java.util.List;

public interface CoreValidator<T> {
    List<ValidationError> validate(T object);
}
