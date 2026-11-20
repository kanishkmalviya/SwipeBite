
package com.swipebite.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import com.swipebite.user.UserMapper;
import com.swipebite.user.dto.UserRequest;
import com.swipebite.user.dto.UserResponse;
import com.swipebite.user.entity.User;
import com.swipebite.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
            this.userService = userService;
            this.userMapper = userMapper;
            this.passwordEncoder = passwordEncoder;
            
            
}   
    
    // Customer Registration
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {

        User user = userMapper.toEntity(request);

        user.setPassword(
        		passwordEncoder.encode(request.getPassword()));

        User savedUser = userService.registerUser(user);

        return new ResponseEntity<>(
                userMapper.toResponse(savedUser),
                HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    // Get Customer Profile
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    // Update Customer Profile
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser( @PathVariable Long id,
    		@RequestBody UserRequest request) {

        User updatedUser = userMapper.toEntity(request);

        User savedUser = userService.updateUser(id, updatedUser);

        return ResponseEntity.ok(userMapper.toResponse(savedUser));
    }
    
 // Delete Customer Permanently
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("Customer deleted successfully");
    }
    
    
}
