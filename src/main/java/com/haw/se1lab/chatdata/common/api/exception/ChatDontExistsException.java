package com.haw.se1lab.chatdata.common.api.exception;

public class ChatDontExistsException extends RuntimeException {
    public ChatDontExistsException(String message) {
        super(message);
    }
}
