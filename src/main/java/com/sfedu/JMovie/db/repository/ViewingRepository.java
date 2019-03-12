package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Viewing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewingRepository extends JpaRepository<Viewing, Integer> {
    List<Viewing> findByMovieIdAndUserId(Integer movieId, Short userId);
    Slice<Viewing> findByUserId(Short id, Pageable pageable);
    int countByUserId(Short id);
}
