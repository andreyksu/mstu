package ru.annikonenkov.day.first.hotel;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {


    public static void main(String agrs[]) {
        Hotel firstHotel = createFirstHotel();
        System.out.println("Вас привествует сервис бронирования Отеля. Для начала работы введите целевую команду. Или для получения справки введите /help");
        Scanner scanner = new Scanner(System.in);
        boolean statusToWork = true;
        while (statusToWork) {
            String command = scanner.next();
            switch (command) {
                case ("/getFullRoomsList"):
                    firstHotel.getFullListOfRooms();
                    break;
                case ("/getFreeRoomsList"):
                    firstHotel.getFreeRoomAtNow();
                    break;
                case ("/doReserveRoom"):
                    System.out.print("Укажите пожалуйста номер целевой комнаты для брони: ");
                    firstHotel.reserveRoom(scanner.nextInt());
                    break;
                case ("/doReleaseRoom"):
                    System.out.print("Укажите пожалуйста номер целевой комнаты для освобождения: ");
                    firstHotel.releaseRoom(scanner.nextInt());
                    break;
                case ("/help"):
                    System.out.println("Список команд следующий");
                    break;
                case ("/exit"):
                    System.out.println("Программа будет завершена");
                    statusToWork = false;
                    break;
                default:
                    System.out.println("В ввели неизвестную команду. Для вывода списка известных команд введите /help");
                    break;
            }
        }
    }

    public static Hotel createFirstHotel() {
        Room room1 = new Room(111, 21, true, true, false);
        Room room2 = new Room(112, 22, false, true, false);
        Room room3 = new Room(113, 23, false, false, false);
        Room room4 = new Room(114, 24, false, false, false);
        Room room5 = new Room(115, 25, false, false, false);
        Room room6 = new Room(116, 26, false, false, false);

        Hotel hotel = new Hotel("FirstHotel", new Room[]{room1, room2, room3, room4, room5, room6});
        return hotel;
    }

    public static Hotel createSecondHotel() {
        Room room1 = new Room(211, 31, true, true, false);
        Room room2 = new Room(212, 32, false, true, false);
        Room room3 = new Room(213, 33, false, false, false);
        Room room4 = new Room(214, 34, false, false, false);
        Room room5 = new Room(215, 35, false, false, false);
        Room room6 = new Room(216, 36, false, false, false);

        Hotel hotel = new Hotel("SecondHotel", new Room[]{room1, room2, room3, room4, room5, room6});
        return hotel;
    }
}
