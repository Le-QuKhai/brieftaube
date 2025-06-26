package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.facade.api.ChatFacade;
import com.haw.se1lab.chatdata.facade.api.NachrichtFacade;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RestController

public class NachrichtFacadeImpl implements NachrichtFacade {
    @Autowired
    private NachrichtUseCase nachrichtUseCase;

    /**
     * @see NachrichtFacade
     */
    @Override
    public ResponseEntity<?> createNachricht(NachrichtErstellung nachrichtErstellung) {

        Nachricht n = nachrichtUseCase.createNachricht(nachrichtErstellung);
        if (n == null) {
            return ResponseEntity.badRequest().body("Chat doesn't exist or Message Sender not in Chat");
        }
        else {
            return ResponseEntity.ok(n);
        }
    }

    /**
     * @see NachrichtFacade
     */
    @Override
    public ResponseEntity<?> getNewMessagesByOneChat(Long chatId, Long lastMessageId) {
        List<Nachricht> messages = nachrichtUseCase.getNewMessages(chatId, lastMessageId);

        return ResponseEntity.ok(messages);
    }

    @Override
    public ResponseEntity<?> getNewMessagesByMultChats(List<Long> chatIds, List<Long> lastMessageIds) {
        Map<Long, List<Nachricht>> newMessages = new HashMap<>();

        if(chatIds.size() != lastMessageIds.size()) {
            return ResponseEntity.badRequest().body("ChatIds and LastMessageIds must have the same size");
        }

        for(int i = 0; i < chatIds.size(); i++) {
            newMessages.put(chatIds.get(i), nachrichtUseCase.getNewMessages(chatIds.get(i), lastMessageIds.get(i)));
        }

        return ResponseEntity.ok(newMessages);
    }

}