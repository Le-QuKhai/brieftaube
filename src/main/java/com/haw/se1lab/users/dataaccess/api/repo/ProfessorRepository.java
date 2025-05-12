package com.haw.se1lab.users.dataaccess.api.repo;

import com.haw.se1lab.users.dataaccess.api.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface ProfessorRepository extends JpaRepository<Professor, Long>
{
}
