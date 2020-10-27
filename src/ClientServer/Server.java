package ClientServer;
import java.net.*;
import java.io.*;


public class Server {
    //register and Login
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
