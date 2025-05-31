package com.suchee.app.enums;

public enum NotificationPreferenceOption {
    ALL("all"),
    MENTIONS("mentions"),
    NONE("none");

    private final String preference;

    NotificationPreferenceOption(String preference) {
        this.preference = preference;
    }

    public String getPreference() {
        return preference;
    }
}

