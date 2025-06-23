package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class ChatUseCaseImpl implements ChatUseCase {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat)
    {
        Assert.notNull(chat, "chat must not be null");
        if (checkIfChatExists(chat)) {
            return null;
        }
        else {
            return chatRepository.save(chat);
        }
    }

    @Override
    public void addParticipant(Chat chat, Benutzer teilnehmer)
    {
        Assert.notNull(chat, "Chat must not be null");
        Assert.notNull(teilnehmer, "Teilnehmer must not be null");
        chat.addTeilnehmer(teilnehmer);
        chatRepository.save(chat);

    }

    @Override
    public boolean checkIfParticipantExists(Chat chat, Benutzer teilnehmer) {
        return chatRepository.findParticipantInChat(chat.getId(), teilnehmer.getId()).isPresent();
    }

    @Override
    public boolean checkIfChatExists(Chat chat) {
        // TODO test if query returns 1 or 0
        return chatRepository.checkIfChatExists(chat.getId()).get() == 1;
    }

    @Override
    public List<Chat> getAllChatsByUser(Benutzer benutzer) {
        Optional<List<Chat>> chats = chatRepository.findMyChats(benutzer.getId());

        return chats.orElse(List.of());
    }

    @Override
    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }

}
