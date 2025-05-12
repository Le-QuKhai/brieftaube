package com.haw.se1lab.surveydata.dataaccess.api.repo;

import com.haw.se1lab.surveydata.dataaccess.api.entity.Umfrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface UmfrageRepository extends JpaRepository<Umfrage, Long>
{
}
