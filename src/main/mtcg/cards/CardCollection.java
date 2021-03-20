package main.mtcg.cards;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

public class CardCollection {
    @JsonDeserialize(as = ArrayList.class, contentAs = Card.class)
    private ArrayList<Card> cardCollection;
    //private User owner;

    public CardCollection() {
    }

    public CardCollection(ArrayList<Card> pack) {
        this.cardCollection = pack;
    }

    @JsonGetter
    public ArrayList<Card> getCardCollection() {
        return cardCollection;
    }

    @JsonSetter
    public void addCardToCollection(Card card) {
        this.cardCollection.add(card);
    }

    public Card getCardFromCollection(String id) {
        for (Card c: this.cardCollection) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void setCardCollection(ArrayList<Card> cardCollection) {
        this.cardCollection = cardCollection;
    }

    @Override
    public String toString() {
        StringBuilder result = null;
        for (Card c: this.cardCollection) {
            result.append(c.toString());
        }
        return result.toString();
    }

}
