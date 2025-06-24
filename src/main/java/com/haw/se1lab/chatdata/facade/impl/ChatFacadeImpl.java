package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.common.api.exception.ParticipantAlreadyExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.facade.api.ChatFacade;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
public class ChatFacadeImpl implements ChatFacade {

    @Autowired
    private ChatUseCase chatUseCase;

    private final Log log = LogFactory.getLog(getClass());

    /**
     * @see ChatFacade
     */
    @Override
    public void addParticipant(Chat chat, Benutzer teilnehmer) throws ParticipantAlreadyExistsException
    {
        log.info("Starte addParticipant");

        if(chatUseCase.checkIfParticipantExists(chat, teilnehmer))
        {
            throw new ParticipantAlreadyExistsException("Participant already exists");
        }

        chatUseCase.addParticipant(chat, teilnehmer);

        log.info("Participant added to Chat: " + chat.getId() + " with User: " + teilnehmer.getId());

    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> createChat(Chat chat) {
        if (chatUseCase.checkIfChatExists(chat)) {
            return ResponseEntity.badRequest().body("Chat already exists");
        } else {
            Chat chat1 = chatUseCase.createChat(chat);
            return ResponseEntity.ok(chat1);
        }
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getAllChatsByUser(Benutzer benutzer) {
        if (benutzer == null) {
            return ResponseEntity.badRequest().body("Benutzer must not be null");
        } else {
            return ResponseEntity.ok(chatUseCase.getAllChatsByUser(benutzer.getId()));
        }
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getChat(Long chatId) {
        Chat chat = chatUseCase.getChat(chatId);
        return ResponseEntity.ok(chat == null ? HttpStatus.BAD_REQUEST : chat); // null, kein Chat mit der Id existiert.
    }

    @Override
    public ResponseEntity<?> getNewChats(List<Long> chatIds, Long userId) {
        List<Chat> newChats = chatUseCase.getNewChats(chatIds, userId);
        return ResponseEntity.ok(newChats);
    }


}
