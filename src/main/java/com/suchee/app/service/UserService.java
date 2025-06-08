package com.suchee.app.service;

import com.suchee.app.dto.PasswordChangeDTO;
import com.suchee.app.dto.UserCreateDTO;
import com.suchee.app.dto.UserDTO;

import java.util.List;

public interface UserService {

    // Create a new user from a DTO, return the created user's DTO or ID
    UserDTO createUser(UserCreateDTO userCreateDTO);

    // Retrieve a user by ID, return User DTO
    UserDTO getUserById(Long id);

    // Retrieve all users (or paged)
    List<UserDTO> getAllUsers();

    // Update user details given user ID and update DTO
    UserDTO updateUser(Long id, UserDTO userUpdateDTO);

    // Delete user by ID
    void deleteUser(Long id);

    // Additional business logic operations (examples)
    void changeUserPassword(Long userId, PasswordChangeDTO passwordChangeDTO);

    void assignRoleToUser(Long userId, String roleName);

    boolean isEmailAvailable(String email);

    UserDTO getUserByEmail(String email);

    UserDTO getMe();
}

