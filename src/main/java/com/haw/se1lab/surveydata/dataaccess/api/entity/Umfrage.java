package com.haw.se1lab.surveydata.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import com.haw.se1lab.users.dataaccess.api.entity.Professor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Umfrage extends AbstractEntity
{

    @OneToMany(fetch = FetchType.LAZY)
    private List<Frage> fragen;
    private Date frist;
    private String titel;
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor ersteller;

    public Umfrage(List<Frage> fragen, Date frist, String titel, Professor ersteller)
    {
        this(fragen, titel, ersteller);
        this.frist = frist;

    }

    public Umfrage(List<Frage> fragen, String titel, Professor ersteller)
    {
        this.fragen = fragen;
        this.titel = titel;
        this.ersteller = ersteller;
    }
}
