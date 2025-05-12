package com.haw.se1lab.users.facade.impl;

import com.haw.se1lab.users.dataaccess.api.entity.Professor;
import com.haw.se1lab.users.facade.api.UserFacade;
import com.haw.se1lab.users.logic.api.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserUseCase userUseCase;

    public void createProfessor(Professor professor){
        userUseCase.createProfessor(professor);
    }
}
