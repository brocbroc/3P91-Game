package gameElements;

import utility.Cost;

public class Catapult extends Fighter {
    public Catapult() {
        super(14, 50);
    }

    @Override
    public Cost getTrainCost() {
        return new Cost(25, 15, 25);
    }

    @Override
    public String toString() {
        return "Catapult (L" + level + ")";
    }
}
