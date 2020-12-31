package main.java.mtcg.clientserver;

import main.java.mtcg.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

//TODO: mehr objektorientierung
//TODO: branches
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
    //connection for sql
    private Connection connection;

    public Server(Socket s, RequestContext requestContext, Dictionary<Integer, String> messages) {
        this.s = s;
        this.requestContext = requestContext;
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
            this.requestContext.parseBody(in);
            String response = this.requestContext.generateResponse(messages);
            //out.write(new String(response, StandardCharsets.UTF_8));
            out.println(response);
            out.flush();
            in.close();
            out.close();
            s.close();
            //out.println(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        //this.connection = DriverManager.getConnection();
    }

    public void disconnect() throws SQLException {
        this.connection.close();
    }

    public void register(User user) {

    }

    public void login(User user) {

    }

}