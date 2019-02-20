package com.sfedu.JMovie.domain.service;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<MovieDomain> getTenMoviesPaged(int page){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findAll(PageRequest.of(page, 10))
                .getContent());
    }
    @Override
    public UserDomain getUserById(Short id){
        return UserConverter.convertToUserDomain(userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such user")));
    }
    @Override
    public MovieDomain getMovieById(Integer id){
        return MovieConverter.convertToMovieDomain(movieRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such movie")));
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
    public List<MovieDomain> getMovieListByGenreId(Short id){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByGenresId(id));
    }
    @Override
    public List<MovieDomain> getMovieListByDirectorId(Integer id){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByDirectorId(id));
    }
    @Override
    public List<MovieDomain> getMovieListByScreenwriterId(Integer id){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByScreenwriterId(id));
    }
    @Override
    public List<MovieDomain> getMovieListByCountryId(Short id){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByCountriesId(id));
    }
    @Override
    public List<MovieDomain> getMovieListByActorId(Integer id){
        return MovieConverter.convertToMovieDomainList(movieRepository
                .findByActorsId(id));
    }
    @Override
    public List<PersonDomain> getPersonListByNameContains(String name){
        return PersonConverter.convertToPersonDomainList(
                personRepository.findByNameLikeIgnoreCase(name));
    }
    @Override
    public UserDomain createUser(String name, RoleType role){
        return UserConverter.convertToUserDomain(userRepository
                .save(new User(name, "", role)));
    }
    @Override
    public UserDomain updateUserPwd(Short id, String pwd){
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No such user"));
        user.setPwd(pwd);
        return UserConverter.convertToUserDomain(userRepository.save(user));
    }
    @Override
    public ViewingDomain createViewing(LocalDate date, Short user_id,
                                       Integer movie_id, float ratingUser){
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
        personRepository.saveAll(movieData.getActors().stream()
                .map(k -> new Person(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addActor);
        genreRepository.saveAll(movieData.getGenres().stream()
                .map(k -> new Genre(k.getId(), k.getName()))
                .collect(Collectors.toList()))
                .forEach(movie::addGenre);
        countryRepository.saveAll(movieData.getCountries().stream()
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
