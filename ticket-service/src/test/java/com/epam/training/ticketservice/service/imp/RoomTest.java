package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;

public class RoomTest {

    private static final String NAGYTEREM_NAME = "Nagyterem";
    private static final String KISTEREM_NAME = "Kisterem";
    private static final Room NAGYTEREM_ROOM = new Room(NAGYTEREM_NAME, 20, 10);
    private static final Room KISTEREM_ROOMS = new Room(KISTEREM_NAME, 10, 8);
    private static final List<Room> ROOMS = List.of(NAGYTEREM_ROOM, KISTEREM_ROOMS);

    private RoomService underTest;

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private Room room;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoomsShouldReturnEmptyListWhenNoRoomsAdded() {
        // Given
        List<Room> expectedResult = Collections.emptyList();
        underTest = new RoomServiceImp(new Room(), roomRepository);
        // When
        List<Room> actualResult = underTest.getRooms();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetRoomsShouldReturnTheListOfRoomsWhenNotEmpty() {
        // Given
        List<Room> expectedResult = Collections.singletonList(NAGYTEREM_ROOM);
        BDDMockito.given(roomRepository.findAll()).willReturn(Collections.singletonList(NAGYTEREM_ROOM));
        underTest = new RoomServiceImp(new Room(), roomRepository);
        underTest.createRoom(NAGYTEREM_NAME, 20, 10);
        // When
        List<Room> actualResult = underTest.getRooms();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetRoomShouldReturnNullWhenNoRoomFounded() {
        // Given
        underTest = new RoomServiceImp(new Room(), roomRepository);
        // When
        Room actualResult = underTest.getRoom(NAGYTEREM_NAME);
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testGetRoomShouldReturnRoomWhenRoomFounded() {
        // Given
        BDDMockito.given(roomRepository.findByName(NAGYTEREM_NAME)).willReturn(NAGYTEREM_ROOM);
        underTest = new RoomServiceImp(new Room(), roomRepository);
        underTest.createRoom(NAGYTEREM_NAME, 20, 10);
        // When
        Room actualResult = underTest.getRoom(NAGYTEREM_NAME);
        // Then
        Assertions.assertEquals(NAGYTEREM_ROOM, actualResult);
    }

    @Test
    public void testUpdateRoomShouldReturnNullWhenNoRoomFounded() {
        // Given
        underTest = new RoomServiceImp(new Room(), roomRepository);
        // When
        Room actualResult = underTest.updateRoom(NAGYTEREM_NAME, 20, 10);
        // Then
        Assertions.assertNull(actualResult);
    }

    @Test
    public void testUpdateRoomShouldReturnTheNewRoomWhenRoomFounded() {
        // Given
        BDDMockito.given(roomRepository.findByName(NAGYTEREM_NAME)).willReturn(NAGYTEREM_ROOM);
        underTest = new RoomServiceImp(new Room(), roomRepository);
        underTest.createRoom(NAGYTEREM_NAME, 10, 5);
        // When
        Room actualResult = underTest.updateRoom(NAGYTEREM_NAME,20,10);
        // Then
        Assertions.assertEquals(NAGYTEREM_ROOM, actualResult);
    }

    @Test
    public void testGetRoomsShouldReturnNullWhenRoomDeleted() {
        // Given
        BDDMockito.given(roomRepository.findAll()).willReturn(null);
        underTest = new RoomServiceImp(new Room(), roomRepository);
        underTest.createRoom(NAGYTEREM_NAME, 20, 10);
        underTest.deleteRoom(NAGYTEREM_ROOM);
        // When
        List<Room> actualResult = underTest.getRooms();
        // Then
        Assertions.assertNull(actualResult);
    }

}
