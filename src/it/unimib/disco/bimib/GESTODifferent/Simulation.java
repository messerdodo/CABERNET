package it.unimib.disco.bimib.GESTODifferent;

/**
 * This class is a single simulation container.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Networks.GraphManager;
import it.unimib.disco.bimib.Sampling.SamplingManager;

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
	
}
