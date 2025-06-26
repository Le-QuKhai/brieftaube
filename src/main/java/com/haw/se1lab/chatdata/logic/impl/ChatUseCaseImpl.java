package com.haw.se1lab.chatdata.logic.impl;

import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ChatUseCaseImpl implements ChatUseCase {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    /**
     * @see ChatUseCase
     */
    @Override
    public Chat createChat(ChatErstellung chatErstellung)
    {
        Optional<Benutzer> benutzer = benutzerRepository.findByBenutzerName(chatErstellung.getBenutzerName());
        Optional<Benutzer> teilnehmer = benutzerRepository.findByBenutzerName(chatErstellung.getTeilnehmerName());
        if (benutzer.isEmpty() || teilnehmer.isEmpty()) {
            return null;
        }
        else if (benutzer.equals(teilnehmer)) {
            return null;
        }
        else {
            Chat chat = new Chat(benutzer.get(), teilnehmer.get());
            return chatRepository.save(chat);
        }
    }

    /**
     * @see ChatUseCase
     */
    @Override
    public void addParticipant(Chat chat, Benutzer teilnehmer)
    {
        Assert.notNull(chat, "Chat must not be null");
        Assert.notNull(teilnehmer, "Teilnehmer must not be null");
        chat.addTeilnehmer(teilnehmer);
        chatRepository.save(chat);

    }

    /**
     * @see ChatUseCase
     */
    @Override
    public boolean checkIfParticipantExists(Chat chat, Benutzer teilnehmer) {
        return chatRepository.findParticipantInChat(chat.getId(), teilnehmer.getId()).isPresent();
    }

    /**
     * @see ChatUseCase
     */
    @Override
    public boolean checkIfChatExists(Chat chat) {
        // TODO test if query returns 1 or 0
        return chatRepository.checkIfChatExists(chat.getId()).get() == 1;
    }

    /**
     * @see ChatUseCase
     */
    @Override
    public List<Chat> getAllChatsByUser(String userName) {
        Optional<Benutzer> user = benutzerRepository.findByBenutzerName(userName);
        if (user.isPresent()) {
            Optional<List<Chat>> chats = chatRepository.findMyChats(user.get().getId());

            return chats.orElse(List.of());
        } else {
            return List.of();
        }
    }

    /**
     * @see ChatUseCase
     */
    @Override
    public Chat getChat(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }

    @Override
    public List<Chat> getNewChats(List<Long> chatIds, String userName) {

        List<Chat> chats = getAllChatsByUser(userName);
        chats.removeIf(chat -> chatIds.contains(chat.getId()));
        return chats;

    }

}
