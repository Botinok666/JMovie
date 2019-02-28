package com.sfedu.JMovie;

import com.sfedu.JMovie.api.data.*;
import com.sfedu.JMovie.api.util.KinoPoiskParser;
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
import java.nio.charset.Charset;
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

            log.info("Countries: " + service.getAllCountries().size());
            log.info("Genres: " + service.getAllGenres().size());
		};
	}
}

