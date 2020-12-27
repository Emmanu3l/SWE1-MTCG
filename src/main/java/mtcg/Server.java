package main.java.mtcg;

import main.java.webservicehandler.RequestContext;
import main.java.webservicehandler.ResponseCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

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
    private main.java.webservicehandler.RequestContext requestContext;
    private Dictionary<Integer, String> messages;
    public Server(Socket s, main.java.webservicehandler.RequestContext requestContext, Dictionary<Integer, String> messages) {
        this.s = s;
        this.requestContext = requestContext;
        //TODO: messages: dictionary, hashmap, treemap um message ID beizubehalten
        this.messages = messages;
    }
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket listener = new ServerSocket(10001);
            main.java.webservicehandler.RequestContext requestContext = new RequestContext();
            Dictionary<Integer, String> messages = new Hashtable<>();
            messages.put(1, "Hallo,");
            messages.put(2, " ich");
            messages.put(3, " bin");
            messages.put(4, " eine");
            messages.put(5, " Nachricht");
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
            //in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            this.requestContext.parseRequest(in);
            this.requestContext.parseBody(in);
            String response = ResponseCodes.generateResponse(requestContext, messages);
            out.println(response); //TODO: doesn't get printed? check whether it gets stuck at body or whether this actually doesn't print
            //out.println(messages);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}