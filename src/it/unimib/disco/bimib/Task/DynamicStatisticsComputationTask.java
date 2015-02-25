/**
 * This class defines the thread used for the dynamic statistics computation
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */
package it.unimib.disco.bimib.Task;
//GRNSim imports
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Utility.UtilityRandom;
import it.unimib.disco.bimib.Atms.AtmManager;
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

	//private NetworkManagment cytoscapeBridge;
//	private CySwingAppAdapter adapter;
//	private CyApplicationManager appManager;
//	private CyNetwork currentNetwork;
//	private VizMapperManager vizMapperManager;
	private String selectedNetwork;
	private int permRandKnockIn;
	private int permRandKnockOut;
	private ArrayList<String> permanentKnockOut;
	private ArrayList<String> permanentKnockIn;
	private boolean permRandom; //Specify if the permanent mutations are random or defined.

	public DynamicStatisticsComputationTask(SimulationsContainer simulations, 
			int permanentKnockIn, int permanentKnockOut,
			Properties perturbationFeatures,
			CySwingAppAdapter adapter, VizMapperManager vizMapperManager, 
			CyNetwork currentNetwork) throws ParamDefinitionException, NotExistingNodeException{
		this.simulations = simulations;
		this.selectedNetwork = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
		this.perturbationFeatures = perturbationFeatures;
//		this.adapter = adapter;
//		this.appManager = this.adapter.getCyApplicationManager();
//		this.vizMapperManager = vizMapperManager;
		this.permRandKnockIn = permanentKnockIn;
		this.permRandKnockOut = permanentKnockOut;
		this.permRandom = true;
	}

	public DynamicStatisticsComputationTask(SimulationsContainer simulations, 
			ArrayList<String> permanentKnockIn, ArrayList<String> permanentKnockOut,
			Properties perturbationFeatures, CySwingAppAdapter adapter, 
			VizMapperManager vizMapperManager, CyNetwork currentNetwork) 
					throws ParamDefinitionException, NotExistingNodeException{

		this.simulations = simulations;
		this.perturbationFeatures = perturbationFeatures;
//		this.adapter = adapter;
//		this.appManager = this.adapter.getCyApplicationManager();
//		this.currentNetwork = currentNetwork;
//		this.vizMapperManager = vizMapperManager;
		this.permanentKnockIn = permanentKnockIn;
		this.permanentKnockOut = permanentKnockOut;
		this.permRandom = false;
		this.selectedNetwork = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
	}


	public void run(final TaskMonitor taskMonitor) throws Exception {

		GraphManager mutatedGraphManager = null;
		SamplingManager samplingManager;
		ArrayList<Integer> knockIn, knockOut;
		Simulation currentSimulation;
		HashMap<String, ArrayList<Integer>> avalanches = new HashMap<String, ArrayList<Integer>>();
		HashMap<String, int[]> sensitivity = new HashMap<String, int[]>();

		double progress = 0;
		int totSims = this.simulations.size();
		taskMonitor.setTitle("CABERNET - Dynamic perturbations analysis");
		taskMonitor.setProgress(0.0);
		for(String simId : this.simulations.getSimulationsId()){
			currentSimulation = this.simulations.getSimulation(simId);
			mutatedGraphManager = currentSimulation.getGraphManager().copy();
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

		//Shows the results
		//		AtmView atmView = new AtmView(atmManager, this.samplingManager, this.adapter, 
		//				this.appManager, this.networkId, vizMapperManager, currentNetwork);
		//		atmView.setVisible(true);

		


	}
}
