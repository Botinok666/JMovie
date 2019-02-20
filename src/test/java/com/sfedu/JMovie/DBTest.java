package com.sfedu.JMovie;

import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.db.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DBTest {
    private CountryRepository countryRepository;
    private GenreRepository genreRepository;
    private PersonRepository personRepository;
    private UserRepository userRepository;
    private ViewingRepository viewingRepository;
    private MovieRepository movieRepository;

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

    //Сохранение одного жанра
    @Test
    public void testSaveGenre(){
        final Short id = 6;
        final String name = "test";
        final Genre genre = new Genre(id, name);
        //Сохраняем и получаем сохранённый объект
        final Genre saved = genreRepository.save(genre);
        assertEquals(saved.getId(), id);
        assertEquals(saved.getName(), name);
        //Получаем его же по ID из репозитория
        Optional<Genre> selectedOpt = genreRepository.findById(saved.getId());
        assertTrue(selectedOpt.isPresent());
        final Genre selected = selectedOpt.get();
        assertEquals(selected.getId(), saved.getId());
        assertEquals(selected.getName(), name);
    }
    //Сохранение одной страны
    @Test
    public void testSaveCountry(){
        final Short id = 6;
        final String name = "test";
        final Country country = new Country(id, name);
        //Сохраняем и получаем сохранённый объект
        final Country saved = countryRepository.save(country);
        assertEquals(saved.getId(), id);
        assertEquals(saved.getName(), name);
        //Получаем его же по ID из репозитория
        Optional<Country> selectedOpt = countryRepository.findById(saved.getId());
        assertTrue(selectedOpt.isPresent());
        final Country selected = selectedOpt.get();
        assertEquals(selected.getId(), saved.getId());
        assertEquals(selected.getName(), name);
    }
    //Сохранение одной персоны
    @Test
    public void testSavePerson(){
        final Integer id = 6;
        final String name = "test";
        final Person person = new Person(id, name);
        //Сохраняем и получаем сохранённый объект
        final Person saved = personRepository.save(person);
        assertEquals(saved.getId(), id);
        assertEquals(saved.getName(), name);
        //Получаем его же по ID из репозитория
        Optional<Person> selectedOpt = personRepository.findById(saved.getId());
        assertTrue(selectedOpt.isPresent());
        final Person selected = selectedOpt.get();
        assertEquals(selected.getId(), saved.getId());
        assertEquals(selected.getName(), name);
    }
    //Сохранение одного пользователя, затем обновление его данных
    @Test
    public void testSaveUpdateUser(){
        final String name = "user";
        final String pwd = "p@ssw0rd";
        final User user = new User(name, pwd);
        //Сохраняем и получаем сохранённый объект
        final User saved = userRepository.save(user);
        assertNotNull(saved.getId());
        assertEquals(saved.getName(), name);
        assertEquals(saved.getPwd(), pwd);
        //Получаем его же из репозитория по сгенерированному ID
        Optional<User> selectedOpt = userRepository.findById(saved.getId());
        assertTrue(selectedOpt.isPresent());
        final User selected = selectedOpt.get();
        assertNotNull(selected.getId());
        assertEquals(selected.getId(), saved.getId());
        assertEquals(selected.getName(), saved.getName());
        assertEquals(selected.getPwd(), saved.getPwd());
        //Меняем пароль
        selected.setPwd("password");
        //Сохраняем и получаем обновлённый объект
        final User updated = userRepository.save(user);
        assertEquals(selected.getId(), updated.getId());
        assertEquals(selected.getName(), updated.getName());
        assertEquals(selected.getPwd(), updated.getPwd());
    }
    //Сохранение одного просмотра
    @Test
    public void testSaveViewing(){
        final LocalDate localDate = LocalDate.now();
        final float rating = 4.9f;
        final Viewing viewing = new Viewing(localDate, rating);
        //Сохраняем и получаем сохранённый объект
        final Viewing saved = viewingRepository.save(viewing);
        assertNotNull(saved.getId());
        assertEquals(saved.getDate(), localDate);
        assertEquals(saved.getRatingUser(), rating, 0.0);
        //Получаем его же из репозитория по сгенерированному ID
        Optional<Viewing> selectedOpt = viewingRepository.findById(saved.getId());
        assertTrue(selectedOpt.isPresent());
        final Viewing selected = selectedOpt.get();
        assertNotNull(selected.getId());
        assertEquals(selected.getDate(), saved.getDate());
        assertEquals(selected.getRatingUser(), saved.getRatingUser(), 0.0);
    }
    //Сохранение одного фильма, затем обновление его данных
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
        //Сохраняем и получаем сохранённый объект
        final Movie saved = movieRepository.save(movie);
        assertEquals(saved.getId(), id);
        assertEquals(saved.getLocalizedTitle(), localized);
        assertEquals(saved.getOriginalTitle(), original);
        assertEquals(saved.getPosterLink(), poster);
        assertEquals(saved.getYear(), year);
        assertNotNull(saved.getCountries());
        assertEquals(saved.getTagLine(), tagLine);
        assertNotNull(saved.getGenres());
        assertEquals(saved.getRuntime(), runtime);
        assertNotNull(saved.getActors());
        assertEquals(saved.getStoryline(), story);
        assertEquals(saved.getRatingKP(), ratingK, .0);
        assertEquals(saved.getRatingIMDB(), ratingI, .0);
        //Получаем его же из репозитория по ID
        Optional<Movie> selectedOpt = movieRepository.findById(id);
        assertTrue(selectedOpt.isPresent());
        final Movie selected = selectedOpt.get();
        assertEquals(selected.getId(), saved.getId());
        assertEquals(selected.getLocalizedTitle(), saved.getLocalizedTitle());
        assertEquals(selected.getOriginalTitle(), saved.getOriginalTitle());
        assertEquals(selected.getPosterLink(), saved.getPosterLink());
        assertEquals(selected.getYear(), saved.getYear());
        assertNotNull(selected.getCountries());
        assertEquals(selected.getTagLine(), saved.getTagLine());
        assertNotNull(selected.getGenres());
        assertEquals(selected.getRuntime(), saved.getRuntime());
        assertNotNull(selected.getActors());
        assertEquals(selected.getStoryline(), saved.getStoryline());
        assertEquals(selected.getRatingKP(), saved.getRatingKP(), .0);
        assertEquals(selected.getRatingIMDB(), saved.getRatingIMDB(), .0);
        //Обновляем поля
        selected.setOriginalTitle("xXx");
        selected.setLocalizedTitle("хХх");
        selected.setPosterLink("http://individualki.ru/111819.jpg");
        selected.setYear((short)2004);
        selected.setTagLine("Mischief. Mayhem. Soap.");
        selected.setRuntime((short)139);
        selected.setStoryline("An insomniac office worker and a devil-may-care soapmaker form " +
                "an underground fight club that evolves into something much, much more.");
        selected.setRatingIMDB(8.8f);
        selected.setRatingKP(8.867f);
        //Сохраняем и получаем обновлённый объект
        final Movie updated = movieRepository.save(movie);
        assertEquals(selected.getId(), updated.getId());
        assertEquals(selected.getLocalizedTitle(), updated.getLocalizedTitle());
        assertEquals(selected.getOriginalTitle(), updated.getOriginalTitle());
        assertEquals(selected.getPosterLink(), updated.getPosterLink());
        assertEquals(selected.getYear(), updated.getYear());
        assertNotNull(updated.getCountries());
        assertEquals(selected.getTagLine(), updated.getTagLine());
        assertNotNull(updated.getGenres());
        assertEquals(selected.getRuntime(), updated.getRuntime());
        assertNotNull(updated.getActors());
        assertEquals(selected.getStoryline(), updated.getStoryline());
        assertEquals(selected.getRatingKP(), updated.getRatingKP(), .0);
        assertEquals(selected.getRatingIMDB(), updated.getRatingIMDB(), .0);
    }
    //Сохранение фильма и списков жанров, стран и актёров
    @Test
    public void testMovieWithRelatedEntities(){
        final Movie movie = new Movie(
                42, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        //Создаём записи в персонах, жанрах и странах для первого фильма
        final Person director = personRepository.save(new Person(8, "p0"));
        final Person screenwriter = personRepository.save(new Person(6, "p1"));
        final List<Person> actors = personRepository.saveAll(Arrays.asList(
                new Person(3, "p2"),
                new Person(24, "p3")
        ));
        final List<Genre> genres = genreRepository.saveAll(Arrays.asList(
                new Genre((short)11, "horror"),
                new Genre((short)2, "drama"),
                new Genre((short)9, "thriller")
        ));
        final List<Country> countries = countryRepository.saveAll(Collections.singletonList(
                new Country((short) 3, "Croatia")
        ));
        //Заполняем поля и списки первого фильма
        movie.setDirector(director);
        movie.setScreenwriter(screenwriter);
        actors.forEach(movie::addActor);
        genres.forEach(movie::addGenre);
        countries.forEach(movie::addCountry);
        //Сохраняем обновлённый фильм и получаем его объект
        final Movie updatedMovie = movieRepository.save(movie);
        //Проверяем связанные таблицы
        assertEquals(personRepository.findAll().size(), 4);
        assertEquals(genreRepository.findAll().size(), 3);
        assertEquals(countryRepository.findAll().size(), 1);
        //Проверяем режиссёра
        assertNotNull(updatedMovie.getDirector());
        assertEquals(updatedMovie.getDirector().getId(), director.getId());
        assertEquals(director.getMoviesDirector().size(), 1);
        //Проверяем сценариста
        assertNotNull(updatedMovie.getScreenwriter());
        assertEquals(updatedMovie.getScreenwriter().getId(), screenwriter.getId());
        assertEquals(screenwriter.getMoviesScreenwriter().size(), 1);
        //Проверяем актёров
        assertEquals(updatedMovie.getActors().size(), 2);
        assertEquals(updatedMovie.getActors().get(0).getId(), actors.get(0).getId());
        assertEquals(actors.get(0).getMoviesActor().size(), 1);
        assertEquals(updatedMovie.getActors().get(1).getId(), actors.get(1).getId());
        assertEquals(actors.get(1).getMoviesActor().size(), 1);
        //Проверяем жанры
        assertEquals(updatedMovie.getGenres().size(), 3);
        assertEquals(updatedMovie.getGenres().get(0).getId(), genres.get(0).getId());
        assertEquals(genres.get(0).getMovies().size(), 1);
        assertEquals(updatedMovie.getGenres().get(1).getId(), genres.get(1).getId());
        assertEquals(genres.get(1).getMovies().size(), 1);
        assertEquals(updatedMovie.getGenres().get(2).getId(), genres.get(2).getId());
        assertEquals(genres.get(2).getMovies().size(), 1);
        //Проверяем страны
        assertEquals(updatedMovie.getCountries().size(), 1);
        assertEquals(updatedMovie.getCountries().get(0).getName(), countries.get(0).getName());
        assertEquals(countries.get(0).getMovies().size(), 1);
    }
    //Сохранение 2 фильмов, для каждого фильма по списку жанров, стран и актёров
    //Списки жанров, стран и актёров содержат повторения
    @Test
    public void testTwoMoviesWithRelatedEntities(){
        final Movie movie1 = new Movie(
                42, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        //Создаём записи в персонах, жанрах и странах для первого фильма
        final Person director1 = personRepository.save(new Person(8, "p0"));
        final Person screenwriter1 = personRepository.save(new Person(6, "p1"));
        final List<Person> actors1 = personRepository.saveAll(Arrays.asList(
                new Person(3, "p2"),
                new Person(24, "p3")
        ));
        final List<Genre> genres1 = genreRepository.saveAll(Arrays.asList(
                new Genre((short)11, "horror"),
                new Genre((short)2, "drama"),
                new Genre((short)9, "thriller")
        ));
        final List<Country> countries1 = countryRepository.saveAll(Collections.singletonList(
                new Country((short) 3, "Croatia")
        ));
        //Заполняем поля и списки первого фильма
        movie1.setDirector(director1);
        movie1.setScreenwriter(screenwriter1);
        actors1.forEach(movie1::addActor);
        genres1.forEach(movie1::addGenre);
        countries1.forEach(movie1::addCountry);
        //Сохраняем обновлённый фильм 1
        movieRepository.save(movie1);
        //Создаём второй фильм, несвязанные поля одинаковые, только ID другой
        final Movie movie2 = new Movie(
                23, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        //Режиссёр "новый"
        final Person director2 = personRepository.save(new Person(15, "p4"));
        //Сценаристом будет актёр-0 из первого фильма
        final Person screenwriter2 = personRepository.save(new Person(
                actors1.get(0).getId(), actors1.get(0).getName()));
        //Один актёр будет новый, два других будут из первого фильма
        final List<Person> actors2 = personRepository.saveAll(Arrays.asList(
                new Person(actors1.get(1).getId(), actors1.get(1).getName()),
                new Person(actors1.get(0).getId(), actors1.get(0).getName()),
                new Person(16, "p5")
        ));
        //Жанр будет один, взятый из первого фильма
        final List<Genre> genres2 = genreRepository.saveAll(Collections.singletonList(
                new Genre(genres1.get(2).getId(), genres1.get(2).getName())
        ));
        //Страна будет одна новая, одна из первого фильма
        final List<Country> countries2 = countryRepository.saveAll(Arrays.asList(
                new Country((short)4, "Hungary"),
                new Country(countries1.get(0).getId(), countries1.get(0).getName())
        ));
        //Заполняем поля и списки второго фильма
        movie2.setDirector(director2);
        movie2.setScreenwriter(screenwriter2);
        actors2.forEach(movie2::addActor);
        genres2.forEach(movie2::addGenre);
        countries2.forEach(movie2::addCountry);
        //Сохраняем обновлённый фильм 2
        final Movie updatedMovie2 = movieRepository.save(movie2);

        //Проверяем связанные таблицы
        assertEquals(personRepository.findAll().size(), 6);
        assertEquals(genreRepository.findAll().size(), 3);
        assertEquals(countryRepository.findAll().size(), 2);

        //Проверяем режиссёра
        assertEquals(director2.getMoviesDirector().size(), 1);
        assertEquals(director2.getMoviesActor().size(), 0);
        assertEquals(director2.getMoviesScreenwriter().size(), 0);

        //Проверяем сценариста, он же актёр из первого и второго фильмов
        assertEquals(screenwriter2.getMoviesDirector().size(), 0);
        assertEquals(screenwriter2.getMoviesActor().size(), 2);
        assertEquals(screenwriter2.getMoviesScreenwriter().size(), 1);

        //Проверяем актёров
        assertEquals(updatedMovie2.getActors().size(), 3);
        //Актёр-0 снимался в 2 фильмах
        assertEquals(actors2.get(0).getMoviesActor().size(), 2);
        assertEquals(actors2.get(0).getMoviesDirector().size(), 0);
        assertEquals(actors2.get(0).getMoviesScreenwriter().size(), 0);
        //Актёр-1 снимался в 2 фильмах и был режиссёром
        assertEquals(actors2.get(0).getMoviesActor().size(), 2);
        assertEquals(actors2.get(0).getMoviesDirector().size(), 0);
        assertEquals(actors2.get(0).getMoviesScreenwriter().size(), 1);
        //Актёр-2 снимался в 1 фильме
        assertEquals(actors2.get(0).getMoviesActor().size(), 1);
        assertEquals(actors2.get(0).getMoviesDirector().size(), 0);
        assertEquals(actors2.get(0).getMoviesScreenwriter().size(), 0);

        //Проверяем жанры
        assertEquals(updatedMovie2.getGenres().size(), 1);
        //Жанр из второго фильма был и в первом
        assertEquals(genres2.get(0).getMovies().size(), 2);
        //Два жанра из первого были только в нём
        assertEquals(genres1.get(0).getMovies().size(), 1);
        assertEquals(genres1.get(1).getMovies().size(), 1);
        assertEquals(genres1.get(2).getMovies().size(), 2);

        //Проверяем страны
        assertEquals(updatedMovie2.getCountries().size(), 2);
        //Первая страна появилась только во втором фильме
        assertEquals(countries2.get(0).getMovies().size(), 1);
        //Вторая фигурировала в двух фильмах
        assertEquals(countries2.get(1).getMovies().size(), 2);
    }
    //Сохранение 4 просмотров, задействовано 2 фильма и 2 пользователя
    @Test
    public void testFourViewingsWithRelatedEntities(){
        final Movie movie1 = new Movie(
                42, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        //Создаём записи в персонах, жанрах и странах для первого фильма
        final Person director1 = personRepository.save(new Person(8, "p0"));
        final Person screenwriter1 = personRepository.save(new Person(6, "p1"));
        final Person actor1 = personRepository.save(new Person(10, "p2"));
        final Genre genre1 = genreRepository.save(new Genre((short)11, "horror"));
        final Country country1 = countryRepository.save(new Country((short)3, "Croatia"));
        //Заполняем поля и списки первого фильма
        movie1.setDirector(director1);
        movie1.setScreenwriter(screenwriter1);
        movie1.addActor(actor1);
        movie1.addGenre(genre1);
        movie1.addCountry(country1);
        //Сохраняем обновлённый фильм 1
        movieRepository.save(movie1);
        //Создаём второй фильм, несвязанные поля одинаковые, только ID другой
        final Movie movie2 = new Movie(
                23, "Тест", "test",
                "http://imdb.com", (short)1999, "Mischief. Mayhem. Soap.",
                (short)139, "Storyline", 8.8f, 8.869f);
        //Создаём записи в персонах, жанрах и странах для первого фильма
        final Person director2 = personRepository.save(new Person(8, "p0"));
        final Person screenwriter2 = personRepository.save(new Person(6, "p1"));
        final Person actor2 = personRepository.save(new Person(10, "p2"));
        final Genre genre2 = genreRepository.save(new Genre((short)11, "horror"));
        final Country country2 = countryRepository.save(new Country((short)3, "Croatia"));
        //Заполняем поля и списки второго фильма
        movie2.setDirector(director2);
        movie2.setScreenwriter(screenwriter2);
        movie2.addActor(actor2);
        movie2.addGenre(genre2);
        movie2.addCountry(country2);
        //Сохраняем обновлённый фильм 2
        movieRepository.save(movie2);

        //Проверим количество записей
        assertEquals(personRepository.findAll().size(), 3);
        assertEquals(genreRepository.findAll().size(), 1);
        assertEquals(countryRepository.findAll().size(), 1);

        //Создадим двух пользователей
        final User user1 = userRepository.save(new User("name", "p@ssw0rd"));
        final User user2 = userRepository.save(new User("user", "password"));

        //Создадим 4 просмотра
        final Viewing viewing1 = new Viewing(LocalDate.now().minusDays(1), 0.4f);
        viewing1.setMovie(movie1);
        viewing1.setUser(user1);
        //Первый просмотр: пользователь 1, фильм 1
        viewingRepository.save(viewing1);
        final Viewing viewing2 = new Viewing(LocalDate.now(), 0.8f);
        viewing2.setMovie(movie1);
        viewing2.setUser(user2);
        //Второй просмотр: пользователь 2, фильм 1
        viewingRepository.save(viewing2);
        final Viewing viewing3 = new Viewing(LocalDate.now().plusDays(1), 1.5f);
        viewing3.setMovie(movie2);
        viewing3.setUser(user1);
        //Третий просмотр: пользователь 1, фильм 2
        viewingRepository.save(viewing3);
        final Viewing viewing4 = new Viewing(LocalDate.now().plusWeeks(1), 1.6f);
        viewing4.setMovie(movie1);
        viewing4.setUser(user1);
        //Четвёртый просмотр: пользователь 1, фильм 1
        viewingRepository.save(viewing4);

        //Проверим просмотры
        assertEquals(viewingRepository.findAll().size(), 4);
        //Просмотры фильмов
        assertEquals(movie1.getViewings().size(), 3);
        assertEquals(movie2.getViewings().size(), 1);
        //Просмотры пользователей
        assertEquals(user1.getViewings().size(), 3);
        assertEquals(user2.getViewings().size(), 1);
    }
}
