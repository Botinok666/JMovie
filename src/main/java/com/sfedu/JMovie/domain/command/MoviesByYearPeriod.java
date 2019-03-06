package com.sfedu.JMovie.domain.command;

import com.sfedu.JMovie.db.entity.Movie;
import com.sfedu.JMovie.db.repository.MovieRepository;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.model.MovieDomain;
import com.sfedu.JMovie.domain.util.MovieConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.List;

public class MoviesByYearPeriod extends GetMovies {
    private MovieRepository movieRepository;
    public MoviesByYearPeriod(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    @Override
    public List<MovieDomain> get(Object param, int page, BoolW hasNext) {
        if (!(param instanceof List)) {
            hasNext.setValue(false);
            return Collections.emptyList();
        }
        Short start = (Short)((List) param).get(0);
        Short end = (Short)((List) param).get(1);
        Slice<Movie> result = movieRepository
                .findByYearBetween(start, end, PageRequest.of(page, 10));
        hasNext.setValue(result.hasNext());
        return MovieConverter.convertToMovieDomainList(result.getContent());
    }
}
