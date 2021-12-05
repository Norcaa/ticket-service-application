package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class MovieCommendHandler {

    private final MovieService movieService;
    private final UserService userService;

    public MovieCommendHandler(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    // todo trycatch
    @ShellMethod(value = "Create a movie", key = "create movie")
    public String createMovie(final String title, String genre, Integer length) {
        if (userService.adminLoggedIn()) {
            Movie createdMovie = movieService.createMovie(title, genre, length);
            return "Movie created.";
        }
        return "You are not an admin";
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public String listMovies() {
        List<Movie> movies = movieService.getMovies();
        if (!movies.isEmpty()) {
            String result = "";
            for (int i = 0; i < movies.size(); i++) {
                result += String.format("%s (%s, %d minutes)%n", movies.get(i).getTitle(),
                        movies.get(i).getGenre(), movies.get(i).getLength());
            }
            return result;
        }
        return "There are no movies at the moment";
    }

    @ShellMethod(value = "Update a movie", key = "update movie")
    public String updateMovie(final String title, String genre, Integer length) {
        if (userService.adminLoggedIn()) {
            movieService.updateMovie(title, genre, length);
            return "Movie updated";
        }
        return "You are not an admin";
    }

    @ShellMethod(value = "Delete a movie", key = "delete movie")
    public String deleteMovie(final String title) {
        if (userService.adminLoggedIn()) {
            Movie movieToDelete = movieService.getMovies().stream()
                    .filter(room -> title.equals(room.getTitle()))
                    .findAny()
                    .orElse(null);
            if (movieToDelete != null) {
                movieService.deleteMovie(movieToDelete);
                return "Movie deleted";
            } else {
                return "Movie do not exists.";
            }
        }
        return "You are not an admin";
    }
}
