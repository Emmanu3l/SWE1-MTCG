package main.java.mtcg;

import main.java.mtcg.cards.Card;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<Card> deck;
    private ArrayList<Card> stack;
    private int coins = 20;
    private int elo = 100;

    public User(String username, String password, ArrayList<Card> deck, ArrayList<Card> stack) {
        this.username = username;
        this.password = password;
        this.deck = deck;
        this.stack = stack;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getStack() {
        return stack;
    }

    public void setStack(ArrayList<Card> stack) {
        this.stack = stack;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
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

    //stack is collection of all current main.java.mtcg.cards
    public void stackTrade() {

    }


}
