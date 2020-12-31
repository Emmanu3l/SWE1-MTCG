package main.java.mtcg.clientserver;

import main.java.mtcg.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.Hashtable;

//TODO: mehr objektorientierung
//TODO: branches
//TODO: connect to postman or insomnia via https://localhost:10001/
//TODO: add user management methods
//interesting resources:
//https://en.wikipedia.org/wiki/HTTP_location
//https://restfulapi.net/http-methods
//https://learnxinyminutes.com/docs/java/
//https://restfulapi.net/http-status-codes/
public class Server implements Runnable {
    //for persistent storage, fields are needed
    private final Socket s;
    //list that is available to all threads
    private final RequestContext requestContext;
    private final Dictionary<Integer, String> messages;
    public Server(Socket s, RequestContext requestContext, Dictionary<Integer, String> messages) {
        this.s = s;
        this.requestContext = requestContext;
        //TODO: messages: dictionary, hashmap, treemap um message ID beizubehalten
        this.messages = messages;
    }
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket listener = new ServerSocket(10001);
            RequestContext requestContext = new RequestContext();
            Dictionary<Integer, String> messages = new Hashtable<>();
            messages.put(0, "Hallo,");
            messages.put(1, " ich");
            messages.put(2, " bin");
            messages.put(3, " eine");
            messages.put(4, " Nachricht");
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
        //BufferedOutputStream out = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            //in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //out = new PrintWriter(s.getOutputStream(), true);
            out = new PrintWriter(s.getOutputStream(), true, StandardCharsets.UTF_8);
            //out = new BufferedOutputStream(s.getOutputStream());
            this.requestContext.parseRequest(in);
            //TODO: test the Server without parsing the body
            this.requestContext.parseBody(in);
            //TODO: there is something wrong with sending the response. Maybe the method needs more arguments?
            String response = this.requestContext.generateResponse(messages);
            //TODO: doesn't get printed? check whether it gets stuck at body or whether this actually doesn't print
            //out.write(new String(response, StandardCharsets.UTF_8));
            out.println(response);
            out.flush();
            //TODO: close server after giving response
            in.close();
            out.close();
            s.close();
            //out.println(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(User user) {

    }

    public void login(User user) {

    }

}