package main.java.mtcg;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.mtcg.cards.Card;
import main.java.mtcg.cards.Element;
import main.java.mtcg.cards.Monster;
import main.java.mtcg.clientserver.RequestContext;
import main.java.mtcg.clientserver.Server;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

//TODO: the main difficulty now is how to save the users within the server and how to save the cards for every user, etc.
//TODO: remove unnecessary imports after finishing

public class Main {
    //TODO: this should be the only class with a main method
    //TODO: in case of any errors: file > invalidate caches/restart
    //TODO: consider removing the possibly redundant dependencies in pom.xml. For more info: https://www.baeldung.com/jackson-object-mapper-tutorial
    //interesting info: https://www.youtube.com/watch?v=GqkWFltAjhw

    //TODO: so basically, the main code work that is left to do is in RequestContext handle request. The main part is adding the received data to the db and using the gamelogic
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //no point in creating a postgres object variable if i'm not gonna use it again. The data stays on the db regardless
        //or would it make more sense to make it static?
        //this makes more sense, since it always creates a new connection, I think
        //TODO: uncomment the next line once you feel confident enough about the database part
        //new Postgres().createTables();
        try {
            ServerSocket listener = new ServerSocket(10001);
            RequestContext requestContext = new RequestContext();
            Dictionary<Integer, String> messages = new Hashtable<>();
            while (true) {
                Socket socket = listener.accept();
                Server server = new Server(socket, requestContext, messages/*, connection*/);
                Thread thread = new Thread(server);
                //start() as opposed to run(), since run() is the equivalent of copy+pasting the code in main()
                //instead of creating a thread
                thread.start();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

        /*ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> stack = new ArrayList<>();
        Monster card = new Monster("Leeroy Jenkins", 6, "paladin");
        User user = new User("Ben Schulz", "letsdothis", deck, stack);*/

//TODO: is having subclasses for Card really necessary? after all, you can determine the type from the name with a simple .contains() statement
//TODO: on the other hand, why not automatically choose between spell and monster when generating an object from json, with a .contains() statement as well
//TODO: so basically, i need a table for everything after the slash: users, sessions, packages, transactions, decks, stats, score, tradings and subtables like packages within transactions
//if exists drop table
//TODO: anschließend mit curl script testen
//TODO: create tests from the old comparisons between manually created objects and parsed objects around 1.01.2021