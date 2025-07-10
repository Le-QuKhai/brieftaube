package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.common.api.datatype.ChatStatus;
import com.haw.se1lab.chatdata.common.api.datatype.UpdateChatsFormular;
import com.haw.se1lab.chatdata.common.api.exception.ParticipantAlreadyExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.facade.api.ChatFacade;
import com.haw.se1lab.chatdata.facade.api.NachrichtFacade;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementierungsklasse für ChatFacade
 */
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
        if (chats == null) {
            return ResponseEntity.badRequest().body("Chats could not be found");
        }
        return ResponseEntity.ok(chats); // return empty list if user has no chats
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getAllMesgsByChatId(Long chatId) {
        List<Nachricht> nachrichten = chatUseCase.getAllMesgsByChatId(chatId);
        if (nachrichten != null) {
            return ResponseEntity.ok(nachrichten);
        }
        return ResponseEntity.badRequest().body("Chat not found");
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getChat(Long chatId) {
        Chat chat = chatUseCase.getChat(chatId);
        return ResponseEntity.ok(chat == null ? HttpStatus.BAD_REQUEST : chat); // null, kein Chat mit der Id existiert.
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getNewMessagesByOneChat(Long chatId, Long lastMessageId) {
        List<Nachricht> messages = chatUseCase.getNewMessages(chatId, lastMessageId);

        return ResponseEntity.ok(messages);
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getNewMessagesByMultChats(UpdateChatsFormular updateChatFormular) {
        Map<Long, List<Nachricht>> newMessages = new HashMap<>();

        List<Long> chatIds = updateChatFormular.getChatIds();
        List<Long> lastMessageIds = updateChatFormular.getLastNachrichtIds();

        if(chatIds.size() != lastMessageIds.size()) {
            return ResponseEntity.badRequest().body("ChatIds and LastMessageIds must have the same size");
        }

        for(int i = 0; i < chatIds.size(); i++) {
            newMessages.put(chatIds.get(i), chatUseCase.getNewMessages(chatIds.get(i), lastMessageIds.get(i)));
        }

        return ResponseEntity.ok(newMessages);
    }

    /**
     * @see ChatFacade
     */
    @Override
    public ResponseEntity<?> getNewChats(ChatStatus  chatStatus) {
        List<Chat> newChats = chatUseCase.getNewChats(chatStatus.getChatIds(), chatStatus.getBenutzerName());
        if (newChats == null) {
            return ResponseEntity.badRequest().body("Chats or Message could not be found");
        }
        return ResponseEntity.ok(newChats);
    }

}