package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Worker;

/**
 * This class creates workers.
 */
public class WorkerConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a worker
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return Worker.getProductionCost(); }

	/**
	 * Creates a new worker
	 * @return the new worker
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Worker();
	}
}
