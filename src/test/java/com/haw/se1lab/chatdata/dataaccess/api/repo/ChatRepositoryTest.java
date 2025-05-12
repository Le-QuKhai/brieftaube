package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.users.dataaccess.api.entity.Professor;
import com.haw.se1lab.users.dataaccess.api.repo.ProfessorRepository;
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
    private ProfessorRepository professorRepository;

    private Chat chat;

    private Professor professor;

    private Professor professor2;

    @BeforeAll
    public void setUpAll()
    {
        professor = new Professor("test", "test");
        professorRepository.save(professor);

        professor2 = new Professor("test2", "test2");
        professorRepository.save(professor2);

        chat = new Chat(professor);
        chat.addTeilnehmer(professor2);
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
        assertTrue(chatRepository.findParticipantInChat(chat.getId(), professor.getId()).isPresent());
    }

    @Test
    public void findParticipantInChat_Failure()
    {
        assertFalse(chatRepository.findParticipantInChat(chat.getId(), 3L).isPresent());
    }
}
