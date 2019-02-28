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
    List<MovieDomain> getTenMoviesPaged(int page, BoolW hasNext);
    UserDomain getUserByName(String name);
    MovieDomain getMovieById(Integer id);
    void addMissingListsToMovie(MovieData movieData);
    List<MovieDomain> getMovieListByTitleContains(String title, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByStorylineContains(String story, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByGenreId(Short id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByDirectorId(Integer id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByScreenwriterId(Integer id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByCountryId(Short id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByActorId(Integer id, int page, BoolW hasNext);
    List<MovieDomain> getMovieListByYearPeriod(Short start, Short end, int page, BoolW hasNext);
    List<PersonDomain> getPersonListByNameContains(String name);
    UserDomain createUser(String name, RoleType role);
    UserDomain updateUserPwd(Short id, String pwd);
    ViewingDomain createViewing(LocalDate date, Short user_id,
                                Integer movie_id, float ratingUser);
    MovieDomain createMovie(MovieData movieData);
}
