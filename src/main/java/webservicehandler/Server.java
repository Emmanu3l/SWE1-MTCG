package main.java.webservicehandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server implements Runnable {
    //for persistent storage, fields are needed
    private final Socket s;
    //eine liste die für alle threads zugänglich ist, ist vonnöten
    private final RequestContext requestContext;
    private ArrayList<String> messages;


    public Server(Socket s, RequestContext requestContext, ArrayList<String> messages) {
        this.s = s;
        this.requestContext = requestContext;
        this.messages = messages;
    }

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket listener = new ServerSocket(8000);
            RequestContext requestContext = new RequestContext();
            ArrayList<String> messages = new ArrayList<>();
            messages.add("Hallo,");
            messages.add(" ich");
            messages.add(" bin");
            messages.add(" eine");
            messages.add(" Nachricht");
            while (true) {
                Socket socket = listener.accept();
                Server server = new Server(socket, requestContext, messages);
                Thread thread = new Thread(server);
                //start() as opposed to run(), since run() is the equivalent of copy+pasting the code in main() instead of creating a thread
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: mehr objektorientierung
        //TODO: branches
        //TODO: connect to postman or insomnia via https://localhost:8000/ (failed)

    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(s.getOutputStream(), true);
            parseRequest(in);
            sendResponse(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseRequest(BufferedReader in) throws IOException {
        //parse the http request's header containing the following information
        String request = null;
        StringBuilder requestBuilder = new StringBuilder();
        String readRequest = in.readLine();
        while (readRequest!= null && !readRequest.isBlank()) {
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

        //detect blank line to make sure the body exists
        //parse body (aka payload)
        //the body is optional and contains additional information for the server

        StringBuilder bodyBuilder = new StringBuilder();
        while (in.ready()) {
            //for some godforsaken reason the body isn't recognized as characters and it took me a few days to get this
            bodyBuilder.append((char)in.read());
        }
        String body = bodyBuilder.toString();
        System.out.println("body: " + body);
        requestContext.setVerb(verb);
        requestContext.setURI(URI);
        requestContext.setVersion(version);
        requestContext.setHeaders(headers);
        requestContext.setBody(body);
    }

    public void sendResponse(PrintWriter out) {
        //TODO: REGISTER MESSAGE API ENDPOINTS
        //TODO: TEST THE WEBSERCVICE HANDLER
        //TODO: store POSTED/PUT DATA AND MAKE IT AVAILABLE FOR FUTURE GET REQUESTS OR UNAVAILABLE IN CASE OF DELETE
        //handle request and send appropriate response
        //helpful info:
        //https://en.wikipedia.org/wiki/HTTP_location
        //https://restfulapi.net/http-methods
        //https://learnxinyminutes.com/docs/java/
        //https://restfulapi.net/http-status-codes/

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append(this.requestContext.getVersion());
        String messageID = this.requestContext.getMessageID();
        if (requestContext.getVerb().equals("GET")) {
            //retrieve information without modifying it
            //if found: 200 (OK)
            //along with response body (XML or JSON)
            //if not found: 404 (NOT FOUND)
            //syntax error: 400 (BAD REQUEST)

            if (this.requestContext.getURI().equals("/messages")) {
                responseBuilder.append(ResponseCodes.OK.toString());
                for (String s: this.messages) {
                    responseBuilder.append(s);
                }
            } else if (messageID != null && this.requestContext.getURI().equals("/messages/" + messageID)) {
                responseBuilder.append(ResponseCodes.OK.toString());
                responseBuilder.append(this.messages.get(Integer.parseInt(messageID) - 1));
            }

        } else if (requestContext.getVerb().equals("POST")) {
            //create new resource
            //if created: 201 (Created) + entity which describes the status of the request and refers to the new resource, and a location header
            //if resource can't be identified by a URI, either HTTP response code 200 (OK) or 204 (No Content)

            if (this.requestContext.getURI().equals("/messages")) {
                this.messages.add(this.requestContext.getBody());
                responseBuilder.append(ResponseCodes.CREATED.toString());
                responseBuilder.append(this.messages.indexOf(this.requestContext.getBody()));
            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }

        } else if (requestContext.getVerb().equals("PUT")) {
            //update an existing resource
            //if it doesn't exist yet, decide between creating it or not (in my case it will not be created to uphold the consistency of the request verbs)
            //if created: 201 (Created)
            //if modified: 200 (OK) or 204 (No Content)

            if (messageID != null && this.requestContext.getURI().equals("/messages/" + messageID)) {
                if (this.messages.get(Integer.parseInt(messageID)) != null && !this.messages.get(Integer.parseInt(messageID)).isEmpty()) {
                    this.messages.add(Integer.parseInt(messageID), this.requestContext.getBody());
                    responseBuilder.append(ResponseCodes.OK.toString());
                } else if (this.requestContext.getBody() == null || this.requestContext.getBody().isEmpty() || this.requestContext.getBody().isBlank()) {
                    responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
                }
            }

        } else if (requestContext.getVerb().equals("DELETE")) {
            //delete resources identified by request URI
            //if successful and response includes entity describing status: 200 (OK)
            //if action has been queued: 202 (Accepted)
            //if action was performed but the response does not include an entity: 204 (No Content)
            if (messageID != null && this.requestContext.getURI().equals("/messages/" + messageID)) {
                this.messages.remove(Integer.parseInt(messageID));
                responseBuilder.append(ResponseCodes.OK.toString());
            } else {
                responseBuilder.append(ResponseCodes.NO_CONTENT.toString());
            }

        } else {
            responseBuilder.append(ResponseCodes.BAD_REQUEST.toString());
        }
        if (out != null) {
            out.println(responseBuilder.toString().trim());
            out.flush();
        }
    }

}