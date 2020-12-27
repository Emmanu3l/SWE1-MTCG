package main.java.mtcg.cards;

import main.java.mtcg.interfaces.Attackable;

public class Monster extends Card implements Attackable {
    private Race race;
    public Monster(String name, int dmg, Element element, Race race) {
        super(name, dmg, element);
        this.race = race;
    }
}
