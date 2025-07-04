package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;

import java.util.List;

public interface ChatUseCase {

    Chat createChat(ChatErstellung chatErstellung);

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

    /**
     * Überprüft ob der Chat existiert.
     * @param chat der Chat, der überprüft werden soll
     * @return true, wenn er existiert. False, wenn nicht.
     */
    boolean checkIfChatExists(Chat chat);

    /**
     * Gibt alle Chats zurück, in denen der übergebene User drinnen ist.
     * @param userName die Id des Benutzers, für den man alle Chats holen soll
     * @return Liste von Chats, leere Liste, wenn er ihn keinen drinnen ist.
     */
    List<Chat> getAllChatsByUser(String userName);

    /**
     * Gibt den Chat mit der übergebenen Id zurück
     * @param chatId die Id von den Chat
     * @return der Chat mit der Id
     */
    Chat getChat(Long chatId);

    List<Chat> getNewChats(List<Long> chatIds, String userName);

    List<Nachricht> getAllMesgsByChatId(Long chatId);
}
