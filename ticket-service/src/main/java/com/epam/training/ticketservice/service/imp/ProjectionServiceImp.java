package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.model.Projection;
import com.epam.training.ticketservice.repository.ProjectionRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProjectionServiceImp implements ProjectionService {

    private final Projection projection;
    private final ProjectionRepository projectionRepository;
    private MovieService movieService;

    private static final String PATTERN = "yyyy-MM-dd HH:mm";

    @Autowired
    public ProjectionServiceImp(Projection projection, ProjectionRepository projectionRepository,
        MovieService movieService) {
        this.projection = projection;
        this.projectionRepository = projectionRepository;
        this.movieService = movieService;
    }

    @Override
    public Projection projection() throws ParseException {
        projectionRepository.save(projection);
        return new Projection(projection.getMovieTitle(), projection.getRoomName(), projection.getStartDate());
    }

    @Override
    public String createProjection(String movieTitle, String roomName, String startDate) throws ParseException {
        String canCreate = canCreate(movieTitle, roomName, startDate);
        if (canCreate == null) {
            Projection projection = new Projection(movieTitle, roomName, startDate);
            projectionRepository.save(projection);
            return "Projection created.";
        }
        return canCreate;
    }

    @Override
    public List<Projection> getProjections() {
        return projectionRepository.findAll();
    }

    @Override
    public void deleteProjection(Projection projectionToDelete) {
        projectionRepository.delete(projectionToDelete);
    }

    @Override
    public Date formatToDate(String date) throws ParseException {
        SimpleDateFormat fomatter = new SimpleDateFormat(PATTERN);
        return fomatter.parse(date);
    }

    @Override
    public Date addToDate(String date, Integer hour, Integer minute) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatToDate(date));
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    @Override
    public String canCreate(String movieTitle, String roomName, String startDate) throws ParseException {
        List<Projection> projections = getProjections();
        if (projections != null) {
            for (Projection projection : projections) {
                if (roomName.equals(projection.getRoomName())) {
                    List<Integer> projectionMovie = movieService.getMovieLength(movieService
                            .getMovie(projection.getMovieTitle()).getLength());

                    Date projectionStartDate = addToDate(projection.getStartDate(), 0, -10);
                    Date projectionEndDate = addToDate(projection.getStartDate(), projectionMovie.get(0),
                            projectionMovie.get(1));

                    List<Integer> newMovie = movieService.getMovieLength(movieService.getMovie(movieTitle).getLength());
                    Date newStartDate = formatToDate(startDate);
                    Date newEndDate = addToDate(startDate, newMovie.get(0), newMovie.get(1));

                    if ((newStartDate.after(projectionStartDate) && newStartDate.before(projectionEndDate))
                            || (newEndDate.after(projectionStartDate) && newEndDate.before(projectionEndDate))
                            || (newStartDate.before(projectionStartDate) && newEndDate.after(projectionEndDate))) {
                        return "There is an overlapping screening";
                    } else {
                        projectionEndDate = addToDate(projection.getStartDate(), projectionMovie.get(0),
                                projectionMovie.get(1) + 10);
                        if ((newStartDate.after(projectionStartDate) && newStartDate.before(projectionEndDate))
                                || (newEndDate.after(projectionStartDate) && newEndDate.before(projectionEndDate))) {
                            return "This would start in the break period after another screening in this room";
                        }
                    }
                    /*
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
                    }*/
                }
            }
            return null;
        }
        return null;
    }
}
