package com.suchee.app.enums;

import lombok.Data;


public enum RoleType {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    MEMBER("Team Member");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
