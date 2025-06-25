package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ChatUseCaseTest
{
    @Autowired
    private ChatUseCase chatUseCase;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    private Chat chat;

    private Benutzer admin;

    private Benutzer admin2;

    @BeforeAll
    public void setUpAll()
    {
        admin = new Benutzer();
        benutzerRepository.save(admin);

        admin2 = new Benutzer();
        benutzerRepository.save(admin2);

        chat = new Chat(admin, admin);
        chatRepository.save(chat);
    }

    @AfterAll
    public void tearDownAll()
    {
        chatRepository.deleteAll();
        benutzerRepository.deleteAll();
    }

    @Test
    public void addParticipant_Success()
    {
        chatUseCase.addParticipant(chat, admin2);
        assertTrue(chatRepository.findParticipantInChat(chat.getId(), admin2.getId()).isPresent());
    }

    @Test
    public void addParticipant_Failure()
    {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> chatUseCase.addParticipant(chat, null));

    }
}
