package ru.annikonenkov.day.first.hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    //TODO: Переделать на Map<String, Command> - Где interface Command будет кроме действия еще иметь описание действия, котрое при ForEach будет отдавать описание для /help.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Command> commandsMap = new HashMap<>();//TODO: Вероятно это должно быть в Отеле. Т.к. отель сам знает что он может а что нет.
        commandsMap.put(PrintFullRoomsList.KEY, new PrintFullRoomsList());
        commandsMap.put(GetFreeRoomsAtNow.KEY, new GetFreeRoomsAtNow());
        commandsMap.put(DoReserve.KEY, new DoReserve());
        commandsMap.put(DoRelease.KEY, new DoRelease());
        commandsMap.put(GetActiveReservationForTargetRoom.KEY, new GetActiveReservationForTargetRoom());
        Hotel firstHotel = createFirstHotel();
        System.out.println("Вас приветствует сервис бронирования Отеля. Для начала работы введите целевую команду. Или для получения справки введите /help");
        boolean statusToWork = true;
        while (statusToWork) {
            String rawStringCommand = scanner.next();
            if (rawStringCommand.equals("/help")) {
                System.out.println("Доступны следующие команды");
                System.out.println("---------------------------------------");
                commandsMap.keySet().stream().forEach(keyItem -> System.out.println(keyItem));
                System.out.println("---------------------------------------");
                System.out.println("Для получения подробной информации по конкретной команде, выберите её и введите ...");
                continue;
            }
            Command command = commandsMap.get(rawStringCommand);
            if (command == null) {
                System.out.println("Вы ввели неизвестную команду. Повторите ввод команды, или введите /help получения подробной информации");
                continue;
            }
            command.execute(firstHotel, scanner);
        }

    }

    public static void main1(String[] args) {
        Hotel firstHotel = createFirstHotel();
        System.out.println("Вас приветствует сервис бронирования Отеля. Для начала работы введите целевую команду. Или для получения справки введите /help");
        Scanner scanner = new Scanner(System.in);
        boolean statusToWork = true;
        while (statusToWork) {
            String command = scanner.next();
            switch (command) {
                case ("/getFullRoomsList"):
                    firstHotel.getFullListOfRooms();
                    break;
                case ("/getFreeRoomsListAtNow"):
                    firstHotel.getFreeRoomAtNow();
                    break;
                case ("/doReserveTargetRoom"):
                    System.out.println("Вы выбрали команду для бронирования комнаты.");
                    var optionalComposedObj = parseComposedDate(scanner);
                    if (optionalComposedObj.isEmpty())
                        break;
                    var composedObject = optionalComposedObj.get();
                    firstHotel.reserveTargetRoom(composedObject.getNumber(), new ReservationDate(composedObject.getStartDate(), composedObject.getEndDate()));
                    break;
                case ("/doReleaseTargetRoom"):
                    System.out.println("Вы выбрали команду снятия брони с комнаты.");
                    var optionalComposedObjForRelease = parseComposedDate(scanner);
                    if (optionalComposedObjForRelease.isEmpty())
                        break;
                    var composedObjectToRelease = optionalComposedObjForRelease.get();
                    firstHotel.releaseTargetRoom(composedObjectToRelease.getNumber(), new ReservationDate(composedObjectToRelease.getStartDate(), composedObjectToRelease.getEndDate()));
                    break;
                case ("/getActiveReservationForTargetRoom"):
                    System.out.println("Вы выбрали команду для отображения списка активных броней для целевой комнаты.");
                    System.out.print("Укажите пожалуйста номер целевой комнаты: ");
                    Integer targetNum = parseRoomAsInt(scanner);
                    if (targetNum == -1)
                        break;
                    firstHotel.printActiveReservationForTargetRoom(targetNum);
                    break;
                case ("/help"):
                    System.out.println("Список команд следующий\n");
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

    private static Optional<ComposedObject> parseComposedDate(Scanner scanner) {
        System.out.print("Укажите пожалуйста номер целевой комнаты: ");
        Integer roomNumber = parseRoomAsInt(scanner);
        if (roomNumber == -1)
            return Optional.empty();
        System.out.print("Укажите стартовую дату в формате (01/10/2023): ");
        Optional<LocalDate> startDate = parsedLocalDate(scanner);
        if (startDate.isEmpty())
            return Optional.empty();
        System.out.print("Укажите конечную дату в формате (03/10/2023): ");
        Optional<LocalDate> endDate = parsedLocalDate(scanner);
        if (endDate.isEmpty())
            return Optional.empty();
        return Optional.of(new Main.ComposedObject(roomNumber, startDate.get(), endDate.get()));
    }

    private static Optional<LocalDate> parsedLocalDate(Scanner scanner) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate parsedLocalDate;
        while (true) {
            try {
                String rawStr = scanner.next();
                if (rawStr.equals("/break")) {
                    System.out.println("Вы решили прекратить ввод даты. Программа перейдет в режим ожидания ввода основных команд");
                    return null;
                }
                parsedLocalDate = LocalDate.parse(rawStr, dtf);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Введенная строка не соответствует требуемому формат. Введите дату еще раз в соответствии с предложенным форматом");
            }
        }
        return Optional.of(parsedLocalDate);
    }

    private static Integer parseRoomAsInt(Scanner scanner) {
        Integer roomNumber;
        while (true) {
            try {
                String rawStr = scanner.next();
                if (rawStr.equals("/break")) {
                    System.out.println("Вы решили прекратить ввод номера комнаты. Будет осуществлен переход для ожидания ввода основных команд");
                    return -1;
                }
                roomNumber = Integer.parseInt(rawStr);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введённая строка не является числом. Попробуйте еще раз ввести номер комнаты в числовом виде или введите /break для прекращения ввода комнаты и для перехода выбора другой команды");
            }
        }
        return roomNumber;
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


    /**
     * Составной объект для передачи информации по брони после прочтения с консоли.
     */
    static class ComposedObject {
        private int number;
        private LocalDate startDate;
        private LocalDate endDate;

        public ComposedObject(int aRoomNumber, LocalDate aStartDate, LocalDate aEndDate) {
            number = aRoomNumber;
            startDate = aStartDate;
            endDate = aEndDate;
        }


        public int getNumber() {
            return number;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }

    private static void reader() {

    }

    interface Command {
        <T> T execute(Hotel hotel, Scanner scanner);//TODO: Наверное команда не должна ничего возвращать. Иначе в RunTime мы не знаем что будет возращено.
    }

    static class PrintFullRoomsList implements Command {
        public static String KEY = "/getFullRoomsList";

        @Override
        public Room[] execute(Hotel hotel, Scanner scanner) {
            return hotel.getFullListOfRooms();
        }
    }

    static class GetFreeRoomsAtNow implements Command {
        public static String KEY = "/getFreeRoomsAtNow";

        @Override
        public Room[] execute(Hotel hotel, Scanner scanner) {
            return hotel.getFreeRoomAtNow();
        }
    }

    static class DoReserve implements Command {
        public static String KEY = "/doReserveTargetRoom";

        @Override
        public Boolean execute(Hotel hotel, Scanner scanner) {
            System.out.println("Вы выбрали команду для бронирования комнаты.");
            var optionalComposedObj = parseComposedDate(scanner);
            if (optionalComposedObj.isEmpty())
                return false;
            var composedObject = optionalComposedObj.get();
            boolean result = hotel.reserveTargetRoom(composedObject.getNumber(), new ReservationDate(composedObject.getStartDate(), composedObject.getEndDate()));
            return result;
        }
    }

    static class DoRelease implements Command {
        public static String KEY = "/doReleaseTargetRoom";

        @Override
        public Boolean execute(Hotel hotel, Scanner scanner) {
            System.out.println("Вы выбрали команду снятия брони с комнаты.");
            var optionalComposedObjForRelease = parseComposedDate(scanner);
            if (optionalComposedObjForRelease.isEmpty())
                return false;
            var composedObjectToRelease = optionalComposedObjForRelease.get();
            boolean result = hotel.releaseTargetRoom(composedObjectToRelease.getNumber(), new ReservationDate(composedObjectToRelease.getStartDate(), composedObjectToRelease.getEndDate()));
            return result;
        }
    }

    static class GetActiveReservationForTargetRoom implements Command {
        public static String KEY = "/getActiveReservationForTargetRoom";

        @Override
        public Boolean execute(Hotel hotel, Scanner scanner) {
            System.out.println("Вы выбрали команду для отображения списка активных броней для целевой комнаты.");
            System.out.print("Укажите пожалуйста номер целевой комнаты: ");
            Integer targetNum = parseRoomAsInt(scanner);
            if (targetNum == -1)
                return null;
            hotel.printActiveReservationForTargetRoom(targetNum);
            return null;
        }
    }


}
