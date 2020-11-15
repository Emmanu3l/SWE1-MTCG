package restwebservices;

import java.util.LinkedList;
import java.util.Scanner;

public class WebserviceHandler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String request = scanner.nextLine();
        System.out.println(request);
        RequestContext response = parseRequest(request);
        System.out.println(response);
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
