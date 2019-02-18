package com.sfedu.JMovie;

import com.sfedu.JMovie.db.entity.Country;
import com.sfedu.JMovie.db.entity.Genre;
import com.sfedu.JMovie.db.entity.Person;
import com.sfedu.JMovie.db.entity.User;
import com.sfedu.JMovie.db.repository.CountryRepository;
import com.sfedu.JMovie.db.repository.GenreRepository;
import com.sfedu.JMovie.db.repository.PersonRepository;
import com.sfedu.JMovie.db.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
