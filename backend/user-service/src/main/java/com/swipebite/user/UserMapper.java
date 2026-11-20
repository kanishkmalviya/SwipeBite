
package com.swipebite.user;

import org.springframework.stereotype.Component;

import com.swipebite.user.dto.UserRequest;
import com.swipebite.user.dto.UserResponse;
import com.swipebite.user.entity.User;

@Component
public class UserMapper {

    // UserRequest to User Entity
	
    public User toEntity(UserRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setMobileNumber(request.getMobileNumber());

        user.setRole("CUSTOMER");

        return user;
    }

    // User Entity to UserResponse
    
    public UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setMobileNumber(user.getMobileNumber());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}
