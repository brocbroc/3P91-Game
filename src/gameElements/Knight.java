package gameElements;

import utility.Cost;

public class Knight extends Fighter {
    public Knight() {
        super(10, 60);
    }

    @Override
    public Cost getTrainCost() {
        return new Cost(20, 20, 10);
    }

    @Override
    public String toString() {
        return "Knight (L" + level + ")";
    }
}