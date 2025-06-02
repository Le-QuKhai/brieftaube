package com.haw.se1lab.users.logic.api.usecase;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;

public interface UserUseCase {

    public Benutzer createUser(RegestrierungsFormular formular) throws RegestrierungsFormularException;
}
