package main.java.mtcg.cards;

import java.util.ArrayList;

public class Package {
    private ArrayList<Card> pack;

    public Package() {
    }

    public Package(ArrayList<Card> pack) {
        this.pack = pack;
    }

    public ArrayList<Card> getPack() {
        return pack;
    }

    public void setPack(ArrayList<Card> pack) {
        this.pack = pack;
    }

    public Card getCardFromPack(String id) {
        for (Card c: this.pack) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = null;
        for (Card c: this.pack) {
            result.append(c.toString());
        }
        return result.toString();
    }
}
