package com.suchee.app.notification;

import com.suchee.app.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public enum TemplatesRegistry {

    MEMBER_INVITATION_EMAIL("member-invitation-email", List.of("teamName", "invitationLink")),
    PASSWORD_RESET("password-reset", List.of("userName", "resetLink")),
    NEWSLETTER("newsletter", List.of("title", "content", "date")),
    USER_CREATION_WELCOME_EMAIL("user-creation-email",List.of("user","loginLink","companyName","year"));

    private final String templateName;
    private final List<String> requiredFields;

    TemplatesRegistry(String templateName, List<String> requiredFields) {
        this.templateName = templateName;
        this.requiredFields = requiredFields;
    }

    public String getTemplateName() {
        return templateName;
    }

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public static TemplatesRegistry getByTemplateName(String templateName) {
        for (TemplatesRegistry template : TemplatesRegistry.values()) {
            if (template.getTemplateName().equals(templateName)) {
                return template;
            }
        }
        return null; // or throw exception if you want strict behavior
    }

    public static void validateTemplateVariables(String templateName, Map<String, Object> variables) {

        TemplatesRegistry template = TemplatesRegistry.getByTemplateName(templateName);

        if(template == null){
            throw new ResourceNotFoundException("Template" , "name" , templateName);
        }

        for (String required : template.getRequiredFields()) {
            if (!variables.containsKey(required) || variables.get(required) == null) {
                throw new IllegalArgumentException("Missing required template variable: " + required);
            }
        }
    }

}

