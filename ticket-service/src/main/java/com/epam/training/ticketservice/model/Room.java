package com.epam.training.ticketservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Component
@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer rows;
    private Integer columns;

    public Room() {

    }

    public Room(String name, Integer rows, Integer columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    /*public Room(final SimpleRoomBuilder simpleRoomBuilder) {
        this.name = simpleRoomBuilder.name;
        this.rows = simpleRoomBuilder.rows;
        this.columns = simpleRoomBuilder.columns;
    }*/

    /*public static SimpleRoomBuilder builder() {
        return new SimpleRoomBuilder();
    }*/

    /*public Room addRoom(String name, Integer rows, Integer columns) {
        Room room = new Room();
        room.setName(name);
        room.setRows(rows);
        room.setColumns(columns);
        return room;
    }

    public Room updateRoom(String name, Integer rows, Integer columns) {
        Room roomToUpdate = RoomRepository.findBy(name);
        roomToUpdate.setRows(rows);
        roomToUpdate.setColumns(columns);
        return roomToUpdate;
    }

    public void deleteRoom(Room room) {
        Room roomToDelete = RoomRepository.findBy(room.getName());
        RoomRepository.delete(roomToDelete);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room that = (Room) o;
        return that.rows.equals(rows) && that.columns.equals(columns) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rows, columns);
    }

    @Override
    public String toString() {
        return "Room{" + "name='" + name + '\'' + ", rows=" + rows + ", columns=" + columns + '}';
    }

    /*public static final class SimpleRoomBuilder {
        private String name;
        private Integer rows;
        private Integer columns;

        private SimpleRoomBuilder() {
        }

        public SimpleRoomBuilder withTitle(String name) {
            this.name = name;
            return this;
        }

        public SimpleRoomBuilder withRows(Integer rows) {
            this.rows = rows;
            return this;
        }

        public SimpleRoomBuilder withColumns(Integer columns) {
            this.columns = columns;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }*/
}
