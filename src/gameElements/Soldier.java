package gameElements;

public class Soldier extends Fighter {

    public Soldier() {
        super(6, 40);
    }

    @Override
    public String toString() {
        return "Soldier L" + level;
    }
}