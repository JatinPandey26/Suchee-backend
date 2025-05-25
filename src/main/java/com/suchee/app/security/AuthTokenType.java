package com.suchee.app.security;

public enum AuthTokenType {
    BASIC_AUTH("BASIC"),
    JSON_WEB_TOKEN("JWT");

    String type;

    AuthTokenType(String type){
        this.type=type;
    }

    String getType(){
        return this.type;
    }
}
