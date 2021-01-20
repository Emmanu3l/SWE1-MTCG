package main.mtcg.clientserver;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import main.mtcg.User;
import main.mtcg.Battle;
import main.mtcg.cards.*;

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

@JsonAutoDetect
public class Server implements Runnable {
    //for persistent storage, fields are needed
    private /*final */Socket s;
    //list that is available to all threads
    private /*final */RequestContext requestContext;
    private /*final */Dictionary<Integer, String> messages;
    //connection for sql
    //private Connection connection;

    public Server(Socket s, RequestContext requestContext, Dictionary<Integer, String> messages/*, Connection connection*/) {
        this.s = s;
        this.requestContext = requestContext;
        this.messages = messages;
        //this.connection = connection;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            out = new PrintWriter(s.getOutputStream(), true, StandardCharsets.UTF_8);
            this.requestContext.parseRequest(in);
            this.requestContext.parseBody(in);
            String response = this.requestContext.handleRequest(messages);
            System.out.println(response);
            out.println(response);
            out.flush();
            in.close();
            out.close();
            s.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
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