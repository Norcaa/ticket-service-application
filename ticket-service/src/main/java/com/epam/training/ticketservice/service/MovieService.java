package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;

import java.util.List;

public interface MovieService {

    Movie movie();

    Movie createMovie(String title, String genre, Integer length);

    List<Movie> getMovies();

    Movie getMovie(String title);

    List<Integer> getMovieLength(Integer length);

    Movie updateMovie(String title, String genre, Integer length);

    void deleteMovie(Movie movieToDelete);

    //void subscribe(Observer observer);
}
