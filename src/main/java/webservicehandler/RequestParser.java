package main.java.webservicehandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

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

        return new RequestContext(verb, URI, version, headers, body);
    }


}
