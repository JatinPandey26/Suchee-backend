package com.suchee.app.dto;

import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Data
public class UserDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Role is required")
    @Valid
    private RoleDTO role;
}
