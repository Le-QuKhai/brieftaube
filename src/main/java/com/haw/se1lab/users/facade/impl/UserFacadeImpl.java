package com.haw.se1lab.users.facade.impl;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.facade.api.UserFacade;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserUseCase userUseCase;

    public ResponseEntity<?> createBenutzer(RegestrierungsFormular formular){
        try {
            Benutzer benutzer = userUseCase.createUser(formular);
            System.out.println("Benutzer wurde erfolgreich registriert!"); // Nur zum Testen, ob Benutzer erfolreich erstellt
            return ResponseEntity.ok(benutzer);
        } catch (IllegalArgumentException | RegestrierungsFormularException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
