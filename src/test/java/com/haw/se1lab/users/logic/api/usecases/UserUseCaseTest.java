package com.haw.se1lab.users.logic.api.usecases;

import com.haw.se1lab.Application;
import com.haw.se1lab.users.common.api.datatype.RegistrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegistrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class UserUseCaseTest {

    @Autowired
    private UserUseCase userUseCase;

    @Autowired
    private BenutzerRepository benutzerRepository;

    // --- SETUP AND TEARDOWN ---
    // @BeforeEach runs before every single test
    @BeforeEach
    public void setUp() {
        // This ensures every test starts with a clean slate and one known user
        benutzerRepository.deleteAll();
        Benutzer benutzer = new Benutzer("test1", "test");
        benutzerRepository.save(benutzer);
    }

    // @AfterEach is good practice but optional here since @BeforeEach cleans up anyway
    @AfterEach
    public void tearDown() {
        benutzerRepository.deleteAll();
    }


    // --- YOUR TESTS ---
    @Test
    public void createUserTest() throws RegistrierungsFormularException {
        // Setup: DB has one user ("test1")
        RegistrierungsFormular newBenutzerForm = new RegistrierungsFormular(
                "test2", "test", "test");

        // Action
        userUseCase.createUser(newBenutzerForm);

        // Assertion
        assertTrue(benutzerRepository.findByBenutzerName("test2").isPresent());
        // We expect 2 users now: the one from setUp() and the one we just created.
        assertEquals(2, benutzerRepository.findAll().size());
    }

    @Test
    public void createUserWithEmptyUsernameTest() {
        RegistrierungsFormular benutzer = new RegistrierungsFormular(
                "", "test", "test");
        assertThrows(RegistrierungsFormularException.class, () -> userUseCase.createUser(benutzer));
    }

    @Test
    public void createUserAlreadyExistTest() {
        // The user "test1" was created in the setUp() method.
        RegistrierungsFormular benutzer = new RegistrierungsFormular("test1", "test", "test");
        assertThrows(RegistrierungsFormularException.class, () -> userUseCase.createUser(benutzer));
    }

    @Test
    public void createUserPasswordsDontMatchTest() {
        RegistrierungsFormular benutzer = new RegistrierungsFormular("test", "test1", "test");
        assertThrows(RegistrierungsFormularException.class, () -> userUseCase.createUser(benutzer));
    }

    @Test
    public void loginTest() throws IncorrectPasswordException, UserDoesntExistsException {
        // The user "test1" with password "test" was created in setUp()
        Benutzer loginAttempt = new Benutzer("test1", "test");

        Benutzer loggedInBenutzer = userUseCase.loginBenutzer(loginAttempt);

        assertEquals("test1", loggedInBenutzer.getBenutzerName());
        assertEquals("test", loggedInBenutzer.getPassword());
    }

    @Test
    public void loginWrongPasswordTest() {
        // The user "test1" exists, but the password is wrong
        Benutzer benutzer = new Benutzer("test1", "test2");
        assertThrows(IncorrectPasswordException.class, () -> userUseCase.loginBenutzer(benutzer));
    }

    @Test
    public void loginWrongUserNameTest() {
        Benutzer benutzer = new Benutzer("non-existent-user", "test");
        assertThrows(UserDoesntExistsException.class, () -> userUseCase.loginBenutzer(benutzer));
    }
}