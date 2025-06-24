package com.haw.se1lab.users.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import com.haw.se1lab.users.common.api.datatype.EmailAdresse;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Benutzer extends AbstractEntity
{
    private String benutzername;
    private EmailAdresse emailAdresse;
    private String password;
    private Timestamp letzerLogin;

    public Benutzer(String benutzername, String password) {
        this.benutzername = benutzername;
        this.password = password;
    }
}