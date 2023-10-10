package ru.annikonenkov.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9123);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            Thread thread = new Thread();
            while (true) {
                String message = scanner.nextLine();
                dos.writeUTF(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
