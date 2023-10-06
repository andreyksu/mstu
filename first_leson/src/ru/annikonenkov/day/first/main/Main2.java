package ru.annikonenkov.day.first.main;

import java.util.Scanner;

/**
 * Проверка числа на равенство 10.
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число: ");
        String num = scanner.next();
        if (num.equals("10")){
            System.out.println("Вероно. Ваше число равно 10.");
        }
        else{
            System.out.println("Не верно. Ваше число не равно 10");
        }
    }
}