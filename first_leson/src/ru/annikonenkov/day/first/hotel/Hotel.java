package ru.annikonenkov.day.first.hotel;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, что описывает объект Отель.
 */
public class Hotel {

    /**
     * Перечень комнат, что присуще данному ателю.
     */
    private Room[] rooms;

    /**
     * Наименование Отеля.
     */
    private String hotelName;

    public Hotel(String aHotelName, Room[] aRooms) {
        hotelName = aHotelName;
        rooms = aRooms;
    }

    private Optional<Room> getRoomByNumber(int aRoomNumber) {
        for (Room room : rooms) {
            if (room.getNumbers() == aRoomNumber) {
                return Optional.of(room);
            }
        }
        System.out.println("Похоже, что указанного номера нет среди номеров отеля. Введите целевую команду еще раз и укажите корректный номер.");
        return Optional.empty();
    }

    public boolean reserveRoom(int aRoomNumber) {
        Optional<Room> roomOptional = getRoomByNumber(aRoomNumber);
        if (!roomOptional.isPresent()) {
            return false;
        }
        Room room = roomOptional.get();
        if (!room.isReserved()) {
            room.setReserved();
            System.out.println("Целевой номер забронирован успешно!");
            return true;
        } else {
            System.out.println("Указанный номер уже забронирован. Введите целевую команду еще раз и выберите пожалуйста другой номер для бронирования");
            return false;
        }
    }

    public void releaseRoom(int aRoomNumber) {
        Optional<Room> roomOptional = getRoomByNumber(aRoomNumber);
        if (!roomOptional.isPresent()) {
            return;
        }
        Room room = roomOptional.get();
        room.setReleased();
        System.out.print("Номер освобождён успшно.");
        printInfoAboutTargetRoom(room);
    }

    public Room[] getFreeRoomAtNow() {
        List<Room> roomList = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isReserved()) {
                continue;
            } else {
                roomList.add(room);
            }
        }
        Room[] freeRoomArray = new Room[roomList.size()];
        for (int i = 0; i < roomList.size(); i++) {
            freeRoomArray[i] = roomList.get(i);
        }
        System.out.println("The List Free Rooms: ");
        printListOfRooms(freeRoomArray);
        return freeRoomArray;
    }

    public Room[] getFullListOfRooms() {
        System.out.println("The Full list of Rooms: ");
        printListOfRooms(rooms);
        return rooms;
    }

    private void printListOfRooms(Room[] aRooms) {
        for (Room room : aRooms) {
            printInfoAboutTargetRoom(room);
        }
    }

    private void printInfoAboutTargetRoom(Room aRoom) {
        System.out.println("The number of Room = " + aRoom.getNumbers() + " The places of room = " + aRoom.getPlace() + " Has AirConditioner = " + aRoom.hasConditioner() + " Has WC = " + aRoom.hasWc());
    }

    public Room[] getTargetRooms() {
        return null;
    }

}
