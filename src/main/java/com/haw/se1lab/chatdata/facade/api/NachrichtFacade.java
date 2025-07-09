package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/message")
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

}
