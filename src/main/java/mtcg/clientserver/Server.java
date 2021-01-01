package main.java.mtcg.clientserver;

import main.java.mtcg.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Dictionary;
import java.util.Hashtable;

//TODO: mehr objektorientierung
//TODO: branches
//TODO: use jackson, siehe moodle: deserialize JSON string into Java objects - JSON to Java Object
//interesting resources:
//https://en.wikipedia.org/wiki/HTTP_location
//https://restfulapi.net/http-methods
//https://learnxinyminutes.com/docs/java/
//https://restfulapi.net/http-status-codes/
//look up more information about "java multi threaded tcp server" to make sure you're doing it correctly

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
            /*
            messages.put(0, "Hallo,");
            messages.put(1, " ich");
            messages.put(2, " bin");
            messages.put(3, " eine");
            messages.put(4, " Nachricht");
             */
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

    public void connect(String url, User user) throws SQLException {
        this.connection = DriverManager.getConnection(url, user.getUsername(), user.getPassword());
    }

    public void disconnect() throws SQLException {
        this.connection.close();
    }

    //TODO: write the appropriate SQL statements and insert them
    public void register(User user) throws SQLException {
        //TODO: introduce id as primary key to allow duplicate usernames? I'll just use the username as a pk for now
        //interesting sources:
        //https://www.tutorialspoint.com/postgresql/postgresql_environment.htm
        //https://alvinalexander.com/java/java-mysql-insert-example-preparedstatement/
        //https://www.javatpoint.com/PreparedStatement-interface
        //https://www.sqlshack.com/learn-sql-naming-conventions/
        //https://stackoverflow.com/questions/36258247/java-prepared-statements-for-postgresql
        //if you try to view the sql operations through the lens of CRUD, sign up would correspond to create which in turn corresponds to insert
        //TODO: use datagrip https://www.jetbrains.com/datagrip/features/generation.html
        PreparedStatement preparedStatement = connection.prepareStatement("insert into user (username, password) values (?, ?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.execute();
    }

    public boolean login(User user) throws SQLException {
        //through the lens of CRUD, login corresponds to READ which in turn corresponds to SELECT
        PreparedStatement preparedStatement = connection.prepareStatement("select username, password from user where username = ? and password = ?");
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getString(1).equals(user.getUsername()) && resultSet.getString(2).equals(user.getPassword());
    }

    //TODO: if content type is JSON, get the body and use an objectmapper to convert to an object
    //https://www.baeldung.com/jackson-object-mapper-tutorial
    public Object parseJSON(String s) {
        //Object o = objectMapper.readValue(s, Object.class);
        return null;
    }

}