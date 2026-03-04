package game;

import utility.*;

public class Inventory {
	private int gold;
	private int iron;
	private int lumber;

	public Inventory(int gold, int iron, int lumber) {
		this.gold = gold;
		this.iron = iron;
		this.lumber = lumber;
	}

	public int getGold() { return gold; }

	public int getIron() { return iron; }

	public int getLumber() { return lumber; }

	public void addGold(int gold) { this.gold += gold; }

	public void addIron(int iron) { this.iron += iron; }

	public void addLumber(int lumber) { this.lumber += lumber; }

	public boolean checkCost(Cost cost) {
		return cost.GOLD <= gold && cost.IRON <= iron && cost.LUMBER <= lumber;
	}

	public void payCost(Cost cost) {
		this.gold -= cost.GOLD;
		this.iron -= cost.IRON;
		this.lumber -= cost.LUMBER;
	}
}
