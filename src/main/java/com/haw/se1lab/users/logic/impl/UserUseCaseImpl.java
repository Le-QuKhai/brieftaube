package com.haw.se1lab.users.logic.impl;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import com.haw.se1lab.users.dataaccess.api.repo.BenutzerRepository;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserUseCaseImpl implements UserUseCase {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Override
    public Benutzer createUser(RegestrierungsFormular formular) throws RegestrierungsFormularException {
        // check preconditions
        Assert.notNull(formular, "Parameter 'customer' must not be null!");

        if(!formular.getPasswort().equals(formular.getPassword2())){
            throw new RegestrierungsFormularException("");
        }
        else if(benutzerRepository.existsByBenutzername(formular.getBenutzername())) {
            throw new RegestrierungsFormularException("Benutzername exestiert schon");
        }

        Benutzer user = new Benutzer(formular.getBenutzername(), formular.getPasswort());

        // store entity in DB (from then on: entity object is observed by Hibernate within current transaction)
        return benutzerRepository.save(user);
    }
}
