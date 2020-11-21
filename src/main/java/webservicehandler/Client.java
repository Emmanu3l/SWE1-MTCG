package main.java.webservicehandler;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write("GET /messages HTTP/1.1\r\n");
            out.write("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
            out.write("Host: www.google.com\r\n");
            out.write("Accept-Language: en-us\r\n");
            out.write("Accept-Encoding: gzip, deflate\r\n");
            out.write("Connection: Keep-Alive\r\n");
            out.write("\r\n");
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
            out.write("<string xmlns=\"http://clearforest.com/\">string</string>\r\n");

            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}