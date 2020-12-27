package main.java.mtcg.cards;

public abstract class Card {
    private String name;
    private int dmg;
    private ElementType element;

    public Card(String name, int dmg, ElementType element) {
        this.name = name;
        this.dmg = dmg;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public ElementType getElement() {
        return element;
    }

    public void setElement(ElementType element) {
        this.element = element;
    }
}
