package gameElements;

import utility.Cost;

public class Soldier extends Fighter {
    public Soldier() {
        super(6, 40);
    }

    @Override
    public Cost getTrainCost() {
        return new Cost(10, 10, 5);
    }

    @Override
    public String toString() {
        return "Soldier (L" + level + ")";
    }
}