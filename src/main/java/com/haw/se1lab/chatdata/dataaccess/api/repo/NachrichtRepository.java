package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository für die Entity Nachricht
 */
public interface NachrichtRepository extends JpaRepository<Nachricht, Long> {

    /**
     * Überprüft, ob eine Nachricht mit der Id schon existiert
     * @param nachrichtId die Id die überprüft werden soll
     * @return 1, wenn eine Nachricht mit der Id existiert
     */
    // TODO check if Query works
    @Query("SELECT 1 FROM Nachricht n WHERE n.id = :nachrichtId")
    Optional<Integer> checkIfNachrichtExists(@Param("nachrichtId") Long nachrichtId);

    /**
     * Gibt eine Liste mit allen Nachrichten zurück, die der Benutzer geschrieben hat.
     * @param sender der Benutzer, von dem man alle Nachrichten haben will
     * @return Listen mit allen Nachrichten
     */
    Optional<List<Nachricht>> findBySender(Benutzer sender);

}