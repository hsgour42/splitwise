package com.scalar.splitwise.services;

import com.scalar.splitwise.exceptions.UserAlreadyExistsException;
import com.scalar.splitwise.models.User;
import com.scalar.splitwise.models.UserStatus;
import com.scalar.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User registerUser(String userName, String phoneNumber , String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByPhone(phoneNumber);
        if(userOptional.isPresent()){
            if(userOptional.get().getUserStatus().equals(UserStatus.ACTIVE)){
                throw new UserAlreadyExistsException();
            }else{
                User user = userOptional.get();
                user.setUserStatus(UserStatus.ACTIVE);
                user.setName(userName);
                user.setPassword(password);
                user.setPhone(phoneNumber);
                return userRepository.save(user);
            }
        }


        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setPhone(phoneNumber);
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }
}
