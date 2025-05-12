package com.haw.se1lab.chatdata.facade.impl;

import com.haw.se1lab.chatdata.common.api.exception.ParticipantAlreadyExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.facade.api.ChatFacade;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.AbstractBenutzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class ChatFacadeImpl implements ChatFacade {

    @Autowired
    private ChatUseCase chatUseCase;

    private final Log log = LogFactory.getLog(getClass());

    @Override
    public void addParticipant(Chat chat, AbstractBenutzer teilnehmer) throws ParticipantAlreadyExistsException
    {
        log.info("Starte addParticipant");

        if(chatUseCase.checkIfParticipantExists(chat, teilnehmer))
        {
            throw new ParticipantAlreadyExistsException("Participant already exists");
        }

        chatUseCase.addParticipant(chat, teilnehmer);

        log.info("Starte addParticipant");

    }

    @Override
    public void createChat(Chat chat) {
        chatUseCase.createChat(chat);
    }
}
