package test.java.webservicehandler;

import main.java.webservicehandler.RequestContext;
import main.java.webservicehandler.Server;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void generateResponse() throws IOException {
        /*String test = "GET /messages HTTP/1.1\r\n"
                + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
                + "Host: www.google.com\r\n"
                + "Accept-Language: en-us\r\n"
                + "\r\n"
                + "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
                + "<string xmlns=\"http://clearforest.com/\">string</string>\r\n";

        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        RequestContext requestContext = new RequestContext();
        requestContext.parseRequest(in);
        assertEquals("HTTP/1.1", Server.generateResponse(test));*/
    }
}