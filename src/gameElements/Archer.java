package gameElements;

public class Archer extends Fighter {

    public Archer() {
        super(7, 30);
    }

    @Override
    public String toString() {
        return "Archer L" + level;
    }
}