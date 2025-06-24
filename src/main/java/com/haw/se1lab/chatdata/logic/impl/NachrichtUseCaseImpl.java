package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.common.api.exception.ChatDontExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


    /**
     * @see NachrichtUseCase
     */
    @Override
    public Nachricht createNachricht(Nachricht nachricht, Chat chat) {
        // Nachricht darf noch nicht existieren | Chat muss schon existieren
        if (!checkIfNachrichtExists(nachricht) && chatUseCase.checkIfChatExists(chat)
               && chat.getTeilnehmer().contains(nachricht.getSender())) {
            chat.addNachricht(nachricht);
            Nachricht saveNachricht =  nachrichtRepository.save(nachricht);
            chatRepository.save(chat);
            return saveNachricht;
        }
        else {
            return null;
        }
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
