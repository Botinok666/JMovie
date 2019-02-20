package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.db.entity.Movie;
import com.sfedu.JMovie.domain.model.MovieDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class MovieConverter {
    private MovieConverter(){}

    public static MovieDomain convertToMovieDomain(Movie movie){
        return new MovieDomain(movie.getId(), movie.getLocalizedTitle(),
                movie.getOriginalTitle(), movie.getPosterLink(), movie.getYear(),
                movie.getDirector().getId(), movie.getScreenwriter().getId(),
                movie.getTagLine(), movie.getRuntime(), movie.getStoryline(),
                movie.getRatingKP(), movie.getRatingIMDB());
    }

    public static List<MovieDomain> convertToMovieDomainList(List<Movie> movies){
        return movies.stream()
                .map(MovieConverter::convertToMovieDomain)
                .collect(Collectors.toList());
    }
}
