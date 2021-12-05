package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieServiceImp implements MovieService {

    private final Movie movie;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImp(Movie movie, MovieRepository movieRepository) {
        this.movie = movie;
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie movie() {
        movieRepository.save(movie);
        return new Movie(movie.getTitle(), movie.getGenre(), movie.getLength());
    }

    @Override
    public Movie createMovie(String title, String genre, Integer length) {
        Movie movie = new Movie(title, genre, length);
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovie(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public Movie updateMovie(String title, String genre, Integer length) {
        Movie movie = movieRepository.findByTitle(title);
        if (movie != null) {
            movie.setGenre(genre);
            movie.setLength(length);
            movieRepository.save(movie);
            return movie;
        }
        return null;
    }

    @Override
    public void deleteMovie(Movie movieToDelete) {
        movieRepository.delete(movieToDelete);
    }

    @Override
    public List<Integer> getMovieLength(Integer length) {
        int movieHours = length / 60;
        int movieMinutes = length - movieHours * 60;
        return Arrays.asList(movieHours, movieMinutes);
    }
}
