package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
