package ru.annikonenkov.day.second.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9123);
            System.out.println("Сервер запущен");
            Class.forName("org.postgresql.Driver");//.getDeclaredConstructor().newInstance();
            while (true) {
                Socket socket = serverSocket.accept(); // Ждём подключения клиентов
                User user = new User(socket);
                System.out.println("Клиент подключился");
                users.add(user);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendMessage(user, "Для регистрации 'req', \n Для авторизации 'login'");
                            //user.getOut().writeUTF("Ввдите имя: ");
                            String command = user.getIs().readUTF();
                            while (true) {
                                if (command.equals("req")) {
                                    user.reg();
                                    break;
                                } else if (command.equals("login")) {
                                    if (user.login()) break;
                                } else {
                                    sendMessage(user, "Неизвестная команда");
                                }
                            }
                            sendMessage(user, "Добро пожаловать на сервер");
                            sendOnlineUsers(users);
                            //user.getOut().writeUTF("Добро пожаловать на сервер");
                            String request;
                            while (true) {
                                request = user.getIs().readUTF();// ждём пока поступит сообщение
                                System.out.println("Сообщение от клиента: " + request);
                                for (User user1 : users) {
                                    if (user.equals(user1)) continue;
                                    //user1.getOut().writeUTF(user.getName() + ": " + request);
                                    sendMessage(user1, user.getName() + ": " + request);
                                }
                            }
                        } catch (IOException e) {
                            users.remove(user);
                            try {
                                sendOnlineUsers(users);
                            } catch (IOException ee) {
                                ee.printStackTrace();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                thread.start();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) { // | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException
            throw new RuntimeException(e);
        }
    }

    static void sendMessage(User user, String message) throws IOException {
        JSONObject json = new JSONObject();
        json.put("message", message);
        user.getOut().writeUTF(json.toJSONString());
    }

    static void sendOnlineUsers(List<User> userList) throws IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (User user : userList) {
            jsonArray.add(user.getName());
        }
        /*userList.stream().forEach((user) -> {
            jsonArray.add(user.getName());
        });*/

        jsonObject.put("onlineUsers", jsonArray);
        for (User user : userList) {
            user.getOut().writeUTF(jsonObject.toJSONString());
        }
    }
}