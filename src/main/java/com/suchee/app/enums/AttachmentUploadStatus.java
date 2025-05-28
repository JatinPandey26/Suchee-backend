package com.suchee.app.enums;

public enum AttachmentUploadStatus {
    PENDING("Pending"),
    FAILED("Failed"),
    COMPLETED("Completed");

    private final String value;

    AttachmentUploadStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

