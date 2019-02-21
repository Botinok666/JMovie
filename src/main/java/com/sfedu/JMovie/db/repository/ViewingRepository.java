package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewingRepository extends JpaRepository<Viewing, Integer> {
    List<Viewing> findByMovieId(Integer id);
    List<Viewing> findByUserId(Short id);
}
