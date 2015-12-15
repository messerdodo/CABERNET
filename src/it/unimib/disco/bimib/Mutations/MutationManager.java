/**
 * This class is the manager for the mutations package.
 * 
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @author Giorgia Previtali (g.previtali6@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2013
 * 
 */

package it.unimib.disco.bimib.Mutations;

import it.unimib.disco.bimib.Exceptions.FeaturesException;
import it.unimib.disco.bimib.Networks.GraphManager;
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;

import java.util.ArrayList;
import java.util.Properties;

public class MutationManager {

	private Mutation mutation;
	private String mutationType;

	/**
	 * Main constructor
	 * @param graphManager
	 * @param samplingManager
	 * @param simulationFeatures
	 * @throws FeaturesException
	 */
	@SuppressWarnings("unchecked")
	public MutationManager(GraphManager graphManager, SamplingManager samplingManager, Properties simulationFeatures) throws FeaturesException {
		//Parameters checking
		if(samplingManager == null)
			throw new NullPointerException("Sampling manager must be not null");
		if(graphManager == null)
			throw new NullPointerException("Graph manager must be not null");

		//Checks the features keys
		if(!simulationFeatures.containsKey(SimulationFeaturesConstants.MUTATION_TYPE))
			throw new FeaturesException(SimulationFeaturesConstants.MUTATION_TYPE + " key must be specified.");

		this.mutationType = simulationFeatures.getProperty(SimulationFeaturesConstants.MUTATION_TYPE);

		if(this.mutationType.equals(SimulationFeaturesConstants.FLIP_MUTATIONS) ||
				this.mutationType.equals(SimulationFeaturesConstants.TEMPORARY_MUTATIONS)){

			if(!simulationFeatures.containsKey(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB))
				throw new FeaturesException(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB + " key must be specified.");
			
			if(!simulationFeatures.containsKey(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB))
				throw new FeaturesException(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB + " key must be specified.");

			//Gets the parameters
			int minTimes = Integer.parseInt(simulationFeatures.get(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB).toString());
			int maxTimes = Integer.parseInt(simulationFeatures.get(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB).toString());
			
			if(minTimes <= 0)
				throw new FeaturesException(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB + " value must be greater than 0.");
			if(maxTimes <= 0)
				throw new FeaturesException(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB + " value must be greater than 0.");
			if(minTimes > maxTimes)
				throw new FeaturesException(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB + " value must be greater or equal than " +
						SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB + " value.");

			if(!simulationFeatures.containsKey(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB))
				throw new FeaturesException(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB + " key must be specified.");

			int nodesToPerturb = Integer.parseInt(simulationFeatures.get(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB).toString());
			
			if(nodesToPerturb < 0 || nodesToPerturb > graphManager.getNodesNumber())
				throw new FeaturesException(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB + " value must be between 1 and nodes.");
			//Parses the list of the specific nodes to flip and converts it from a string of names to an array of ids.
			ArrayList<Integer> specificFlips;
			if(simulationFeatures.containsKey(SimulationFeaturesConstants.SPECIFIC_PERTURB_NODES)){
				specificFlips = new ArrayList<Integer>();
				for(String nodeName : (ArrayList<String>)simulationFeatures.get(SimulationFeaturesConstants.SPECIFIC_PERTURB_NODES)){
					int nodeId = graphManager.getNodeNumber(nodeName);
					if(nodeId != -1)
						specificFlips.add(nodeId);
				}
				//Binary mutation with specific perturbations
				this.mutation = new BinaryMutation(graphManager, this.mutationType, 0, minTimes, maxTimes, specificFlips);
			}else{
				//Binary mutation without specific perturbations
				this.mutation = new BinaryMutation(graphManager, this.mutationType, nodesToPerturb, minTimes, maxTimes);
			}

		}else if(this.mutationType.equals(SimulationFeaturesConstants.KNOCKIN_KNOCKOUT_MUTATIONS)){
			ArrayList<Integer> specificKnockIn, specificKnockOut;
			int randomKI, randomKO, minKiTimes, maxKiTimes, minKoTimes, maxKoTimes;
			
			minKiTimes = Integer.valueOf(simulationFeatures.getProperty(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION));
			minKoTimes = Integer.valueOf(simulationFeatures.getProperty(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION));
			maxKiTimes = Integer.valueOf(simulationFeatures.getProperty(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION));
			maxKoTimes = Integer.valueOf(simulationFeatures.getProperty(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION));
			
			if(simulationFeatures.contains(SimulationFeaturesConstants.SPECIFIC_KNOCK_IN_NODES) ||
					simulationFeatures.contains(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES)){
				specificKnockIn = (ArrayList<Integer>)simulationFeatures.get(SimulationFeaturesConstants.SPECIFIC_KNOCK_IN_NODES);
				specificKnockOut = (ArrayList<Integer>)simulationFeatures.get(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES);
				this.mutation = new BinaryMutation(graphManager, this.mutationType, 0, 0, minKiTimes, 
						maxKiTimes, minKoTimes, maxKoTimes, specificKnockIn, specificKnockOut);
			}else{
				randomKI = Integer.valueOf(simulationFeatures.getProperty(SimulationFeaturesConstants.KNOCKIN_NODES));
				randomKO = Integer.valueOf(simulationFeatures.getProperty(SimulationFeaturesConstants.KNOCKOUT_NODES));
				this.mutation = new BinaryMutation(graphManager, this.mutationType, randomKI, 
						randomKO, minKiTimes, maxKiTimes, minKoTimes, maxKoTimes);
			}
		}

	}

	/**
	 * Returns the mutation object
	 * @return the mutation object
	 */
	public Mutation getMutation(){
		return this.mutation;
	}


}
