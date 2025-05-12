package com.haw.se1lab.users.dataaccess.api.entity;

import com.haw.se1lab.users.common.api.datatype.EmailAdresse;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class Professor extends Benutzer
{
    private String akedemischerGrad;

    public Professor(String name, String vorname, Date geburstag, EmailAdresse emailAdresse, String akedemischerGrad)
    {
        super(name, vorname, geburstag, emailAdresse);
        this.akedemischerGrad = akedemischerGrad;
    }

    public Professor(String name, String vorname, Date geburstag, EmailAdresse emailAdresse)
    {
        super(name, vorname, geburstag, emailAdresse);
    }

    public Professor(String name, String vorname, Date geburstag, String akedemischerGrad)
    {
        super(name, vorname, geburstag);
        this.akedemischerGrad = akedemischerGrad;
    }

    public Professor(String name, String vorname, EmailAdresse emailAdresse, String akedemischerGrad)
    {
        super(name, vorname, emailAdresse);
        this.akedemischerGrad = akedemischerGrad;
    }

    public Professor(String name, String vorname, Date geburstag)
    {
        super(name, vorname, geburstag);
    }

    public Professor(String name, String vorname, EmailAdresse emailAdresse)
    {
        super(name, vorname, emailAdresse);
    }

    public Professor(String name, String vorname, String akedemischerGrad)
    {
        super(name, vorname);
        this.akedemischerGrad = akedemischerGrad;
    }

    public Professor(String name, String vorname)
    {
        super(name, vorname);
    }
}
