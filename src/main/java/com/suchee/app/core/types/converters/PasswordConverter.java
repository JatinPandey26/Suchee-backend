package com.suchee.app.core.types.converters;

import com.suchee.app.core.types.Password;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PasswordConverter implements AttributeConverter<Password,String> {

    /**
     * @param password
     * @return
     */
    @Override
    public String convertToDatabaseColumn(Password password) {
        return password != null ? password.getValue() : null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Password convertToEntityAttribute(String s) {
        return s != null ? new Password(s) : null;
    }
}
