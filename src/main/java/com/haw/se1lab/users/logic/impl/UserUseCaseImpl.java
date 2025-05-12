package com.haw.se1lab.users.logic.impl;

import com.haw.se1lab.users.dataaccess.api.entity.Professor;
import com.haw.se1lab.users.dataaccess.api.repo.ProfessorRepository;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserUseCaseImpl implements UserUseCase {

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public Professor createProfessor(Professor professor) {
        // check preconditions
        Assert.notNull(professor, "Parameter 'customer' must not be null!");
        Assert.isNull(professor.getId(), "Parameter 'customer' must not already have an ID!");

        // store entity in DB (from then on: entity object is observed by Hibernate within current transaction)
        return professorRepository.save(professor);
    }
}
