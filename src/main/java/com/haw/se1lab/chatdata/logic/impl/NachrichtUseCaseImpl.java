package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.common.api.exception.ChatDontExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NachrichtUseCaseImpl implements NachrichtUseCase {

    @Autowired
    private NachrichtRepository nachrichtRepository;

    // TODO: ist das erlaubt bzw. gibt es etwas besseres / ja ist erlaubt?
    // wird verwendet, um zu prüfen, ob der Chat schon existiert.
    @Autowired
    private ChatUseCase chatUseCase;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;


    /**
     * @see NachrichtUseCase
     */
    @Override
    public Nachricht createNachricht(NachrichtErstellung nachrichtErstellung) {
        // Nachricht darf noch nicht existieren | Chat muss schon existieren
        Date date = new Date();
        Optional<Chat> chat = chatRepository.findById(nachrichtErstellung.getChatID());
        Optional<Benutzer> sender = benutzerRepository.findByBenutzerName(nachrichtErstellung.getSenderName());
        if (sender.isPresent()) {
            Nachricht nachricht = new Nachricht(nachrichtErstellung.getNachricht(), date, sender.get());
            if (chat.isPresent()
                    && chat.get().getTeilnehmer().contains(nachricht.getSender())) {
                chat.get().addNachricht(nachricht);
                Nachricht saveNachricht =  nachrichtRepository.save(nachricht);
                chatRepository.save(chat.get());
                return saveNachricht;
            }
        }
        return null;

    }

    /**
     * @see NachrichtUseCase
     */
    @Override
    public boolean checkIfNachrichtExists(Nachricht nachricht) {
        Optional<Integer> nachrichte = nachrichtRepository.checkIfNachrichtExists(nachricht.getId());
        return nachrichte.isPresent() && nachrichte.get() == 1;
    }

    /**
     * @see NachrichtUseCase
     */
    public List<Nachricht> getNewMessages(Long chatId, Long lastMessageId) {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        Chat chat;
        if(optionalChat.isPresent()) {
            chat = optionalChat.get();
        }
        else{
            throw new ChatDontExistsException("Chat with id " + chatId + " doesn't exist");
        }

        List<Nachricht> messages = chat.getNachrichten();
        List<Nachricht> newMessages = new ArrayList<>();

        if(nachrichtRepository.checkIfNachrichtExists(lastMessageId).get() == 0) {
            newMessages = messages;
        }
        else {
            for(int i = messages.size() - 1; i >= 0; i--) {
                Nachricht message = messages.get(i);
                if(Objects.equals(message.getId(), lastMessageId)) {
                    break;
                }
                newMessages.add(message);
            }
        }
        return newMessages;

    }

}
