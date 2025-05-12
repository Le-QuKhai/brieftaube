package com.haw.se1lab.users.dataaccess.api.entity;

import com.haw.se1lab.users.common.api.datatype.EmailAdresse;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Benutzer extends AbstractBenutzer
{
    public Benutzer(String name, String vorname)
    {
        super(name, vorname);
    }

    public Benutzer(String name, String vorname, Date geburstag)
    {
        super(name, vorname, geburstag);
    }

    public Benutzer(String name, String vorname, EmailAdresse emailAdresse)
    {
        super(name, vorname, emailAdresse);
    }

    public Benutzer(String name, String vorname, Date geburstag, EmailAdresse emailAdresse)
    {
        super(name, vorname, geburstag, emailAdresse);
    }
}
