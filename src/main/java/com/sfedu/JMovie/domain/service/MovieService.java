package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public List<MovieDomain> getAllMovies(){
        return MovieConverter.convertToMovieDomainList(
                movieRepository.findAll()
        );
    }
    @Override
    public UserDomain getUserById(Short id){
        return UserConverter.convertToUserDomain(
                userRepository.findById(id).orElseThrow(
                        () -> new NoSuchElementException("No such user")));
    }
    @Override
    public MovieDomain getMovieById(Integer id){
        return MovieConverter.convertToMovieDomain(
                movieRepository.findById(id).orElseThrow(
                        () -> new NoSuchElementException("No such movie")));
    }
    @Override
    public List<MovieDomain> getMovieListByTitleContains(String title){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByLocalizedTitleLikeIgnoreCase(title));
    }
    @Override
    public List<MovieDomain> getMovieListByStorylineContains(String story){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByStorylineLikeIgnoreCase(story));
    }
    @Override
    public List<PersonDomain> getMovieActorsById(Integer movieId){
        return PersonConverter.convertToPersonDomainList(movieRepository
                .findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("No such movie"))
                .getActors());
    }
    @Override
    public List<GenreDomain> getMovieGenresById(Integer movieId){
        return GenreConverter.convertToGenreDomainList(movieRepository
                .findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("No such movie"))
                .getGenres());
    }
    @Override
    public List<CountryDomain> getMovieCountriesById(Integer movieId){
        return CountryConverter.convertToCountryDomainList(movieRepository
                .findById(movieId)
                .orElseThrow(() -> new NoSuchElementException("No such movie"))
                .getCountries());
    }
    @Override
    public List<PersonDomain> getPersonByNameContains(String name){
        return PersonConverter.convertToPersonDomainList(
                personRepository.findByNameLikeIgnoreCase(name));
    }
    @Override
    public UserDomain createUser(String name){
        return UserConverter.convertToUserDomain(userRepository.save(
                new User(name, "")
        ));
    }
    @Override
    public UserDomain updateUserPwd(Short id, String pwd){
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such user"));
        user.setPwd(pwd);
        return UserConverter.convertToUserDomain(userRepository.save(user));
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
    @Override
    public MovieDomain createMovie(MovieData movieData, PersonData director, PersonData screenwriter,
                                   List<PersonData> actors, List<GenreData> genres,
                                   List<CountryData> countries){
        final Movie movie = new Movie(
                movieData.getId(), movieData.getLocalizedTitle(), movieData.getOriginalTitle(),
                movieData.getPosterLink(), movieData.getYear(), movieData.getTagLine(),
                movieData.getRuntime(), movieData.getStoryline(), movieData.getRatingKP(),
                movieData.getRatingIMDB());
        movie.setDirector(personRepository.save(
                new Person(director.getId(), director.getName())));
        movie.setScreenwriter(personRepository.save(
                new Person(screenwriter.getId(), screenwriter.getName())));
        personRepository.saveAll(actors.stream()
                .map(k -> new Person(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addActor);
        genreRepository.saveAll(genres.stream()
                .map(k -> new Genre(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addGenre);
        countryRepository.saveAll(countries.stream()
                .map(k -> new Country(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addCountry);
        return MovieConverter.convertToMovieDomain(movieRepository.save(movie));
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
