package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}
