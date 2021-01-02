package main.java.mtcg.cards;

public abstract class Card {
    private String id;
    private String name;
    private double damage;
    private Element element;

    public Card(String id, String name, double damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public Card(String id, String name, double damage, Element element) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
