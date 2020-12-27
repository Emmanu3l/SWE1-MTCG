package main.java.mtcg.cards;

public abstract class Card {
    private String name;
    private int dmg;
    private Element element;

    public Card(String name, int dmg, Element element) {
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

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
