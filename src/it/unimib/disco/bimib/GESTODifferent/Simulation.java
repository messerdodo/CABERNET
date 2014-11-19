package it.unimib.disco.bimib.GESTODifferent;

/**
 * This class is a single simulation container.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

import java.util.Properties;

import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Exceptions.InputTypeException;
import it.unimib.disco.bimib.Exceptions.NotExistingNodeException;
import it.unimib.disco.bimib.Exceptions.ParamDefinitionException;
import it.unimib.disco.bimib.Networks.GraphManager;
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Statistics.NetworkStructureStatistics;
import it.unimib.disco.bimib.Utility.OutputConstants;

public class Simulation {

	private String networkId;
	private GraphManager graphManager;
	private AtmManager atmManager;
	private SamplingManager samplingManager;

	/**
	 * Default constructor
	 */
	public Simulation(String networkId){
		this.networkId = networkId;
		this.graphManager = null;
		this.atmManager = null;
		this.samplingManager = null;
	}

	/**
	 * This method sets the GraphManager object
	 * @param graphManager: a GraphManager object
	 */
	public void setGraphManager(GraphManager graphManager){
		this.graphManager = graphManager;
	}

	/**
	 * This method returns the GraphManager object
	 * @return the graph manager
	 */
	public GraphManager getGraphManager(){
		return this.graphManager;
	}

	/**
	 * This method allows to set the ATMManager object
	 * @param atmManager: an AtmManager object
	 */
	public void setAtmManager(AtmManager atmManager){
		this.atmManager = atmManager;
	}

	/**
	 * This method returns the AtmManager object
	 * @return the atm manager.
	 */
	public AtmManager getAtmManager(){
		return this.atmManager;
	}

	/**
	 * This method allows to set a SamplingManager object
	 * @param samplingManager: a Sampling Manager object
	 */
	public void setSamplingManager(SamplingManager samplingManager){
		this.samplingManager = samplingManager;
	}

	/**
	 * This method returns the sampling manager.
	 * @return the sampling manager.
	 */
	public SamplingManager getSamplingManager(){
		return this.samplingManager;
	}

	/**
	 * This method returns the networkId of the simulation
	 */
	public String getNetworkId(){
		return this.networkId;
	}

	/**
	 * This method returns a property object with all the network structure statistics.
	 * @return  a property object with all the network structure statistics.
	 * @throws NotExistingNodeException
	 * @throws ParamDefinitionException
	 * @throws InputTypeException
	 */
	public Properties getNetworkStatistics() throws NotExistingNodeException, ParamDefinitionException, InputTypeException{
		//Saves the statistics
		Properties statistics = new Properties();
		statistics.put(OutputConstants.SIMULATION_ID, this.networkId);
		statistics.put(OutputConstants.CLUSTERING_COEFFICIENT, NetworkStructureStatistics.getClusteringCoefficient(graphManager));
		statistics.put(OutputConstants.AVERAGE_BIAS, NetworkStructureStatistics.getAverageBiasValue(graphManager));
		statistics.put(OutputConstants.AVERAGE_PATH_LENGTH, NetworkStructureStatistics.getAveragePath(graphManager));
		statistics.put(OutputConstants.NETWORK_DIAMETER, NetworkStructureStatistics.getNetworkDiameter(graphManager));
		statistics.put(OutputConstants.ATTRACTORS_NUMBER, samplingManager.getAttractorFinder().getAttractorsNumber());
		//Gets the average attractor length 
		double avgLength = 0.0;
		for(Object attractor : samplingManager.getAttractorFinder().getAttractors())
			avgLength = avgLength + samplingManager.getAttractorFinder().getAttractorLength(attractor);
		statistics.put(OutputConstants.ATTRACTORS_LENGTH, avgLength/samplingManager.getAttractorFinder().getAttractorsNumber());
		statistics.put(OutputConstants.TREE_DISTANCE, 0);
		statistics.put(OutputConstants.NOT_FOUND_ATTRACTORS, 0);
		return statistics;
	}

}
