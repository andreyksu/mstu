package ru.annikonenkov.day.first.main;

import java.util.Scanner;

/**
 * Увеличение числа на 15%
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число: ");
        int num = scanner.nextInt();
        double add = num + (num * 0.15);
        System.out.println("Ваше число увеличенно на 15% = " + add);
    }
}