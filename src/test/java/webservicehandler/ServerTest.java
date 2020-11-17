package test.java.webservicehandler;

import main.java.webservicehandler.RequestContext;
import main.java.webservicehandler.Server;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @org.junit.jupiter.api.Test
    void parseRequest() throws IOException {
        String test = "GET /hello.htm HTTP/1.1\r\n"
                + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
                + "Host: www.google.com\r\n"
                + "Accept-Language: en-us\r\n";
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        headers.put("Host", "www.google.com");
        headers.put("Accept-Language", "en-us");
        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        RequestContext requestContext = Server.parseRequest(in);
        assertEquals("GET", requestContext.getVerb());
        assertEquals("/hello.htm", requestContext.getURI());
        assertEquals("HTTP/1.1", requestContext.getVersion());
        assertEquals(headers, requestContext.getHeaders());
    }

}