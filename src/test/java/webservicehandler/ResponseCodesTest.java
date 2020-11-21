package test.java.webservicehandler;

import main.java.webservicehandler.RequestContext;
import main.java.webservicehandler.ResponseCodes;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResponseCodesTest {

    @Test
    void generateResponse() throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Hallo,");
        messages.add(" ich");
        messages.add(" bin");
        messages.add(" eine");
        messages.add(" Nachricht");

        String test = "GET /messages HTTP/1.1\r\n"
            + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
            + "Host: www.google.com\r\n"
            + "Accept-Language: en-us\r\n"
            + "\r\n"
            + "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
            + "<string xmlns=\"http://clearforest.com/\">string</string>\r\n";

        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        ArrayList<String> headers = new ArrayList<>();
        headers.add("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        headers.add("Host: www.google.com");
        headers.add("Accept-Language: en-us");
        RequestContext requestContext = new RequestContext("GET","/messages", "HTTP/1.1", headers,
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + "<string xmlns=\"http://clearforest.com/\">string</string>\r\n");
        requestContext.parseRequest(in);
        assertEquals("HTTP/1.1 200 (OK)\r\nHallo, ich bin eine Nachricht", ResponseCodes.generateResponse(requestContext, messages));

    }
}