package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Projection;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.ProjectionRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.ProjectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProjectionTest {

    private static final String ABRAKADABRA_TITLE = "Abrakadabra";
    private static final String SATANTANGO_TITLE = "Sátántangó";
    private static final String NAGYTEREM_NAME = "Nagyterem";
    private static final String KISTEREM_NAME =  "Kisterem";
    private static final Movie ABRAKADABRA_MOVIE = new Movie(ABRAKADABRA_TITLE, "krimi", 90);
    private static final Movie SATANTANGO_MOVIE = new Movie(SATANTANGO_TITLE, "krimi", 180);
    private static Projection PROJECTION_ABRAKADABRA;
    static {
        try {
            PROJECTION_ABRAKADABRA = new Projection(ABRAKADABRA_TITLE, NAGYTEREM_NAME,
                    "2021-12-03 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static Projection PROJECTION_SATANTANGO;
    static {
        try {
            PROJECTION_ABRAKADABRA = new Projection(ABRAKADABRA_TITLE, NAGYTEREM_NAME,
                    "2021-12-03 18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static final List<Projection> PROJECTIONS = List.of(PROJECTION_ABRAKADABRA);

    private ProjectionService underTest;
    private MovieService movieService;

    @Mock
    private ProjectionRepository projectionRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private Projection projection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProjectionsShouldReturnEmptyListWhenNoProjectionsAdded() {
        // Given
        List<Projection> expectedResult = Collections.emptyList();
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        List<Projection> actualResult = underTest.getProjections();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetProjectionsShouldReturnTheListOfProjectionsWhenNotEmpty() throws ParseException {
        // Given
        List<Projection> expectedResult = Collections.singletonList(PROJECTION_ABRAKADABRA);
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        underTest.createProjection(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 18:00");
        // When
        List<Projection> actualResult = underTest.getProjections();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetProjectionsShouldReturnNullWhenProjectionDeleted() throws ParseException {
        // Given
        BDDMockito.given(projectionRepository.findAll()).willReturn(null);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        movieService.createMovie(ABRAKADABRA_TITLE, "krimi", 180);
        underTest.createProjection(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 18:00");
        underTest.deleteProjection(PROJECTION_ABRAKADABRA);
        // When
        List<Projection> actualResult = underTest.getProjections();
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testFormatToDate() throws ParseException {
        // Given
        SimpleDateFormat fomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date expectedResult = (fomatter.parse("2021-12-13 18:00"));
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        Date actualResult = underTest.formatToDate("2021-12-13 18:00");
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddToDateWhenZeroIsGiven() throws ParseException {
        // Given
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        Date expectedResult = underTest.formatToDate("2021-12-13 18:00");
        // When
        Date actualResult = underTest.addToDate("2021-12-13 18:00", 0, 0);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddToDateWhenMinuteIsGiven() throws ParseException {
        // Given
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        Date expectedResult = underTest.formatToDate("2021-12-13 18:30");
        // When
        Date actualResult = underTest.addToDate("2021-12-13 18:00", 0, 30);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddToDateWhenHourIsGiven() throws ParseException {
        // Given
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        Date expectedResult = underTest.formatToDate("2021-12-13 20:00");
        // When
        Date actualResult = underTest.addToDate("2021-12-13 18:00", 2, 0);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddToDateWhenHourAndMinuteIsGiven() throws ParseException {
        // Given
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        Date expectedResult = underTest.formatToDate("2021-12-13 20:30");
        // When
        Date actualResult = underTest.addToDate("2021-12-13 18:00", 2, 30);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddToDateWhenMoreThan60MinuteIsGiven() throws ParseException {
        // Given
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        Date expectedResult = underTest.formatToDate("2021-12-13 19:30");
        // When
        Date actualResult = underTest.addToDate("2021-12-13 18:00", 0, 90);
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCanCreateProjectionWhenThereIsNoOtherProjection() throws ParseException {
        // Given
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        String actualResult = underTest.canCreate(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 18:00");
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testCanCreateProjectionWhenThereIsOverlappingProjectionInDifferentRoom() throws ParseException {
        // Given
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        String actualResult = underTest.canCreate(ABRAKADABRA_TITLE, KISTEREM_NAME, "2021-12-03 18:00");
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testCanNotCreateProjectionWhenThereIsOverlappingProjectionInTheSameRoomBefore() throws ParseException {
        // Given
        String expectedResult = "There is an overlapping screening";
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        String actualResult = underTest.canCreate(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 17:00");
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCanNotCreateProjectionWhenThereIsOverlappingProjectionInTheSameRoomAfter() throws ParseException {
        // Given
        String expectedResult = "There is an overlapping screening";
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        String actualResult = underTest.canCreate(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 19:00");
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCanNotCreateProjectionWhenThereIsOverlappingProjectionInTheSameRoomDuring() throws ParseException {
        // Given
        String expectedResult = "There is an overlapping screening";
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        BDDMockito.given(movieRepository.findByTitle(SATANTANGO_TITLE)).willReturn(SATANTANGO_MOVIE);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        String actualResult = underTest.canCreate(SATANTANGO_TITLE, NAGYTEREM_NAME, "2021-12-03 17:00");
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCanNotCreateProjectionDuringProjectionBreakInDifferentRoom() throws ParseException {
        // Given
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        // When
        String actualResult = underTest.canCreate(ABRAKADABRA_TITLE, KISTEREM_NAME, "2021-12-03 21:09");
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testCanNotCreateProjectionDuringProjectionBreakInTheSameRoomAfter() throws ParseException {
        // Given
        String expectedResult = "This would start in the break period after another screening in this room";
        BDDMockito.given(projectionRepository.findAll()).willReturn(Collections.singletonList(PROJECTION_ABRAKADABRA));
        BDDMockito.given(movieRepository.findByTitle(ABRAKADABRA_TITLE)).willReturn(ABRAKADABRA_MOVIE);
        movieService = new MovieServiceImp(new Movie(), movieRepository);
        underTest = new ProjectionServiceImp(new Projection(), projectionRepository, movieService);
        underTest.createProjection(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 18:00");
        // When
        String actualResult = underTest.canCreate(ABRAKADABRA_TITLE, NAGYTEREM_NAME, "2021-12-03 19:30");
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }



    /*
    @Override
    public String canCreate(String movieTitle, String roomName, String startDate) throws ParseException {
        List<Projection> projections = getProjections();
        for (Projection projection : projections) {
            if (roomName.equals(projection.getRoomName())) {
                List<Integer> projectionMovie = movieService.getMovieLength(projection.getMovieTitle());

                Date projectionStartDate = addToDate(projection.getStartDate(), 0, -10);
                Date projectionEndDate = addToDate(projection.getStartDate(), projectionMovie.get(0),
                        projectionMovie.get(1));

                List<Integer> newMovie = movieService.getMovieLength(movieTitle);
                Date newStartDate = formatToDate(startDate);
                Date newEndDate = addToDate(startDate, newMovie.get(0), newMovie.get(1));


                if (newStartDate.after(projectionStartDate) && newStartDate.before(projectionEndDate)) {
                    return "There is an overlapping screening";
                } else if (newEndDate.after(projectionStartDate) && newEndDate.before(projectionEndDate)) {
                    return "There is an overlapping screening";
                } else if (newStartDate.before(projectionStartDate) && newEndDate.after(projectionEndDate)) {
                    return "There is an overlapping screening";
                } else {
                    projectionEndDate = addToDate(projection.getStartDate(), projectionMovie.get(0),
                            projectionMovie.get(1) + 10);
                    if (newStartDate.after(projectionStartDate) && newStartDate.before(projectionEndDate)) {
                        return "This would start in the break period after another screening in this room";
                    } else if (newEndDate.after(projectionStartDate) && newEndDate.before(projectionEndDate)) {
                        return "This would start in the break period after another screening in this room";
                    }
                }
            }
        }
        return null;
    }
    */
}
