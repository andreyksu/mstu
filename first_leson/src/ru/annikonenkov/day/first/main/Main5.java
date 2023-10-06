package ru.annikonenkov.day.first.main;

/**
 * Класс с рекурсивной функцией.
 */
public class Main5 {
    public static void main(String args[]) {
        System.out.println(f(5));

    }

    public static int f(int num) {
        if (num > 2) {
            return f(num - 1) + g(num - 2);
        }
        return num;
    }

    public static int g(int num) {
        if (num > 1) {
            return f(num - 1) + g(num - 2);
        }
        return num;
    }
}
