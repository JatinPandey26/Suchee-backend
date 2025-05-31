package com.suchee.app.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLinks {

    @Value("${suchee.base.url}")
    public String APPLICATION_BASE_URL;

    public String getApplicationBaseUrl(){
        return this.APPLICATION_BASE_URL;
    }


}
