package main.java.mtcg;

import main.java.mtcg.cards.Card;
import main.java.mtcg.cards.ElementType;
import main.java.mtcg.cards.Monster;
import main.java.mtcg.cards.Spell;

import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private static User battlerOne;
    private static User battlerTwo;

    public static void main(String[] args) {
        //maximum of 100 cards
        for (int i = 0; i < 100; i++) {
            // each battler draws one card each
            Card randomCardOne = battlerOne.getDeck().get(ThreadLocalRandom.current().nextInt(battlerOne.getDeck().size()));
            Card randomCardTwo = battlerTwo.getDeck().get(ThreadLocalRandom.current().nextInt(battlerTwo.getDeck().size()));
            int damageDealt = gameLogic(randomCardOne, randomCardTwo);



        }

    }

    //create GameResult Class which contains damage dealt and the winner?
    public static int gameLogic(Card randomCardOne, Card randomCardTwo) {
        int damageDealt = 0;
        if (randomCardOne instanceof Monster && randomCardTwo instanceof Monster) {
            damageDealt = randomCardOne.getDmg() - randomCardTwo.getDmg();
        } else if (randomCardOne instanceof Spell && randomCardTwo instanceof Spell){
            if (randomCardOne.getElement() == randomCardOne.getElement()) {
                //if they both have the same element type, no matter which one, the damage will not be changed
                damageDealt = randomCardOne.getDmg() - randomCardTwo.getDmg();
            } else if (randomCardOne.getElement() == ElementType.WATER && randomCardOne.getElement() == ElementType.FIRE) {
                damageDealt = randomCardOne.getDmg() * 2 - randomCardTwo.getDmg();
            } else if (randomCardOne.getElement() == ElementType.FIRE && randomCardOne.getElement() == ElementType.WATER) {
                damageDealt = randomCardOne.getDmg() - randomCardTwo.getDmg() * 2;
            } else if (randomCardOne.getElement() == ElementType.FIRE && randomCardOne.getElement() == ElementType.NORMAL) {
                damageDealt = randomCardOne.getDmg() * 2 - randomCardTwo.getDmg();
            } else if (randomCardOne.getElement() == ElementType.NORMAL && randomCardOne.getElement() == ElementType.FIRE) {
                damageDealt = randomCardOne.getDmg() - randomCardTwo.getDmg() * 2;
            } else if (randomCardOne.getElement() == ElementType.NORMAL && randomCardOne.getElement() == ElementType.WATER) {
                damageDealt = randomCardOne.getDmg() * 2 - randomCardTwo.getDmg();
            } else if (randomCardOne.getElement() == ElementType.WATER && randomCardOne.getElement() == ElementType.NORMAL) {
                damageDealt = randomCardOne.getDmg() - randomCardTwo.getDmg() * 2;
            }
        }
        return damageDealt;
    }

    public static String generateOutput() {
        return "";
    }

}
