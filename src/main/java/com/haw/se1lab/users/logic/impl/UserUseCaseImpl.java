package com.haw.se1lab.users.logic.impl;

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
    public Benutzer createUser(Benutzer user) {
        // check preconditions
        Assert.notNull(user, "Parameter 'customer' must not be null!");
        Assert.isNull(user.getId(), "Parameter 'customer' must not already have an ID!");

        // store entity in DB (from then on: entity object is observed by Hibernate within current transaction)
        return benutzerRepository.save(user);
    }
}
