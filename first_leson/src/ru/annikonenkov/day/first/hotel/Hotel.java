package ru.annikonenkov.day.first.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, что описывает объект Отель.
 */
public class Hotel {

    //TODO: Если так оставлять, то вероятно лучше сделать через Set - ведь комнаты являются уникальными а не массив.
    //TODO: В этом случае вероятно лучшим случаем будет хранить в Map<Int, Room> и при добавлении из Room брать номер. Так будет быстрее работать, чем каждый раз перебирать.
    /**
     * Перечень комнат, что присуще данному отелю.
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

    /**
     * Возвращает комнату по её номеру.
     *
     * @param aRoomNumber
     * @return Optional<Room> - комната обёрнутая в Optional если такая комната найдена. Иначе пустой Optional.
     */
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
     * Выполняет резервирование целевой комнаты на целевую дату. Если есть пересечение по датам с уже существующей бронью, то бронь выполнена не будет.
     *
     * @param aRoomNumber  - номер целевой комнаты.
     * @param aReserveDate - диапазон дат для резеровирования.
     * @return true - Если комнату удалась успешно зарезервировать. false - в ином случае.
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
     * Выполняет отмену брони. Отмена выполняется только при совпадении дат брони - как бы защита от случайного снятия брони.
     *
     * @param aRoomNumber
     * @param aReserveDate
     * @return - true - если бронь снята. false - в ином случае (если комната не найдена в списке или если не нашли бронь с заданными границами).
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

    /**
     * Возвращает комнаты, что на текущий день доступны (не забронированы).
     * Приведение к массиву осуществляется из-за ограничения, т.к. на данном этапе(в первый день) еще не проходили Collections :-)
     *
     * @return
     */
    public Room[] getFreeRoomAtNow() {
        List<Room> roomList = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isReservedAtNow()) {
                continue;
            } else {
                roomList.add(room);
            }
        }
        Room[] freeRoomArray = new Room[roomList.size()];
        for (int i = 0; i < roomList.size(); i++) {
            freeRoomArray[i] = roomList.get(i);
        }
        System.out.println("Список свободных комнат в отеле: ");
        printRoomsList(freeRoomArray);
        return freeRoomArray;
    }

    //TODO: Добавить метод, что будет возвращать список СВОБОДНЫДХ комнат на заданный диапазон (а не только на текущий день). По сути - это будет общий метод для getFreeRoomAtNow().

    /**
     * Возвращает список активных броней для целевой комнаты.
     *
     * @param aRoomNumber - номер целевой комнаты.
     */
    public void printActiveReservationForTargetRoom(int aRoomNumber) {
        Optional<Room> roomOptional = getRoomByNumber(aRoomNumber);
        if (!roomOptional.isPresent()) {
            return;
        }
        var room = roomOptional.get();
        List<ReservationDate> reservationList = room.getCurrentAndFutureReservation();
        reservationList.stream().forEach(item -> System.out.println(item));
        if (reservationList.size() == 0)
            System.out.println("Для заданной комнаты не активных броней.");
    }


    /**
     * Возвращает полный список комнат, что принадлежит отелю.
     *
     * @return
     */
    public Room[] getFullListOfRooms() {
        System.out.println("Полный список комнат для отеля: ");
        printRoomsList(rooms);
        return rooms;
    }

    /**
     * Печатает список номеров.
     *
     * @param aRooms
     */
    private void printRoomsList(Room[] aRooms) {
        for (Room room : aRooms) {
            System.out.println(room);
        }
    }

    public String toString() {
        return String.format("Отель = %s содержит следующее количество %s комнат!", hotelName, rooms.length);
    }
}
