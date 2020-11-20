package main.java.webservicehandler;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestContext {
    //GET, POST, PUT, DELETE
    private String verb;
    private String URI;
    private String version;
    //private Map<String, String> headers;
    private ArrayList<String> headers;
    private String body;

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
}