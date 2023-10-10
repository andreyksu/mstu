package ru.annikonenkov.day.first.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, что описывает объект Отель.
 */
public class Hotel {

    /**
     * Перечень комнат, что присуще данному отелю.
     */
    //TODO: Если так оставлять, то вероятно лучше сделать через Set - ведь комнаты являеются уникальными а не массив.
    //TODO: В этом случае вероятно лучшим случаем будет хранить в Map<Int, Room> и при добавлении из Room брать номер. Так будет быстрее работать.
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
        System.out.println("Похоже, что указанного номера нет среди номеров отеля. Введите целевую команду еще раз и укажите корректный номер комнаты!");
        return Optional.empty();
    }

    /**
     * Выполняет резервирование целевой комнаты на целевую дату.
     *
     * @param aRoomNumber  - номер целевой комнаты.
     * @param aReserveDate - диапазон дат для резеровирования.
     * @return - true - Если комнату удалась успешно зарезервировать. false - в ином случае.
     */
    public boolean reserveTargetRoom(int aRoomNumber, ReservationDate aReserveDate) {
        Optional<Room> roomOptional = getRoomByNumber(aRoomNumber);
        if (!roomOptional.isPresent()) {
            return false;
        }
        Room room = roomOptional.get();
        if (room.reserve(aReserveDate)) {
            String successResultInfo = String.format("Целевой номер №_%s забронирован успешно на дату %s!", aRoomNumber, aReserveDate.toString());
            System.out.println(successResultInfo);
            return true;
        } else {
            String warnResultInfo = String.format("Указанный номер №_%s уже забронирован на указанные даты. Введите целевую команду еще раз и выберите пожалуйста другой номер для бронирования", aRoomNumber, aReserveDate.toString());
            System.out.println(warnResultInfo);
            return false;
        }
    }

    /**
     * Выполняет отмену брони. Отмена выполняется только при совпадении дат брони - как бы защита от снятия случайной брони.
     *
     * @param aRoomNumber
     * @param aReserveDate
     * @return
     */
    public boolean releaseTargetRoom(int aRoomNumber, ReservationDate aReserveDate) {
        Optional<Room> roomOptional = getRoomByNumber(aRoomNumber);
        if (!roomOptional.isPresent()) {
            return false;
        }
        Room room = roomOptional.get();
        boolean releaseResult = room.release(aReserveDate);
        if (releaseResult) {
            String printInfo = String.format("Номер №_%s на дату '%s' освобожён успешно", aRoomNumber, aReserveDate.toString());
            System.out.print(printInfo);
            return releaseResult;
        }
        String printInfo = String.format("Номер №_%s на даты '%s' не имеет брони", aRoomNumber, aReserveDate.toString());
        System.out.print(printInfo);
        return releaseResult;
    }

    public Room[] getFreeRoomAtNow() {
        List<Room> roomList = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isReservedAtNow()) {//TODO: Добавить метод для вывода свободных номеров на сейчас.
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
        printRoomsList(freeRoomArray);
        return freeRoomArray;
    }

    public Room[] getFullListOfRooms() {
        System.out.println("The Full list of Rooms: ");
        printRoomsList(rooms);
        return rooms;
    }

    private void printRoomsList(Room[] aRooms) {
        for (Room room : aRooms) {
            System.out.println(room);
        }
    }

    public String toString() {
        return String.format("Отель = %s содержит %s комнат!", hotelName, rooms.length);
    }
}
