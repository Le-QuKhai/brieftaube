package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.facade.api.NachrichtFacade;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class NachrichtFacadeImpl implements NachrichtFacade {
    @Autowired
    private NachrichtUseCase nachrichtUseCase;

    @Override
    public ResponseEntity<?> createNachricht(Nachricht nachricht, Chat chat) {
        if (nachrichtUseCase.createNachricht(nachricht, chat)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
