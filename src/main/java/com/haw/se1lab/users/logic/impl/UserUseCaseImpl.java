package com.haw.se1lab.users.logic.impl;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Component
public class UserUseCaseImpl implements UserUseCase {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Override
    public Benutzer createUser(RegestrierungsFormular formular) throws RegestrierungsFormularException {
        // check preconditions
        Assert.notNull(formular, "Parameter 'formular' must not be null!");

        if(!formular.getPassword().equals(formular.getPasswordConfirm())){
            throw new RegestrierungsFormularException("Passwörter stimmen nicht überein.");
        }
        else if(formular.getBenutzerName().isEmpty()){
            throw new RegestrierungsFormularException("Benutzername darf nicht leer sein.");
        }
        else if(benutzerRepository.existsByBenutzerName(formular.getBenutzerName())) {
            throw new RegestrierungsFormularException("Benutzername existiert schon.");
        }

        Benutzer user = new Benutzer(formular.getBenutzerName(), formular.getPassword());

        // store entity in DB (from then on: entity object is observed by Hibernate within current transaction)
        return benutzerRepository.save(user);
    }

    @Override
    public Benutzer loginBenutzer(Benutzer benutzer) throws IncorrectPasswordException, UserDoesntExistsException {

        Optional<Benutzer> dataBaseBenutzer = benutzerRepository.findByBenutzerName(benutzer.getBenutzerName());

        if(dataBaseBenutzer.isPresent()){

            if(dataBaseBenutzer.get().getPassword().equals(benutzer.getPassword())){
                return dataBaseBenutzer.get();
            }
            else {
                throw new IncorrectPasswordException("Falsches Password!");
            }
        }
        else {
            throw new UserDoesntExistsException("Benutzer mit dem benutzernamen: " + benutzer.getBenutzerName() + " existiert nicht");
        }
    }
}
