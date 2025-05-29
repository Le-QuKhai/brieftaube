package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NachrichtRepository extends JpaRepository<Nachricht, Long> {

}