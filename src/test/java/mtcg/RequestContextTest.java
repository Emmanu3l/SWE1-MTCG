package java.mtcg;

import main.java.mtcg.clientserver.RequestContext;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RequestContextTest {

    @Test
    void parseRequest() throws IOException {
        String test = "GET /messages HTTP/1.1\r\n"
                + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
                + "Host: www.google.com\r\n"
                + "Accept-Language: en-us\r\n"
                + "\r\n"
                + "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
                + "<string xmlns=\"http://clearforest.com/\">string</string>\r\n";

        ArrayList<String> headers = new ArrayList<>();
        headers.add("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        headers.add("Host: www.google.com");
        headers.add("Accept-Language: en-us");
        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        RequestContext requestContext = new RequestContext();
        requestContext.parseRequest(in);
        assertEquals("GET", requestContext.getVerb());
        assertEquals("/messages", requestContext.getURI());
        assertEquals("HTTP/1.1", requestContext.getVersion());
        assertEquals(headers, requestContext.getHeaders());
    }
}