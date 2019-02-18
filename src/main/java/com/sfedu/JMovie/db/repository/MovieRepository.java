package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
