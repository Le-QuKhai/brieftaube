package com.haw.se1lab.surveydata.dataaccess.api.repo;

import com.haw.se1lab.surveydata.dataaccess.api.entity.Frage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface FrageRepository extends JpaRepository<Frage, Long>
{
}
