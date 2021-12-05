package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Projection;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.ProjectionService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;
import java.util.List;

@ShellComponent
public class ProjectionCommandHandler {

    private final ProjectionService projectionService;
    private final MovieService movieService;
    private final RoomService roomService;
    private final UserService userService;

    public ProjectionCommandHandler(ProjectionService projectionService, MovieService movieService,
                                    RoomService roomService, UserService userService) {
        this.projectionService = projectionService;
        this.movieService = movieService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethod(value = "Create a projection", key = "create screening")
    public String createProjection(final String movieTitle, String roomName, String startDate) throws ParseException {
        /*if (userService.adminLoggedIn()) {
            String canCreate = projectionService.canCreate(movieTitle, roomName, startDate);
            if (canCreate == null) {
                projectionService.createProjection(movieTitle, roomName, startDate);
                return "Projection created.";
            }
            return canCreate;
        }
        return "You are not an admin";*/
        return projectionService.createProjection(movieTitle, roomName, startDate);
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public String listProjections() {
        List<Projection> projections = projectionService.getProjections();
        if (!projections.isEmpty()) {
            String result = "";
            for (int i = 0; i < projections.size(); i++) {
                Movie movie = movieService.getMovie(projections.get(i).getMovieTitle());
                result += (String.format("%s (%s, %d minutes), screened in room %s, at %s%n",
                        projections.get(i).getMovieTitle(), movie.getGenre(), movie.getLength(),
                        projections.get(i).getRoomName(), projections.get(i).getStartDate()));
            }
            return result;
        } else {
            return "There are no screenings";
        }
    }

    @ShellMethod(value = "Delete a screening", key = "delete screening")
    public String deleteProjections(final String movie, String room, String date) {
        if (userService.adminLoggedIn()) {
            Projection projectionToDelete = projectionService.getProjections().stream()
                    .filter(projection -> movie.equals(projection.getMovieTitle()))
                    .filter(projection -> room.equals(projection.getRoomName()))
                    .filter(projection -> date.equals(projection.getStartDate()))
                    .findAny()
                    .orElse(null);
            if (projectionToDelete != null) {
                projectionService.deleteProjection(projectionToDelete);
                return "Projection deleted.";
            } else {
                return "Projection do not exists.";
            }
        }
        return "You are not an admin";
    }
}
