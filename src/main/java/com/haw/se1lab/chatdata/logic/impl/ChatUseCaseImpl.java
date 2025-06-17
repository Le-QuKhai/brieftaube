package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ChatUseCaseImpl implements ChatUseCase {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public void createChat(Chat chat)
    {
        Assert.notNull(chat, "chat must not be null");
        chatRepository.save(chat);
    }

    @Override
    public void addParticipant(Chat chat, Benutzer teilnehmer)
    {
        Assert.notNull(chat, "Chat must not be null");
        Assert.notNull(teilnehmer, "Teilnehmer must not be null");
        chat.addTeilnehmer((Benutzer) teilnehmer);
        chatRepository.save(chat);

    }

    @Override
    public boolean checkIfParticipantExists(Chat chat, Benutzer teilnehmer) {
        return chatRepository.findParticipantInChat(chat.getId(), teilnehmer.getId()).isPresent();
    }

    @Override
    public boolean checkIfChatExists(Chat chat) {
        // TODO test if query returns 1 or 0
        return chatRepository.findChat(chat.getId()).get() == 1;
    }
}
