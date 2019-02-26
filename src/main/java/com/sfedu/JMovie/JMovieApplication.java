package com.sfedu.JMovie;

import com.sfedu.JMovie.api.data.*;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.service.MovieService;
import com.sfedu.JMovie.domain.util.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JMovieApplication {
    private static final Logger log = LoggerFactory.getLogger(JMovieApplication.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(JMovieApplication.class, args);
	}

	@Bean
	public CommandLineRunner createData(MovieService service){
		return (args) -> {
		    UserData user = UserConverter.convertToUserDTO(
		            service.createUser("user", RoleType.ROLE_ADMIN));
		    service.updateUserPwd(user.getId(), passwordEncoder.encode("pass"));
		    log.info("User created: " + service.getUserByName(user.getName()).getId());

            try(ZipFile content = new ZipFile("./src/main/Resources/content.zip")) {
                Enumeration<? extends ZipEntry> entries = content.entries();
                short entryCount = 0;
                while (entries.hasMoreElements()) {
                    content.getInputStream(entries.nextElement());
                    entryCount++;
                }
                log.info("Entries in zip file: " + entryCount);
            }
            catch (IOException e){
                log.info("Error opening content.zip");
            }

            final MovieData movie1 = new MovieData(
                    42, "Фильм1", "Movie1",
                    "https://st.kp.yandex.net/images/film_iphone/iphone360_893916.jpg",
                    (short)1999, "TagLine1",
                    (short)139, "Storyline1", 8.8f, 8.869f);
            //Создаём записи в персонах, жанрах и странах для первого фильма
            movie1.setDirector(new PersonData(8, "Director1"));
            movie1.setScreenwriter(new PersonData(6, "Screenwriter1"));
            Arrays.asList(
                    new PersonData(3, "Actor1"),
                    new PersonData(24, "Actor2")
            ).forEach(movie1::addActor);
            Arrays.asList(
                    new GenreData((short)11, "horror"),
                    new GenreData((short)2, "drama"),
                    new GenreData((short)9, "thriller")
            ).forEach(movie1::addGenre);
            Collections.singletonList(
                    new CountryData((short)3, "Croatia")
            ).forEach(movie1::addCountry);
            service.createMovie(movie1);

            //Создаём второй фильм
            final MovieData movie2 = new MovieData(
                    23, "Фильм2", "Movie2",
                    "https://st.kp.yandex.net/images/film_iphone/iphone360_361.jpg",
                    (short)1999, "TagLine2",
                    (short)139, "Storyline2", 7.8f, 7.869f);
            //Создаём записи в персонах, жанрах и странах для первого фильма
            movie2.setDirector(new PersonData(8, "Director1"));
            movie2.setScreenwriter(new PersonData(1, "Screenwriter2"));
            Arrays.asList(
                    new PersonData(5, "Actor3"),
                    new PersonData(24, "Actor2")
            ).forEach(movie2::addActor);
            Arrays.asList(
                    new GenreData((short)8, "comedy"),
                    new GenreData((short)9, "thriller")
            ).forEach(movie2::addGenre);
            Collections.singletonList(
                    new CountryData((short)4, "Hungary")
            ).forEach(movie2::addCountry);
            service.createMovie(movie2);
            log.info("Countries: " + service.getAllCountries().size());
            log.info("Genres: " + service.getAllGenres().size());
		};
	}
}

