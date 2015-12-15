/**
 * This class defines the thread used for the dynamic statistics computation
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */
package it.unimib.disco.bimib.Task;
//GRNSim imports
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Statistics.DynamicPerturbationsStatistics;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;
import it.unimib.disco.bimib.Utility.UtilityRandom;
import it.unimib.disco.bimib.CABERNET.Simulation;
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
import it.unimib.disco.bimib.Exceptions.NotExistingNodeException;
import it.unimib.disco.bimib.Exceptions.ParamDefinitionException;

//import it.unimib.disco.bimib.GUI.AtmView;
import it.unimib.disco.bimib.GUI.DynamicPerturbationsStatsView;
import it.unimib.disco.bimib.Middleware.VizMapperManager;
import it.unimib.disco.bimib.Mutations.MutationManager;
import it.unimib.disco.bimib.Networks.GraphManager;

//System imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;












//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
//import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class DynamicStatisticsComputationTask extends AbstractTask{

	private Properties perturbationFeatures;
	private SimulationsContainer simulations;

	private String selectedNetwork;
	private int perturbExp;
	private String mutationType;
	private boolean specificKIKO;
	private boolean perpetuallyKIKO;
	
	private DynamicPerturbationsStatistics stats;
	
	//Specify if the permanent mutations are random or defined.

	public DynamicStatisticsComputationTask(SimulationsContainer simulations, 
			Properties perturbationFeatures,
			CySwingAppAdapter adapter, VizMapperManager vizMapperManager, 
			CyNetwork currentNetwork) throws ParamDefinitionException, NotExistingNodeException{
		
		
		
		this.simulations = simulations;
		this.selectedNetwork = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
		this.perturbationFeatures = perturbationFeatures;
		
		if(!perturbationFeatures.containsKey(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP))
			throw new ParamDefinitionException(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP + " param must be specified");
		this.perturbExp = Integer.valueOf(perturbationFeatures.getProperty(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP));
		
		if(!perturbationFeatures.containsKey(SimulationFeaturesConstants.MUTATION_TYPE))
			throw new ParamDefinitionException(SimulationFeaturesConstants.MUTATION_TYPE + " param must be specified");
		this.mutationType = perturbationFeatures.getProperty(SimulationFeaturesConstants.MUTATION_TYPE);
		
		if(perturbationFeatures.containsKey(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES) ||
				perturbationFeatures.containsKey(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES)){
			this.specificKIKO = true;
		}else{
			this.specificKIKO = false;
		}
		if(perturbationFeatures.containsKey(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION) ||
				perturbationFeatures.containsKey(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION)){
			this.perpetuallyKIKO = Integer.valueOf(perturbationFeatures.getProperty(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION)) == -1;
		}
	}




	@SuppressWarnings("unchecked")
	public void run(final TaskMonitor taskMonitor) throws Exception {

		GraphManager mutatedGraphManager = null;
		SamplingManager samplingManager, mutatedSamplingManager;
		MutationManager mutationManager;
		Simulation currentSimulation;
		String condition, newAttractor = null, oldAttractor;
		ArrayList<Integer> randomKI, randomKO;
		int kINumber = 0, kONumber = 0;
		HashMap<String, ArrayList<Integer>> avalanches = new HashMap<String, ArrayList<Integer>>();
		HashMap<String, int[]> sensitivity = new HashMap<String, int[]>();


		double progress = 0;
		int totSims = this.simulations.size();
		taskMonitor.setTitle("CABERNET - Dynamic perturbations analysis");
		taskMonitor.setProgress(0.0);
		
		for(String simId : this.simulations.getSimulationsId()){
			
			//Forced exit in case of thread interruption.
			if(Thread.interrupted())
				break;
			
			taskMonitor.setStatusMessage(simId + "network processing");
			
			currentSimulation = this.simulations.getSimulation(simId);
			mutatedGraphManager = currentSimulation.getGraphManager().copy();
			samplingManager = currentSimulation.getSamplingManager();
			mutatedSamplingManager = new SamplingManager(samplingManager.getAttractorFinder().copy());
			mutationManager = new MutationManager(mutatedGraphManager, samplingManager, perturbationFeatures);
			
			if(perturbationFeatures.containsKey(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES) &&
					perturbationFeatures.containsKey(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES)){
				ArrayList<String> specKI = (ArrayList<String>) perturbationFeatures.get(
						SimulationFeaturesConstants.SPECIFIC_KNOCK_IN_NODES);
				ArrayList<String> specKO = (ArrayList<String>) perturbationFeatures.get(
						SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES);
			
				for(String gene : specKI){
					mutatedGraphManager.perpetuallyChangeFunctionValue(gene, true);
				}
				for(String gene : specKO){
					mutatedGraphManager.perpetuallyChangeFunctionValue(gene, false);
				}
				//Removes all the stored attractors: new network dynamics
				mutatedSamplingManager.getAttractorFinder().clear();
				
			}else if(perturbationFeatures.containsKey(SimulationFeaturesConstants.KNOCKIN_NODES) &&
					perturbationFeatures.containsKey(SimulationFeaturesConstants.KNOCKOUT_NODES)){
				kINumber = Integer.valueOf(perturbationFeatures.getProperty(SimulationFeaturesConstants.KNOCKIN_NODES));
				if(kINumber < 0 || kINumber > mutatedGraphManager.getNodesNumber()){
					throw new ParamDefinitionException("The number of nodes to knock-in must be between 0 and nodes.");
				}
				kONumber = Integer.valueOf(perturbationFeatures.getProperty(SimulationFeaturesConstants.KNOCKOUT_NODES));
				if(kONumber < 0 || kONumber > mutatedGraphManager.getNodesNumber()){
					throw new ParamDefinitionException("The number of nodes to knock-out must be between 0 and nodes.");
				}
				if(kINumber + kONumber > mutatedGraphManager.getNodesNumber()){
					throw new ParamDefinitionException("The sum of the number of nodes to knock-in and knock-out must be between 0 and nodes.");
				}
			}
			//Statistics initialization	
			stats = new DynamicPerturbationsStatistics(mutatedGraphManager.getNodesNumber());
			
			for(int initialCondition = 0; initialCondition < this.perturbExp; initialCondition ++){
				
				//Creates the random state
				condition = UtilityRandom.createRandomBinarySequence(mutatedGraphManager.getNodesNumber(), 0.5);
			
				//Gets the original attractor
				oldAttractor = (String) samplingManager.getAttractorFinder().getAttractor(condition);
				
				if(this.mutationType.equals(SimulationFeaturesConstants.FLIP_MUTATIONS)){
					newAttractor = (String) mutatedSamplingManager.getAttractorFinder().getAttractor(
								mutationManager.getMutation().doMutation(condition));
				}else{
					if(this.perpetuallyKIKO){
						if(this.specificKIKO){
							newAttractor = (String) mutatedSamplingManager.getAttractorFinder().getAttractor(condition);
						}else{
							//Chooses the genes to knock in randomlly.
							randomKI = UtilityRandom.randomSubset(mutatedGraphManager.getNodesNumber(), kINumber);
							randomKO = UtilityRandom.randomSubset(mutatedGraphManager.getNodesNumber(), kONumber, randomKI);
							for(Integer gene : randomKI)
								mutatedGraphManager.perpetuallyChangeFunctionValue(gene, true);
							for(Integer gene : randomKO)
								mutatedGraphManager.perpetuallyChangeFunctionValue(gene, false);
							newAttractor = (String) mutatedSamplingManager.getAttractorFinder().getAttractor(condition);
							//Restore the previous graph state
							for(Integer gene : randomKI)
								mutatedGraphManager.restoreFunction(gene);
							for(Integer gene : randomKO)
								mutatedGraphManager.restoreFunction(gene);
						}
					}else{
						newAttractor = (String) mutatedSamplingManager.getAttractorFinder().getAttractor(
								mutationManager.getMutation().doMutation(condition));
					}
				}
				
				
				//Generates the statistics for the condition
				stats.avalanchesAndSensitivityComputation(
						samplingManager.getAttractorFinder().getStatesInAttractor(oldAttractor),
						mutatedSamplingManager.getAttractorFinder().getStatesInAttractor(newAttractor));
				
			
				//Forced exit in case of thread interruption.
				if(Thread.interrupted())
					break;
			}
			
			avalanches.put(simId, stats.getAvalanches());
			sensitivity.put(simId, stats.getSensitivity());
			
			progress = progress + 1;
			taskMonitor.setProgress(progress/totSims);
		}
		
		DynamicPerturbationsStatsView dynPerturbView = new DynamicPerturbationsStatsView(avalanches, sensitivity, 
				mutatedGraphManager.getGraph().getNodesNames(), this.selectedNetwork);
		dynPerturbView.setVisible(true);
	}
}
	
	
	/*
	 samplingManager = currentSimulation.getSamplingManager();

			if(this.permRandom){
				//Sets the permanent mutations (knock-in and knock-out)
				knockIn = UtilityRandom.randomSubset(mutatedGraphManager.getNodesNumber(), this.permRandKnockIn);
				knockOut = UtilityRandom.randomSubset(mutatedGraphManager.getNodesNumber(), this.permRandKnockOut, knockIn);
				for(Integer node: knockIn)
					mutatedGraphManager.perpetuallyChangeFunctionValue(node, true);
				for(Integer node: knockOut)
					mutatedGraphManager.perpetuallyChangeFunctionValue(node, false);
			}else{
				//Sets the knock-in nodes if specified
				if(permanentKnockIn != null){
					for(String geneName : permanentKnockIn)
						mutatedGraphManager.perpetuallyChangeFunctionValue(geneName, true);
				}

				//Sets the knock-out nodes if specified
				if(permanentKnockOut != null){
					for(String geneName : permanentKnockOut)
						mutatedGraphManager.perpetuallyChangeFunctionValue(geneName, false);
				}
			}
			
			//Defines the mutation manager
			MutationManager mutationManager = new MutationManager(mutatedGraphManager, samplingManager, this.perturbationFeatures);
			//Defines the mutation manager and computes the dynamic perturbation statistics
			AtmManager atmManager = new AtmManager(this.perturbationFeatures, samplingManager, mutationManager, mutatedGraphManager.getNodesNumber());	

			avalanches.put(simId, atmManager.getAtm().getDynamicPerturbationsStatistics().getAvalanches());
			sensitivity.put(simId, atmManager.getAtm().getDynamicPerturbationsStatistics().getSensitivity());

			progress = progress + 1;
			taskMonitor.setProgress(progress/totSims);
		}

		DynamicPerturbationsStatsView dynPerturbView = new DynamicPerturbationsStatsView(avalanches, sensitivity, 
				mutatedGraphManager.getGraph().getNodesNames(), this.selectedNetwork);
		dynPerturbView.setVisible(true);
		
		taskMonitor.setProgress(1.0);
	 
	 */
