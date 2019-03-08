package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);
    int countByNameContainingIgnoreCase(String name);
}
