package gameElements;

import utility.Cost;

public class Archer extends Fighter {
    public Archer() {
        super(7, 30);
    }

    @Override
    public Cost getTrainCost() {
        return new Cost(12, 8, 8);
    }

    @Override
    public String toString() {
        return "Archer (L" + level + ")";
    }
}