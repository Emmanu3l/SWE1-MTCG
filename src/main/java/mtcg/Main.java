package main.java.mtcg;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.mtcg.cards.Card;
import main.java.mtcg.cards.Element;
import main.java.mtcg.cards.Monster;
import main.java.mtcg.clientserver.RequestContext;
import main.java.mtcg.clientserver.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {
    //TODO: this should be the only class with a main method
    //TODO: in case of any errors: file > invalidate caches/restart
    //TODO: consider removing the possibly redundant dependencies in pom.xml. For more info: https://www.baeldung.com/jackson-object-mapper-tutorial
    //interesting info: https://www.youtube.com/watch?v=GqkWFltAjhw
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
        /*User user1 = new User("kienboec", "daniel");
        User user2 = Server.parseUser("{\"Username\":\"kienboec\", \"Password\":\"daniel\"}");
        System.out.println(user1.equals(user2));
        Card card1 = new Card("845f0dc7-37d0-426e-994e-43fc3ac83c08", "WaterGoblin", 10.0);
        Card card2 = Server.parseCard("{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}");
        System.out.println(card1.equals(card2));
        System.out.println(card1);
        System.out.println(card2);
        //TODO: test creating packages
        List<Card> pack1 = Server.parsePackage("[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"Dragon\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 20.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"Ork\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 25.0}]");
        System.out.println(pack1);
        for (Card c: pack1) {
            System.out.println(c.getElement());
            //TODO: this won't work when it's a spell. How to check for that without creating Monster and Spell instances?
            if (c.isMonster()) {
                System.out.println(c.getRace());
            }
        }*/
    }

}

        /*ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> stack = new ArrayList<>();
        Monster card = new Monster("Leeroy Jenkins", 6, "paladin");
        User user = new User("Ben Schulz", "letsdothis", deck, stack);*/

//TODO: is having subclasses for Card really necessary? after all, you can determine the type from the name with a simple .contains() statement
//TODO: on the other hand, why not automatically choose between spell and monster when generating an object from json, with a .contains() statement as well