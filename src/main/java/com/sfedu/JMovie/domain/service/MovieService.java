package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.db.entity.Person;
import com.sfedu.JMovie.db.entity.Viewing;
import com.sfedu.JMovie.db.repository.*;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieService implements IMovieService {
    private CountryRepository countryRepository;
    private GenreRepository genreRepository;
    private PersonRepository personRepository;
    private UserRepository userRepository;
    private ViewingRepository viewingRepository;
    private MovieRepository movieRepository;

    @Override
    public List<GenreDomain> getAllGenres(){
        return GenreConverter.convertToGenreDomainList(
                genreRepository.findAll());
    }
    @Override
    public List<CountryDomain> getAllCountries(){
        return CountryConverter.convertToCountryDomainList(
                countryRepository.findAll());
    }
    @Override
    public List<UserDomain> getAllUsers(){
        return UserConverter.convertToUserDomainList(
                userRepository.findAll());
    }
    @Override
    public PersonDomain createPerson(Integer id, String name){
        return PersonConverter.convertToPersonDomain(
                personRepository.save(new Person(id, name)));
    }
    @Override
    public ViewingDomain createViewing(LocalDate date, Short user_id,
                                       Integer movie_id, float ratingUser){
        Viewing viewing = new Viewing(date, ratingUser);
        viewing.setUser(userRepository.findById(user_id).orElseThrow(
                () -> new NoSuchElementException("No such user")));
        viewing.setMovie(movieRepository.findById(movie_id).orElseThrow(
                () -> new NoSuchElementException("No such movie")));
        return ViewingConverter.convertToViewingDomain(viewingRepository.save(viewing));
    }

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }
    @Autowired
    public void setGenreRepository(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }
    @Autowired
    public void setPersonRepository(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Autowired
    public void setViewingRepository(ViewingRepository viewingRepository){
        this.viewingRepository = viewingRepository;
    }
    @Autowired
    public void setMovieRepository(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
}
