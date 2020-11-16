package webservicehandler;


public class RequestContext {
    //GET, POST, PUT, DELETE
    private String verb;
    private String URI;
    private String version;
    private String headers;

    public RequestContext(String verb, String URI, String version, String headers) {
        this.verb = verb;
        this.URI = URI;
        this.version = version;
        this.headers = headers;
    }

}
