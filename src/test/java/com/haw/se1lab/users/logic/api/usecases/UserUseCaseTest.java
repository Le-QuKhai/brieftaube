package com.haw.se1lab.users.logic.api.usecases;

import com.haw.se1lab.Application;
import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserUseCaseTest {

    @Autowired
    private UserUseCase userUseCase;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @BeforeAll
    public void setUp() {
        Benutzer benutzer = new Benutzer("test1", "test");
        benutzerRepository.save(benutzer);
    }

    @AfterAll
    public void tearDown() {
        benutzerRepository.deleteAll();
    }

    @Test
    public void createUserTest() {
        RegestrierungsFormular benutzer = new RegestrierungsFormular("test2", "test", "test");
        try {
            userUseCase.createUser(benutzer);
        } catch (RegestrierungsFormularException ignored) {
        }
        assertTrue(benutzerRepository.findByBenutzerName(benutzer.getBenutzerName()).isPresent());
        assertEquals(2, benutzerRepository.findAll().size());
    }

    @Test
    public void createUserWithEmptyUsernameTest() {
        RegestrierungsFormular benutzer = new RegestrierungsFormular("", "test", "test");
        assertThrows(RegestrierungsFormularException.class, () -> {userUseCase.createUser(benutzer);});

    }

    @Test
    public void createUserAlreadyExistTest() {
        RegestrierungsFormular benutzer = new RegestrierungsFormular("test1", "test", "test");
        assertThrows(RegestrierungsFormularException.class, () -> userUseCase.createUser(benutzer));
    }

    @Test
    public void createUserPasswordsDontMatchTest() {
        RegestrierungsFormular benutzer = new RegestrierungsFormular("test", "test1", "test");
        assertThrows(RegestrierungsFormularException.class, () -> userUseCase.createUser(benutzer));
    }

    @Test
    public void loginTest() {
        Benutzer benutzer = new Benutzer("test1", "test");
        try {
            benutzer = userUseCase.loginBenutzer(benutzer);
        } catch (IncorrectPasswordException | UserDoesntExistsException ignored) {
        }
        assertEquals("test1", benutzer.getBenutzerName());
        assertEquals("test", benutzer.getPassword());
    }

    @Test
    public void loginWrongPasswordTest() {
        Benutzer benutzer = new Benutzer("test1", "test2");
        assertThrows(IncorrectPasswordException.class, () -> userUseCase.loginBenutzer(benutzer));
    }

    @Test
    public void loginWrongUserNameTest() {
        Benutzer benutzer = new Benutzer("test", "test");
        assertThrows(UserDoesntExistsException.class, () -> userUseCase.loginBenutzer(benutzer));
    }
}
