package com.sfedu.JMovie;

import com.sfedu.JMovie.api.data.*;
import com.sfedu.JMovie.api.util.KinoPoiskParser;
import com.sfedu.JMovie.db.RoleType;
import com.sfedu.JMovie.domain.service.IMovieService;
import com.sfedu.JMovie.domain.util.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JMovieApplication {
    private static final Logger log = LoggerFactory.getLogger(JMovieApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JMovieApplication.class, args);
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
    @Profile("!test")
	public CommandLineRunner createData(IMovieService service){
		return (args) -> {
		    //Создадим одного пользователя - админа
            if (service.getAllCountries().size() > 0){
                log.info("Database already filled");
                return;
            }

		    UserData user = UserConverter.convertToUserDTO(
		            service.createUser("admin", RoleType.ROLE_ADMIN));
		    service.updateUserPwd(user.getId(), passwordEncoder().encode("pass"));
            //Добавим все фильмы из zip файла
            try (ZipFile content = new ZipFile("./src/main/Resources/content",
                    Charset.forName("cp866"))) {
                Enumeration<? extends ZipEntry> entries = content.entries();
                short entryCount = 0;
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    try {
                        service.createMovie(KinoPoiskParser.parseStream(
                                content.getInputStream(entry)
                        ));
                    }
                    catch (NoSuchFieldException e){
                        log.info(String.format("Error in entry %s: %s",
                                entry.getName(), e.getMessage()));
                    }
                    entryCount++;
                }
                log.info("Entries processed from zip file: " + entryCount);
            }
            catch (IOException e){
                log.info("Error opening content.zip");
            }
            //Выведем количество стран и жанров
            log.info("Countries: " + service.getAllCountries().size());
            log.info("Genres: " + service.getAllGenres().size());
		};
	}
}

