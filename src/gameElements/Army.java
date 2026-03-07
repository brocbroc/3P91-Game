package gameElements;

import gameElements.inhabitant.*;
import java.util.ArrayList;
import java.util.List;

public class Army {
	private List<Soldier> soldiers;
	private List<Archer> archers;
	private List<Knight> knights;
	private List<Catapult> catapults;
	private int count;

	public Army() {
		soldiers = new ArrayList<>();
		archers = new ArrayList<>();
		knights = new ArrayList<>();
		catapults = new ArrayList<>();
		count = 0;
	}

	public int getCount() { return count; }

	public void addSoldier(Soldier s) {
		soldiers.add(s);
		count++;
	}

	public void addArcher(Archer a) {
		archers.add(a);
		count++;
	}

	public void addKnight(Knight k) {
		knights.add(k);
		count++;
	}

	public void addCatapult(Catapult c) {
		catapults.add(c);
		count++;
	}
}
