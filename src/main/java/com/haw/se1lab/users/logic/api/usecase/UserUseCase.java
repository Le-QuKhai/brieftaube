package com.haw.se1lab.users.logic.api.usecase;

import com.haw.se1lab.users.common.api.datatype.RegistrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegistrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;

public interface UserUseCase {

    Benutzer createUser(RegistrierungsFormular formular) throws RegistrierungsFormularException;

    /**
     * Versucht einen Benutzer einzuloggen.
     * @param benutzer der Benutzer der sich einloggen will.
     * @return der Benutzer der sich einloggen will.
     * @throws IncorrectPasswordException wenn der Benutzer ein falsches Passwort eingegeben hat
     * @throws UserDoesntExistsException wenn der Benutzer mit diesen Benutzernamen gar nicht existiert
     */
    Benutzer loginBenutzer(Benutzer benutzer) throws IncorrectPasswordException, UserDoesntExistsException;
}
