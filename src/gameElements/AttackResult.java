package gameElements;

import utility.Cost;
import java.util.Random;

public class AttackResult {
    private Random rand = new Random();

    public AttackOutcome judgeAttack(int attackScore, int defenceScore) {

        double luck = 0.9 + (rand.nextDouble() * 0.2);
        int finalAttack = (int)(attackScore * luck);

        boolean success = finalAttack >= defenceScore;

        int loot = success ? (finalAttack - defenceScore) * 2 : 0;

        return new AttackOutcome(success, new Cost(loot, loot/2, loot/2));
    }
}
