package com.scalar.splitwise.exceptions;

public class GroupNotFoundException extends Exception{
    public GroupNotFoundException(
            String message
    ){
            super(message);
    }
}
