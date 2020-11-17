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
        RequestContext response = parseRequest(in);

    }

    //parse the http request's header containing the following information
    public static RequestContext parseRequest(BufferedReader in) throws IOException {
        String request = null;
        request = in.readLine();
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
                System.out.println("headers: " + headers);
            } catch (ArrayIndexOutOfBoundsException a) {
                bodyBuilder.append(request);
            }
        }

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

    //TODO: REGISTER MESSAGE API ENDPOINTS
    //TODO: TEST THE WEBSERCVICE HANDLER
    public static void sendResponse(RequestContext requestContext) {

    }

}
