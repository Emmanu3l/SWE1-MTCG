package main.java.cards;

import main.java.interfaces.Attackable;

public class Monster extends Card implements Attackable {
    public Monster(String name, int dmg, String elementType) {
        super(name, dmg, elementType);
    }
}