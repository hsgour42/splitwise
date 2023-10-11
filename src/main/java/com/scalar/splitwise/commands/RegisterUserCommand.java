package com.scalar.splitwise.commands;

import com.scalar.splitwise.controllers.UserController;
import com.scalar.splitwise.dtos.RegisterUserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RegisterUserCommand implements Command{
    private  UserController userController;

    @Autowired
    public RegisterUserCommand(UserController userController){
        this.userController = userController;
    }
    @Override
    public boolean matches(String input) {
        List<String> inpWords = Arrays.stream(input.split(" ")).toList();
        return inpWords.size() == 4 || inpWords.get(0).equals(CommandKeywords.REGISTER_USER);
    }

    @Override
    public void execute(String input) {
        List<String> inpWords = Arrays.stream(input.split(" ")).toList();
        String password = inpWords.get(1);
        String phoneNumber = inpWords.get(2);
        String userName = inpWords.get(3);

        RegisterUserRequestDto request = new RegisterUserRequestDto();
        request.setPassword(password);
        request.setUserName(userName);
        request.setPhoneNumber(phoneNumber);
        //call user controller and do their work
        userController.registerUser(request);
    }
}
