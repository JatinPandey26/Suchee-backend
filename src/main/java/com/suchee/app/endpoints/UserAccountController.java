package com.suchee.app.endpoints;

import com.suchee.app.dto.UserCreateDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userAccount")
public class UserAccountController {

    private UserService userService;

    UserAccountController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserAccountById(@PathVariable Long id){
        UserDTO userDTO = this.userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO){
        UserDTO savedUser = this.userService.createUser(userCreateDTO);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/email-available")
    public ResponseEntity<Boolean> checkIfEmailAvailable(@RequestParam @Email String email){
        boolean isAvailable = this.userService.isEmailAvailable(email);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(){

        UserDTO userDTO = this.userService.getMe();
        return ResponseEntity.ok(userDTO);
    }

}
