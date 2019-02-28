package com.sfedu.JMovie;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.sfedu.JMovie.domain.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ServiceTest {
    @TestConfiguration
    static class ServiceTestConfiguration{
        @Bean
        public IMovieService movieService(){
            return new MovieService();
        }
    }
    @Autowired
    private IMovieService movieService;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private CountryRepository countryRepository;
    @MockBean
    private ViewingRepository viewingRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MovieRepository movieRepository;

    @Before
    public void setUp(){
        final List<Person> actors = Arrays.asList(
                new Person(14, "Hillary Swank"),
                new Person(67, "Michael Shannon"));
        Mockito.when(personRepository.findTop10ByNameContainingIgnoreCase("Name"))
                .thenReturn(actors);
        Mockito.when(personRepository.saveAll(Mockito.anyList()))
                .thenReturn(actors);

        final List<Genre> genres = Arrays.asList(
                new Genre((short)15, "Comedy"),
                new Genre((short)68, "Noir"));
        Mockito.when(genreRepository.findAll())
                .thenReturn(genres);
        Mockito.when(genreRepository.saveAll(Mockito.anyList()))
                .thenReturn(genres);

        final List<Country> countries = Arrays.asList(
                new Country((short)13, "Malaysia"),
                new Country((short)66, "Mexico"));
        Mockito.when(countryRepository.findAll())
                .thenReturn(countries);
        Mockito.when(countryRepository.saveAll(Mockito.anyList()))
                .thenReturn(countries);

        final List<Movie> movies = Arrays.asList(
                new Movie(13, "Фильм1", "Movie1",
                        "http://imdb.com/1.jpg", (short)1999,
                        "Tag1", (short)139, "Storyline1",
                        8.8f, 8.869f),
                new Movie(66, "Фильм2", "Movie2",
                        "http://imdb.com/2.jpg", (short)2009,
                        "Tag2", (short)140, "Storyline2",
                        8.9f, 8.879f));
        movies.forEach(m -> {
            m.setDirector(actors.get(0));
            m.setScreenwriter(actors.get(0));
            countries.forEach(m::addCountry);
            genres.forEach(m::addGenre);
            actors.forEach(m::addActor);
        });
        Page<Movie> moviesPage = new PageImpl<>(movies);
        Mockito.when(movieRepository
                .findAll(Mockito.any(PageRequest.class)))
                .thenReturn(moviesPage);
        Mockito.when(movieRepository.findById(13)).thenReturn(Optional.of(movies.get(0)));
        Mockito.when(movieRepository.findByLocalizedTitleContainingOrOriginalTitleContainingAllIgnoreCase("Фильм"))
                .thenReturn(movies);
        Mockito.when(movieRepository.findByStorylineContainingIgnoreCase("Story"))
                .thenReturn(movies);
        Mockito.when(movieRepository.findByGenresId((short)1))
                .thenReturn(movies);
        Mockito.when(movieRepository.findByDirectorId(1))
                .thenReturn(movies);
        Mockito.when(movieRepository.findByScreenwriterId(1))
                .thenReturn(movies);
        Mockito.when(movieRepository.findByCountriesId((short)1))
                .thenReturn(movies);
        Mockito.when(movieRepository.findByActorsId(1))
                .thenReturn(movies);
        Mockito.when(movieRepository.save(Mockito.any(Movie.class)))
                .thenReturn(movies.get(0));

        final User user = new User("Jack", "", RoleType.ROLE_USER);
        user.setId((short)12);
        Mockito.when(userRepository.findByName("Jack"))
                .thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);

        final Viewing viewing = new Viewing(LocalDate.now(), 5.25f);
        viewing.setId(1);
        Mockito.when(viewingRepository.save(Mockito.any(Viewing.class)))
                .thenReturn(viewing);
    }
    @Test
    public void testGetAllGenres(){
        final List<GenreDomain> genres = movieService.getAllGenres();

        assertNotNull(genres);
        assertEquals(2, genres.size());
        assertEquals("Comedy", genres.get(0).getName());
        assertEquals("Noir", genres.get(1).getName());
    }
    @Test
    public void testGetAllCountries(){
        final List<CountryDomain> countries = movieService.getAllCountries();

        assertNotNull(countries);
        assertEquals(2, countries.size());
        assertEquals("Malaysia", countries.get(0).getName());
        assertEquals("Mexico", countries.get(1).getName());
    }
    @Test
    public void testGetTenMoviesPaged(){
        final List<MovieDomain> movies = movieService.getTenMoviesPaged(0);

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetUserByName(){
        final UserDomain user = movieService.getUserByName("Jack");

        assertNotNull(user);
        assertEquals("Jack", user.getName());
    }
    @Test
    public void testGetMovieById(){
        final MovieDomain movie = movieService.getMovieById(13);

        assertNotNull(movie);
        assertEquals("Movie1", movie.getOriginalTitle());
    }
    @Test
    public void testGetMovieListByTitleContains(){
        final List<MovieDomain> movies = movieService.getMovieListByTitleContains("Фильм");

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetMovieListByStorylineContains(){
        final List<MovieDomain> movies = movieService.getMovieListByStorylineContains("Story");

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetMovieListByGenreId(){
        final List<MovieDomain> movies = movieService.getMovieListByGenreId((short)1);

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetMovieListByDirectorId(){
        final List<MovieDomain> movies = movieService.getMovieListByDirectorId(1);

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetMovieListByScreenwriterId(){
        final List<MovieDomain> movies = movieService.getMovieListByScreenwriterId(1);

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetMovieListByCountryId(){
        final List<MovieDomain> movies = movieService.getMovieListByCountryId((short)1);

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetMovieListByActorId(){
        final List<MovieDomain> movies = movieService.getMovieListByActorId(1);

        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals("Tag1", movies.get(0).getTagLine());
        assertEquals("Tag2", movies.get(1).getTagLine());
    }
    @Test
    public void testGetPersonListByNameContains(){
        final List<PersonDomain> people = movieService.getPersonListByNameContains("Name");

        assertNotNull(people);
        assertEquals(2, people.size());
        assertEquals("Hillary Swank", people.get(0).getName());
        assertEquals("Michael Shannon", people.get(1).getName());
    }
    @Test
    public void testCreateUser(){
        final UserDomain user = movieService.createUser("Jack", RoleType.ROLE_USER);

        assertNotNull(user);
        assertEquals("Jack", user.getName());
        assertEquals("", user.getPwd());
    }
    @Test
    public void testUpdateUserPwd(){
        final UserDomain user = movieService.updateUserPwd((short)12, "");

        assertNotNull(user);
        assertEquals("Jack", user.getName());
        assertEquals("", user.getPwd());
    }
    @Test
    public void testCreateViewing(){
        final ViewingDomain viewing = movieService.createViewing(
                LocalDate.now(), (short)12, 13, 5.25f);

        assertNotNull(viewing);
        assertEquals(LocalDate.now(), viewing.getDate());
        assertEquals(5.25f, viewing.getRatingUser(), .0f);
    }
    @Test
    public void testCreateMovie(){
        final MovieData movie = new MovieData(13, "Фильм1",
                "Movie1", "http://imdb.com/1.jpg", (short)1999,
                "Tag1", (short)139, "Storyline1",
                8.8f, 8.869f);
        final List<CountryData> countries = Collections.singletonList(
                new CountryData((short) 13, "Malaysia"));
        final List<PersonData> actors = Collections.singletonList(
                new PersonData(14, "Hillary Swank"));
        final List<GenreData> genres = Collections.singletonList(
                new GenreData((short) 15, "Comedy"));
        movie.setDirector(actors.get(0));
        movie.setScreenwriter(actors.get(0));
        countries.forEach(movie::addCountry);
        genres.forEach(movie::addGenre);
        actors.forEach(movie::addActor);

        final MovieDomain domain = movieService.createMovie(movie);

        assertNotNull(domain);
        assertEquals("Movie1", domain.getOriginalTitle());
        assertEquals(8.869f, domain.getRatingIMDB(), .0f);
    }
}
