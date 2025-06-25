package com.haw.se1lab.chatdata.facade.api;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.common.api.datatype.ChatErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ChatFacadeTest {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private BenutzerRepository benutzerRepository;

    private Benutzer user;
    private Benutzer user2;
    private Benutzer user3;
    private Benutzer user4;
    Benutzer userTestC1;
    Benutzer userTestC2;
    private Chat chat1;
    private Chat chat2;
    private Chat chat3;
    private Chat chat4;


    @BeforeAll
    public void setUpAll() {
        user = new Benutzer("user1", "1");
        benutzerRepository.save(user);
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

        chat1 = new Chat(user, user2);
        chatRepository.save(chat1);
        chat2 = new Chat(user);
        chatRepository.save(chat2);
        chat3 = new Chat(user3);
        chatRepository.save(chat3);
        chat4 = new Chat(user2);
        chatRepository.save(chat4);
    }

    @AfterAll
    public void tearDown() {
        chatRepository.deleteAll();
        benutzerRepository.deleteAll();
    }


    @Test
    public void createNewChatTest() {
        ChatErstellung chatErstellung = new ChatErstellung(userTestC1, userTestC2);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(chatErstellung)

                .when()
                .post("/api/chat/create")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("teilnehmer.size()", is(2));
    }


    @Test
    public void findMyChatTest() {

        Response response = RestAssured.given()
                 .contentType(ContentType.JSON)
                 .body(user)

                 .when()
                 .get("/api/chat/all")

                 .then()
                 .statusCode(HttpStatus.OK.value())
                .body("size()", is(2))
                .body("[0].teilnehmer.size()", is(2))
                .body("[1].teilnehmer.size()", is(1))
                 .extract().response();

        assertEquals(chat1.getId(), response.jsonPath().getLong("[0].id"));
        assertEquals(chat2.getId(), response.jsonPath().getLong("[1].id"));
     }


     @Test
     public void findMyChatTest2() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user4)

                .when()
                .get("/api/chat/all")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
     }

     @Test
    public void findMyChatUserDontExistsTest() {

        Benutzer user = new Benutzer("user5", "5");

         RestAssured.given()
                 .contentType(ContentType.JSON)
                 .body(user)

                 .when()
                 .get("/api/chat/all")

                 .then()
                 .statusCode(HttpStatus.OK.value())
                 .body("size()", is(0));
    }

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
        assertEquals(user2.getId(), response.jsonPath().getLong("teilnehmer[0].id"));
        assertEquals(user.getId(), response.jsonPath().getLong("teilnehmer[1].id"));
    }

    @Test
    public void getChatByIdWrongIdTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(-1)

                .when()
                .get("api/chat")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getAllChats() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)

                .when()
                .get("api/chat/all")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2))
                .body("[0].teilnehmer.size()", is(2))
                .body("[1].teilnehmer.size()", is(1))
                .extract().response();

        assertEquals(chat1.getId(), response.jsonPath().getLong("[0].id"));
        assertEquals(chat2.getId(), response.jsonPath().getLong("[1].id"));
    }

    @Test
    public void getAllChatsUserDontExists() {
        Benutzer user = new Benutzer("user5", "5");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user)

                .when()
                .get("api/chat/all")

                .then()
                .statusCode(HttpStatus.OK.value()).
                body("size()", is(0));
    }

    @Test
    public void getAllchatsUserDontHaveChats() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(user4)

                .when()
                .get("api/chat/all")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    @Test
    public void getNewChats() {

        List<Long> knownChats = new ArrayList<>(){{add(chat1.getId());}};

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(knownChats)
                .queryParam("userId", user.getId())

                .when()
                .get("api/chat/get_new")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1))
                .body("[0].teilnehmer.size()", is(1))
                .extract().response();

        assertEquals(chat2.getId(), response.jsonPath().getLong("[0].id"));
        assertEquals(user.getId(), response.jsonPath().getLong("[0].teilnehmer[0].id"));
    }

    @Test
    public void getNewChatsNoNewChats() {
        List<Long> knownChats = new ArrayList<>(){{add(chat1.getId()); add(chat2.getId());}};

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(knownChats)
                .queryParam("userId", user.getId())

                .when()
                .get("api/chat/get_new")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }



}
