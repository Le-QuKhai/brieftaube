package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
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

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;

    /**
     * @see NachrichtUseCase
     */
    @Override
    public Nachricht createNachricht(NachrichtErstellung nachrichtErstellung) {
        Date date = new Date();
        Optional<Chat> chat = chatRepository.findById(nachrichtErstellung.getChatId());
        Optional<Benutzer> sender = benutzerRepository.findByBenutzerName(nachrichtErstellung.getSenderName());
        // Chat und Sender müssen existieren
        if (chat.isPresent() && sender.isPresent()) {
            Nachricht nachricht = new Nachricht(nachrichtErstellung.getNachricht(), date, sender.get());
            if (chat.get().getTeilnehmer().contains(nachricht.getSender())) {
                chat.get().addNachricht(nachricht);
                Nachricht saveNachricht = nachrichtRepository.save(nachricht);
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
    public boolean checkIfNachrichtExistsById(Long nachrichtId) {
        Optional<Integer> nachrichtOpt = nachrichtRepository.checkIfNachrichtExists(nachrichtId);
        return nachrichtOpt.isPresent() && nachrichtOpt.get() == 1;
    }
}
