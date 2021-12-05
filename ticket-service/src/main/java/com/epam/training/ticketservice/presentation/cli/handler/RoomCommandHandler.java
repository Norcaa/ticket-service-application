package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class RoomCommandHandler {

    private final RoomService roomService;
    private final UserService userService;

    public RoomCommandHandler(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethod(value = "Create a room", key = "create room")
    public String createRoom(final String name, Integer rows, Integer columns) {
        if (userService.adminLoggedIn()) {
            roomService.createRoom(name, rows, columns);
            return "Room created.";
        }
        return "You are not an admin";

    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public String listRooms() {
        List<Room> rooms = roomService.getRooms();
        if (!rooms.isEmpty()) {
            String result = "";
            for (int i = 0; i < rooms.size(); i++) {
                result += String.format("Room %s with %d seats, %d rows and %d columns%n",
                        rooms.get(i).getName(), rooms.get(i).getRows() * rooms.get(i).getColumns(),
                        rooms.get(i).getRows(), rooms.get(i).getColumns());
            }
            return result;
        }
        return "There are no rooms at the moment";
    }

    @ShellMethod(value = "Update a room", key = "update room")
    public String updateRoom(final String name, Integer rows, Integer columns) {
        if (userService.adminLoggedIn()) {
            roomService.updateRoom(name, rows, columns);
            return null;
        }
        return "You are not an admin";
    }

    @ShellMethod(value = "Delete a room", key = "delete room")
    public String deleteRoom(final String name) {
        if (userService.adminLoggedIn()) {
            Room roomToDelete = roomService.getRooms().stream()
                    .filter(room -> name.equals(room.getName()))
                    .findAny()
                    .orElse(null);
            if (roomToDelete != null) {
                roomService.deleteRoom(roomToDelete);
                return null;
            } else {
                return "Room do not exists.";
            }
        }
        return "You are not an admin";
    }
}
