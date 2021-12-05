package com.epam.training.ticketservice.presentation.cli.config;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Projection;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.ProjectionRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.ProjectionService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import com.epam.training.ticketservice.service.imp.MovieServiceImp;
import com.epam.training.ticketservice.service.imp.ProjectionServiceImp;
import com.epam.training.ticketservice.service.imp.RoomServiceImp;
import com.epam.training.ticketservice.service.imp.UserServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketServiceConfiguration {

    @Bean
    public MovieService movieService(Movie movie, MovieRepository movieRepository) {
        return new MovieServiceImp(movie, movieRepository);
    }

    @Bean
    public RoomService roomService(Room room, RoomRepository roomRepository) {
        return new RoomServiceImp(room, roomRepository);
    }

    @Bean
    public ProjectionService projectionService(Projection projection, ProjectionRepository projectionRepository,
                                               MovieService movieService) {
        return new ProjectionServiceImp(projection, projectionRepository, movieService);
    }

    @Bean
    public UserService userService() {
        return new UserServiceImp();
    }
}
