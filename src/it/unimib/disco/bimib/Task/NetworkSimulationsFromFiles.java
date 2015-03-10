/**
 * This class defines the thread for the network simulation process.
 * Each network is read from a GRNML file.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */

package it.unimib.disco.bimib.Task;
//GRNSim imports
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Statistics.DynamicalStatistics;
import it.unimib.disco.bimib.Tes.TesManager;
import it.unimib.disco.bimib.Tes.TesTree;
import it.unimib.disco.bimib.Utility.OutputConstants;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Exceptions.InputFormatException;
import it.unimib.disco.bimib.Exceptions.TesTreeException;
import it.unimib.disco.bimib.IO.Input;
import it.unimib.disco.bimib.IO.Output;
import it.unimib.disco.bimib.Middleware.NetworkManagment;
import it.unimib.disco.bimib.Mutations.MutationManager;
import it.unimib.disco.bimib.Networks.GraphManager;
//CABERNET imports
import it.unimib.disco.bimib.CABERNET.CABERNETConstants;
import it.unimib.disco.bimib.CABERNET.Simulation;
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;



//System imports
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;





//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class NetworkSimulationsFromFiles extends AbstractTask{

	private Properties simulationFeatures;
	private Properties requiredOutputs;
	private NetworkManagment cytoscapeBridge;
	private CySwingAppAdapter adapter;
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;
	private boolean atmComputation;
	private boolean treeMatching;
	private TesTree givenTree;
	private String matchingType;
	private int threshold;
	private ArrayList<String> filesPath;
	private boolean editing;

	public NetworkSimulationsFromFiles(Properties networkFeatures, Properties requiredOutputs, CySwingAppAdapter adapter, 
			CyApplicationManager appManager, SimulationsContainer simulationsContainer, boolean atmComputation,
			ArrayList<String> filesPath, boolean editing) throws NumberFormatException, 
			NullPointerException, FileNotFoundException, TesTreeException, InputFormatException{
		this(networkFeatures, requiredOutputs, adapter, simulationsContainer, 
				atmComputation, false, null, filesPath, editing);
	}

	public NetworkSimulationsFromFiles(Properties networkFeatures, Properties requiredOutputs, CySwingAppAdapter adapter, 
			SimulationsContainer simulationsContainer, boolean atmComputation, boolean treeMatching, 
			TesTree tree, ArrayList<String> filesPath, boolean editing) throws NumberFormatException, NullPointerException, 
			FileNotFoundException, TesTreeException, InputFormatException{
		this(networkFeatures, requiredOutputs, adapter, simulationsContainer, atmComputation, 
				treeMatching, tree, CABERNETConstants.PERFECT_MATCH, -1, filesPath, editing);
	}

	public NetworkSimulationsFromFiles(Properties networkFeatures, Properties requiredOutputs, CySwingAppAdapter adapter, 
			SimulationsContainer simulationsContainer, boolean atmComputation, 
			boolean treeMatching, TesTree tree, String matchingType, int threshold, ArrayList<String> filesPath, boolean editing) 
					throws NumberFormatException, NullPointerException, FileNotFoundException, 
					TesTreeException, InputFormatException{
		this.simulationFeatures = networkFeatures;
		this.requiredOutputs = requiredOutputs;
		this.adapter = adapter;
		this.appManager = this.adapter.getCyApplicationManager();
		this.cytoscapeBridge = new NetworkManagment(this.adapter, this.appManager);
		this.simulationsContainer = simulationsContainer;
		this.atmComputation = atmComputation;
		this.treeMatching = treeMatching;
		this.givenTree = tree;
		this.matchingType = matchingType;
		this.threshold = threshold;
		this.filesPath = filesPath;
		this.editing = editing;
	}


	public void run(final TaskMonitor taskMonitor) throws Exception {
		// Give the task a title.
		taskMonitor.setTitle("CABERNET");
		taskMonitor.setProgress(0.0);
		String networkId;
		int requiredNetworks = Integer.parseInt(this.simulationFeatures.getProperty(SimulationFeaturesConstants.MATCHING_NETWORKS));
		GraphManager graphManager = null;
		SamplingManager samplingManager = null;
		MutationManager mutationManager = null;
		AtmManager atmManager = null;
		TesManager tesManager = null;
		Simulation newSim;
		CyNetwork parent;
		int distance = -1;
		double[] deltas = null;
		boolean match;
		int net;
		int file_number = 0;
		String outputPath;
		GraphManager originalNetwork;
		for(String networkFile : this.filesPath){
			originalNetwork = Input.readGRNMLFile(networkFile);
			net = 0;
			file_number = file_number + 1;
			while(net < requiredNetworks){
				parent = null;
				taskMonitor.setStatusMessage("Network " + (net + 1) + ": Network creation");
				match = true;
				deltas = null;
				//Creates the network
				graphManager = originalNetwork.copy();

				//Edits the given network (if required)
				if(this.editing)
					graphManager.modify(simulationFeatures);

				networkId = "network_" + (net + 1) + "_file_" + file_number;

				//Samples the network in order to find the attractors
				taskMonitor.setStatusMessage("Network " + (net + 1) + ": Attractors sampling");
				samplingManager = new SamplingManager(simulationFeatures, graphManager);

				//Creates the ATM manager and the ATM matrix (if required)
				if(this.atmComputation){
					//Defines the mutation manager
					mutationManager = new MutationManager(graphManager, samplingManager, simulationFeatures);
					taskMonitor.setStatusMessage("Network " + (net + 1) + ": Atm creation");
					atmManager = new AtmManager(simulationFeatures, samplingManager, mutationManager, graphManager.getNodesNumber());	

					if(treeMatching){
						taskMonitor.setStatusMessage("Network " + (net + 1) + ": Matching");
						//Creates the TES manager in order to match the network with the tree
						tesManager = new TesManager(atmManager, samplingManager);
						try{
							if(this.matchingType.equals(CABERNETConstants.PERFECT_MATCH)){
								//Tries to match the network with the given differentiation tree
								distance = tesManager.findCorrectTesTree(this.givenTree);
								if(distance == 0){
									match = true;
									deltas = tesManager.getThresholds();
								}else{
									match = false;
								}
							}else if(this.matchingType.equals(CABERNETConstants.MIN_DISTANCE)){
								//Min distance comparison
								distance = tesManager.findMinDistanceTesTree(this.givenTree);
								if(distance == -1){
									match = false;
								}else if(distance <= threshold)
									deltas = tesManager.getThresholds();
							}else{
								//Computes the histogram distance
								distance = tesManager.findMinHistogramDistanceTesTree(this.givenTree);
								if(distance <= threshold){
									deltas = tesManager.getThresholds();
									match = true;
								}else{
									match = false;
								}
							}
						}catch(Exception ex){
							match = false;
						}
					}
				}
				//Stores the network only if it matches with the given tree (if the tree matching is required else it always match)
				if(match){
					taskMonitor.setStatusMessage("Network " + (net + 1) + ": Exporting");
					taskMonitor.setProgress((net + 1)/((double)requiredNetworks));
					//Adds the simulation in the container
					newSim = new Simulation(networkId);
					newSim.setGraphManager(graphManager);
					newSim.setAtmManager(atmManager);
					newSim.setSamplingManager(samplingManager);
					newSim.setDistance(distance);
					newSim.setThresholds(deltas);
					this.simulationsContainer.addSimulation(networkId, newSim);

					//Creates the network view on Cytoscape (if required)
					if(this.requiredOutputs.getProperty(CABERNETConstants.NETWORK_VIEW).equals(CABERNETConstants.YES))
						parent = this.cytoscapeBridge.createNetwork(graphManager, networkId);

					//Creates the attractors view on Cytoscape (if required)
					if(this.requiredOutputs.getProperty(CABERNETConstants.ATTRACTORS_NETWORK_VIEW).equals(CABERNETConstants.YES))
						if(parent == null)
							this.cytoscapeBridge.createAttractorGraph(samplingManager.getAttractorFinder(), networkId);
						else
							this.cytoscapeBridge.createAttractorGraph(samplingManager.getAttractorFinder(), networkId, parent);
					net = net + 1;

					//Exporting
					if(requiredOutputs.getProperty(OutputConstants.EXPORT_TO_FILE_SYSTEM, OutputConstants.NO).equals(OutputConstants.YES)){
						outputPath = requiredOutputs.getProperty(OutputConstants.OUTPUT_PATH, "");
						outputPath = outputPath + "/" + networkId;
						//Creates the network folder
						Output.createFolder(outputPath);

						//GRNML File
						if(requiredOutputs.getProperty(OutputConstants.GRNML_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createGRNMLFile(graphManager.getGraph(), outputPath + "/network.grnml");
						}

						//SIF File
						if(requiredOutputs.getProperty(OutputConstants.SIF_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createSIFFile(graphManager.getGraph(), outputPath + "/network.sif");
						}

						//ATM File
						if(requiredOutputs.getProperty(OutputConstants.ATM_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createATMFile(atmManager.getAtm(), outputPath + "/atm.csv");
						}

						//STATES IN EACH ATTRACTOR FILE
						if(requiredOutputs.getProperty(OutputConstants.STATES_IN_EACH_ATTRACTOR, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.saveStatesAttractorsFile(outputPath + "/states_in_each_attractor.csv", samplingManager.getAttractorFinder());
						}

						//ATTRACTORS FILE
						if(requiredOutputs.getProperty(OutputConstants.ATTRACTORS, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.saveAttractorsFile(samplingManager.getAttractorFinder(), outputPath + "/attractors.csv");
						}

						//SYNTHESIS FILE
						if(requiredOutputs.getProperty(OutputConstants.SYNTHESIS_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createSynthesisFile(newSim.getNetworkStatistics(), outputPath + "/synthesis.csv");
						}
					}

				}else{
					taskMonitor.setStatusMessage("Network " + (net + 1) + ": No match");
				}
			}
		}
		//COMMON STATISTICS

		DynamicalStatistics dymStats;

		//
		if(requiredOutputs.getProperty(OutputConstants.ATTRACTOR_LENGTHS, OutputConstants.NO).equals(OutputConstants.YES)){
			HashMap<String, ArrayList<Integer>> attractorLengths = new HashMap<String, ArrayList<Integer>>();
			for(String simId : this.simulationsContainer.getSimulationsId()){
				dymStats = new DynamicalStatistics(this.simulationsContainer.getSimulation(simId).getSamplingManager());
				attractorLengths.put(simId, dymStats.getAttractorsLength(true));
			}
			Output.saveAttractorsLengths(requiredOutputs.getProperty(OutputConstants.OUTPUT_PATH, "") + "/attractor_lengths.csv", 
					attractorLengths);
		}
		//Basins of attraction
		if(requiredOutputs.getProperty(OutputConstants.BASINS_OF_ATTRACTION, OutputConstants.NO).equals(OutputConstants.YES)){
			HashMap<String, ArrayList<Integer>> basinsOfAttraction = new HashMap<String, ArrayList<Integer>>();
			for(String simId : this.simulationsContainer.getSimulationsId()){
				dymStats = new DynamicalStatistics(this.simulationsContainer.getSimulation(simId).getSamplingManager());
				basinsOfAttraction.put(simId, dymStats.getBasinOfAttraction(true));
			}
			Output.saveAttractorsLengths(requiredOutputs.getProperty(OutputConstants.OUTPUT_PATH, "") + "/basins_of_attraction.csv", 
					basinsOfAttraction);
		}
	}
}
