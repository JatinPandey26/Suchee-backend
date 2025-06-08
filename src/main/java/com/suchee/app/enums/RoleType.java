package com.suchee.app.enums;

import lombok.Data;


public enum RoleType {
    ADMIN("Administrator"),
    TEAM_ADMIN("Team Admin"),
    TEAM_MEMBER("Team Member"),
    BOARD_ADMIN("Board Admin"),
    BOARD_MEMBER("Board Member"),
    VIEWER("Viewer"),
    USER("Default User");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
