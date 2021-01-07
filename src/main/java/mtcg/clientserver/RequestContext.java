package main.java.mtcg.clientserver;
import com.fasterxml.jackson.core.JsonProcessingException;
import main.java.mtcg.Json;
import main.java.mtcg.Postgres;
import main.java.mtcg.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;

public class RequestContext {
    //GET, POST, PUT, DELETE
    private String verb;
    private String URI;
    private String version;
    //TODO: shouldn't it be "header"?
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

    //TODO: retire messageID
    //TODO: the purpose of this method could perhaps be more fittingly described with the name "handleRequest" instead of generateResponse
    public String handleRequest(Dictionary<Integer, String> messages) throws JsonProcessingException, SQLException {
        //TODO: TEST THE WEBSERVICE HANDLER
        //handle request and send appropriate response
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(getVersion());
        //String messageID = getMessageID();
        if (getVerb().equals("GET")) {
            //retrieve information without modifying it
            //if found: 200 (OK)
            //along with response body (XML or JSON)
            //if not found: 404 (NOT FOUND)
            //syntax error: 400 (BAD REQUEST)
            //TODO: THE ERROR LIES HERE. MAKE SURE TO ACCOUNT FOR TRAILING SLASHES, AS IN: IT COULD BE "/messages" or "/messages/"
            //TODO: recommended procedure: remove all trailing slashes
            /*if (getURI().equals("/messages")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                for (int i = 0; i < messages.size(); i++) {
                    responseBuilder.append(messages.get(i));
                }
            } else if (messageID != null && getURI().equals("/messages" + messageID)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(messages.get(Integer.parseInt(messageID) - 1));
            }*/
            if (getURI().equals("/cards")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //output all cards that belong to a specific user
                //...
            } else if (getURI().equals("/deck")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //...
            } else if (getURI().equals("/users" /* + /username */)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //...
            } else if (getURI().equals("/stats")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //...
            } else if (getURI().equals("/score")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //...
            }

        } else if (getVerb().equals("POST")) {
            //create new resource
            //if created: 201 (Created) + entity which describes the status of the request
            //and refers to the new resource, and a location header
            //if resource can't be identified by a URI, either HTTP response code 200 (OK) or 204 (No Content)
            //if (getURI().equals("/messages")) {
            //messages.put(messages.size(), getBody());
            if (getURI().equals("/users")) {
                User user = Json.parseUser(getBody());
                new Postgres().register(user);
                responseBuilder.append(ResponseCodes.CREATED.toString());
                responseBuilder.append(messages.get(getBody()));
                //TODO: parse user, pass object to database
                //TODO: this is what you have to do next
            } else if (getURI().equals("/sessions")) {

            } else if (getURI().equals("/packages")) {

            } else if (getURI().equals("/transactions/packages")) {

            } else if (getURI().equals("/tradings")) {

            } else if (getURI().equals("/battles")) {
                //TODO: so this is where the battle happens


            } else if (getURI().equals("/tradings" + "" /* + /id */)) {

            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }
        } else if (getVerb().equals("PUT")) {
            //update an existing resource
            //if it doesn't exist yet, decide between creating it or not (in my case it will not be created
            //to uphold the consistency of the request verbs)
            //if created: 201 (Created)
            //if modified: 200 (OK) or 204 (No Content)
            /*if (messageID != null && getURI().equals("/messages/" + messageID)) {
                if (messages.get(Integer.parseInt(messageID)) != null
                        && !messages.get(Integer.parseInt(messageID)).isEmpty()) {
                    messages.put(Integer.parseInt(messageID), getBody());
                    responseBuilder.append(ResponseCodes.OK.toString());
                    //responseBuilder.append(messages.indexOf(requestContext.getBody()));
                } else if (getBody() == null || getBody().isEmpty()
                        || getBody().isBlank()) {
                    responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
                }
            }*/
            if (getURI().equals("/deck")) {
                    responseBuilder.append(ResponseCodes.OK.toString());
                    //...

            } else if (getURI().equals("/users" /* + /username */)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //...
            }

        } else if (getVerb().equals("DELETE")) {
            //delete resources identified by request URI
            //if successful and response includes entity describing status: 200 (OK)
            //if action has been queued: 202 (Accepted)
            //if action was performed but the response does not include an entity: 204 (No Content)
            /*if (messageID != null && getURI().equals("/messages/" + messageID)) {
                messages.remove(Integer.parseInt(messageID));
                responseBuilder.append(ResponseCodes.OK.toString());
            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }*/
            if (getURI().equals("/tradings" /* + /id */)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                //...
            }
        } else {
            responseBuilder.append(ResponseCodes.BAD_REQUEST.toString());
        }
        //TODO:something went wrong here... check with println
        //System.out.println("RESPONSE " + responseBuilder.toString());
        return responseBuilder.toString().trim();
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

    /*public String getMessageID() {
        String messageID;
        String[] extractID = URI.split("/");
        if (extractID.length == 3) {
            messageID = extractID[2];
        } else {
            messageID = null;
        }
        return messageID;
    }*/

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
        if (readRequest != null) {
            request = requestBuilder.toString();
            String[] parsedRequest = request.trim().split("\r\n");
            String[] requestLine = parsedRequest[0].trim().split(" ");
            System.out.println("requestLine: "+ Arrays.toString(requestLine));
            String verb = requestLine[0].trim();
            System.out.println("verb: " + verb);
            String URI = requestLine[1].trim();
            System.out.println("URI: " + URI);
            String version = requestLine[2].trim();
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