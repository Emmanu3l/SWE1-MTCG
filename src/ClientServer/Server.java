package ClientServer;
import java.io.*;
import java.net.*;
import java.sql.SQLOutput;

public class Server {
    //register and Login
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Socket socket = serverSocket.accept();
            System.out.println("Client connected successfully.");
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string = bufferedReader.readLine();
            System.out.println("Client: " + string);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Hi, I'm a server.");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
