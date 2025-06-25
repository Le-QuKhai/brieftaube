package com.haw.se1lab.users.facade.impl;

import com.haw.se1lab.users.common.api.datatype.RegistrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegistrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.facade.api.UserFacade;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class UserFacadeImpl implements UserFacade {

    private static final Logger LOG = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private UserUseCase userUseCase;

    public ResponseEntity<?> createBenutzer(RegistrierungsFormular formular){
        try {
            Benutzer benutzer = userUseCase.createUser(formular);
            LOG.info("Benutzer '{}' wurde erfolgreich registriert!", benutzer.getBenutzerName());
            return ResponseEntity.ok(benutzer);
        } catch (IllegalArgumentException | RegistrierungsFormularException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> loginBenutzer(Benutzer benutzer) {
        try {
            Benutzer dataBaseBenutzer = userUseCase.loginBenutzer(benutzer);
            LOG.info("Benutzer '{}' wurde erfolgreich eingeloggt!", dataBaseBenutzer.getBenutzerName());
            return ResponseEntity.ok(dataBaseBenutzer);
        } catch (IncorrectPasswordException | UserDoesntExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> checkIfUserExists(Long userId) {
        if(userUseCase.checkIfUserExists(userId)) {
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.ok(false);
        }
    }
}
