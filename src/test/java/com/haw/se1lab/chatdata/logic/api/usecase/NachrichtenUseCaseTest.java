package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class NachrichtenUseCaseTest {

    @Autowired
    private NachrichtUseCase nachrichtUseCase;
    @Autowired
    private NachrichtRepository nachrichtRepository;
    @Autowired
    private ChatRepository chatRepository;
    private Chat chat;
    private Benutzer user;
    private Benutzer user2;
    @Autowired
    private BenutzerRepository benutzerRepository;

    @BeforeAll
    public void setUpAll() {
        user = new Benutzer("user1", "1");
        benutzerRepository.save(user);
        user2 = new Benutzer("user2", "2");
        benutzerRepository.save(user2);
        chat = new Chat(user, user2);
        chatRepository.save(chat);
    }

    @AfterAll
    public void tearDownAll() {
        chatRepository.deleteAll();
        nachrichtRepository.deleteAll();
        benutzerRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createNachricht_Success() {
        NachrichtErstellung nachrichtErstellung = new NachrichtErstellung(
                "Hallo", chat.getId(), user.getBenutzerName());
        Nachricht savedNachricht = nachrichtUseCase.createNachricht(nachrichtErstellung);

        assertNotNull(savedNachricht);
        Optional<List<Nachricht>> nachrichtenOptional = nachrichtRepository.findBySender(user);
        assertTrue(nachrichtenOptional.isPresent());
        assertEquals(1, nachrichtenOptional.get().size());
        assertEquals("Hallo", nachrichtenOptional.get().get(0).getNachricht());

        Chat savedChat = chatRepository.findById(chat.getId()).get();
        assertEquals(1, savedChat.getNachrichten().size());
        assertEquals(savedNachricht.getId(), savedChat.getNachrichten().get(0).getId());
    }

    @Test
    @Transactional
    public void getNewMessagesTest() {
        NachrichtErstellung nachrichtErstellung1 = new NachrichtErstellung(
                "Hallo", chat.getId(), user.getBenutzerName());
        Nachricht savedNachricht1 = nachrichtUseCase.createNachricht(nachrichtErstellung1);

        NachrichtErstellung nachrichtErstellung2 = new NachrichtErstellung(
                "Hey", chat.getId(), user2.getBenutzerName());
        Nachricht savedNachricht2 = nachrichtUseCase.createNachricht(nachrichtErstellung2);

        List<Nachricht> newMessages = nachrichtUseCase.getNewMessages(chat.getId(), savedNachricht1.getId());

        assertEquals(1, newMessages.size());
        assertEquals(savedNachricht2.getId(), newMessages.get(0).getId());

    }

}
