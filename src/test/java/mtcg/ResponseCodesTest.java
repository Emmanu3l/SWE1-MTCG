package test.java.mtcg;

import main.java.webservicehandler.RequestContext;
import main.java.webservicehandler.ResponseCodes;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class ResponseCodesTest {

    //TODO: add generateResponse Test for POST and DELETE

    @Test
    void generateResponse1() throws IOException {
        Dictionary<Integer, String> messages = new Hashtable<>();
        messages.put(1, "Hallo,");
        messages.put(2, " ich");
        messages.put(3, " bin");
        messages.put(4, " eine");
        messages.put(5, " Nachricht");

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

    @Test
    void generateResponse2() throws IOException {
        Dictionary<Integer, String> messages = new Hashtable<>();
        messages.put(1, "Hallo,");
        messages.put(2, " ich");
        messages.put(3, " bin");
        messages.put(4, " eine");
        messages.put(5, " Nachricht");

        String test = "PUT /messages/3 HTTP/1.1\r\n"
                + "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
                + "Host: www.google.com\r\n"
                + "Accept-Language: en-us\r\n"
                + "\r\n"
                + "keine\r\n";

        Reader inputString = new StringReader(test);
        BufferedReader in = new BufferedReader(inputString);
        ArrayList<String> headers = new ArrayList<>();
        headers.add("User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
        headers.add("Host: www.google.com");
        headers.add("Accept-Language: en-us");
        RequestContext requestContext = new RequestContext("GET","/messages", "HTTP/1.1", headers,
                "keine");
        requestContext.parseRequest(in);
        assertEquals("HTTP/1.1 200 (OK)\r\n[Hallo,,  ich,  bin, keine,  Nachricht]", ResponseCodes.generateResponse(requestContext, messages) + "\r\n" + messages);
    }

}