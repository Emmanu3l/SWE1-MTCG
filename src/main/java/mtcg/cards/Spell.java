package main.java.mtcg.cards;

public class Spell extends Card {

    public Spell(String id, String name, double dmg) {
        super(id, name, dmg);
    }

    public Spell(String id, String name, double dmg, Element element) {
        super(id, name, dmg, element);
    }
}
