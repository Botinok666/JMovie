package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewingRepository extends JpaRepository<Viewing, Integer> {
}
