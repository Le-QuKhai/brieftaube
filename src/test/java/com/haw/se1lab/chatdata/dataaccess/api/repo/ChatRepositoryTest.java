package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class) // required to use Spring TestContext Framework in JUnit 5
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ChatRepositoryTest
{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private BenutzerRepository professorRepository;

    private Chat chat;

    private Benutzer admin;

    private Benutzer admin2;

    @BeforeAll
    public void setUpAll()
    {
        admin = new Benutzer();
        professorRepository.save(admin);

        admin2 = new Benutzer();
        professorRepository.save(admin2);

        chat = new Chat(admin);
        chat.addTeilnehmer(admin2);
        chatRepository.save(chat);
    }

    @AfterAll
    public void tearDownAll()
    {
        chatRepository.deleteAll();
        professorRepository.deleteAll();
    }

    @Test
    public void findParticipantInChat_Success()
    {
        assertTrue(chatRepository.findParticipantInChat(chat.getId(), admin.getId()).isPresent());
    }

    @Test
    public void findParticipantInChat_Failure()
    {
        assertFalse(chatRepository.findParticipantInChat(chat.getId(), 3L).isPresent());
    }
}
