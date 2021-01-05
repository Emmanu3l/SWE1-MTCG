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

public class Main {
    //TODO: this should be the only class with a main method
    //TODO: in case of any errors: file > invalidate caches/restart
    //TODO: consider removing the possibly redundant dependencies in pom.xml. For more info: https://www.baeldung.com/jackson-object-mapper-tutorial
    //interesting info: https://www.youtube.com/watch?v=GqkWFltAjhw
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:10001/postgres";

    //Database credentials
    static final String USER = "postgres";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //register JDBC driver
            Class.forName(JDBC_DRIVER);

            //open connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            ServerSocket listener = new ServerSocket(10001);
            RequestContext requestContext = new RequestContext();
            Dictionary<Integer, String> messages = new Hashtable<>();
            stmt = conn.createStatement();
            while (true) {
                Socket socket = listener.accept();
                Server server = new Server(socket, requestContext, messages, conn);
                Thread thread = new Thread(server);
                //start() as opposed to run(), since run() is the equivalent of copy+pasting the code in main()
                //instead of creating a thread
                thread.start();
            }

            /*already done
            String sql = "CREATE DATABASE USERS";
            stmt.executeUpdate(sql);
             */
        } catch (IOException | SQLException | ClassNotFoundException e){
            e.printStackTrace();
            System.exit(0);
        }
        finally {
            //close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch(SQLException ignored){
            }
            try {
                if (conn != null)
                    conn.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

}

        /*ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> stack = new ArrayList<>();
        Monster card = new Monster("Leeroy Jenkins", 6, "paladin");
        User user = new User("Ben Schulz", "letsdothis", deck, stack);*/

//TODO: is having subclasses for Card really necessary? after all, you can determine the type from the name with a simple .contains() statement
//TODO: on the other hand, why not automatically choose between spell and monster when generating an object from json, with a .contains() statement as well