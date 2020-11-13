import cards.Card;
import cards.Monster;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Card> deck = new LinkedList<>();
        LinkedList<Card> stack = new LinkedList<>();
        Monster card = new Monster("Leeroy Jenkins", 6, "paladin");
        User user = new User("Ben Schulz", "letsdothis", deck, stack);
    }
}
