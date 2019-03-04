package com.sfedu.JMovie;

import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.util.KinoPoiskParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ParserTest {
    @Test
    public void testParseURL(){
        try {
            final MovieData movie = KinoPoiskParser.parseURL(
                    new URL("https://www.kinopoisk.ru/film/57166/"));
            assertNotNull(movie);
            assertEquals("Бумер", movie.getLocalizedTitle());
            assertTrue(movie.getOriginalTitle().isEmpty());
            assertEquals((short)2003, movie.getYear());
            assertEquals(1, movie.getCountries().size());
            assertEquals("Россия", movie.getCountries().get(0).getName());
            assertEquals("Петр Буслов", movie.getDirector().getName());
            assertNotNull(movie.getScreenwriter());
            assertEquals(2, movie.getGenres().size());
            assertEquals((short)110, movie.getRuntime());
            assertEquals(10, movie.getActors().size());
            assertFalse(movie.getStoryline().isEmpty());
            assertFalse(movie.getTagLine().isEmpty());
        } catch (Exception ignored){
            fail();
        }
    }
}
