package ru.annikonenkov.day.first.hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс описывает комнату - наличе тех или иных условий, дату бронирования итд.
 */
public class Room {

    private int numbers;
    private int place;
    private boolean wc;
    private boolean conditioner;
    private boolean reserved;

    /**
     * Пока не используется. Идея была бронировать на заданные даты.
     */
    private List<ReservedDate> reservedDates = new ArrayList<ReservedDate>();

    public Room(int aNumber, int aPlace, boolean aWC, boolean aConditioner, boolean aReserved) {
        numbers = aNumber;
        place = aPlace;
        wc = aWC;
        conditioner = aConditioner;
        reserved = aReserved;
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
     * Возвращает статус, забронирован ли номер.
     *
     * @return true - если номер забронирован.
     */
    public boolean isReserved() {
        return reserved;
    }

    public void setReserved() {
        reserved = true;
    }

    public void setReleased() {
        reserved = false;
    }

    /**
     * Пока не используется. Идея была бронировать на заданные даты.
     */
    public void setReserved(boolean aReserved, Date aStartDate, Date aEndDate) {
        if (aStartDate.before(aEndDate)) {
            this.reserved = reserved;
            System.out.println("На заданные даты ");
        } else {

        }
    }

    public Date getCurrentDate() {
        return new Date();
    }


}
