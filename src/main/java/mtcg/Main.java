package main.java.mtcg;

import main.java.mtcg.cards.Card;
import main.java.mtcg.cards.Monster;
import main.java.mtcg.clientserver.RequestContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Main {
    //TODO: move the stuff from Server.java here, retire Client.java. This should be the only class with a main method
    //TODO: in case of any errors: file > invalidate caches/restart
    //TODO: consider removing the possibly redundant dependencies in pom.xml. For more info: https://www.baeldung.com/jackson-object-mapper-tutorial
    public static void main(String[] args) throws IOException {
        /*ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> stack = new ArrayList<>();
        Monster card = new Monster("Leeroy Jenkins", 6, "paladin");
        User user = new User("Ben Schulz", "letsdothis", deck, stack);*/
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
                main.java.mtcg.clientserver.Server server = new main.java.mtcg.clientserver.Server(socket, requestContext, messages);
                Thread thread = new Thread(server);
                //start() as opposed to run(), since run() is the equivalent of copy+pasting the code in main()
                //instead of creating a thread
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
