package com.haw.se1lab.users.common.api.exception;

public class UserDoesntExistsException extends Exception {
    public UserDoesntExistsException(String message) {
        super(message);
    }
}
