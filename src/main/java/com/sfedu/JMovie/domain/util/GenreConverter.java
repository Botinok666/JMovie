package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.db.entity.Genre;
import com.sfedu.JMovie.domain.model.GenreDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class GenreConverter {
    private GenreConverter(){}
    public static GenreDomain convertToGenreDomain(Genre genre){
        return new GenreDomain(genre.getId(), genre.getName());
    }
    public static List<GenreDomain> convertToGenreDomainList(List<Genre> genres){
        return genres.stream()
                .map(GenreConverter::convertToGenreDomain)
                .collect(Collectors.toList());
    }
}
