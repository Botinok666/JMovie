package com.sfedu.JMovie.domain.command;

import com.sfedu.JMovie.domain.BoolW;
import com.sfedu.JMovie.domain.model.MovieDomain;

import java.util.List;

public abstract class GetMovies {
    GetMovies(){}
    public abstract List<MovieDomain> get(Object param, int page, BoolW hasNext);
}
