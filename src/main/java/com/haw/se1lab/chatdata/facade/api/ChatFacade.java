package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.common.api.datatype.ChatStatus;
import com.haw.se1lab.chatdata.common.api.datatype.UpdateChatsFormular;
import com.haw.se1lab.chatdata.common.api.exception.ParticipantAlreadyExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Die Rest-Schnittstelle, um von außen, mit dem Server zu kommunizieren.
 * Hierüber werden alle Sachen geregelt die mit Chats zutun haben.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api/chat")
public interface ChatFacade
{
    /**
     * Fügt einen Benutzer zu diesem Chat hinzu
     * @param chat der Chat zu dem der Benutzer hinzugefügt werden soll
     * @param teilnehmer der Benutzer der zu dem Chat hinzugefügt werden soll
     * @throws ParticipantAlreadyExistsException wenn der Benutzer schon im Chat existiert.
     */
    @PostMapping("/add")
   void addParticipant(@RequestBody Chat chat, @RequestBody Benutzer teilnehmer) throws ParticipantAlreadyExistsException;

    /**
     * Erstellt einen Chat und speichert ihn in der Datenbank
     * @param chatErstellung enthaelt den Benutzer, der den Chat erstellt
     * und den Teilnehmer, mit dem der Benutzer einen Chat eröffnet
     * @return den erstellten Chat.
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createChat(@RequestBody ChatErstellung chatErstellung);

    /**
     * Gibt alle Chats zurück, in denen der übergebene User drinnen ist.
     * @param benutzerName der Benutzer, für den man alle Chats holen soll
     * @return Liste von Chats, leere Liste, wenn er ihn keinen drinnen ist.
     */
    @PostMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllChatsByUser(@RequestBody String benutzerName);

    /**
     * Gibt alle Nachrichten des Chats anhand der ChatId aus.
     * @param chatId - ID des Chats
     * @return ResponseEntity ok, wenn alles gut läuft und alle Nachrichten im Chat.
     *                        badRequest, wenn ein Fehler vorkommt.
     */
    @GetMapping("/get_msgs")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllMesgsByChatId(@RequestParam Long chatId);

    /**
     * Gibt alle neuen Nachrichten zurück, die seit der letzten Nachricht geschrieben wurden.
     * @param chatId die Id des Chats
     * @param lastMessageId die Id der letzten Nachricht
     * @return Liste von Nachrichten, leere Liste, wenn es keine neuen Nachrichten gibt.
     */
    @GetMapping("/get_new_msgs_by_chat")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getNewMessagesByOneChat(@RequestParam Long chatId, @RequestParam Long lastMessageId);

    /**
     * Gibt den Chat mit der übergebenen Id zurück
     * @param chatId die Id von den Chat
     * @return der Chat mit der Id
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getChat(@RequestParam Long chatId);

    /**
     * Gibt alle neuen Nachrichten pro Chat zurück, anhand einer letzten Nachricht
     * @param updateChatFormular - Datentyp für das Updaten der Chats
     * @return ResponseEntity ok, wenn erfolgreich oder badRequest bei Fehler.
     */
    @PostMapping("/update_chats")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getNewMessagesByMultChats(@RequestBody UpdateChatsFormular updateChatFormular);

    /**
     * Gibt alle neuen Chats zurück, die der benutzer anhand seines ChatStatus, noch nicht hat.
     * @param chatStatus - Datentyp, enthält den Benutzernamen und die ChatIds, die der Benutzer bereits sieht.
     * @return ResponseEntity ok, wenn alles gut läuft und die neuen Chats (falls vorhanden)
     *                        badRequest, wenn ein Fehler geschieht.
     */
    @PostMapping("/get_new")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getNewChats(@RequestBody ChatStatus chatStatus);
}
