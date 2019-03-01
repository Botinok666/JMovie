package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.model.*;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {
    List<GenreDomain> getAllGenres();
    List<CountryDomain> getAllCountries();
    List<MovieDomain> getAllMovies(int page, BoolW hasNext);
    MovieDomain getMovieById(Integer id);
    boolean isMovieExists(Integer id);
    void addMissingListsToMovie(MovieData movieData);
    List<MovieDomain> getMovieListByUserId(Short id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByTitleContains(String title, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByStorylineContains(String story, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByGenreId(Short id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByDirectorId(Integer id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByCountryId(Short id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByActorId(Integer id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByYearPeriod(Short start, Short end, int page, BoolW hasNext);
    List<PersonDomain> getPersonListByNameContains(String name);
    UserDomain createUser(String name, RoleType role);
    UserDomain updateUserPwd(Short id, String pwd);
    List<ViewingDomain> getViewingsByMovieAndUserId(Integer movieId, Short userId);
    ViewingDomain createViewing(LocalDate date, Short user_id,
                                Integer movie_id, float ratingUser);
    MovieDomain createMovie(MovieData movieData);
}
