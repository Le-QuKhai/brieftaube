package com.haw.se1lab.chatdata.common.api.exception;

public class MessageInvalidException extends Exception
{
    public MessageInvalidException(String nachricht)
    {
        super("Die Nachricht: \"" + nachricht + "\" ist keine gültige Nachricht");
    }
}
