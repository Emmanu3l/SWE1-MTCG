package main.java.mtcg;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestContext {
    //GET, POST, PUT, DELETE
    private String verb;
    private String URI;
    private String version;
    private ArrayList<String> headers;
    private String body;

    public RequestContext(String verb, String URI, String version, ArrayList<String> headers, String body) {
        this.verb = verb;
        this.URI = URI;
        this.version = version;
        this.headers = headers;
        this.body = body;
    }

    public RequestContext() {

    }

    public String getVerb() {
        return verb;
    }

    public String getURI() {
        return URI;
    }

    public String getVersion() {
        return version;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessageID() {
        String messageID;
        String[] extractID = this.URI.split("/");
        if (extractID.length == 3) {
            messageID = extractID[2];
        } else {
            messageID = null;
        }
        return messageID;
    }
    public void parseRequest(BufferedReader in) throws IOException {
        //parse the http request's header containing the following information
        String request = null;
        StringBuilder requestBuilder = new StringBuilder();
        String readRequest = in.readLine();
        while (readRequest != null && !readRequest.isEmpty() && !readRequest.isBlank()) {
            requestBuilder.append(readRequest).append("\r\n");
            System.out.println("readRequest: " + readRequest);
            readRequest = in.readLine();
        }
        request = requestBuilder.toString();
        String[] parsedRequest = request.trim().split("\r\n");
        String[] requestLine = parsedRequest[0].trim().split(" ");
        System.out.println("requestLine: "+ Arrays.toString(requestLine));
        String verb = requestLine[0];
        System.out.println("verb: " + verb);
        String URI = requestLine[1];
        System.out.println("URI: " + URI);
        String version = requestLine[2];
        System.out.println("version: " + version);
        //parse header
        //request line: verb, URI, Version
        //optional request headers (name:value, ...)
        //check for blank line, then call parseBody
        //new line
        //skip spaces and/or empty lines or avoid continuing if all that's left is whitespace
        ArrayList<String> headers = new ArrayList<>(Arrays.asList(parsedRequest).subList(1, parsedRequest.length));
        System.out.println("headers: " + headers);
        this.setVerb(verb);
        this.setURI(URI);
        this.setVersion(version);
        this.setHeaders(headers);
    }

    public void parseBody(BufferedReader in) throws IOException {
        //separate method because in.ready() doesn't work with the BufferedReader created from a StringReader
        //which is used in RequestContextTest
        //detect blank line to make sure the body exists
        //parse body (aka payload)
        //the body is optional and contains additional information for the server
        StringBuilder bodyBuilder = new StringBuilder();
        while (in.ready()) {
            //for some godforsaken reason the body isn't recognized as chars and it took me a few days to notice
            bodyBuilder.append((char)in.read());
        }
        String body = bodyBuilder.toString().trim();
        System.out.println("body: " + body);
        this.setBody(body);
    }

}