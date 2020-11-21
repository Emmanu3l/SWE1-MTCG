package test.java.webservicehandler;

import main.java.webservicehandler.RequestContext;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void parseRequest() {
        /*String test = "GET /messages HTTP/1.1\r\n"
                + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
                + "Host: www.google.com\r\n"
                + "Accept-Language: en-us\r\n";
        ArrayList<String, String> headers = new ArrayList<>();
        headers.add("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        headers.add("Host", "www.google.com");
        headers.add("Accept-Language", "en-us");
        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        RequestContext requestContext = new RequestContext();
        Server.parseRequest(in);
        assertEquals("GET", requestContext.getVerb());
        assertEquals("/hello.htm", requestContext.getURI());
        assertEquals("HTTP/1.1", requestContext.getVersion());
        assertEquals(headers, requestContext.getHeaders());*/
    }

    @Test
    void sendResponse() {
    }
}