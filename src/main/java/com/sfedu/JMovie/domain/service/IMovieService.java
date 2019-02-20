package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.domain.model.*;

import java.time.LocalDate;
import java.util.List;

public interface IMovieService {
    List<GenreDomain> getAllGenres();
    List<CountryDomain> getAllCountries();
    List<UserDomain> getAllUsers();
    PersonDomain createPerson(Integer id, String name);
    ViewingDomain createViewing(LocalDate date, Short user_id,
                                Integer movie_id, float ratingUser);
}
