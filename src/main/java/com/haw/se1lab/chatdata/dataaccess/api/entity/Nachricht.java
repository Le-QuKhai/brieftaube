package com.haw.se1lab.chatdata.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Eine Nachricht repräsentiert einen Text, der über einen Chat geschrieben und verschickt wird.
 * Zusätzlich hat eine Nachricht noch seinen Sender, der Benutzer, der die Nachricht geschrieben hat.
 * Zudem das Datum, an das die Nachricht geschickt wurde.
 */

@Getter
@Entity
@NoArgsConstructor
@Embeddable
public class Nachricht extends AbstractEntity
{
    private String nachricht;
    private Date sendDate;
    @ManyToOne
    private Benutzer sender;
    public Nachricht(String nachricht, Date sendDate, Benutzer sender)
    {
        this.nachricht = nachricht;
        this.sendDate = sendDate;
        this.sender = sender;
    }
}
