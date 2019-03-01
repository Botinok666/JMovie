package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<MovieDomain> getAllMovies(int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findAll(PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public boolean isMovieExists(Integer id){
        return movieRepository.existsById(id);
    }
    @Override
    public MovieDomain getMovieById(Integer id)
            throws NoSuchElementException {
        return MovieConverter.convertToMovieDomain(movieRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such movie")));
    }
    @Override
    public List<MovieDomain> getMovieListByUserId(Short id, int page, BoolW hasNext) {
        Slice<Viewing> viewings = viewingRepository
                .findByUserId(id, PageRequest.of(page, 10));
        hasNext.setValue(viewings.hasNext());
        return MovieConverter.convertToMovieDomainList(viewings
                .stream()
                .map(Viewing::getMovie)
                .collect(Collectors.toList())
        );
    }
    @Override
    public List<MovieDomain> getMovieListByTitleContains(
            String title, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByLocalizedTitleContainingOrOriginalTitleContainingAllIgnoreCase(
                        title, title, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    @Transactional(readOnly = true)
    public void addMissingListsToMovie(MovieData movieData)
            throws NoSuchElementException {
        Movie movie = movieRepository
                .findById(movieData.getId())
                .orElseThrow(() -> new NoSuchElementException("No such movie"));
        movie.getCountries()
                .stream()
                .map(country -> CountryConverter.convertToCountryDTO(
                        CountryConverter.convertToCountryDomain(country)))
                .forEach(movieData::addCountry);
        movie.getGenres()
                .stream()
                .map(genre -> GenreConverter.convertToGenreDTO(
                        GenreConverter.convertToGenreDomain(genre)))
                .forEach(movieData::addGenre);
        movie.getActors()
                .stream()
                .map(actor -> PersonConverter.convertToPersonDTO(
                        PersonConverter.convertToPersonDomain(actor)))
                .forEach(movieData::addActor);
    }
    @Override
    public List<MovieDomain> getMovieListByStorylineContains(
            String story, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByStorylineContainingIgnoreCase(story, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public List<MovieDomain> getMovieListByGenreId(
            Short id, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByGenresId(id, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public List<MovieDomain> getMovieListByDirectorId(
            Integer id, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByDirectorId(id, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public List<MovieDomain> getMovieListByCountryId(
            Short id, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByCountriesId(id, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public List<MovieDomain> getMovieListByActorId(
            Integer id, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByActorsId(id, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public List<MovieDomain> getMovieListByYearPeriod(
            Short start, Short end, int page, BoolW hasNext){
        Slice<Movie> result = movieRepository
                .findByYearBetween(start, end, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
    @Override
    public List<PersonDomain> getPersonListByNameContains(String name){
        return PersonConverter.convertToPersonDomainList(
                personRepository.findTop10ByNameContainingIgnoreCase(name));
    }
    @Override
    public UserDomain createUser(String name, RoleType role) throws IllegalArgumentException{
        if (userRepository.findByName(name) != null)
            throw new IllegalArgumentException("User " + name + " already exists!");
        return UserConverter.convertToUserDomain(userRepository
                .save(new User(name, "", role)));
    }
    @Override
    public UserDomain updateUserPwd(Short id, String pwd) throws NoSuchElementException{
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such user"));
        user.setPwd(pwd);
        return UserConverter.convertToUserDomain(userRepository.save(user));
    }
    @Override
    public ViewingDomain createViewing(LocalDate date, Short user_id,
                                       Integer movie_id, float ratingUser)
            throws NoSuchElementException {
        Viewing viewing = new Viewing(date, ratingUser);
        viewing.setUser(userRepository
                .findById(user_id)
                .orElseThrow(() -> new NoSuchElementException("No such user")));
        viewing.setMovie(movieRepository
                .findById(movie_id)
                .orElseThrow(() -> new NoSuchElementException("No such movie")));
        return ViewingConverter.convertToViewingDomain(viewingRepository.save(viewing));
    }
    @Override
    public List<ViewingDomain> getViewingsByMovieAndUserId(Integer movieId, Short userId) {
        return ViewingConverter.convertToViewingDomainList(
                viewingRepository.findByMovieIdAndUserId(movieId, userId));
    }
    @Override
    public MovieDomain createMovie(MovieData movieData){
        final Movie movie = new Movie(
                movieData.getId(), movieData.getLocalizedTitle(), movieData.getOriginalTitle(),
                movieData.getPosterLink(), movieData.getYear(), movieData.getTagLine(),
                movieData.getRuntime(), movieData.getStoryline(), movieData.getRatingKP(),
                movieData.getRatingIMDB());
        movie.setDirector(personRepository.save(
                new Person(movieData.getDirector().getId(), movieData.getDirector().getName())));
        movie.setScreenwriter(personRepository.save(
                new Person(movieData.getScreenwriter().getId(), movieData.getScreenwriter().getName())));
        personRepository.saveAll(movieData.getActors()
                .stream()
                .map(k -> new Person(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addActor);
        genreRepository.saveAll(movieData.getGenres()
                .stream()
                .map(k -> new Genre(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addGenre);
        countryRepository.saveAll(movieData.getCountries()
                .stream()
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
