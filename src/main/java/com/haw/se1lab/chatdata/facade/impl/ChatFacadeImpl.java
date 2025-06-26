package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.common.api.datatype.ChatStatus;
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
    public ResponseEntity<?> createChat(ChatErstellung chatErstellung) {
        Chat chat1 = chatUseCase.createChat(chatErstellung);
        if (chat1 == null) {
            return ResponseEntity.badRequest().body("Chat could not be created. User doesn't exist.");
        } else {
            return ResponseEntity.ok(chat1);
        }
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getAllChatsByUser(String benutzerName) {
        if (benutzerName == null || benutzerName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Benutzername must not be null or empty");
        }

        List<Chat> chats = chatUseCase.getAllChatsByUser(benutzerName);
        return ResponseEntity.ok(chats); // return empty list if user has no chats
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
    public ResponseEntity<?> getNewChats(ChatStatus  chatStatus) {
        List<Chat> newChats = chatUseCase.getNewChats(chatStatus.getChatIds(), chatStatus.getBenutzerName());
        return ResponseEntity.ok(newChats);
    }


}
