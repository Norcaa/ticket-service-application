package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class MovieTest {

    private static final String ABRAKADABRA_TITLE = "Abrakadabra";
    private static final String SATANTANGO_TITLE = "Sátántangó";
    private static final Movie ABRAKADABRA_MOVIE = new Movie(ABRAKADABRA_TITLE, "krimi", 180);
    private static final Movie SATANTANGO_MOVIE = new Movie(SATANTANGO_TITLE, "drama", 450);
    private static final List<Movie> MOVIES = List.of(ABRAKADABRA_MOVIE, SATANTANGO_MOVIE);

    private MovieService underTest;

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private Movie movie;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMoviesShouldReturnEmptyListWhenNoMoviesAdded() {
        // Given
        List<Movie> expectedResult = Collections.emptyList();
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        // When
        List<Movie> actualResult = underTest.getMovies();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetMoviesShouldReturnTheListOfMoviesWhenNotEmpty() {
        // Given
        List<Movie> expectedResult = Collections.singletonList(ABRAKADABRA_MOVIE);
        BDDMockito.given(movieRepository.findAll()).willReturn(Collections.singletonList(ABRAKADABRA_MOVIE));
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 180);
        // When
        List<Movie> actualResult = underTest.getMovies();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetMovieShouldReturnNullWhenNoMovieFounded() {
        // Given
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        // When
        Movie actualResult = underTest.getMovie(ABRAKADABRA_TITLE);
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testGetMovieShouldReturnMovieWhenMovieFounded() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 180);
        // When
        Movie actualResult = underTest.getMovie(ABRAKADABRA_TITLE);
        // Then
        Assertions.assertEquals(ABRAKADABRA_MOVIE, actualResult);
    }

    @Test
    public void testUpdateMovieShouldReturnNullWhenNoMovieFounded() {
        // Given
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        // When
        Movie actualResult = underTest.updateMovie(ABRAKADABRA_TITLE,"krimi", 180);
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testUpdateMovieShouldReturnTheNewMovieWhenMovieFounded() {
        // Given
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        underTest.createMovie(ABRAKADABRA_TITLE, "drama", 60);
        // When
        Movie actualResult = underTest.updateMovie(ABRAKADABRA_TITLE,"krimi", 180);
        // Then
        Assertions.assertEquals(ABRAKADABRA_MOVIE, actualResult);
    }

    @Test
    public void testGetMoviesShouldReturnNullWhenMovieDeleted() {
        // Given
        BDDMockito.given(movieRepository.findAll()).willReturn(null);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 180);
        underTest.deleteMovie(ABRAKADABRA_MOVIE);
        // When
        List<Movie> actualResult = underTest.getMovies();
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testMoviesEqualsShouldReturnTrue() {
        // Given
        Boolean expectedResult = true;
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 180);
        // When
        Boolean actualResult = underTestMovie.equals(underTest.getMovie(ABRAKADABRA_TITLE));
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMoviesEqualsWithNullShouldReturnFalse() {
        // Given
        Boolean expectedResult = false;
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 180);
        // When
        Boolean actualResult = underTestMovie.equals(null);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMoviesEqualsWithDifferentClassShouldReturnFalse() {
        // Given
        Boolean expectedResult = false;
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 450);
        // When
        Boolean actualResult = underTestMovie.equals(ABRAKADABRA_TITLE);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMoviesEqualsWithDifferentTitleShouldReturnFalse() {
        // Given
        Boolean expectedResult = false;
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(SATANTANGO_TITLE, "krimi", 180);
        // When
        Boolean actualResult = underTestMovie.equals(underTest.getMovie(ABRAKADABRA_TITLE));
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMoviesEqualsWithDifferentGenreShouldReturnFalse() {
        // Given
        Boolean expectedResult = false;
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(ABRAKADABRA_TITLE, "drama", 180);
        // When
        Boolean actualResult = underTestMovie.equals(underTest.getMovie(ABRAKADABRA_TITLE));
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMoviesEqualsWithDifferentLengthShouldReturnFalse() {
        // Given
        Boolean expectedResult = false;
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 450);
        // When
        Boolean actualResult = underTestMovie.equals(underTest.getMovie(ABRAKADABRA_TITLE));
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMoviesToStringShouldReturnTrue() {
        // Given
        String expectedResult = "Movie{" + "title='" + ABRAKADABRA_TITLE + '\'' + ", genre=" + "krimi" + ", length=" + 450 + '}';
        underTest = new MovieServiceImp(new Movie(), movieRepository);
        Movie underTestMovie = underTest.createMovie(ABRAKADABRA_TITLE, "krimi", 450);
        // When
        String actualResult = underTestMovie.toString();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }


    /*
    @Override
    public List<Integer> getMovieLength(String title) {
        Movie movie = getMovie(title);
        int movieHours = movie.getLength() / 60;
        int movieMinutes = movie.getLength() - movieHours * 60;
        return Arrays.asList(movieHours, movieMinutes);
    }*/

}
