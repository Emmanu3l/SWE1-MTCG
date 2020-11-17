package test.java.webservicehandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @org.junit.jupiter.api.Test
    void parseRequest() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("GET /hello.htm HTTP/1.1\r\n");

    }
}