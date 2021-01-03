package main.java.mtcg.cards;

import java.util.Objects;

public abstract class Card {
    private String id;
    private String name;
    private double damage;
    //private Element element;

    public Card() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Double.compare(card.damage, damage) == 0 && id.equals(card.id) && name.equals(card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, damage);
    }

    public Card(String id, String name, double damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    /*public Card(String id, String name, double damage, Element element) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
    }*/

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
        for (Element e: Element.values()) {
            if (this.name.contains(e.toString())) {
                return e;
            }
        }
        return null;
    }

    public Race getRace() {
        for (Race r: Race.values()) {
            if (this.name.contains(r.toString())) {
                return r;
            }
        }
        return null;
    }

    /*public void setElement(Element element) {
        this.element = element;
    }*/

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", damage=" + damage +
                '}';
    }
}
