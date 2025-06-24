package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/message")
public interface NachrichtFacade {

    /**
     * Erstellt eine Nachricht, in den übergebenen Chat
     * @param nachricht die Nachricht, die erstellt werden soll
     * @param chat der Chat, in den die Nachricht geschrieben worden ist
     * @return die erstellte Nachricht
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createNachricht(@RequestBody Nachricht nachricht, Chat chat);


    /**
     * Gibt alle neuen Nachrichten zurück, die seit der letzten Nachricht geschrieben wurden.
     * @param chatId die Id des Chats
     * @param lastMessageId die Id der letzten Nachricht
     * @return Liste von Nachrichten, leere Liste, wenn es keine neuen Nachrichten gibt.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getNewMessages(@RequestParam Long chatId, @RequestParam Long lastMessageId);

}
