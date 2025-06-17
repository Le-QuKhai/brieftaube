package com.haw.se1lab.users.logic.api.usecases.facade.api;

import com.haw.se1lab.Application;
import com.haw.se1lab.users.common.api.datatype.EmailAdresse;
import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.EmailInvalidException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

import static io.restassured.path.json.JsonPath.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // environment
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserFacadeTest {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Test
    public void createUserTest() {
        RegestrierungsFormular formular = new RegestrierungsFormular("test1", "test", "test");

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(formular)

        .when()
        .post("/api/user")

        .then()
        .statusCode(HttpStatus.OK.value())
        .body("benutzerName", equalTo(formular.getBenutzerName()))
        .body("password", equalTo(formular.getPassword()));
    }

    @Test
    public void loginTest() throws EmailInvalidException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        EmailAdresse email = EmailAdresse.get("test", "", "@gmail", ".com");
        Benutzer benutzer = new Benutzer("test", email, "test", timestamp);
        benutzerRepository.save(benutzer);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(benutzer)

                .when()
                .post("/api/user/login")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("benutzerName", equalTo(benutzer.getBenutzerName()))
                .body("password", equalTo(benutzer.getPassword()));

    }
}
