package com.haw.se1lab.chatdata.common.api.datatype;

import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatErstellung {

    private Benutzer benutzer;
    private Benutzer teilnehmer;
}
