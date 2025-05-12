package com.haw.se1lab.users.dataaccess.api.repo;

import com.haw.se1lab.users.dataaccess.api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long>
{
    /**
     * Sucht nach allen Student mit einem bestimmte namen
     * @param name der Name nach den gesucht werden soll
     * @return
     */
    List<Student> findByName(String name);


}
