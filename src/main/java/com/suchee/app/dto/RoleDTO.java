package com.suchee.app.dto;

import com.suchee.app.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDTO {
    private long id;

    @NotNull
    private RoleType role;
}
