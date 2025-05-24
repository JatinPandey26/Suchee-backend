package com.suchee.app.validation.Validators;

import com.suchee.app.validation.Errors.ValidationError;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class for core validators that provides a common structure for
 * validating objects and their individual values. This class manages a list of
 * {@link ValidationError} instances that are accumulated during validation.
 *
 * <p>Subclasses are required to implement the {@link #performValidation(Object)} method
 * to define specific validation logic for the target object.</p>
 *
 * @param <T> the type of the object being validated
 * @param <V> the type of the individual value that may also be validated
 */

public abstract class AbstractCoreValidator<T,V> implements CoreValidator<T>{
    protected List<ValidationError> errors = new ArrayList<>();

    protected void addError(ValidationError message) {
        errors.add(message);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    @Override
    public List<ValidationError> validate(T object) {
        errors.clear();
        performValidation(object);
        return errors;
    }

    protected abstract void performValidation(T object);

    public List<ValidationError> validateValue(V object){
        errors.clear();
        performValidationForValue(object);
        return errors;
    }

    protected void performValidationForValue(V object){
        // No impl
    }
}
