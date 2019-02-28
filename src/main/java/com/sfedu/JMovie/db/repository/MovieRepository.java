package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Slice<Movie> findByLocalizedTitleContainingOrOriginalTitleContainingAllIgnoreCase(
            String localizedTitle, String originalTitle, Pageable pageable);
    Slice<Movie> findByStorylineContainingIgnoreCase(String storyline, Pageable pageable);
    Slice<Movie> findByDirectorId(Integer id, Pageable pageable);
    Slice<Movie> findByActorsId(Integer id, Pageable pageable);
    Slice<Movie> findByScreenwriterId(Integer id, Pageable pageable);
    Slice<Movie> findByGenresId(Short id, Pageable pageable);
    Slice<Movie> findByCountriesId(Short id, Pageable pageable);
    Slice<Movie> findByYearBetween(Short start, Short end, Pageable pageable);
}
