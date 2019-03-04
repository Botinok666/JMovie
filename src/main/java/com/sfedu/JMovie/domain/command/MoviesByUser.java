package com.sfedu.JMovie.domain.command;

import com.sfedu.JMovie.db.entity.Viewing;
import com.sfedu.JMovie.db.repository.ViewingRepository;
import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.model.MovieDomain;
import com.sfedu.JMovie.domain.util.MovieConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesByUser extends GetMovies {
    private ViewingRepository viewingRepository;
    public MoviesByUser(ViewingRepository viewingRepository) {
        this.viewingRepository = viewingRepository;
    }
    @Override
    public List<MovieDomain> get(Object param, int page, BoolW hasNext) {
        if (!(param instanceof Short)) {
            hasNext.setValue(false);
            return Collections.emptyList();
        }
        Slice<Viewing> viewings = viewingRepository
            .findByUserId((Short)param, PageRequest.of(page, 10));
        hasNext.setValue(viewings.hasNext());
        return MovieConverter.convertToMovieDomainList(viewings
                .stream()
                .map(Viewing::getMovie)
                .collect(Collectors.toList())
        );
    }
}
