package com.haw.se1lab.users.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import com.haw.se1lab.users.common.api.datatype.EmailAdresse;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractBenutzer extends AbstractEntity
{
    @Setter
    private String name;
    private String vorname;
    private Date geburstag;
    @Setter
    private EmailAdresse emailAdresse;

    public AbstractBenutzer(String name, String vorname)
    {
        this.name = name;
        this.vorname = vorname;
    }

    public AbstractBenutzer(String name, String vorname, Date geburstag)
    {
        this(name, vorname);
        this.geburstag = geburstag;
    }

    public AbstractBenutzer(String name, String vorname, EmailAdresse emailAdresse)
    {
        this(vorname, name);
        this.emailAdresse = emailAdresse;
    }

    public AbstractBenutzer(String name, String vorname, Date geburstag, EmailAdresse emailAdresse)
    {
        this(vorname, name, geburstag);
        this.emailAdresse = emailAdresse;
    }
}
