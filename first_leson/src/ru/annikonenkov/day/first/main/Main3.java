package ru.annikonenkov.day.first.main;

/**
 * Класс поиска среднего.
 * Класс поиска максимального нечетного.
 */
public class Main3 {
    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Введите число: ");
        //String num = scanner.next();
        int[] score = {45, 55, 332, 34, 90};
        double summ = 0;
        for (int i : score) {
            summ = summ + i;
        }
        //System.out.println(summ / score.length);

        //----------------------------------------
        //Найти максимальное нечётное число
        int[] numbers = {-11, 234, 532, -45, -47};
        int result = 0;
        boolean isChanged = false;
        for (int i : numbers) {
            int tmp = 0;
            if (i % 2 != 0) {
                tmp = i;
                if (tmp != result && ((!isChanged) || result < tmp)) {
                    result = tmp;
                    isChanged = true;
                }
            }
        }
        System.out.println(result);
    }
}
