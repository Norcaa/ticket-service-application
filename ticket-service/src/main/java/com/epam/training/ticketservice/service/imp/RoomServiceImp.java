package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImp implements RoomService {

    private final Room room;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImp(Room room, RoomRepository roomRepository) {
        this.room = room;
        this.roomRepository = roomRepository;
    }

    @Override
    public Room room() {
        roomRepository.save(room);
        return new Room();
    }

    @Override
    public Room createRoom(String name, Integer rows, Integer columns) {
        Room room = new Room(name, rows, columns);
        roomRepository.save(room);
        return room;
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(String name) {
        return roomRepository.findByName(name);
    }

    @Override
    public Room updateRoom(String name, Integer rows, Integer columns) {
        Room roomToUpdate = roomRepository.findByName(name);
        if (roomToUpdate != null) {
            roomToUpdate.setRows(rows);
            roomToUpdate.setColumns(columns);
            roomRepository.save(roomToUpdate);
            return roomToUpdate;
        }
        return null;
    }

    @Override
    public void deleteRoom(Room roomToDelete) {
        roomRepository.delete(roomToDelete);
    }

}
