package com.haw.se1lab.users.logic.api.usecase;

import com.haw.se1lab.users.common.api.datatype.RegestrierungsFormular;
import com.haw.se1lab.users.common.api.exception.IncorrectPasswordException;
import com.haw.se1lab.users.common.api.exception.RegestrierungsFormularException;
import com.haw.se1lab.users.common.api.exception.UserDoesntExistsException;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.http.ResponseEntity;

public interface UserUseCase {

    Benutzer createUser(RegestrierungsFormular formular) throws RegestrierungsFormularException;

    /**
     * Versucht einen Benutzer einzuloggen.
     * @param benutzer der Benutzer der sich einloggen will.
     * @return der Benutzer der sich einloggen will.
     * @throws IncorrectPasswordException wenn der Benutzer ein falsches Passwort eingegeben hat
     * @throws UserDoesntExistsException wenn der Benutzer mit diesen Benutzernamen gar nicht existiert
     */
    Benutzer loginBenutzer(Benutzer benutzer) throws IncorrectPasswordException, UserDoesntExistsException;
}
