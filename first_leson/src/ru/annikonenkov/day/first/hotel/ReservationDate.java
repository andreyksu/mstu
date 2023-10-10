package ru.annikonenkov.day.first.hotel;

import java.time.LocalDate;

/**
 * Пока не используется. Идея была бронировать на заданные даты с проверокой при бронировании - не забранорово ли на заданную дату.
 */
public class ReservationDate {

    private LocalDate startDateReserve;
    private LocalDate endDateReserve;
    /**
     * Поле говорит об актуальности резерва/брони. Т.к. может быть так, что пользователь выехал раньше или отказался от брони.
     * Но в случае отказа от брони и ведения истории брони, лучше завести класс-журнал бронирования, где будет и история и ФИО бронирующего.
     */
    private boolean isActiveReservation = false;

    public ReservationDate(LocalDate aStartReserve, LocalDate aEndReserve) {//TODO: Добавить проверку, что первая дата, меньше даты окончания. Хотя можно менять их местами.
        startDateReserve = aStartReserve;
        endDateReserve = aEndReserve;
    }

    public LocalDate getStartDateReserve() {
        return startDateReserve;
    }

    public LocalDate getEndDateReserve() {
        return endDateReserve;
    }

    public void setActiveReservation(boolean aActiveReservation) {
        isActiveReservation = aActiveReservation;
    }

    /**
     * Проверяет, есть ли пересечение по диапазону дат с уже имеющейся бронью.
     *
     * @return - true - если на указанаую дату уже существует активная бронь. false - если брони нет, либо бронь снята.
     */
    public boolean isBelongToRange(ReservationDate otherReservationDate) {
        LocalDate first = otherReservationDate.getStartDateReserve();
        LocalDate second = otherReservationDate.getEndDateReserve();

        if (!isActiveReservation)//Если активация не активна, то считаем что эта брон допустима для перебронирования.
            return false;

        if ((startDateReserve.isBefore(first) && endDateReserve.isAfter(first)))
            return true;
        else if (startDateReserve.isBefore(second) && endDateReserve.isAfter(second))
            return true;
        else if (startDateReserve.isAfter(first) && endDateReserve.isBefore(second))
            return true;
        else
            return false;
    }

    public boolean isSameDate(ReservationDate otherReservationDate) {
        LocalDate first = otherReservationDate.getStartDateReserve();
        LocalDate second = otherReservationDate.getEndDateReserve();
        return startDateReserve.isEqual(first) && endDateReserve.isEqual(second);
    }

    /**
     * Проверяет, является ли текущая бронь актипвной, т.е. дата окончания брони должна быть больше чем заданная дата.
     *
     * @param targetDateForCompare - дата, относительно которой будет вестись сравнение даты окончания брони.
     * @return - true - если бронь активна И дата окончания брони больше чем заданная дата. false - в ином случае
     */
    public boolean isActiveReservationOnFutureDate(LocalDate targetDateForCompare) {
        return isActiveReservation && endDateReserve.isAfter(targetDateForCompare);
    }

    public boolean isActiveReservationAtNow() {
        LocalDate now = LocalDate.now();
        return isActiveReservation && (startDateReserve.isBefore(now) && endDateReserve.isAfter(now));
    }

    /**
     * Проверяет, является ли текущая бронь актипвной и находится ли эта бронь в прошлом по отношению текущей дате.
     *
     * @return - true - если бронь активна И дата окончания брони больше чем заданная дата. false - в ином случае
     */
    public boolean isActiveReservationOnPassedDate() {
        LocalDate now = LocalDate.now();
        return isActiveReservation && endDateReserve.isBefore(now);
    }

    //TODO: Возможно стоит добавить метод, который будет пробегаться по датам и выставлять у прошедших дат - как неактуальные даты резервирования. Т.е. возможен случай, когда прошло время а бронь не сняли.
    public String toString() {
        String reservedInfo = String.format("Дата начала = %s ----- Дата окончания = %s", startDateReserve.toString(), endDateReserve.toString());
        return reservedInfo;
    }


}
