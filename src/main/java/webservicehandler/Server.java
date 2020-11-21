package main.java.webservicehandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
//TODO: mehr objektorientierung
//TODO: branches
//TODO: connect to postman or insomnia via https://localhost:8000/ (failed)
//interesting resources:
//https://en.wikipedia.org/wiki/HTTP_location
//https://restfulapi.net/http-methods
//https://learnxinyminutes.com/docs/java/
//https://restfulapi.net/http-status-codes/
public class Server implements Runnable {
    //for persistent storage, fields are needed
    private final Socket s;
    //list that is available to all threads
    private RequestContext requestContext;
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
                //start() as opposed to run(), since run() is the equivalent of copy+pasting the code in main()
                //instead of creating a thread
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(s.getOutputStream(), true);
            this.requestContext.parseRequest(in);
            this.requestContext.parseBody(in);
            String response = ResponseCodes.generateResponse(requestContext, messages);
            out.println(response);
            out.println(messages);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}