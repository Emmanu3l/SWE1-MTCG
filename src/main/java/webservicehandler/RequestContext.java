package main.java.webservicehandler;


import java.util.HashMap;
import java.util.Map;

public class RequestContext {
    //GET, POST, PUT, DELETE
    private String verb;
    private String URI;
    private String version;
    private Map<String, String> headers;
    private String body;

    public RequestContext(String verb, String URI, String version, Map<String, String> headers, String body) {
        this.verb = verb;
        this.URI = URI;
        this.version = version;
        this.headers = new HashMap<>();
        this.body = body;
    }

}
