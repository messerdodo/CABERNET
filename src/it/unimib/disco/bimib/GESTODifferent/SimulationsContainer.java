package it.unimib.disco.bimib.GESTODifferent;

import java.util.HashMap;

/**
 * This class is container for the computed simulations.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

public class SimulationsContainer {
	
	private HashMap<String, Simulation> simulations;
	
	/**
	 * Default constructor
	 */
	public SimulationsContainer(){
		this.simulations = new HashMap<String, Simulation>();
	}
	
	/**
	 * This method stores the simId simulation 
	 * @param simId: the simulation identifier
	 * @param simulation: the simulation to be stored
	 */
	public void addSimulation(String simId, Simulation simulation){
		this.simulations.put(simId, simulation);
	}
	
	/**
	 * This method returns the simId simulation. 
	 * If simId does not exist the method returns null.
	 * @param simId: the simulation identifier
	 * @return the simId simulation. 
	 */
	public Simulation getSimulation(String simId){
		if(this.simulations.containsKey(simId))
			return this.simulations.get(simId);
		else
			return null;
	}

}
