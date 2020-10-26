import Card.Card;

public class User {
    private String username;
    private String password;
    private int coins = 20;
    private Card[] stack;
    private Card[] deck;
    private int elo = 100;

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

    public void battle() {

    }

    public void manageCards() {

    }

}
