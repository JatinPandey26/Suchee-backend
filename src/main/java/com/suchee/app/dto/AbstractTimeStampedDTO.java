package com.suchee.app.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AbstractTimeStampedDTO {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
