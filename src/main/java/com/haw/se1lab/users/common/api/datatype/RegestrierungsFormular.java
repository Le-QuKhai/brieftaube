package com.haw.se1lab.users.common.api.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegestrierungsFormular {

    private final String benutzername;
    private final String passwort;
    private final String password2;
}
