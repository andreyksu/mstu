package ru.annikonenkov.day.second.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class User {
    private String name;
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream out;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;

    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.is = new DataInputStream(this.socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOut() {
        return out;
    }


    public boolean reg() throws IOException, SQLException {
        System.out.println("Внутри метода req()");
        Connection connection = DriverManager.getConnection(Database.DB_URL, Database.DB_LOGIN, Database.DB_PASSWORD);
        Statement statement = connection.createStatement();
        Server.sendMessage(this, "Введите Имя");
        String name = this.getIs().readUTF();
        Server.sendMessage(this, "Введите логин");
        String login = this.getIs().readUTF();
        Server.sendMessage(this, "Введите пароль");
        String pass = this.getIs().readUTF();
        // ДЗ: проверить, можно ли зарегистрировать пользователя (проверить что такого логина нет)
        //statement.executeUpdate("INSERT INTO `users`(`name`, `login`, `pass`) VALUES ('" + name + "','" + login + "','" + pass + "')");
        statement.executeUpdate("insert into users (username, login, pass) values('Ivan3', 'Ivan2@gmail.com', 123)");
        statement.close();
        return true;
    }

    public boolean login() throws IOException, SQLException {
        Connection connection = DriverManager.getConnection(Database.DB_URL, Database.DB_LOGIN, Database.DB_PASSWORD);
        Statement statement = connection.createStatement();
        Server.sendMessage(this, "Введите логин");
        String login = this.getIs().readUTF();
        Server.sendMessage(this, "Введите пароль");
        String pass = this.getIs().readUTF();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `users` WHERE `login`='" + login + "' AND `pass`='" + pass + "'");
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            int userId = resultSet.getInt("id");
            this.setName(name);
            this.setUserId(userId);
            return true;
        } else {
            Server.sendMessage(this, "Неправильный логин или пароль");
            return false;
        }
    }
}