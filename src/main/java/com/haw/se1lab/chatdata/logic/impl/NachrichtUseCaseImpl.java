package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NachrichtUseCaseImpl implements NachrichtUseCase {

    @Autowired
    private NachrichtRepository nachrichtRepository;

    // TODO: ist das erlaubt bzw. gibt es etwas besseres?
    // wird verwendet, um zu prüfen, ob der Chat schon existiert.
    @Autowired
    private ChatUseCase chatUseCase;


    @Override
    public Nachricht createNachricht(Nachricht nachricht, Chat chat) {
        // Nachricht darf noch nicht existieren | Chat muss schon existieren
        if (!checkIfNachrichtExists(nachricht) && chatUseCase.checkIfChatExists(chat)
               && chat.getTeilnehmer().contains(nachricht.getSender())) {
            chat.addNachricht(nachricht);
            return nachrichtRepository.save(nachricht);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean checkIfNachrichtExists(Nachricht nachricht) {
        return (nachrichtRepository.checkIfNachrichtExists(nachricht.getId()).get() == 1);
    }

}
