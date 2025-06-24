package com.haw.se1lab.chatdata.common.api.exception;

/**
 * Wenn versucht wird einen Benutzer zu einer Gruppe hinzuzufügen wo er schon drinnen ist.
 */
public class ParticipantAlreadyExistsException extends Exception
{
    public ParticipantAlreadyExistsException(String message)
    {
        super(message);
    }
}
