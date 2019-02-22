package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.domain.model.*;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {
    List<GenreDomain> getAllGenres();
    List<CountryDomain> getAllCountries();
    List<MovieDomain> getTenMoviesPaged(int page);
    UserDomain getUserById(Short id);
    MovieDomain getMovieById(Integer id);
    List<MovieDomain> getMovieListByTitleContains(String title);
    List<MovieDomain> getMovieListByStorylineContains(String story);
    List<MovieDomain> getMovieListByGenreId(Short id);
    List<MovieDomain> getMovieListByDirectorId(Integer id);
    List<MovieDomain> getMovieListByScreenwriterId(Integer id);
    List<MovieDomain> getMovieListByCountryId(Short id);
    List<MovieDomain> getMovieListByActorId(Integer id);
    List<PersonDomain> getPersonListByNameContains(String name);
    UserDomain createUser(String name);
    UserDomain updateUserPwd(Short id, String pwd);
    ViewingDomain createViewing(LocalDate date, Short user_id,
                                Integer movie_id, float ratingUser);
    MovieDomain createMovie(MovieData movieData);
}
