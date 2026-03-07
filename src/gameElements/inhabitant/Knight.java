package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a heavily armored knight unit.
 */
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