package main.java.webservicehandler;

import javax.swing.plaf.TableHeaderUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server implements Runnable {
    //for persistent storage, fields are needed
    private Socket s;
    //eine liste die für alle threads zugänglich ist, ist vonnöten
    private RequestContext requestContext;


    public Server(Socket s, RequestContext requestContext) {
        this.s = s;
        this.requestContext = requestContext;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8000);
        RequestContext requestContext = new RequestContext();
        while (true) {
            Socket socket = listener.accept();
            Server server = new Server(socket, requestContext);
            Thread thread = new Thread(server);
            //start() as opposed to run(), since run() is the equivalent of copy+pasting the code in main() instead of creating a thread
            thread.start();
        }
        //thread für server um immer neue requests zu akzeptieren
        //online resourcen checken
        //thread für Request Parser
        //eventuell RequestParser umbenennen
        //mehr objektorientierung
        //branches
        //mehr recherchieren
        //server LOOP!!!
        //TODO: connect to postman or insomnia via https://localhost:8000/

    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            RequestContext request = parseRequest(in);
            sendResponse(request, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //parse the http request's header containing the following information
    public RequestContext parseRequest(BufferedReader in) throws IOException {
        String request = null;
        do {
            request = in.readLine();
        } while (request == null);

        String[] parsedRequest = request.trim().split(" ");
        //parse header
        //request line: verb, URI, Version
        //optional request headers (name:value, ...)
        //check for blank line, then call parseBody
        String verb = parsedRequest[0];
        System.out.println("verb: " + verb);
        String URI = parsedRequest[1];
        System.out.println("URI: " + URI);
        String version = parsedRequest[2];
        System.out.println("version: " + version);
        //new line
        //further header values are supposed to be managed as key-value pairs -> HashMap<Key, Value>
        //skip spaces and/or empty lines or avoid continuing if all that's left is whitespace

        Map<String, String> headers = new HashMap<>();
        StringBuilder bodyBuilder = new StringBuilder();
        while (in.ready()) {
            try {
                request = in.readLine();
                if (request == null) break;
                String[] headerData = request.split(": ", 2);
                headers.put(headerData[0], headerData[1]);
            } catch (ArrayIndexOutOfBoundsException a) {
                bodyBuilder.append(request);
            }
        }
        System.out.println("headers: " + headers);

        //detect blank line to make sure the body exists
        //parse body (aka payload)
        //the body is optional and contains additional information for the server
        //int bodyLength = Integer.parseInt(headers.get("Content-Length"));

        String body = bodyBuilder.toString();
        System.out.println("body: " + body);
        /* {
            if (!in.readLine().isBlank()) {
                while (!in.readLine().isEmpty())
                bodyBuilder.append(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        requestContext.setVerb(verb);
        requestContext.setURI(URI);
        requestContext.setVersion(version);
        requestContext.setHeaders(headers);
        requestContext.setBody(body);
        return requestContext;
    }

    //TODO: REGISTER MESSAGE API ENDPOINTS
    //TODO: TEST THE WEBSERCVICE HANDLER
    //TODO: store POSTED/PUT DATA AND MAKE IT AVAILABLE FOR FUTURE GET REQUESTS OR UNAVAILABLE IN CASE OF DELETE
    public static String sendResponse(RequestContext requestContext, PrintWriter out) {
        StringBuilder responseBuilder = new StringBuilder();
        //handle request and send appropriate response
        //helpful info:
        //https://en.wikipedia.org/wiki/HTTP_location
        //https://restfulapi.net/http-methods
        //https://learnxinyminutes.com/docs/java/
        //https://restfulapi.net/http-status-codes/



        if (requestContext.getVerb().equals("GET")) {
            //retrieve information without modifying it
            //if found: 200 (OK)
            //along with response body (XML or JSON)
            //if not found: 404 (NOT FOUND)
            //syntax error: 400 (BAD REQUEST)

            /*if requestContext.getURI().equals()) {
                return requestContext.getVerb() + " " +
            }*/
            out.write(ResponseCodes.BAD_REQUEST.toString());


        } else if (requestContext.getVerb().equals("POST")) {
            //create new resource
            //if created: 201 (Created) + entity which describes the status of the request and refers to the new resource, and a location header
            //if resource can't be identified by a URI, either HTTP response code 200 (OK) or 204 (No Content)
            out.write(ResponseCodes.BAD_REQUEST.toString());

        } else if (requestContext.getVerb().equals("PUT")) {
            //update an existing resource
            //if it doesn't exist yet, decide between creating it or not
            //if created: 201 (Created)
            //if modified: 200 (OK) or 204 (No Content)
            out.write(ResponseCodes.BAD_REQUEST.toString());

        } else if (requestContext.getVerb().equals("DELETE")) {
            //delete resources identified by request URI
            //if successful and response includes entity describing status: 200 (OK)
            //if action has been queued: 202 (Accepted)
            //if action was performed but the response does not include an entity: 204 (No Content)
            out.write(ResponseCodes.BAD_REQUEST.toString());

        } else {
            out.write(ResponseCodes.BAD_REQUEST.toString());
        }
        return responseBuilder.toString();
    }

}