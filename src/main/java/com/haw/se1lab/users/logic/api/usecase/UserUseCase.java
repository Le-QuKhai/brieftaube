package com.haw.se1lab.users.logic.api.usecase;

import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;

public interface UserUseCase {

    public Benutzer createUser(Benutzer user);
}
