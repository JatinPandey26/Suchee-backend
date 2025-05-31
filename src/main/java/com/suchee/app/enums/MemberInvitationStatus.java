package com.suchee.app.enums;

public enum MemberInvitationStatus {
    PENDING("pending"),
    SENT("sent"),
    ACCEPTED("accepted"),
    DECLINED("declined"),
    EXPIRED("expired");

    private final String status;

    MemberInvitationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}