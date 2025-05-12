package com.haw.se1lab.groupdata.dataaccess.api.repo;

import com.haw.se1lab.groupdata.dataaccess.api.entity.Gruppe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface GruppeRepository extends JpaRepository<Gruppe, Long>
{
}
