package webservicehandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket listener;
    public static void main(String[] args) {
        try {
            listener = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Socket socket = listener.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();
            RequestContext response = parseRequest(request);
            while ((request = in.readLine()) != null) {
                System.out.println(request);
            }
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //parse the http request's header containing the following information
    public static RequestContext parseRequest(String request) {
        String[] parsedRequest = request.trim().split(" ");
        //parse header
        //request line: verb, URI, Version
        //optional request headers (name:value, ...)
        //check for blank line, then call parseBody
        String verb = parsedRequest[0];
        System.out.println(verb);
        String URI = parsedRequest[1];
        System.out.println(URI);
        String version = parsedRequest[2];
        System.out.println(version);
        //new line
        String headers = "";

        //blank line

        //parse body
        //optional body with additional information for the server

        return new RequestContext(verb, URI, version, headers);
    }

}
