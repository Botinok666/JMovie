package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.GetOptions;
import com.sfedu.JMovie.domain.command.*;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.EnumMap;
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

    private EnumMap<GetOptions, GetMovies> moviesEnumMap =
            new EnumMap<>(GetOptions.class);

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
    public List<MovieDomain> getMovies(GetOptions option,
                                       Object param, int page, BoolW hasNext) {
        if (moviesEnumMap.isEmpty()) {
            moviesEnumMap.put(GetOptions.GetAll,
                    new MoviesAll(movieRepository));
            moviesEnumMap.put(GetOptions.GetByActor,
                    new MoviesByActor(movieRepository));
            moviesEnumMap.put(GetOptions.GetByCountry,
                    new MoviesByCountry(movieRepository));
            moviesEnumMap.put(GetOptions.GetByDirector,
                    new MoviesByDirector(movieRepository));
            moviesEnumMap.put(GetOptions.GetByGenre,
                    new MoviesByGenre(movieRepository));
            moviesEnumMap.put(GetOptions.GetByStoryline,
                    new MoviesByStoryline(movieRepository));
            moviesEnumMap.put(GetOptions.GetByTitle,
                    new MoviesByTitle(movieRepository));
            moviesEnumMap.put(GetOptions.GetByUser,
                    new MoviesByUser(viewingRepository));
            moviesEnumMap.put(GetOptions.GetByYearPeriod,
                    new MoviesByYearPeriod(movieRepository));
        }
        return moviesEnumMap.get(option).get(param, page, hasNext);
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
