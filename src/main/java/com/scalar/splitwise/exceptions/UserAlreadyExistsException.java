package com.scalar.splitwise.exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(){
        super("User already exists");
    }
}
