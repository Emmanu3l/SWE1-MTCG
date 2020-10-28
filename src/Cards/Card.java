package Cards;

public abstract class Card {
    private String name;
    private int dmg;
    private String elementType;

    public Card(String name, int dmg, String elementType) {
        this.name = name;
        this.dmg = dmg;
        this.elementType = elementType;
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

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
}
