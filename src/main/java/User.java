package main.java;

import main.java.cards.Card;

import java.util.LinkedList;

public class User {
    private String username;
    private String password;
    private LinkedList<Card> deck;
    private LinkedList<Card> stack;
    private int coins = 20;
    private int elo = 100;

    public User(String username, String password, LinkedList<Card> deck, LinkedList<Card> stack) {
        this.username = username;
        this.password = password;
        this.deck = deck;
        this.stack = stack;
    }

    public void acquirePackages() {
        if (coins >= 4) {
            coins-=40;
            //add a package
        }
    }

    public void register() {

    }

    public void login() {

    }

    public void defineDeck() {

    }

    public void manageCards() {

    }
    //deck is used in battles against other players
    public void deckAddCard() {

    }
    public void deckRemoveCard() {

    }

    //stack is collection of all current main.java.cards
    public void stackTrade() {

    }


}
