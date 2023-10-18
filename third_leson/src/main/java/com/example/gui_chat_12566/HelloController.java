package com.example.gui_chat_12566;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HelloController {
    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    @FXML
    private VBox userList;

    DataOutputStream outputStream;

    @FXML
    private void onSendHandler() {
        String msg = textField.getText();
        textArea.appendText("\n" + msg);
        textField.clear();
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            System.out.println("Потеряно соединение с сервером");
        }
    }

    @FXML
    private void connect() {
        try {
            Socket socket = new Socket("127.0.0.1", 9123);
            outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            Thread thread = new Thread(new Runnable() { //Создание потока для чтения входящего сообщения
                @Override
                public void run() {
                    try {
                        while (true) {
                            String resp = inputStream.readUTF();
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(resp);
                            if (jsonObject.containsKey("onlineUsers")) {
                                Platform.runLater(new Runnable() {//Отложенное исполнение, кода, что взаимодействует с UI - объектами/элементами.
                                    @Override
                                    public void run() {
                                        userList.getChildren().removeAll();
                                        JSONArray jsonArray = (JSONArray) jsonObject.get("onlineUsers");
                                        jsonArray.forEach(item -> {
                                            //textArea.appendText(item.toString() + "\n");
                                            Button userBtn = new Button();
                                            userBtn.setText(item.toString());
                                            userBtn.setPrefWidth(200);
                                            userList.getChildren().add(userBtn);
                                        });
                                    }
                                });
                            } else {
                                String msg = jsonObject.get("message").toString();
                                textArea.appendText(resp + "\n");
                            }
                        }
                    } catch (IOException | ParseException e) {
                        textArea.appendText("Вероятно соединение прервалось...");
                    }

                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}