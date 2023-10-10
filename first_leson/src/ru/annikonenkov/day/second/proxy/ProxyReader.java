package ru.annikonenkov.day.second.proxy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Arrays;

public class ProxyReader {
    public static void main(String[] str) {
        checkSocket(null, 0);
    }

    private static void readFromFile() {
        FileInputStream fis;
        try {
            fis = new FileInputStream("ip.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        String ip;
        int port;
        String socket = "";
        while (i != -1) {
            try {
                i = fis.read();
                if (i == 10) {
                    socket += "\n";
                } else if (i == 13) {
                    continue;
                } else if (i == 9) {
                    socket += ":";
                } else {
                    socket += (char) i;
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String[] sockets = socket.split("\n");
        for (String sock : sockets) {
            String[] sk = sock.split(":");
            //System.out.println("ip = " + sk[0] + " port = " + sk[1]);
            checkSocket(sk[0], Integer.valueOf(sk[1]));
        }
    }

    private static void checkSocket(String ip, int port) {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            URL url = new URL("https://vozhzhaev.ru/test.php");
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection(proxy);
            InputStream is = urlCon.getInputStream();
            int i = 0;
            while (i != -1) {
               // i = is.read();
                //System.out.println((char) i);

            }

        } catch (Exception e) {

        }

    }
}
