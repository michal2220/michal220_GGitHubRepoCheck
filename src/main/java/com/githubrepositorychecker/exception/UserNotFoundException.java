package com.githubrepositorychecker.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("No such user exists.");
    }
}
