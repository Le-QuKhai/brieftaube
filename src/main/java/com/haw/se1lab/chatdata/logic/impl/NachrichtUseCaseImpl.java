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
    private ChatUseCase chatUseCase;


    @Override
    public boolean createNachricht(Nachricht nachricht, Chat chat) {
        if (chatUseCase.checkIfChatExists(chat)
               && chat.getTeilnehmer().contains(nachricht.getSender())) {
            nachrichtRepository.save(nachricht);
            chat.addNachricht(nachricht);
            return true;
        }
        else {
            return false;
        }
    }
}
