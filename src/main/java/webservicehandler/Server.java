package main.java.webservicehandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        while (true) {
            ServerSocket listener = new ServerSocket(8000);
            Socket socket = listener.accept();
        }
        /*BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        RequestParser requestParser = new RequestParser();
        RequestContext request = requestParser.parseRequest(in);
        ResponseBuilder responseBuilder = new ResponseBuilder();
        System.out.println(responseBuilder.sendResponse(request));*/
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


}