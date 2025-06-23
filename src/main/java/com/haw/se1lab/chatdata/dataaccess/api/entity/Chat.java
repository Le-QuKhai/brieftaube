package com.haw.se1lab.chatdata.dataaccess.api.entity;

import com.haw.se1lab.general.dataaccess.api.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import lombok.NoArgsConstructor;

import java.util.*;


/**
 * Ein Chat ist ein Objekt, wo sich Benutzer über Nachrichten austauschen.
 * Ein Chat hat immer mindestens 1 Benutzer, nämlich der, der den Chat erstellt hat.
 * In der Regel sind zwei Benutzer in einem Chat, durch Gruppen können aber auch mehr drinnen sein.
 */

@Getter
@NoArgsConstructor
@Entity
public class Chat extends AbstractEntity
{

    @ElementCollection
    private List<Nachricht> nachrichten;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "C",
               joinColumns = @JoinColumn(name = "chatId"),
                inverseJoinColumns = @JoinColumn(name = "participantId"))
    private Set<Benutzer> teilnehmer;

    public Chat(Benutzer benutzer)
    {
        nachrichten = new ArrayList<Nachricht>();
        teilnehmer = new HashSet<Benutzer>();
        addTeilnehmer(benutzer);
    }

    /**
     * Es wird eine Nachricht zum Chat hinzugefügt, die ein Teilnehmer geschrieben hat.
     * @param nachricht die Nachricht die hinzugefügt werden soll
     */
    public void addNachricht(Nachricht nachricht)
    {
        Objects.requireNonNull(nachricht);
        nachrichten.add(nachricht);
    }

    /**
     * Fügt einen Benutzer zu dem Chat hinzu.
     */
    public void addTeilnehmer(Benutzer benutzer)
    {
        Objects.requireNonNull(benutzer);
        teilnehmer.add(benutzer);
    }
}
