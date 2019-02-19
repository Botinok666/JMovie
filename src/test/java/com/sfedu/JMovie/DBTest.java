package com.sfedu.JMovie;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DBTest {
    @Autowired
    private CountryRepository coutryRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ViewingRepository viewingRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testSaveGenre(){
        final Short id = 6;
        final String name = "test";
        final Genre genre = new Genre(id, name);
        final Genre saved = genreRepository.save(genre);

        Assert.assertEquals(saved.getId(), id);
        Assert.assertEquals(saved.getName(), name);

        Optional<Genre> selected = genreRepository.findById(saved.getId());
        Assert.assertTrue(selected.isPresent());

        final Genre opt = selected.get();
        Assert.assertEquals(opt.getId(), saved.getId());
        Assert.assertEquals(opt.getName(), name);
    }
    @Test
    public void testSaveCountry(){
        final Short id = 6;
        final String name = "test";
        final Country country = new Country(id, name);
        final Country saved = coutryRepository.save(country);

        Assert.assertEquals(saved.getId(), id);
        Assert.assertEquals(saved.getName(), name);

        Optional<Country> selected = coutryRepository.findById(saved.getId());
        Assert.assertTrue(selected.isPresent());

        final Country opt = selected.get();
        Assert.assertEquals(opt.getId(), saved.getId());
        Assert.assertEquals(opt.getName(), name);
    }
    @Test
    public void testSavePerson(){
        final Integer id = 6;
        final String name = "test";
        final Person person = new Person(id, name);
        final Person saved = personRepository.save(person);

        Assert.assertEquals(saved.getId(), id);
        Assert.assertEquals(saved.getName(), name);

        Optional<Person> selected = personRepository.findById(saved.getId());
        Assert.assertTrue(selected.isPresent());

        final Person opt = selected.get();
        Assert.assertEquals(opt.getId(), saved.getId());
        Assert.assertEquals(opt.getName(), name);
    }
    @Test
    public void testSaveUpdateUser(){
        final String name = "user";
        final String pwd = "p@ssw0rd";
        final User user = new User(name, pwd);
        final User saved = userRepository.save(user);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getName(), name);
        Assert.assertEquals(saved.getPwd(), pwd);

        Optional<User> selected = userRepository.findById(saved.getId());
        Assert.assertTrue(selected.isPresent());

        final User opt = selected.get();
        Assert.assertNotNull(opt.getId());
        Assert.assertEquals(opt.getId(), saved.getId());
        Assert.assertEquals(opt.getName(), saved.getName());
        Assert.assertEquals(opt.getPwd(), saved.getPwd());

        opt.setPwd("password");
        final User updated = userRepository.save(user);
        Assert.assertEquals(opt.getId(), updated.getId());
        Assert.assertEquals(opt.getName(), updated.getName());
        Assert.assertEquals(opt.getPwd(), updated.getPwd());
    }
    @Test
    public void testSaveViewing(){
        final LocalDate localDate = LocalDate.now();
        final float rating = 4.9f;
        final Viewing viewing = new Viewing(localDate, rating);
        final Viewing saved = viewingRepository.save(viewing);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getDate(), localDate);
        Assert.assertEquals(saved.getRatingUser(), rating, 0.0);

        Optional<Viewing> selected = viewingRepository.findById(saved.getId());
        Assert.assertTrue(selected.isPresent());

        final Viewing opt = selected.get();
        Assert.assertNotNull(opt.getId());
        Assert.assertEquals(opt.getDate(), saved.getDate());
        Assert.assertEquals(opt.getRatingUser(), saved.getRatingUser(), 0.0);
    }
    @Test
    public void testSaveUpdateMovie(){
        final Integer id = 56;
        final String localized = "Тест";
        final String original = "test";
        final String poster = "http://individualki.ru/165644.jpg";
        final short year = 1999;
        final String tagLine = "Inspired by a True Friendship";
        final short runtime = 128;
        final String story = "All w\n" +
                "ork and no play makes Jack\n" +
                "a dull boy. All work and no pla\n" +
                "y makes Jack a dull boy. All work an\n" +
                "d no play makes Jack a dull boy. All work\n" +
                " and no play makes Jack a dull boy. All work a\n" +
                "nd no play makes Jack a dull boy. All work and no p\n" +
                "lay makes Jack a dull boy. All work and no play makes Ja\n" +
                "ck a dull boy. All work and no play makes Jack a dull boy. A\n" +
                "ll work and no play makes Jack a dull boy. All work and no play ma\n" +
                "kes Jack a dull boy. All work and no play makes Jack a dull boy. All w\n" +
                "ork and no play makes Jack a dull boy. All work and no play makes Jack a dul\n" +
                "l boy. All work and no play makes Jack a dull boy. All work and no pla\n" +
                "y makes Jack a dull boy. All work and no play makes Jack a dull bo\n" +
                "y. All work and no play makes Jack a dull boy.";
        final float ratingK = 6.6f;
        final float ratingI = 6.9f;
        final Movie movie = new Movie(id, localized, original, poster, year, tagLine,
                runtime, story, ratingK, ratingI);

        final Movie saved = movieRepository.save(movie);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getId(), id);
        Assert.assertEquals(saved.getLocalizedTitle(), localized);
        Assert.assertEquals(saved.getOriginalTitle(), original);
        Assert.assertEquals(saved.getPosterLink(), poster);
        Assert.assertEquals(saved.getYear(), year);
        Assert.assertNotNull(saved.getCountries());
        Assert.assertEquals(saved.getTagLine(), tagLine);
        Assert.assertNotNull(saved.getGenres());
        Assert.assertEquals(saved.getRuntime(), runtime);
        Assert.assertNotNull(saved.getActors());
        Assert.assertEquals(saved.getStoryline(), story);
        Assert.assertEquals(saved.getRatingKP(), ratingK, .0);
        Assert.assertEquals(saved.getRatingIMDB(), ratingI, .0);

        Optional<Movie> selected = movieRepository.findById(id);
        Assert.assertTrue(selected.isPresent());

        final Movie opt = selected.get();
        Assert.assertNotNull(opt.getId());
        Assert.assertEquals(opt.getId(), saved.getId());
        Assert.assertEquals(opt.getLocalizedTitle(), saved.getLocalizedTitle());
        Assert.assertEquals(opt.getOriginalTitle(), saved.getOriginalTitle());
        Assert.assertEquals(opt.getPosterLink(), saved.getPosterLink());
        Assert.assertEquals(opt.getYear(), saved.getYear());
        Assert.assertEquals(opt.getTagLine(), saved.getTagLine());
        Assert.assertEquals(opt.getRuntime(), saved.getRuntime());
        Assert.assertEquals(opt.getStoryline(), saved.getStoryline());
        Assert.assertEquals(opt.getRatingKP(), saved.getRatingKP(), .0);
        Assert.assertEquals(opt.getRatingIMDB(), saved.getRatingIMDB(), .0);

        opt.setOriginalTitle("xXx");
        opt.setLocalizedTitle("хХх");
        opt.setPosterLink("http://individualki.ru/111819.jpg");
        opt.setYear((short)2004);
        opt.setTagLine("Mischief. Mayhem. Soap.");
        opt.setRuntime((short)139);
        opt.setStoryline("An insomniac office worker and a devil-may-care soapmaker form " +
                "an underground fight club that evolves into something much, much more.");
        opt.setRatingIMDB(8.8f);
        opt.setRatingKP(8.867f);

        final Movie updated = movieRepository.save(movie);
        Assert.assertNotNull(opt.getId());
        Assert.assertEquals(opt.getId(), updated.getId());
        Assert.assertEquals(opt.getLocalizedTitle(), updated.getLocalizedTitle());
        Assert.assertEquals(opt.getOriginalTitle(), updated.getOriginalTitle());
        Assert.assertEquals(opt.getPosterLink(), updated.getPosterLink());
        Assert.assertEquals(opt.getYear(), updated.getYear());
        Assert.assertEquals(opt.getTagLine(), updated.getTagLine());
        Assert.assertEquals(opt.getRuntime(), updated.getRuntime());
        Assert.assertEquals(opt.getStoryline(), updated.getStoryline());
        Assert.assertEquals(opt.getRatingKP(), updated.getRatingKP(), .0);
        Assert.assertEquals(opt.getRatingIMDB(), updated.getRatingIMDB(), .0);
    }
    @Test
    public void testInterTableRelationships(){
        final Movie movie = new Movie(42, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        final Person person0 = new Person(8, "p0");
        final Person person1 = new Person(6, "p1");
        final List<Person> actors = new ArrayList<>();
        actors.add(new Person(3, "p2"));
        actors.add(new Person(24, "p3"));
        final List<Genre> genres = new ArrayList<>();
        genres.add(new Genre((short)11, "horror"));
        genres.add(new Genre((short)2, "drama"));
        genres.add(new Genre((short)9, "thriller"));
        final List<Country> country1 = new ArrayList<>();
        country1.add(new Country((short)3, "Croatia"));
        final Person p0s = personRepository.save(person0);
        final Person p1s = personRepository.save(person1);
        movie.setDirector(p0s);
        movie.setScreenwriter(p1s);
        final List<Person> a0s = personRepository.saveAll(actors);
        movie.setActors(a0s);
        final List<Genre> g0s = genreRepository.saveAll(genres);
        movie.setGenres(g0s);
        final List<Country> c0s = coutryRepository.saveAll(country1);
        movie.setCountries(c0s);

        final Movie saved = movieRepository.save(movie);
        p0s.getMoviesDirector().add(saved);
        p1s.getMoviesScreenwriter().add(saved);
        a0s.forEach(actor -> actor.getMoviesActor().add(saved));
        personRepository.saveAll(a0s);
        g0s.forEach(genre -> genre.getMovies().add(saved));
        c0s.forEach(country -> country.getMovies().add(saved));

        Assert.assertEquals(personRepository.findAll().size(), 4);
        Assert.assertEquals(genreRepository.findAll().size(), 3);
        Assert.assertEquals(coutryRepository.findAll().size(), 1);
        Assert.assertNotNull(saved.getDirector());
        Assert.assertEquals(saved.getDirector().getId(), p0s.getId());
        Assert.assertNotNull(saved.getScreenwriter());
        Assert.assertEquals(saved.getScreenwriter().getId(), p1s.getId());
        Assert.assertEquals(saved.getActors().size(), 2);
        Assert.assertEquals(saved.getActors().get(0).getId(), a0s.get(0).getId());
        Assert.assertEquals(saved.getActors().get(1).getId(), a0s.get(1).getId());
        Assert.assertEquals(saved.getGenres().size(), 3);
        Assert.assertEquals(saved.getGenres().get(0).getId(), g0s.get(0).getId());
        Assert.assertEquals(saved.getCountries().size(), 1);
        Assert.assertEquals(saved.getCountries().get(0).getName(), c0s.get(0).getName());

        final Movie movie2 = new Movie(23, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        final Person person4_3 = new Person(actors.get(0).getId(), actors.get(0).getName());
        final Person person5 = new Person(15, "p5");
        final List<Person> actors2 = new ArrayList<>();
        actors2.add(new Person(16, "p6"));
        actors2.add(new Person(person1.getId(), person1.getName()));
        actors2.add(new Person(actors.get(0).getId(), actors.get(0).getName()));
        final List<Genre> genres2 = new ArrayList<>();
        genres2.add(new Genre(genres.get(1).getId(), genres.get(1).getName()));
        final List<Country> country2 = new ArrayList<>();
        country2.add(new Country((short)5, "Hungary"));
        country2.add(new Country(country1.get(0).getId(), country1.get(0).getName()));
        final Person p4s = personRepository.save(person4_3);
        final Person p5s = personRepository.save(person5);
        movie2.setDirector(p4s);
        movie2.setScreenwriter(p5s);
        final List<Person> a1s = personRepository.saveAll(actors2);
        movie2.setActors(a1s);
        final List<Genre> g1s = genreRepository.saveAll(genres2);
        movie2.setGenres(g1s);
        final List<Country> c1s = coutryRepository.saveAll(country2);
        movie2.setCountries(c1s);

        final Movie saved2 = movieRepository.save(movie2);
        p4s.getMoviesDirector().add(saved2);
        p5s.getMoviesScreenwriter().add(saved2);
        a1s.forEach(actor -> actor.getMoviesActor().add(saved2));
        personRepository.saveAll(a1s);
        g1s.forEach(genre -> genre.getMovies().add(saved2));
        c1s.forEach(country -> country.getMovies().add(saved2));

        Assert.assertEquals(personRepository.findAll().size(), 6);
        Assert.assertEquals(p4s.getMoviesDirector().size(), 1);
        Assert.assertEquals(p5s.getMoviesScreenwriter().size(), 1);
        Assert.assertEquals(p5s.getMoviesActor().size(), 0);
        Assert.assertEquals(a1s.get(2).getId(), a0s.get(0).getId());
        Assert.assertEquals(a1s.get(2).getMoviesActor().size(), 2);
        Assert.assertEquals(genreRepository.findAll().size(), 3);
        Assert.assertEquals(g1s.get(0).getMovies().size(), 2);
        Assert.assertEquals(coutryRepository.findAll().size(), 2);
        Assert.assertEquals(c1s.get(0).getMovies().size(), 1);
        Assert.assertNotNull(saved2.getDirector());
        Assert.assertEquals(saved2.getDirector().getId(), p4s.getId());
        Assert.assertNotNull(saved2.getScreenwriter());
        Assert.assertEquals(saved2.getScreenwriter().getId(), p5s.getId());
        Assert.assertEquals(saved2.getActors().size(), 2);
        Assert.assertEquals(saved2.getActors().get(0).getId(), a1s.get(0).getId());
        Assert.assertEquals(saved2.getActors().get(1).getId(), a1s.get(1).getId());
        Assert.assertEquals(saved2.getGenres().size(), 1);
        Assert.assertEquals(saved2.getGenres().get(0).getId(), g1s.get(0).getId());
        Assert.assertEquals(saved2.getCountries().size(), 1);
        Assert.assertEquals(saved2.getCountries().get(0).getName(), c1s.get(0).getName());

        final User user1 = userRepository.save(new User("name", "p@ssw0rd"));
        final User user2 = userRepository.save(new User("user", "password"));
        final Viewing viewing1 = new Viewing(LocalDate.now().minusDays(1), 0.4f);
        viewing1.setMovie(movie);
        movie.getViewings().add(viewing1);
        viewing1.setUser(user1);
        user1.getViewings().add(viewing1);
        final Viewing sv1 = viewingRepository.save(viewing1);
        final Viewing viewing2 = new Viewing(LocalDate.now(), 0.8f);
        viewing2.setMovie(movie);
        movie.getViewings().add(viewing2);
        viewing2.setUser(user2);
        user2.getViewings().add(viewing2);
        final Viewing sv2 = viewingRepository.save(viewing2);
        final Viewing viewing3 = new Viewing(LocalDate.now().plusDays(1), 1.5f);
        viewing3.setMovie(movie2);
        movie2.getViewings().add(viewing3);
        viewing3.setUser(user1);
        user1.getViewings().add(viewing3);
        final Viewing sv3 = viewingRepository.save(viewing3);
        final Viewing viewing4 = new Viewing(LocalDate.now().plusWeeks(1), 1.6f);
        viewing4.setMovie(movie);
        movie.getViewings().add(viewing4);
        viewing4.setUser(user1);
        user1.getViewings().add(viewing4);
        final Viewing sv4 = viewingRepository.save(viewing4);

        Assert.assertEquals(viewingRepository.findAll().size(), 4);
        Assert.assertEquals(movie.getViewings().size(), 3);
        Assert.assertTrue(movie.getViewings().stream().anyMatch(
                viewing -> viewing.getId().equals(sv1.getId())));
        Assert.assertEquals(movie2.getViewings().size(), 1);
        Assert.assertTrue(movie2.getViewings().stream().anyMatch(
                viewing -> viewing.getId().equals(sv3.getId())));
        Assert.assertEquals(userRepository.findAll().size(), 2);
        Assert.assertEquals(user1.getViewings().size(), 3);
        Assert.assertTrue(user1.getViewings().stream().anyMatch(
                viewing -> viewing.getDate().equals(sv4.getDate())));
        Assert.assertEquals(user2.getViewings().size(), 1);
        Assert.assertTrue(user2.getViewings().stream().anyMatch(
                viewing -> viewing.getDate().equals(sv2.getDate())));
    }
}
