package com.scalar.splitwise.controllers;

import com.scalar.splitwise.dtos.RegisterUserRequestDto;
import com.scalar.splitwise.dtos.RegisterUserResponseDto;
import com.scalar.splitwise.exceptions.UserAlreadyExistsException;
import com.scalar.splitwise.models.User;
import com.scalar.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto request) {
        User user;
        RegisterUserResponseDto response = new RegisterUserResponseDto();
        try {
            user = userService.registerUser(
                    request.getUserName(),
                    request.getPhoneNumber(),
                    request.getPassword()
            );
            response.setUserId(user.getId());
            response.setStatus("SUCCESS");

        }catch (UserAlreadyExistsException e){
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
        };
        return response;
    }
}
