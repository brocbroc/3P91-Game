package gameElements;

import utility.Cost;

public class AttackOutcome {
    private boolean success;
    private Cost loot;

    public AttackOutcome(boolean success, Cost loot) {
        this.success = success;
        this.loot = loot;
    }

    public boolean isSuccess() {
        return success;
    }

    public Cost getLoot() {
        return loot;
    }
}
