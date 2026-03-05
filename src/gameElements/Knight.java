package gameElements;

public class Knight extends Fighter {

    public Knight() {
        super(10, 60);
    }

    @Override
    public String toString() {
        return "Knight L" + level;
    }
}