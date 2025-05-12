package com.haw.se1lab.groupdata.dataaccess.api.entity;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import com.haw.se1lab.users.dataaccess.api.entity.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Entity
public class Gruppe extends AbstractEntity
{
    @Setter
    private String name;
    @Setter
    private String beschreibung;
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor ersteller;
    @NotNull
    @Valid
    @OneToOne(fetch = FetchType.LAZY)
    private Chat gruppenChat;

    public Gruppe(String name, String beschreibung, Professor ersteller, Chat gruppenChat)
    {
        this.name = name;
        this.beschreibung = beschreibung;
        this.ersteller = ersteller;
        this.gruppenChat = gruppenChat;
    }

    public Gruppe(String name, Chat gruppenChat)
    {
        this.name = name;
        this.gruppenChat = gruppenChat;
    }

}
