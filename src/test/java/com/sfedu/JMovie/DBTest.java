package com.sfedu.JMovie;

import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
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
        Assert.assertEquals(saved.getTagLine(), tagLine);
        Assert.assertEquals(saved.getRuntime(), runtime);
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
    }
}
