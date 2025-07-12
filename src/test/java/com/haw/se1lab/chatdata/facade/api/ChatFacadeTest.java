package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.common.api.datatype.ChatStatus;
import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.common.api.datatype.UpdateChatsFormular;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.chatdata.dataaccess.api.repo.NachrichtRepository;
import com.haw.se1lab.chatdata.logic.api.usecase.ChatUseCase;
import com.haw.se1lab.chatdata.logic.api.usecase.NachrichtUseCase;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testklasse für ChatFacade
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ChatFacadeTest {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;
    @Autowired
    private NachrichtRepository nachrichtRepository;

    private Benutzer user1;
    private Benutzer user2;
    private Benutzer user3;
    private Benutzer user4;
    Benutzer userTestC1;
    Benutzer userTestC2;
    private Chat chatTest;
    private Chat chat1;
    private Chat chat2;
    private Chat chat3;
    private Chat chat4;
    @Autowired
    private ChatUseCase chatUseCase;
    @Autowired
    private NachrichtUseCase nachrichtUseCase;


    @BeforeAll
    public void setUpAll() {
        user1 = new Benutzer("user1", "1");
        benutzerRepository.save(user1);
        user2 = new Benutzer("user2", "2");
        benutzerRepository.save(user2);
        user3 = new Benutzer("user3", "3");
        benutzerRepository.save(user3);
        user4 = new Benutzer("user4", "4");
        benutzerRepository.save(user4);

        userTestC1 = new Benutzer("userTestC1", "c1");
        userTestC2 = new Benutzer("userTestC2", "c2");
        benutzerRepository.save(userTestC1);
        benutzerRepository.save(userTestC2);

        chatTest = new Chat(userTestC1, userTestC2);
        chatRepository.save(chatTest);
        chat1 = new Chat(user1, user2);
        chatRepository.save(chat1);
        chat2 = new Chat(user1);
        chatRepository.save(chat2);
        chat3 = new Chat(user3);
        chatRepository.save(chat3);
        chat4 = new Chat(user2);
        chatRepository.save(chat4);
    }

    @AfterAll
    public void tearDown() {
        chatRepository.deleteAll();
        nachrichtRepository.deleteAll();
        benutzerRepository.deleteAll();
    }

    /**
     * Positivtest: Testet das Erstellen neuer Chats.
     * Erwartet: Status OK und dass der Chat zwei Teilnehmer hat.
     */
    @Test
    public void createNewChatTest() {
        ChatErstellung chatErstellung = new ChatErstellung(
                userTestC1.getBenutzerName(), userTestC2.getBenutzerName());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(chatErstellung)

                .when()
                .post("/api/chat/create")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("teilnehmer.size()", is(2));
    }

    /**
     * Positivtest: Testet das Finden aller Chats des Benutzers. Der Benutzer hat mehrere Chats.
     * Erwartet Status OK und die übergebenen Chats, mit der korrekten Anzahl Teilnehmer.
     */
    @Test
    public void findMyChatTest() {
        Response response = RestAssured.given()
                 .contentType(ContentType.JSON)
                 .body(user1.getBenutzerName())

                 .when()
                 .post("/api/chat/all")

                 .then()
                 .statusCode(HttpStatus.OK.value())
                .body("size()", is(2))
                .body("[0].teilnehmer.size()", is(2))
                .body("[1].teilnehmer.size()", is(1))
                 .extract().response();

        assertEquals(chat1.getId(), response.jsonPath().getLong("[0].id"));
        assertEquals(chat2.getId(), response.jsonPath().getLong("[1].id"));
     }

    /**
     * Positivtest: Testet das Finden aller Chats des Benutzers. Der Benutzer hat keine Chats.
     * Erwartet Status OK und das übergeben einer leeren Liste.
     */
    @Test
    public void findMyChatTestNoChats() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user4.getBenutzerName())

                .when()
                .post("/api/chat/all")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    /**
     * Negativtest: Testet das Finden aller Chats des Benutzers. Der Benutzer existiert nicht in der Datenbank.
     * Erwartet BadRequest.
     */
    @Test
    public void findMyChatUserDontExistsTest() {

        Benutzer user = new Benutzer("user5", "5");

         RestAssured.given()
                 .contentType(ContentType.JSON)
                 .body(user.getBenutzerName())

                 .when()
                 .post("/api/chat/all")

                 .then()
                 .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Positivtest: Testet das Finden eines Chats anhand der ChatID.
     * Erwartet Status OK und das der erhaltene Chat die Teilnehmer enthält.
     */
    @Test
    public void getChatByIdTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .queryParam("chatId", chat1.getId())

                .when()
                .get("api/chat")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("teilnehmer.size()", is(2))
                .extract().response();

        assertEquals(chat1.getId(), response.jsonPath().getLong("id"));
        assertEquals(chat1.getTeilnehmer().size(), response.jsonPath().getLong("teilnehmer.size()"));
    }

    /**
     * Positivtest: Testet das Erhalten von neuen Chats, die der Benutzer noch nicht sieht.
     * Erwartet Status OK und den erhaltenen Chat mit dem richtigen Teilnehmer.
     */
    @Test
    public void getNewChats() {
        List<Long> knownChats = new ArrayList<>(){{add(chat1.getId());}};
        ChatStatus chatStatus = new ChatStatus(user1.getBenutzerName(), knownChats);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(chatStatus)

                .when()
                .post("api/chat/get_new")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1))
                .body("[0].teilnehmer.size()", is(1))
                .extract().response();

        assertEquals(chat2.getId(), response.jsonPath().getLong("[0].id"));
        assertEquals(user1.getId(), response.jsonPath().getLong("[0].teilnehmer[0].id"));
    }

    /**
     * Positivtest: Testet das Erhalten von neuen Chats, die der Benutzer noch nicht sieht.
     * Es gibt keine neuen Chats.
     * Erwartet Status OK und keine erhaltenen Chats.
     */
    @Test
    public void getNewChatsNoNewChats() {
        List<Long> knownChats = new ArrayList<>(){{add(chat1.getId()); add(chat2.getId());}};
        ChatStatus chatStatus = new ChatStatus(user1.getBenutzerName(), knownChats);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(chatStatus)

                .when()
                .get("api/chat/get_new")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    /**
     * Positivtest: Testet das Updaten von Nachrichten in mehreren Chats.
     * Ein Chat hat eine neue Nachricht. Ein weiterer Chat hat keine neue Nachricht.
     */
    @Transactional
    @Test
    public void updateChatMessagesTest() {
        NachrichtErstellung nachrichtErstellung1 = new NachrichtErstellung(
                "Hallo1", chatTest.getId(), userTestC1.getBenutzerName());
        NachrichtErstellung nachrichtErstellung2 = new NachrichtErstellung(
                "Hey2", chatTest.getId(), userTestC2.getBenutzerName());
        NachrichtErstellung nachrichtErstellung3 = new NachrichtErstellung(
                "Moin3", chat1.getId(), user1.getBenutzerName());
        Nachricht nachricht1 = nachrichtUseCase.createNachricht(nachrichtErstellung1);
        Nachricht nachricht2 = nachrichtUseCase.createNachricht(nachrichtErstellung2);
        Nachricht nachricht3 = nachrichtUseCase.createNachricht(nachrichtErstellung3);

        List<Long> chatIds = new ArrayList<>() {{
            add(chatTest.getId());
            add(chat1.getId());
        }};
        List<Long> lastMsgIds = new ArrayList<>() {{
            add(nachricht1.getId());
            add(nachricht3.getId());
        }};
        UpdateChatsFormular updateChatsFormular = new UpdateChatsFormular(chatIds, lastMsgIds);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateChatsFormular)

                .when()
                .post("api/chat/update_chats")

                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        /*
        Map<Long, List<Nachricht>> responseMap = response.as(Map.class);
        assertEquals(nachricht2.getId(), responseMap.get(chatTest.getId()).get(0).getId());
        assertEquals(List.of(), responseMap.get(chat1.getId()));

         */
    }

}