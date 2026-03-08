package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Worker;

/**
 * This class creates a worker.
 */
public class WorkerConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a worker
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Worker.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Worker.getProductionTime();
	}

	/**
	 * Creates a new worker
	 * @return the new worker
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Worker();
	}
}
