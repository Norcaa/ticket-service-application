package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;

import java.util.List;

public interface RoomService {

    Room room();

    Room createRoom(String name, Integer rows, Integer columns);

    List<Room> getRooms();

    Room getRoom(String name);

    Room updateRoom(String name, Integer rows, Integer columns);

    void deleteRoom(Room movieToDelete);

    //void subscribe(Observer observer);
}
