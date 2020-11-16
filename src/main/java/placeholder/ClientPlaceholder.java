package main.java.placeholder;

import java.io.*;
import java.net.*;

public class ClientPlaceholder {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Hi, I'm a client.");
            printWriter.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string = bufferedReader.readLine();
            System.out.println("ClientServer.Server: " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}