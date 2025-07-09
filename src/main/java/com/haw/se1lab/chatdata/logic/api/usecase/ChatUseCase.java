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

    /**
     * Gibt alle Chats zurück, die der Benutzer in der Datenbank hat, die nicht Teil der übergebenen Liste
     * von ChatIds ist.
     * @param chatIds - Liste von Ids von Chats, die nicht zurückgegeben werden sollen
     * @param userName - Benutzername des Benutzers
     * @return alle Chats, die der Benutzer hat, die nicht Teil der Liste von ChatIds ist.
     */
    List<Chat> getNewChats(List<Long> chatIds, String userName);

    /**
     * Gibt alle neuen Nachrichten zurück, die seit der letzten Nachricht geschrieben wurden.
     * @param chatId die Id des Chats
     * @param lastMessageId die Id der letzten Nachricht
     * @return Liste von Nachrichten, leere Liste, wenn es keine neuen Nachrichten gibt.
     */
    List<Nachricht> getNewMessages(Long chatId, Long lastMessageId);

    /**
     * Gibt alle Nachrichten aus einem Chat, anhand der ChatId, zurück.
     * @param chatId - Id des Chats
     * @return alle Nachrichten aus dem Chat
     */
    List<Nachricht> getAllMesgsByChatId(Long chatId);
}
