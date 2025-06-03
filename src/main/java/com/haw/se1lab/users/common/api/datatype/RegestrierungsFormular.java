package com.haw.se1lab.users.common.api.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegestrierungsFormular {

    private String benutzerName;
    private String password;
    private String passwordConfirm;
}
