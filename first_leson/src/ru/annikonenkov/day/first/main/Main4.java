package ru.annikonenkov.day.first.main;

/**
 * Класс с дополнительным методом.
 */
public class Main4 {
    public static void main(String[] args) {
        doSomething(3, 4);
    }

    public static boolean doSomething(int x, int y) {
        return x + y > 10 ? true : false;
    }
}
