package com.haw.se1lab.chatdata.common.api.exception;

/**
 * Wenn eine Nachricht nicht valid ist, z.B zu viele Zeichen beinhalten, wird diese Exception geschmissen.
 */
public class MessageInvalidException extends Exception
{
    public MessageInvalidException(String nachricht)
    {
        super("Die Nachricht: \"" + nachricht + "\" ist keine gültige Nachricht");
    }
}
