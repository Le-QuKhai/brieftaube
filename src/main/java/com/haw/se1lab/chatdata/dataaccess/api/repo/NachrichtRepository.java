package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NachrichtRepository extends JpaRepository<Nachricht, Long> {

    // TODO check if Query works
    @Query("SELECT 1 FROM Nachricht n WHERE n.id = :nachrichtId")
    Optional<Integer> checkIfNachrichtExists(@Param("nachrichtId") Long nachrichtId);

}