package main.mtcg;

import main.mtcg.cards.*;

import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private User battlerOne;
    private User battlerTwo;
    private Card winningCard;
    private User winningUser;
    private User losingUser;
    private Card randomCardOne;
    private Card randomCardTwo;
    private double damageDealt;
    private boolean isDraw = false;
    private String whichWins;
    private int battlerOneWins = 0;
    private int battlerTwoWins = 0;
    //TODO: variable for winner?

    public Battle() {

    }

    public Battle(User battlerOne, User battlerTwo) {
        this.battlerOne = battlerOne;
        this.battlerTwo = battlerTwo;
    }


    //a battle is a request to the server to compete against another user with your currently defined deck.

    //TODO: need to parse type and race from curl input so i can get those in my game logic method
    //TODO: need to implement "game mechanics" (users can login, register, acquire cards, define a deck, battle, and compare stats in score board)
    //TODO: trading deals
    //TODO: unique feature (idee: "intrinsic motivation" oder "pride" - monster gewinnen in round 1 immer gegen spells). Or maybe random critical hit?
    //TODO: set git repository to PUBLIC after handing in the project
    //TODO: trading deals
    //TODO: focus on parsing the requests, take a closer look at the curl script

    //TODO: create a test for this class
    public User returnWinner() {
        //maximum of 100 rounds
        for (int i = 0; i < 100; i++) {
            // each battler draws one card each
            //TODO: adjust the following three lines for the new deck datatype
            randomCardOne = battlerOne.getDeck().get(ThreadLocalRandom.current().nextInt(battlerOne.getDeck().size()));
            randomCardTwo = battlerTwo.getDeck().get(ThreadLocalRandom.current().nextInt(battlerTwo.getDeck().size()));
            Card card = returnWinningCard(randomCardOne, randomCardTwo);
            winningUser.addToDeck(card);
            losingUser.removeFromDeck(card);
            System.out.println(generateLog());
            //TODO: take over cards
            //count wins for users
        }
        if (battlerOneWins > battlerTwoWins) {
            battlerOne.eloUp();
            battlerTwo.eloDown();
            System.out.println(battlerOne.getUsername() + " wins");
            return battlerOne;
        } else {
            battlerTwo.eloUp();
            battlerOne.eloDown();
            System.out.println(battlerTwo.getUsername() + " wins");
            return battlerTwo;
        }
        //if it is a draw, the elo stays unchanged
    }

    //create GameResult Class which contains damage dealt by both Cards and the winner?
    //TODO: this is the perfect method for a unit test
    public Card returnWinningCard(Card randomCardOne, Card randomCardTwo) {
        double damageDealt = 0;
        //Monster Fight
        if (randomCardOne.isMonster() && randomCardTwo.isMonster()) {
            //specialties
            if (randomCardOne.getRace() == Race.GOBLIN && (randomCardTwo).getRace() == Race.DRAGON) {
                //Goblins are too afraid of Dragons to attack.
                damageDealt = -randomCardTwo.getDamage();
            } else if (randomCardOne.getRace() == Race.WIZZARD && (randomCardTwo).getRace() == Race.ORK) {
                //Wizzard can control Orks so they are not able to damage them.
                damageDealt = randomCardOne.getDamage();
            } else if (randomCardOne.getRace() == Race.ELF && randomCardOne.getElement() == Element.FIRE && (randomCardTwo).getRace() == Race.DRAGON) {
                //The FireElves know Dragons since they were little and can evade their attacks.
                damageDealt = randomCardOne.getDamage();
            } else {
                //by default, element types are irrelevant for Monster Battles
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage();
            }
            //Spell Fight
        } else if (randomCardOne.isSpell() && randomCardTwo.isSpell()) {
            if (randomCardOne.getElement() == Element.WATER && randomCardOne.getElement() == Element.FIRE) {
                damageDealt = randomCardOne.getDamage() * 2 - randomCardTwo.getDamage();
            } else if (randomCardOne.getElement() == Element.FIRE && randomCardOne.getElement() == Element.WATER) {
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage() * 2;
            } else if (randomCardOne.getElement() == Element.FIRE && randomCardOne.getElement() == Element.NORMAL) {
                damageDealt = randomCardOne.getDamage() * 2 - randomCardTwo.getDamage();
            } else if (randomCardOne.getElement() == Element.NORMAL && randomCardOne.getElement() == Element.FIRE) {
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage() * 2;
            } else if (randomCardOne.getElement() == Element.NORMAL && randomCardOne.getElement() == Element.WATER) {
                damageDealt = randomCardOne.getDamage() * 2 - randomCardTwo.getDamage();
            } else if (randomCardOne.getElement() == Element.WATER && randomCardOne.getElement() == Element.NORMAL) {
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage() * 2;
            } else {
                //if they both have the same element type, no matter which one, the damage will not be changed
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage();
            }
            //Mixed Fight
        } else if (randomCardOne.isMonster() && randomCardTwo.isSpell()) {
            ///specialties
            if (randomCardOne.getRace() == Race.KNIGHT && randomCardTwo.getElement() == Element.WATER) {
                damageDealt = -randomCardTwo.getDamage();
            } else if (randomCardOne.getRace() == Race.KRAKEN) {
                damageDealt = randomCardOne.getDamage();
            } else {
                //by default, the damage numbers are used as is
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage();
            }
            //Reverse Mixed Fight
        } else if (randomCardOne.isSpell() && randomCardTwo.isMonster()) {
            if (randomCardOne.getElement() == Element.WATER && (randomCardTwo).getRace() == Race.KNIGHT) {
                damageDealt = randomCardOne.getDamage();
            } else if (randomCardTwo.getRace() == Race.KRAKEN) {
                damageDealt = -randomCardTwo.getDamage();
            } else {
                //by default, the damage numbers are used as is
                damageDealt = randomCardOne.getDamage() - randomCardTwo.getDamage();
            }
        }
        this.damageDealt = damageDealt;
        if (damageDealt < 0) {
            winningUser = battlerTwo;
            losingUser = battlerOne;
            winningCard = randomCardTwo;
            battlerTwoWins += 1;
            whichWins = winningCard.getName() + " wins";
            //TODO: take over cards
        } else if (damageDealt > 0) {
            winningUser = battlerOne;
            losingUser = battlerTwo;
            winningCard = randomCardOne;
            battlerOneWins += 1;
            whichWins = winningCard.getName() + " wins";
            //TODO: take over cards
        } else {
            isDraw = true;
            whichWins = "Draw";
        }

        return winningCard;
    }

    public String generateLog() {
        return battlerOne.getUsername() + ": " + randomCardOne.getName() + "(" + randomCardOne.getDamage() + " Damage) vs " +
                battlerTwo.getUsername() + ": " + randomCardTwo.getName() + "(" + randomCardTwo.getDamage() + " Damage) " +
                "=> " + whichWins;

    }

}
