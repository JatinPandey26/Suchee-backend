package com.suchee.app.core.types.converters;

import com.suchee.app.core.types.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // Applies to all Email fields globally
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email email) {
        return email != null ? email.getValue() : null;
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData != null ? new Email(dbData) : null;
    }
}