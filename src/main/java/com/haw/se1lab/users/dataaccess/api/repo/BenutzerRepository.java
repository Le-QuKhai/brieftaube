package com.haw.se1lab.users.dataaccess.api.repo;

import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {

    boolean existBybenutzername(String benutzername);

}
