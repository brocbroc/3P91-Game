package gameElements;

public class Catapult extends Fighter {

    public Catapult() {
        super(14, 50);
    }

    @Override
    public String toString() {
        return "Catapult L" + level;
    }
}
