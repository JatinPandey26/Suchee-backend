package com.suchee.app.endpoints;

import com.suchee.app.dto.UserCreateDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.service.UserService;
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

    @PutMapping("/create")
    public  ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO){
        UserDTO savedUser = this.userService.createUser(userCreateDTO);
        return ResponseEntity.ok(savedUser);
    }

}
