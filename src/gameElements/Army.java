package gameElements;

import gameElements.inhabitant.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an army of fighters.
 */
public class Army {
	private List<Soldier> soldiers;
	private List<Archer> archers;
	private List<Knight> knights;
	private List<Catapult> catapults;
	private int count;

	/**
	 * Class constructor.
	 */
	public Army() {
		soldiers = new ArrayList<>();
		archers = new ArrayList<>();
		knights = new ArrayList<>();
		catapults = new ArrayList<>();
		count = 0;
	}

	/**
	 * Returns the number of fighters.
	 * @return the number of fighters
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the number of soldiers.
	 * @return the number of soldiers
	 */
	public int getSoldierCount() {
		return soldiers.size();
	}

	/**
	 * Returns the number of archers.
	 * @return the number of archers
	 */
	public int getArcherCount() {
		return archers.size();
	}

	/**
	 * Returns the number of knights.
	 * @return the number of knights
	 */
	public int getKnightCount() {
		return knights.size();
	}

	/**
	 * Returns the number of catapults.
	 * @return the number of catapults
	 */
	public int getCatapultCount() {
		return catapults.size();
	}

	/**
	 * Adds a soldier to the army
	 * @param s the new soldier
	 */
	public void addSoldier(Soldier s) {
		soldiers.add(s);
		count++;
	}

	/**
	 * Adds an archer to the army
	 * @param a the new archer
	 */
	public void addArcher(Archer a) {
		archers.add(a);
		count++;
	}

	/**
	 * Adds a knight to the army
	 * @param k the new knight
	 */
	public void addKnight(Knight k) {
		knights.add(k);
		count++;
	}

	/**
	 * Adds a new catapult to the army
	 * @param c the new catapult
	 */
	public void addCatapult(Catapult c) {
		catapults.add(c);
		count++;
	}
}
