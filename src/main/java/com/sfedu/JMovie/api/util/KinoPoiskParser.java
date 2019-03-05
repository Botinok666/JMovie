package com.sfedu.JMovie.api.util;

import com.sfedu.JMovie.JMovieApplication;
import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.api.data.GenreData;
import com.sfedu.JMovie.api.data.MovieData;
import com.sfedu.JMovie.api.data.PersonData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.*;
import java.util.Optional;

public final class KinoPoiskParser {
    private static final Logger log = LoggerFactory.getLogger(JMovieApplication.class);
    private KinoPoiskParser(){}
    private enum stateEnum{
        ID, Poster, Localized, Original, Year, Country, TagLine, Director,
        Screenwriter, Genre, Runtime, Actor, Storyline, RatingKP, RatingIMDB
    }

    private static CountryData convertToCountry(Element e){
        String href = e.attr("href");
        return new CountryData(
                Short.parseShort(href.substring(26, href.length() - 1)),
                e.text()
        );
    }
    private static PersonData convertToPerson(Element e){
        String href = e.attr("href");
        return new PersonData(
                Integer.parseInt(href.substring(6, href.length() - 1)),
                e.text()
        );
    }
    private static GenreData convertToGenre(Element e){
        String href = e.attr("href");
        return new GenreData(
                Short.parseShort(href.substring(24, href.length() - 1)),
                e.text()
        );
    }

    private static MovieData parse(Document document) throws NoSuchFieldException {
        MovieData movie = new MovieData();
        stateEnum state = stateEnum.ID;
        try {
            Element element = document
                    .selectFirst("div[class=\"js-ott-widget online_button_film\"]");
            movie.setId(Integer.parseInt(element
                    .attr("data-kp-film-id")));
            state = stateEnum.Poster;
            movie.setPosterLink(document
                    .selectFirst("link[rel=\"image_src\"]")
                    .attr("href"));
            state = stateEnum.Localized;
            movie.setLocalizedTitle(element
                    .attr("data-title"));
            state = stateEnum.Original;
            movie.setOriginalTitle(document
                    .selectFirst("span[itemprop=\"alternativeHeadline\"]")
                    .text());
            state = stateEnum.Year;
            element = document.selectFirst("table.info").child(0);
            movie.setYear(Short.parseShort(element
                    .child(0)
                    .selectFirst("a")
                    .text()));
            state = stateEnum.Country;
            element.child(1)
                    .select("a")
                    .stream()
                    .map(KinoPoiskParser::convertToCountry)
                    .forEach(movie::addCountry);
            state = stateEnum.TagLine;
            movie.setTagLine(element
                    .child(2)
                    .child(1)
                    .text());
            state = stateEnum.Director;
            movie.setDirector(Optional.ofNullable(element.child(3).selectFirst("a"))
                    .map(KinoPoiskParser::convertToPerson)
                    .orElse(new PersonData(0, "-")));
            state = stateEnum.Screenwriter;
            movie.setScreenwriter(Optional.ofNullable(element.child(4).selectFirst("a"))
                    .map(KinoPoiskParser::convertToPerson)
                    .orElse(new PersonData(0, "-")));
            state = stateEnum.Screenwriter;
            state = stateEnum.Genre;
            Optional.ofNullable(element.selectFirst("span[itemprop=\"genre\"]"))
                    .ifPresent(e -> e
                            .select("a")
                            .stream()
                            .map(KinoPoiskParser::convertToGenre)
                            .forEach(movie::addGenre)
                    );
            state = stateEnum.Runtime;
            movie.setRuntime(Short.parseShort(element
                    .select(".time")
                    .text()
                    .split(" ", 2)[0]));
            state = stateEnum.Actor;
            document.selectFirst("div#actorList > ul")
                    .children()
                    .stream()
                    .map(li -> li.child(0))
                    .filter(li -> !li.text().equals("..."))
                    .map(KinoPoiskParser::convertToPerson)
                    .forEach(movie::addActor);
            state = stateEnum.Storyline;
            movie.setStoryline(document
                    .selectFirst("div.film-synopsys")
                    .text());
            state = stateEnum.RatingKP;
            element = document.selectFirst("div.block_2");
            Optional.ofNullable(element.selectFirst("span.rating_ball"))
                    .ifPresent(e -> movie
                            .setRatingKP(Float.parseFloat(e.text()))
                    );
            state = stateEnum.RatingIMDB;
            float imdb;
            try {
                imdb = Float.parseFloat(element
                        .child(1)
                        .text()
                        .split(" ", 3)[1]);
            } catch (NumberFormatException e) {
                imdb = 0;
            }
            movie.setRatingIMDB(imdb);
            //Сохраним постер, если это ещё не сделано
            Path fName = Paths.get("")
                    .toAbsolutePath()
                    .resolve("src/main/webapp/frontend/images" +
                        movie.getPosterLink().substring(
                                movie.getPosterLink().lastIndexOf('/'))
                    );
            if (!(new File(fName.toString()).exists())) {
                try (InputStream in = new URL(movie.getPosterLink()).openStream()){
                    Files.copy(in, fName);
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
            return movie;
        } catch (Exception e) {
            throw new NoSuchFieldException("Error occurred when parsing " + state.toString());
        }
    }

    public static MovieData parseStream(InputStream stream) throws NoSuchFieldException {
        Document document;
        try {
            document = Jsoup.parse(stream, "windows-1251",
                    "https://kinopoisk.ru");
        } catch (IOException e){
            throw new NoSuchFieldException("Error occurred when opening stream");
        }
        return parse(document);
    }

    public static MovieData parseURL(URL url) throws NoSuchFieldException, SocketTimeoutException {
        Document document;
        try {
            document = Jsoup.parse(url, 5000);
        } catch (SocketTimeoutException e){
            throw new SocketTimeoutException(e.getMessage());
        } catch (IOException e) {
            throw new NoSuchFieldException("Ошибка при загрузке страницы");
        }
        return parse(document);
    }
}
