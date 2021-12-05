package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Projection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectionRepository extends JpaRepository<Projection, Long> {

}
