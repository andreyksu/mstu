package ru.annikonenkov.day.first.hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                case ("/doReserveRoom")://TODO: Добавить проверку на валидность данных. Вернее обработку исключения. Как по дате так и по номеру.
                    System.out.print("Укажите пожалуйста номер целевой комнаты для брони: ");
                    int targetNum0 = scanner.nextInt();
                    System.out.print("Укажите стартовую дату брони в формате (01/10/2023): ");
                    String startDate0 = scanner.next();
                    System.out.print("Укажите конечную дату брони в формате (03/10/2023): ");
                    String endDate0 = scanner.next();
                    DateTimeFormatter dtf0 = DateTimeFormatter.ofPattern("d/MM/yyyy");
                    LocalDate startLocalDate0 = LocalDate.parse(startDate0, dtf0);
                    LocalDate endLocalDate0 = LocalDate.parse(endDate0, dtf0);
                    firstHotel.reserveTargetRoom(targetNum0, new ReservationDate(startLocalDate0, endLocalDate0));
                    break;
                case ("/doReleaseRoom"):
                    System.out.print("Укажите пожалуйста номер целевой комнаты для снятия брони: ");
                    int targetNum1 = scanner.nextInt();
                    System.out.print("Укажите стартовую дату брони в формате (01/10/2023): ");
                    String startDate1 = scanner.next();
                    System.out.print("Укажите конечную дату брони в формате (03/10/2023): ");
                    String endDate1 = scanner.next();
                    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("d/MM/yyyy");
                    LocalDate startLocalDate1 = LocalDate.parse(startDate1, dtf1);
                    LocalDate endLocalDate1 = LocalDate.parse(endDate1, dtf1);
                    firstHotel.releaseTargetRoom(targetNum1, new ReservationDate(startLocalDate1, endLocalDate1));
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
        Room room1 = new Room(111, 21, true, true);
        Room room2 = new Room(112, 22, false, true);
        Room room3 = new Room(113, 23, false, false);
        Room room4 = new Room(114, 24, false, false);
        Room room5 = new Room(115, 25, false, false);
        Room room6 = new Room(116, 26, false, false);

        Hotel hotel = new Hotel("FirstHotel", new Room[]{room1, room2, room3, room4, room5, room6});
        return hotel;
    }

    public static Hotel createSecondHotel() {
        Room room1 = new Room(211, 31, true, true);
        Room room2 = new Room(212, 32, false, true);
        Room room3 = new Room(213, 33, false, false);
        Room room4 = new Room(214, 34, false, false);
        Room room5 = new Room(215, 35, false, false);
        Room room6 = new Room(216, 36, false, false);

        Hotel hotel = new Hotel("SecondHotel", new Room[]{room1, room2, room3, room4, room5, room6});
        return hotel;
    }
}
