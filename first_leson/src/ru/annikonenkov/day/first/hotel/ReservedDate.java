package ru.annikonenkov.day.first.hotel;

import java.util.Date;

/**
 * Пока не используется. Идея была бронировать на заданные даты.
 */
public class ReservedDate {

    private Date startReserve;
    private Date endReserve;

    public ReservedDate(Date aStartReserve, Date aEndReserve) {
        startReserve = aStartReserve;
        endReserve = aEndReserve;
    }

    /**
     * Проверяет, есть ли пересечение по диапазону дат с уже имеющейся бронью.
     *
     * @param first
     * @param second
     * @return
     */
    public boolean isBelongToRange(Date first, Date second) {
        if (startReserve.after(first) && endReserve.before(first)) {
            return true;
        } else if (startReserve.after(second) && endReserve.before(second)) {
            return true;
        } else {
            return false;
        }

    }

}
