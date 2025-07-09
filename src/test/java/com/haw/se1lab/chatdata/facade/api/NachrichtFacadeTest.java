package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.is;

/**
 * Testklasse für die Nachricht Facade.
 * Testet die Funktionen der Schnittstelle Nachricht Facade.
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class NachrichtFacadeTest {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;
    @Autowired
    private NachrichtRepository nachrichtRepository;

    private Benutzer benutzer1;
    private Benutzer benutzer2;

    private Chat chat1;

    @BeforeAll
    public void setUpAll() {
        benutzer1 = new Benutzer("user1", "1");
        benutzerRepository.save(benutzer1);
        benutzer2 = new Benutzer("user2", "2");
        benutzerRepository.save(benutzer2);

        chat1 = new Chat(benutzer1, benutzer2);
        chatRepository.save(chat1);
    }

    @AfterAll
    public void tearDownAll() {
        chatRepository.deleteAll();
        nachrichtRepository.deleteAll();
        benutzerRepository.deleteAll();
    }

    /**
     * Positivtest:
     * Testet das Erstellen einer Nachricht.
     * Erwartet OK als Response und dass der Inhalt der Nachricht korrekt ist.
     */
    @Test
    public void createNewNachrichtTest() {
        NachrichtErstellung nachrichtErstellung = new NachrichtErstellung(
                "Neue Nachricht", chat1.getId(), benutzer1.getBenutzerName());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(nachrichtErstellung)

                .when()
                .post("/api/message/create_new")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nachricht", is("Neue Nachricht"));
    }

    /**
     * Negativtest:
     * Testet das Erstellen einer Nachricht von einem Sender, der nicht existiert.
     * Erwartet BadRequest.
     */
    @Test
    public void createNewNachrichtFAILTestSenderNotExist() {
        NachrichtErstellung nachrichtErstellung = new NachrichtErstellung(
                "Eune Nochrecht", chat1.getId(), "haiopai");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(nachrichtErstellung)

                .when()
                .post("/api/message/create_new")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Negativtest:
     * Testet das Erstellen von einer Nachricht in einem Chat, der nicht existiert.
     * Erwartet BadRequest.
     */
    @Test
    public void createNewNachrichtFAILTestChatNotExist() {
        NachrichtErstellung nachrichtErstellung = new NachrichtErstellung(
                "un message", 1234L, benutzer2.getBenutzerName());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(nachrichtErstellung)

                .when()
                .post("/api/message/create_new")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
