package com.epam.training.ticketservice.repository;


import com.epam.training.ticketservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByName(String roomName);

    List<Room> findAll();
}
