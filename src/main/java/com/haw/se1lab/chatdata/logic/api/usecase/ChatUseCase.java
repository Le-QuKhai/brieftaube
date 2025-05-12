package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.users.dataaccess.api.entity.AbstractBenutzer;

public interface ChatUseCase {

    public void createChat(Chat chat);

    /**
     * Fügt einen Benutzer zu einem Chat hinzu
     * @param chat der Chat wo der Benutzer hinzugefügt werden soll
     * @param teilnehmer der Benutzer der hinzugefügt werden soll
     * @return der hinzugefügt Benutzer
     */
    public void addParticipant(Chat chat, AbstractBenutzer teilnehmer);

    /**
     * Überprüft ob der Benutzer im Chat ein Teilnehmer ist
     * @param chat der Chat
     * @param teilnehmer der Benutzer
     * @return true, wenn der Benutzer im Chat drinnen ist, false wenn nicht
     */
    public boolean checkIfParticipantExists(Chat chat, AbstractBenutzer teilnehmer);

}
