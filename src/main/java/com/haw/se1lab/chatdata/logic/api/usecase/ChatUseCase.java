package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;

import java.util.List;

public interface ChatUseCase {

    Chat createChat(Chat chat);

    /**
     * Fügt einen Benutzer zu einem Chat hinzu
     * @param chat der Chat wo der Benutzer hinzugefügt werden soll
     * @param teilnehmer der Benutzer der hinzugefügt werden soll
     * @return der hinzugefügt Benutzer
     */
    void addParticipant(Chat chat, Benutzer teilnehmer);

    /**
     * Überprüft ob der Benutzer im Chat ein Teilnehmer ist
     * @param chat der Chat
     * @param teilnehmer der Benutzer
     * @return true, wenn der Benutzer im Chat drinnen ist, false wenn nicht
     */
    boolean checkIfParticipantExists(Chat chat, Benutzer teilnehmer);

    boolean checkIfChatExists(Chat chat);

    List<Chat> getAllChatsByUser(Benutzer benutzer);

    Chat getChat(Long chatId);
}
