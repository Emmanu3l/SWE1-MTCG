package main.mtcg;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.mtcg.cards.Card;
import main.mtcg.cards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    @JsonProperty("Username")
    private String username;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("Bio")
    private String bio;
    @JsonProperty("Image")
    private String image;
    private int coins = 20;
    //the deck is used in the battles against other players.
    //private ArrayList<Card> deck;
    //private Deck deck;
    private ArrayList<Card> deck =  new ArrayList<>();
    //a user has multiple cards in his stack.
    //a stack is the collection of all his current cards (hint: cards can be removed by trading).
    private ArrayList<Card> stack;
    private int elo = 100;
    private int gamesPlayed = 0;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String bio, String image) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public User(String username, String password, String bio, String image, int coins, int elo) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
        this.coins = coins;
        this.elo = elo;
    }



    public User(String username, String password, ArrayList<Card> deck, ArrayList<Card> stack) {
        this.username = username;
        this.password = password;
        this.deck = deck;
        this.stack = stack;
    }

    public void removeFromDeck(Card c) {
        this.deck.remove(c);
    }

    public void addToDeck(Card c) {
        this.deck.add(c);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //TODO: is it really best practice to have a getPassword() method? (probably not)
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

    public void eloUp() {
        this.elo += 3;
    }

    public void eloDown() {
        this.elo -= 5;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void acquirePackages() {
        if (coins >= 4) {
            coins-=5;
            //add a package
        }
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

    //stack is collection of all current main.mtcg.cards
    public void stackTrade() {

    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", deck=" + deck +
                ", stack=" + stack +
                ", coins=" + coins +
                ", elo=" + elo +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return coins == user.coins && elo == user.elo && gamesPlayed == user.gamesPlayed && username.equals(user.username) && password.equals(user.password) && Objects.equals(deck, user.deck) && Objects.equals(stack, user.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, deck, stack, coins, elo, gamesPlayed);
    }
}
