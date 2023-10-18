package ru.annikonenkov.day.second.thread;

/**
 * Второй день. Три примера запуска потоков.
 */
public class MultiThread {
    public static void main(String[] rgs) {
        MyThread mthread1 = new MyThread("Мыть посуду");
        MyThread mthread2 = new MyThread("Стирать");
        MyThread mthread3 = new MyThread("Убираться");
        //mthread1.start();
        //mthread2.start();
        //mthread3.start();

        MyRunnable mr1 = new MyRunnable("Мыть посуду");
        MyRunnable mr2 = new MyRunnable("Стирать");
        MyRunnable mr3 = new MyRunnable("Убираться");
        Thread thread = new Thread(mr1);
        Thread thread1 = new Thread(mr2);
        Thread thread2 = new Thread(mr3);
        //thread.start();
        //thread1.start();
        //thread2.start();

        Thread threadd1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 100; i++) {
                    System.out.println("Моя задача" + "'i++ " + i + "%");
                }
            }
        });
        threadd1.start();
    }
}

class MyRunnable implements Runnable {
    String task;

    public MyRunnable(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 1; i < 100; i++) {
            System.out.println(task + "'i++ " + i + "%");
        }
    }
}

class MyThread extends Thread {
    String task;

    public MyThread(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i < 100; i++) {
            System.out.println(task + "'i++ " + i + "%");
        }
    }
}