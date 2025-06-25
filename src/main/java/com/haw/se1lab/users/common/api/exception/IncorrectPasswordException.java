package com.haw.se1lab.users.common.api.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Inkorrektes Passwort");
    }
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
