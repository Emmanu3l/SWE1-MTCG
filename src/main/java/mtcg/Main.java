package main.java.mtcg;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.mtcg.cards.Card;
import main.java.mtcg.cards.Monster;
import main.java.mtcg.clientserver.RequestContext;
import main.java.mtcg.clientserver.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Locale;

public class Main {
    //TODO: this should be the only class with a main method
    //TODO: in case of any errors: file > invalidate caches/restart
    //TODO: consider removing the possibly redundant dependencies in pom.xml. For more info: https://www.baeldung.com/jackson-object-mapper-tutorial
    //interesting info: https://www.youtube.com/watch?v=GqkWFltAjhw
    public static void main(String[] args) throws IOException {
        /*try {
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
        }*/
        User user1 = new User("kienboec", "daniel");
        User user2 = Server.parseUser("{\"Username\":\"kienboec\", \"Password\":\"daniel\"}");
        System.out.println(user1.equals(user2));
        Card card1 = new Monster("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0);
        Card card2 = Server.parseMonster("{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}");
    }

}

        /*ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> stack = new ArrayList<>();
        Monster card = new Monster("Leeroy Jenkins", 6, "paladin");
        User user = new User("Ben Schulz", "letsdothis", deck, stack);*/
