package com.haw.se1lab.surveydata.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import com.haw.se1lab.surveydata.common.api.datatype.Antworttyp;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Frage extends AbstractEntity
{
    private String frage;
    private Antworttyp antworttyp;


    public Frage(String frage, Antworttyp antworttyp)
    {
        this.frage = frage;
        this.antworttyp = antworttyp;
    }
}
