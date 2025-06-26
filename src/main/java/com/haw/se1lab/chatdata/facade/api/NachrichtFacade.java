package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/message", consumes = "application/json;charset=UTF-8")
public interface NachrichtFacade {

    /**
     * Erstellt eine Nachricht, in den übergebenen Chat
     * @param nachrichtErstellung enthält die Nachricht, die erstellt werden soll und
     * die ChatID, des Chats, in den die Nachricht geschrieben worden ist
     * @return die erstellte Nachricht
     */
    @PostMapping("/create_new")
    @ResponseStatus(HttpStatus.OK) // defines the HTTP status of the returned HTTP response
    ResponseEntity<?> createNachricht(@RequestBody NachrichtErstellung nachrichtErstellung);


    /**
     * Gibt alle neuen Nachrichten zurück, die seit der letzten Nachricht geschrieben wurden.
     * @param chatId die Id des Chats
     * @param lastMessageId die Id der letzten Nachricht
     * @return Liste von Nachrichten, leere Liste, wenn es keine neuen Nachrichten gibt.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getNewMessagesByOneChat(@RequestParam Long chatId, @RequestParam Long lastMessageId);

    @GetMapping("/get_new")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getNewMessagesByMultChats(@RequestBody List<Long> chatIds, @RequestBody List<Long> lastMessageIds);

}
