package main.java.mtcg.clientserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import main.java.mtcg.User;
import main.java.mtcg.Battle;
import main.java.mtcg.cards.*;

import java.io.*;
import java.lang.Package;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private /*final */Socket s;
    //list that is available to all threads
    private /*final */RequestContext requestContext;
    private /*final */Dictionary<Integer, String> messages;
    //connection for sql
    //private static Connection connection;
    private Connection connection;

    public Server(Socket s, RequestContext requestContext, Dictionary<Integer, String> messages, Connection connection) {
        this.s = s;
        this.requestContext = requestContext;
        this.messages = messages;
        this.connection = connection;
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
            String response = this.requestContext.handleRequest(messages);
            //out.write(new String(response, StandardCharsets.UTF_8));
            out.println(response);
            out.flush();
            in.close();
            out.close();
            s.close();
            //out.println(messages);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect(String url, User user) throws SQLException {
        this.connection = DriverManager.getConnection(url, user.getUsername(), user.getPassword());
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    //TODO: write the appropriate SQL statements and insert them
    public /*static */void register(User user) throws SQLException {
        //TODO: introduce id as primary key to allow duplicate usernames? I'll just use the username as a pk for now.
        //TODO: update - it seems like there is not point in adding a userid, since the username and password have to be checked anyway
        //interesting sources:
        //https://www.tutorialspoint.com/postgresql/postgresql_environment.htm
        //https://alvinalexander.com/java/java-mysql-insert-example-preparedstatement/
        //https://www.javatpoint.com/PreparedStatement-interface
        //https://www.sqlshack.com/learn-sql-naming-conventions/
        //https://stackoverflow.com/questions/36258247/java-prepared-statements-for-postgresql
        //if you try to view the sql operations through the lens of CRUD, sign up would correspond to create which in turn corresponds to insert
        //TODO: use datagrip https://www.jetbrains.com/datagrip/features/generation.html
        //TODO: should fail if there is an attempt to create another user with the same username AND password
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
    //TODO: maybe just parse JSON first and then figure out what to do with it?
    //https://www.baeldung.com/jackson-object-mapper-tutorial
    public static User parseUser(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, User.class);
    }

    public static String convertUser(User u) throws JsonProcessingException {
        //TODO: find out how to only print username and password
        return new ObjectMapper().writeValueAsString(u);
    }

    public static Card parseCard(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Card.class);
    }

    public static Spell parseSpell(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Spell.class);
    }

    public Battle parseBattle(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Battle.class);
    }

    public static List<Card> parsePackage(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, new TypeReference<>() {
        });
    }

    public Deck parseDeck(String s) throws JsonProcessingException {
        return new ObjectMapper().readValue(s, Deck.class);
    }

}
//TODO:
//how does the deck actually work? after a user has acquired packages, they can build a deck from it
//but when building a deck you only refer to the ids of the cards in the packages in your posession
//so a deck building request is simply a list of package-card ids
//so every time building a a deck is requested, you have to access the cards that a given user posesses
//i could either do this by creating a list of cards which is simply all of the cards he posesses
//and then removing them from the packages (get with id) and adding them to the decks
//or i could add them to a database
//for now, i will try implementing it without a database
//postpone database stuff until the end since it is pretty much the only thing not required for the curl script to execute properly