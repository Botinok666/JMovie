package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Short> {
}
