package test.java.webservicehandler;

import main.java.webservicehandler.RequestContext;
import main.java.webservicehandler.Server;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @org.junit.jupiter.api.Test
    void parseRequest() throws IOException {
        String test = "GET /hello.htm HTTP/1.1\r\n";
        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        RequestContext requestContext = Server.parseRequest(in);
        assertEquals(requestContext.getVerb(), "GET");
    }
}