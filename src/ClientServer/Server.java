package ClientServer;
import java.io.*;
import java.net.*;

public class Server {
    //register and Login
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Socket socket = serverSocket.accept();

            System.out.println("connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
