package com.haw.se1lab.groupdata.facade.api;

import com.haw.se1lab.Application;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.repo.ChatRepository;
import com.haw.se1lab.groupdata.dataaccess.api.entity.Gruppe;
import com.haw.se1lab.groupdata.dataaccess.api.repo.GruppeRepository;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.response.Response;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class GroupFacadeTest
{
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    BenutzerRepository benutzerRepository;

    @Autowired
    GruppeRepository ruppeRepository;

    Benutzer admin;
    Chat chat;
    @Autowired
    private GruppeRepository gruppeRepository;


    @BeforeAll
    public void setUpAll()
    {
        admin = new Benutzer();
        benutzerRepository.save(admin);
        chat = new Chat(admin, admin);
        chatRepository.save(chat);

    }
    @AfterAll
    public void clearDownAll()
    {
        gruppeRepository.deleteAll();
        chatRepository.deleteAll();
        benutzerRepository.deleteAll();

    }

    @Test
    public void createGroup_Success()
    {
        Gruppe gruppe = new Gruppe("name", "de", admin, chat);

        Response response = given()
        .contentType(ContentType.JSON)
        .body(gruppe)

        .when()
        .post("/group")

        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("name", equalTo(gruppe.getName()))
        .body("beschreibung", equalTo(gruppe.getBeschreibung()))
        .extract().response();

        Gruppe returnedGruppe = response.as(Gruppe.class);
        assertEquals(returnedGruppe.getAdmin().getId(), gruppe.getAdmin().getId());
        assertEquals(returnedGruppe.getGruppenChat().getId(), gruppe.getGruppenChat().getId());

    }
}
