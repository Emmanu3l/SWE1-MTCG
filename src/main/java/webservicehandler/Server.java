package main.java.webservicehandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8000);
        Socket socket = listener.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        RequestParser request = new RequestParser();
        RequestContext response = request.parseRequest(in);

    }

    //TODO: REGISTER MESSAGE API ENDPOINTS
    //TODO: TEST THE WEBSERCVICE HANDLER
    //TODO: store POSTED/PUT DATA AND MAKE IT AVAILABLE FOR FUTURE GET REQUESTS OR UNAVAILABLE IN CASE OF DELETE
    public static String sendResponse(RequestContext requestContext) {
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


        } else if (requestContext.getVerb().equals("POST")) {
            //create new resource
            //if created: 201 (Created) + entity which describes the status of the request and refers to the new resource, and a location header
            //if resource can't be identified by a URI, either HTTP response code 200 (OK) or 204 (No Content)

        } else if (requestContext.getVerb().equals("PUT")) {
            //update an existing resource
            //if it doesn't exist yet, decide between creating it or not
            //if created: 201 (Created)
            //if modified: 200 (OK) or 204 (No Content)

        } else if (requestContext.getVerb().equals("DELETE")) {
            //delete resources identified by request URI
            //if successful and response includes entity describing status: 200 (OK)
            //if action has been queued: 202 (Accepted)
            //if action was performed but the response does not include an entity: 204 (No Content)

        } else {

        }
        return responseBuilder.toString();
    }

}