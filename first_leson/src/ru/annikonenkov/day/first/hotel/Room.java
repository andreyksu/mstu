package ru.annikonenkov.day.first.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс описывает комнату - наличе тех или иных условий, дату бронирования итд.
 */
public class Room {

    private int numbers;
    private int place;
    private boolean wc;
    private boolean conditioner;


    // TODO: Вероятно стоит добавить вторую коллекцию - для неактивных броней. Т.е. сняли бронь или прошло время бронирования, переносим дату брони в неактивный список, а в тек. списке оставляем только активные.
    private List<ReservationDate> reservationDates = new ArrayList<>();

    public Room(int aNumber, int aPlace, boolean aWC, boolean aConditioner) {
        numbers = aNumber;
        place = aPlace;
        wc = aWC;
        conditioner = aConditioner;
    }

    /**
     * Возвращате номер комнаты.
     *
     * @return
     */
    public int getNumbers() {
        return numbers;
    }

    /**
     * Возвращает площадь комнаты.
     *
     * @return площадь кв.м.
     */
    public int getPlace() {
        return place;
    }

    /**
     * Возвращает статус, имеется ли туалет в комнате.
     *
     * @return true - туалет есть.
     */
    public boolean hasWc() {
        return wc;
    }

    /**
     * Возвращает статус, имеется ли кондиционер в комнате.
     *
     * @return true - кондиционер есть.
     */
    public boolean hasConditioner() {
        return conditioner;
    }

    /**
     * Выполняет резервирование комнаты.
     *
     * @param aReservationDate
     * @return true - если резервирование прошло успешно и на указанную дату нет существующей брони. false - в ином случае.
     */
    public boolean reserve(ReservationDate aReservationDate) {
        if (!isReservedOnTargetDate(aReservationDate)) {
            reservationDates.add(aReservationDate);
            aReservationDate.setActiveReservation(true);
            String outputMessage = String.format("На указанные даты '%s', номер №_%s забронирован.", aReservationDate.toString(), numbers);
            System.out.println(outputMessage);
            return true;
        } else {
            String outputMessage = String.format("На указанные даты %s, номер №_%s забронировать не удалось. Выберите другую дату брони.", aReservationDate.toString(), numbers);
            System.out.println(outputMessage);
            return false;
        }
    }

    /**
     * Снимает бронь с команты. Бронь снимается только при совпадении дат начала брони и окончания брони.
     *
     * @param aReservationDate
     * @return - true - если бронь снята. false - если бронь не была снята. Не нашли бронь по указанным заданным датам.
     */
    public boolean release(ReservationDate aReservationDate) {
        Optional<ReservationDate> oReservedDate = getSameReservedDate(aReservationDate);
        if (oReservedDate.isPresent()) {
            oReservedDate.get().setActiveReservation(false);
            String outputMessage = String.format("Для указанных дат '%s', с номера №_%s снята бронь.", aReservationDate.toString(), numbers);
            System.out.println(outputMessage);
            return true;
        }
        String outputMessage = String.format("Для указанных дат '%s', для номера №_%s нет брони. Бронь снята не будет", aReservationDate.toString(), numbers);
        System.out.println(outputMessage);
        return false;
    }

    /**
     * Выполняет проверку, нет ли брони на указаную дату.
     *
     * @return - true - если на указанную дату уже есть бронь. Проверяется на (1. Включение диапазона. 2. Пересечение диапазона). false - в ином случае.
     */
    private boolean isReservedOnTargetDate(ReservationDate aReservationDate) {
        boolean isPresentRange = false;
        for (ReservationDate reservationDate : reservationDates) {
            isPresentRange = isPresentRange || reservationDate.isBelongToRange(aReservationDate);
            if (isPresentRange)
                return isPresentRange;
        }
        return isPresentRange;
    }

    /**
     * Ищет идентичную бронь, что совпадает по датам.
     * Необходимо для снятия брони с номера.
     *
     * @param aReservationDate
     * @return
     */
    private Optional<ReservationDate> getSameReservedDate(ReservationDate aReservationDate) {
        for (ReservationDate reservationDate : reservationDates) {
            if (reservationDate.isSameDate(aReservationDate))
                return Optional.of(reservationDate);
        }
        return Optional.empty();
    }

    /**
     * Возвращает все активные брони от текущей даты и вперед (в будущее).
     * Необходимо при бронировании, когда хотим понять, в какой период свободна комната.
     *
     * @return List<ReservationDate> - список броней для текущей комнаты.
     */
    public List<ReservationDate> getCurrentAndFutureReservation() {
        LocalDate now = LocalDate.now();
        List<ReservationDate> list = new ArrayList<>();
        for (ReservationDate reservationDate : reservationDates) {
            if (reservationDate.isActiveReservationOnFutureDate(now))
                list.add(reservationDate);
        }

        return list;
    }

    /**
     * Проверяет есть ли активная бронь на текущей день.
     *
     * @return - true - если активная бронь на текущей день найдена. false - если брьни нет.
     */
    public boolean isReservedAtNow() {
        for (ReservationDate rd : reservationDates) {
            if (rd.isActiveReservationAtNow()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String infoNumber = String.format("Номер = %d, Площадь = %d, Наличие туалета = %b, Наличие кондиционера = %b", numbers, place, wc, conditioner);
        return infoNumber;
    }
}
