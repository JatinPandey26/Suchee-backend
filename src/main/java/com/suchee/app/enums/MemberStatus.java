package com.suchee.app.enums;

public enum MemberStatus {

    ACTIVE("Active"),
    INVITED("Invited"),
    REMOVED("Removed"),
    LEFT("Left");

    private final String status;

    MemberStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
