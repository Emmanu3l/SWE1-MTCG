package main.mtcg.cards;

import main.mtcg.interfaces.Attackable;

public class Monster extends Card implements Attackable {
    //private Race race;

    public Monster() {
    }

    public Monster(String id, String name, double dmg) {
        super(id, name, dmg);
    }

    /*public Monster(String id, String name, int dmg, Element element, Race race) {
        super(id, name, dmg, element);
        this.race = race;
    }*/

    /*public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }*/
}
