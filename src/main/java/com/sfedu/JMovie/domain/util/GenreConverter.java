package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.api.data.GenreData;
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
    public static GenreData convertToGenreDTO(GenreDomain genre){
        return new GenreData(genre.getId(), genre.getName());
    }
    public static List<GenreData> convertToGenreListDTO(List<GenreDomain> genres){
        return genres.stream()
                .map(GenreConverter::convertToGenreDTO)
                .collect(Collectors.toList());
    }
}
