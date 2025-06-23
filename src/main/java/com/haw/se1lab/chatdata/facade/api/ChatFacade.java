package com.haw.se1lab.chatdata.facade.api;


import com.haw.se1lab.chatdata.common.api.exception.ParticipantAlreadyExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Die Rest-Schnittstelle, um von außen, mit dem Server zu kommunizieren.
 * Hierüber werden alle Sachen geregelt die mit Chats zutun haben.
 */
@RequestMapping(path="/api/chat")
public interface ChatFacade
{
    /**
     * Fügt einen Benutzer zu diesem Chat hinzu
     * @param chat der Chat zu dem der Benutzer hinzugefügt werden soll
     * @param teilnehmer der Benutzer der zu dem Chat hinzugefügt werden soll
     * @return der hinzugefügt Benutzer
     * @throws ParticipantAlreadyExistsException wenn der Benutzer schon im Chat existiert.
     */
    @PostMapping("/add")
   void addParticipant(@RequestBody Chat chat, @RequestBody Benutzer teilnehmer) throws ParticipantAlreadyExistsException;


    /**
     * Erstellt einen Chat und speichert ihn in der Datenbank
     * @param chat der Chat, der erstellt werden soll
     * @return den erstellten Chat.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createChat(@RequestBody Chat chat);

    /**
     * Gibt alle Chats zurück, in denen der übergebene User drinnen ist.
     * @param benutzer der Benutzer, für den man alle Chats holen soll
     * @return Liste von Chats, leere Liste, wenn er ihn keinen drinnen ist.
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllChatsByUser(@RequestBody Benutzer benutzer);

    /**
     * Gibt den Chat mit der übergebenen Id zurück
     * @param chatId die Id von den Chat
     * @return der Chat mit der Id
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getChat(@RequestParam Long chatId);


}
