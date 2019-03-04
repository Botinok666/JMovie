package com.sfedu.JMovie;

import com.sfedu.JMovie.api.data.*;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.db.entity.*;
import com.sfedu.JMovie.domain.model.*;
import com.sfedu.JMovie.domain.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ConvertersTest {
    @Test
    public void testCountryConverters(){
        final Country entity = new Country((short)12, "New Zealand");
        final CountryDomain domain = CountryConverter.convertToCountryDomain(entity);
        //Проверим объект domain
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());

        final CountryData data = CountryConverter.convertToCountryDTO(domain);
        //Проверим объект data
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getName(), data.getName());
    }
    @Test
    public void testCountryListConverters(){
        final List<Country> entities = Arrays.asList(
                new Country((short)13, "Malaysia"),
                new Country((short)66, "Mexico"));
        final List<CountryDomain> domains =
                CountryConverter.convertToCountryDomainList(entities);
        //Проверим объекты domain
        assertNotNull(domains);
        assertFalse(domains.isEmpty());
        assertEquals(entities.get(0).getName(), domains.get(0).getName());
        assertEquals(entities.get(1).getName(), domains.get(1).getName());

        final List<CountryData> dataList =
                CountryConverter.convertToCountryListDTO(domains);
        //Проверим объекты data
        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());
        assertEquals(entities.get(0).getName(), dataList.get(0).getName());
        assertEquals(entities.get(1).getName(), dataList.get(1).getName());
    }
    @Test
    public void testGenreConverters(){
        final Genre entity = new Genre((short)12, "Melodrama");
        final GenreDomain domain = GenreConverter.convertToGenreDomain(entity);
        //Проверим объект domain
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());

        final GenreData data = GenreConverter.convertToGenreDTO(domain);
        //Проверим объект data
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getName(), data.getName());
    }
    @Test
    public void testGenreListConverters(){
        final List<Genre> entities = Arrays.asList(
                new Genre((short)13, "Comedy"),
                new Genre((short)66, "Noir"));
        final List<GenreDomain> domains =
                GenreConverter.convertToGenreDomainList(entities);
        //Проверим объекты domain
        assertNotNull(domains);
        assertFalse(domains.isEmpty());
        assertEquals(entities.get(0).getName(), domains.get(0).getName());
        assertEquals(entities.get(1).getName(), domains.get(1).getName());

        final List<GenreData> dataList =
                GenreConverter.convertToGenreListDTO(domains);
        //Проверим объекты data
        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());
        assertEquals(entities.get(0).getName(), dataList.get(0).getName());
        assertEquals(entities.get(1).getName(), dataList.get(1).getName());
    }
    @Test
    public void testPersonConverters(){
        final Person entity = new Person(12, "Bradley Cooper");
        final PersonDomain domain = PersonConverter.convertToPersonDomain(entity);
        //Проверим объект domain
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());

        final PersonData data = PersonConverter.convertToPersonDTO(domain);
        //Проверим объект data
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getName(), data.getName());
    }
    @Test
    public void testPersonListConverters(){
        final List<Person> entities = Arrays.asList(
                new Person(13, "Hillary Swank"),
                new Person(66, "Michael Shannon"));
        final List<PersonDomain> domains =
                PersonConverter.convertToPersonDomainList(entities);
        //Проверим объекты domain
        assertNotNull(domains);
        assertFalse(domains.isEmpty());
        assertEquals(entities.get(0).getName(), domains.get(0).getName());
        assertEquals(entities.get(1).getName(), domains.get(1).getName());

        final List<PersonData> dataList =
                PersonConverter.convertToPersonListDTO(domains);
        //Проверим объекты data
        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());
        assertEquals(entities.get(0).getName(), dataList.get(0).getName());
        assertEquals(entities.get(1).getName(), dataList.get(1).getName());
    }
    @Test
    public void testUserConverters(){
        final User entity = new User("Jack", "p@ssw0rd", RoleType.ROLE_ADMIN);
        entity.setId((short)12);
        final UserDomain domain = UserConverter.convertToUserDomain(entity);
        //Проверим объект domain
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getPwd(), domain.getPwd());
        assertEquals(entity.getRole(), domain.getRole());

        final UserData data = UserConverter.convertToUserDTO(domain);
        //Проверим объект data
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getName(), data.getName());
        assertEquals(entity.getPwd(), data.getPwd());
        assertEquals(entity.getRole(), data.getRole());
    }
    @Test
    public void testUserListConverters(){
        final List<User> entities = Arrays.asList(
                new User("Alice", "qwerty", RoleType.ROLE_ADMIN),
                new User("Bob", "12345", RoleType.ROLE_USER));
        final List<UserDomain> domains =
                UserConverter.convertToUserDomainList(entities);
        //Проверим объекты domain
        assertNotNull(domains);
        assertFalse(domains.isEmpty());
        assertEquals(entities.get(0).getName(), domains.get(0).getName());
        assertEquals(entities.get(0).getPwd(), domains.get(0).getPwd());
        assertEquals(entities.get(1).getName(), domains.get(1).getName());
        assertEquals(entities.get(1).getPwd(), domains.get(1).getPwd());

        final List<UserData> dataList =
                UserConverter.convertToUserListDTO(domains);
        //Проверим объекты data
        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());
        assertEquals(entities.get(0).getName(), dataList.get(0).getName());
        assertEquals(entities.get(0).getPwd(), dataList.get(0).getPwd());
        assertEquals(entities.get(1).getName(), dataList.get(1).getName());
        assertEquals(entities.get(1).getPwd(), dataList.get(1).getPwd());
    }
    @Test
    public void testViewingConverters(){
        final Viewing entity = new Viewing(LocalDate.now(), 6.6f);
        entity.setId(12);
        final ViewingDomain domain = ViewingConverter.convertToViewingDomain(entity);
        //Проверим объект domain
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getDate(), domain.getDate());
        assertEquals(entity.getRatingUser(), domain.getRatingUser(), .0);

        final ViewingData data = ViewingConverter.convertToViewingDTO(domain);
        //Проверим объект data
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getDate(), data.getDate());
        assertEquals(entity.getRatingUser(), data.getRatingUser(), .0);
    }
    @Test
    public void testViewingListConverters(){
        final List<Viewing> entities = Arrays.asList(
                new Viewing(LocalDate.now().minusDays(1), 4.9f),
                new Viewing(LocalDate.now().plusDays(1), 3.6f));
        final List<ViewingDomain> domains =
                ViewingConverter.convertToViewingDomainList(entities);
        //Проверим объекты domain
        assertNotNull(domains);
        assertFalse(domains.isEmpty());
        assertEquals(entities.get(0).getDate(), domains.get(0).getDate());
        assertEquals(entities.get(0).getRatingUser(), domains.get(0).getRatingUser(), .0);
        assertEquals(entities.get(1).getDate(), domains.get(1).getDate());
        assertEquals(entities.get(1).getRatingUser(), domains.get(1).getRatingUser(), .0);

        final List<ViewingData> dataList =
                ViewingConverter.convertToViewingListDTO(domains);
        //Проверим объекты data
        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());
        assertEquals(entities.get(0).getDate(), dataList.get(0).getDate());
        assertEquals(entities.get(0).getRatingUser(), dataList.get(0).getRatingUser(), .0);
        assertEquals(entities.get(1).getDate(), dataList.get(1).getDate());
        assertEquals(entities.get(1).getRatingUser(), dataList.get(1).getRatingUser(), .0);
    }
    @Test
    public void testMovieConverters(){
        final Movie entity = new Movie(
                12, "Бойцовский клуб", "Fight club",
                "https://st.kp.yandex.net/images/film_iphone/iphone360_361.jpg",
                (short)1999, "Mischief. Mayhem. Soap.", (short)131,
                "Терзаемый хронической бессонницей и отчаянно пытающийся вырваться " +
                        "из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, " +
                        "харизматического торговца мылом с извращенной философией. " +
                        "Тайлер уверен, что самосовершенствование - удел слабых, а " +
                        "саморазрушение - единственное, ради чего стоит жить." +
                        "Пройдет немного времени, и вот уже главные герои лупят друг друга " +
                        "почем зря на стоянке перед баром, и очищающий мордобой доставляет " +
                        "им высшее блаженство. Приобщая других мужчин к простым радостям " +
                        "физической жестокости, они основывают тайный Бойцовский Клуб, который " +
                        "имеет огромный успех. Но в концовке фильма всех ждет шокирующее " +
                        "открытие, которое может привести к непредсказуемым событиям",
                8.648f, 8.8f);
        final Person director = new Person(12, "Bradley Cooper");
        final Person screenwriter = new Person(12, "Bradley Cooper");
        entity.setDirector(director);
        entity.setScreenwriter(screenwriter);

        final MovieDomain domain = MovieConverter.convertToMovieDomain(entity);
        //Проверим объект domain
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getLocalizedTitle(), domain.getLocalizedTitle());
        assertEquals(entity.getOriginalTitle(), domain.getOriginalTitle());
        assertEquals(entity.getPosterLink(), domain.getPosterLink());
        assertEquals(entity.getYear(), domain.getYear());
        assertNotNull(domain.getDirector());
        assertEquals(director.getId(), domain.getDirector().getId());
        assertNotNull(domain.getScreenwriter());
        assertEquals(screenwriter.getId(), domain.getScreenwriter().getId());
        assertEquals(entity.getTagLine(), domain.getTagLine());
        assertEquals(entity.getRuntime(), domain.getRuntime());
        assertEquals(entity.getStoryline(), domain.getStoryline());
        assertEquals(entity.getRatingKP(), domain.getRatingKP(), .0);
        assertEquals(entity.getRatingIMDB(), domain.getRatingIMDB(), .0);

        final MovieData data = MovieConverter.convertToMovieDTO(domain);
        //Проверим объект data
        assertNotNull(data);
        assertEquals(entity.getId(), data.getId());
        assertEquals(entity.getLocalizedTitle(), data.getLocalizedTitle());
        assertEquals(entity.getOriginalTitle(), data.getOriginalTitle());
        assertEquals(entity.getPosterLink(), data.getPosterLink());
        assertEquals(entity.getYear(), data.getYear());
        assertNotNull(data.getDirector());
        assertEquals(director.getId(), data.getDirector().getId());
        assertNotNull(domain.getScreenwriter());
        assertEquals(screenwriter.getId(), data.getScreenwriter().getId());
        assertEquals(entity.getTagLine(), data.getTagLine());
        assertEquals(entity.getRuntime(), data.getRuntime());
        assertEquals(entity.getStoryline(), data.getStoryline());
        assertEquals(entity.getRatingKP(), data.getRatingKP(), .0);
        assertEquals(entity.getRatingIMDB(), data.getRatingIMDB(), .0);
    }
    @Test
    public void testMovieListConverters(){
        final List<Movie> entities = Arrays.asList(
                new Movie(13, "Фильм1", "Movie1",
                        "http://imdb.com/1.jpg", (short)1999,
                        "Tag1", (short)139, "Storyline1",
                        8.8f, 8.869f),
                new Movie(66, "Фильм2", "Movie2",
                        "http://imdb.com/2.jpg", (short)2009,
                        "Tag2", (short)140, "Storyline2",
                        8.9f, 8.879f));
        final Person director = new Person(12, "Bradley Cooper");
        final Person screenwriter = new Person(12, "Bradley Cooper");
        entities.forEach(movie -> movie.setDirector(director));
        entities.forEach(movie -> movie.setScreenwriter(screenwriter));
        final List<MovieDomain> domains =
                MovieConverter.convertToMovieDomainList(entities);
        //Проверим объекты domain
        assertNotNull(domains);
        assertFalse(domains.isEmpty());
        assertEquals(entities.get(0).getId(), domains.get(0).getId());
        assertEquals(entities.get(0).getLocalizedTitle(), domains.get(0).getLocalizedTitle());
        assertEquals(entities.get(0).getOriginalTitle(), domains.get(0).getOriginalTitle());
        assertEquals(entities.get(0).getPosterLink(), domains.get(0).getPosterLink());
        assertEquals(entities.get(0).getYear(), domains.get(0).getYear());
        assertEquals(entities.get(0).getTagLine(), domains.get(0).getTagLine());
        assertEquals(entities.get(0).getRuntime(), domains.get(0).getRuntime());
        assertEquals(entities.get(0).getStoryline(), domains.get(0).getStoryline());
        assertEquals(entities.get(0).getRatingKP(), domains.get(0).getRatingKP(), .0);
        assertEquals(entities.get(0).getRatingIMDB(), domains.get(0).getRatingIMDB(), .0);

        assertEquals(entities.get(1).getId(), domains.get(1).getId());
        assertEquals(entities.get(1).getLocalizedTitle(), domains.get(1).getLocalizedTitle());
        assertEquals(entities.get(1).getOriginalTitle(), domains.get(1).getOriginalTitle());
        assertEquals(entities.get(1).getPosterLink(), domains.get(1).getPosterLink());
        assertEquals(entities.get(1).getYear(), domains.get(1).getYear());
        assertEquals(entities.get(1).getTagLine(), domains.get(1).getTagLine());
        assertEquals(entities.get(1).getRuntime(), domains.get(1).getRuntime());
        assertEquals(entities.get(1).getStoryline(), domains.get(1).getStoryline());
        assertEquals(entities.get(1).getRatingKP(), domains.get(1).getRatingKP(), .0);
        assertEquals(entities.get(1).getRatingIMDB(), domains.get(1).getRatingIMDB(), .0);

        final List<MovieData> dataList =
                MovieConverter.convertToMovieListDTO(domains);
        //Проверим объекты data
        assertNotNull(dataList);
        assertFalse(dataList.isEmpty());
        assertEquals(entities.get(0).getId(), dataList.get(0).getId());
        assertEquals(entities.get(0).getLocalizedTitle(), dataList.get(0).getLocalizedTitle());
        assertEquals(entities.get(0).getOriginalTitle(), dataList.get(0).getOriginalTitle());
        assertEquals(entities.get(0).getPosterLink(), dataList.get(0).getPosterLink());
        assertEquals(entities.get(0).getYear(), dataList.get(0).getYear());
        assertEquals(entities.get(0).getTagLine(), dataList.get(0).getTagLine());
        assertEquals(entities.get(0).getRuntime(), dataList.get(0).getRuntime());
        assertEquals(entities.get(0).getStoryline(), dataList.get(0).getStoryline());
        assertEquals(entities.get(0).getRatingKP(), dataList.get(0).getRatingKP(), .0);
        assertEquals(entities.get(0).getRatingIMDB(), dataList.get(0).getRatingIMDB(), .0);

        assertEquals(entities.get(1).getId(), dataList.get(1).getId());
        assertEquals(entities.get(1).getLocalizedTitle(), dataList.get(1).getLocalizedTitle());
        assertEquals(entities.get(1).getOriginalTitle(), dataList.get(1).getOriginalTitle());
        assertEquals(entities.get(1).getPosterLink(), dataList.get(1).getPosterLink());
        assertEquals(entities.get(1).getYear(), dataList.get(1).getYear());
        assertEquals(entities.get(1).getTagLine(), dataList.get(1).getTagLine());
        assertEquals(entities.get(1).getRuntime(), dataList.get(1).getRuntime());
        assertEquals(entities.get(1).getStoryline(), dataList.get(1).getStoryline());
        assertEquals(entities.get(1).getRatingKP(), dataList.get(1).getRatingKP(), .0);
        assertEquals(entities.get(1).getRatingIMDB(), dataList.get(1).getRatingIMDB(), .0);
    }
}
