package com.haw.se1lab.users.facade.api;

import com.haw.se1lab.Application;
import com.haw.se1lab.users.common.api.datatype.RegistrierungsFormular;
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
        RegistrierungsFormular formular = new RegistrierungsFormular(
                "test1", "test", "test");

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(formular)

        .when()
        .post("/api/user/register")

        .then()
        .statusCode(HttpStatus.OK.value())
        .body("benutzerName", equalTo(formular.getBenutzerName()))
        .body("password", equalTo(formular.getPassword()));
    }

    @Test
    public void createUserWrongPWConfirmTest() {
        RegistrierungsFormular formular = new RegistrierungsFormular(
                "test1", "test", "tset");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(formular)

                .when()
                .post("/api/user/register")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createUserAlreadyExistTest() {
        RegistrierungsFormular formular = new RegistrierungsFormular(
                "testNameExist", "test", "test");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(formular)

                .when()
                .post("/api/user/register")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("benutzerName", equalTo(formular.getBenutzerName()))
                .body("password", equalTo(formular.getPassword()));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(formular)

                .when()
                .post("/api/user/register")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void loginTest() {
        Benutzer benutzer = new Benutzer("test", "test");
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

    @Test
    public void loginWrongPasswordTest() {
        Benutzer benutzer = new Benutzer("userTest", "password");
        Benutzer login = new Benutzer("userTest", "1234");
        benutzerRepository.save(benutzer);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(login)

                .when()
                .post("/api/user/login")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void loginUserNotExistTest() {
        Benutzer login = new Benutzer("bestUser", "userBrieftaube");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(login)

                .when()
                .post("/api/user/login")

                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
