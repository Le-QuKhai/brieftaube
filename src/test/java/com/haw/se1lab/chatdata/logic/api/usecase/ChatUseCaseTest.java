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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testklasse für ChatUseCase
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ChatUseCaseTest
{
    @Autowired
    private ChatUseCase chatUseCase;
    @Autowired
    private NachrichtUseCase nachrichtUseCase;

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;
    @Autowired
    private NachrichtRepository nachrichtRepository;

    private Chat chat;
    private Benutzer admin1;
    private Benutzer admin2;
    private Benutzer admin3;


    @BeforeAll
    public void setUpAll() {
        admin1 = new Benutzer("Billy", "pw");
        benutzerRepository.save(admin1);

        admin2 = new Benutzer("Hans", "pw");
        benutzerRepository.save(admin2);

        admin3 = new Benutzer("Jochen", "pw");
        benutzerRepository.save(admin3);

        chat = new Chat(admin1, admin2);
        chatRepository.save(chat);
    }

    @AfterAll
    public void tearDownAll() {
        chatRepository.deleteAll();
        nachrichtRepository.deleteAll();
        benutzerRepository.deleteAll();
    }

    /**
     * Positivtest: Testet das Hinzufügen eines Teilnehmers zu einem Chat.
     */
    @Test
    public void addParticipant_Success() {
        chatUseCase.addParticipant(chat, admin3);
        assertTrue(chatRepository.findParticipantInChat(chat.getId(), admin3.getId()).isPresent());
    }

    /**
     * Negativtest: Testet das Hinzufügen von "null" als Teilnehmer zu einem Chat.
     */
    @Test
    public void addParticipant_Failure() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> chatUseCase.addParticipant(chat, null));
    }

    /**
     * Positivtest: Testet das Suchen von Neuen Nachrichten, ab einer bestimmten Nachricht.
     */
    @Test
    @Transactional
    public void getNewMessagesTest() {
        NachrichtErstellung nachrichtErstellung1 = new NachrichtErstellung(
                "Hallo", chat.getId(), admin1.getBenutzerName());
        Nachricht savedNachricht1 = nachrichtUseCase.createNachricht(nachrichtErstellung1);

        NachrichtErstellung nachrichtErstellung2 = new NachrichtErstellung(
                "Hey", chat.getId(), admin2.getBenutzerName());
        Nachricht savedNachricht2 = nachrichtUseCase.createNachricht(nachrichtErstellung2);

        List<Nachricht> newMessages = chatUseCase.getNewMessages(chat.getId(), savedNachricht1.getId());

        assertEquals(1, newMessages.size());
        assertEquals(savedNachricht2.getId(), newMessages.get(0).getId());
    }

}
