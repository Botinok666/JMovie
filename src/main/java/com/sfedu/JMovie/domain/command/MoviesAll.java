package com.sfedu.JMovie.domain.command;

import com.sfedu.JMovie.db.entity.Movie;
import com.sfedu.JMovie.db.repository.MovieRepository;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.model.MovieDomain;
import com.sfedu.JMovie.domain.util.MovieConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

public class MoviesAll extends GetMovies {
    private MovieRepository movieRepository;
    public MoviesAll(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    @Override
    public List<MovieDomain> get(Object param, int page, BoolW hasNext) {
        Slice<Movie> result = movieRepository
                .findAll(PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
}
