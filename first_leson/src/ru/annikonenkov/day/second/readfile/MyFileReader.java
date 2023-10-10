package ru.annikonenkov.day.second.readfile;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyFileReader {
    public static void main(String[] args) {
        writeStringInFile("String");
    }

    public static void writeStringInFile(String str) {
        try {
            FileOutputStream fos = new FileOutputStream("text.txt", true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(str.getBytes());
            bos.flush();
            bos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
