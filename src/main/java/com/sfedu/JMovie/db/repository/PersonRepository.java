package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
