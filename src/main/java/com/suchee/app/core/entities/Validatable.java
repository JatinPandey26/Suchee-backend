package com.suchee.app.core.entities;

import com.suchee.app.exception.ValidationException;
import com.suchee.app.logging.Trace;
import com.suchee.app.validation.Errors.ValidationError;
import com.suchee.app.validation.Validators.CoreValidator;

import java.util.List;

public interface Validatable extends BaseEntity{

    default List<ValidationError> validate(boolean throwError) {
        CoreValidator validator = getValidator();
        List<ValidationError> errors = validator.validate(this);
        if(errors.size() > 0 && throwError){
            if(Trace.validationErrors){
                Trace.log("Validation failed with " , errors.size() , " error(s).");

                for(ValidationError error : errors){
                    Trace.log(" - " + error.getMessage());
                }

            }
            throw new ValidationException("Validation failed with " + errors.size() + " error(s).");
        }
        return errors;
    }

    CoreValidator getValidator();

}
