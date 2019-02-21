package com.sfedu.JMovie.db.repository;

import com.sfedu.JMovie.db.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByLocalizedTitleLikeIgnoreCase(String localizedTitle);
    List<Movie> findByStorylineLikeIgnoreCase(String storyline);
    List<Movie> findByDirectorId(Integer id);
    List<Movie> findByActorsId(Integer id);
    List<Movie> findByScreenwriterId(Integer id);
    List<Movie> findByGenresId(Short id);
    List<Movie> findByCountriesId(Short id);
}
