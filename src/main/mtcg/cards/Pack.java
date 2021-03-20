package main.mtcg.cards;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import main.mtcg.User;

import java.util.ArrayList;

public class Pack extends CardCollection {

    @JsonDeserialize(as = ArrayList.class, contentAs = Card.class)
    private ArrayList<Card> pack;
    //private User owner;

    public Pack() {
    }

    public Pack(ArrayList<Card> pack) {
        this.pack = pack;
    }

    @JsonGetter
    public ArrayList<Card> getPack() {
        return pack;
    }

    @JsonSetter
    public void addCardToCollection(Card card) {
        this.pack.add(card);
    }

    public Card getCardFromCollection(String id) {
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
