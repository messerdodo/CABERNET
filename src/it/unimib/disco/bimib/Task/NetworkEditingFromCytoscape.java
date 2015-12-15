/**
 * This class defines the thread for the network editing process.
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
import it.unimib.disco.bimib.Exceptions.FeaturesException;
import it.unimib.disco.bimib.Exceptions.InputFormatException;
import it.unimib.disco.bimib.Exceptions.TesTreeException;
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
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class NetworkEditingFromCytoscape extends AbstractTask{

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
	private boolean representativeTreeComputation;
	private String representativeTreeDepthMode;
	private double representativeTreeDepthValue;
	private int representativeTreeCutoff;
	private int comparison_cutoff;
	private String comparison_type;
	private int trees_research_cutoff;
	private String trees_research_type;
	private int maxNetToTest;

	private GraphManager graphManager;
	private SamplingManager samplingManager;
	private MutationManager mutationManager;
	private AtmManager atmManager;
	private TesManager tesManager;

	public NetworkEditingFromCytoscape(Properties networkFeatures, Properties requiredOutputs, CySwingAppAdapter adapter, 
			CyApplicationManager appManager, SimulationsContainer simulationsContainer, boolean atmComputation,
			boolean representative_tree, String representativeTreeDepthMode, double representativeTreeDepthValue) 
					throws NumberFormatException, 
					NullPointerException, FileNotFoundException, TesTreeException, InputFormatException, FeaturesException{
		this(networkFeatures, requiredOutputs, adapter, simulationsContainer, 
				atmComputation, false, null, representative_tree, representativeTreeDepthMode, 
				representativeTreeDepthValue);
	}

	public NetworkEditingFromCytoscape(Properties networkFeatures, Properties requiredOutputs, CySwingAppAdapter adapter, 
			SimulationsContainer simulationsContainer, boolean atmComputation, boolean treeMatching, 
			TesTree tree, boolean representative_tree, String representativeTreeDepthMode, 
			double representativeTreeDepthValue)
					throws NumberFormatException, NullPointerException, 
					FileNotFoundException, TesTreeException, InputFormatException, FeaturesException{
		this(networkFeatures, requiredOutputs, adapter, simulationsContainer, atmComputation, 
				treeMatching, tree, CABERNETConstants.PERFECT_MATCH, -1, representative_tree, representativeTreeDepthMode, 
				representativeTreeDepthValue);
	}

	public NetworkEditingFromCytoscape(Properties networkFeatures, Properties requiredOutputs, CySwingAppAdapter adapter, 
			SimulationsContainer simulationsContainer, boolean atmComputation, 
			boolean treeMatching, TesTree tree, String matchingType, int threshold,
			boolean representative_tree, String representativeTreeDepthMode, double representativeTreeDepthValue) 
					throws NumberFormatException, NullPointerException, FileNotFoundException, 
					TesTreeException, InputFormatException, FeaturesException{
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

		this.graphManager = null;
		this.samplingManager = null;
		this.mutationManager = null;
		this.atmManager = null;
		this.tesManager = null; 

		this.representativeTreeComputation = representative_tree;
		this.representativeTreeDepthMode = representativeTreeDepthMode;
		this.representativeTreeDepthValue = representativeTreeDepthValue;
		if(this.representativeTreeComputation){
			if(!networkFeatures.containsKey(CABERNETConstants.REPRESENTATIVE_TREE_CUTOFF)){
				throw new FeaturesException("The " + CABERNETConstants.REPRESENTATIVE_TREE_CUTOFF + " value must be defined");
			}
			this.representativeTreeCutoff = Integer.valueOf(networkFeatures.getProperty(CABERNETConstants.REPRESENTATIVE_TREE_CUTOFF));
			if(this.representativeTreeCutoff < -1){
				throw new NumberFormatException("The " + CABERNETConstants.REPRESENTATIVE_TREE_CUTOFF + " value must be greater or equal than 0 or -1.");
			}
		}

		if(this.treeMatching){
			if(!networkFeatures.containsKey(SimulationFeaturesConstants.TREES_RESEARCH_CUTOFF))
				throw new FeaturesException(SimulationFeaturesConstants.TREES_RESEARCH_CUTOFF + " value must be defined.");
			this.trees_research_cutoff = Integer.valueOf(networkFeatures.getProperty(SimulationFeaturesConstants.TREES_RESEARCH_CUTOFF));
			if(this.trees_research_cutoff <= 0)
				throw new NumberFormatException("The " + SimulationFeaturesConstants.TREES_RESEARCH_CUTOFF + " value must be greater than 0");

			if(!networkFeatures.containsKey(SimulationFeaturesConstants.TREES_RESEARCH_TYPE))
				throw new FeaturesException(SimulationFeaturesConstants.TREES_RESEARCH_TYPE + " value must be defined.");
			this.trees_research_type = networkFeatures.getProperty(SimulationFeaturesConstants.TREES_RESEARCH_TYPE);
			if(!trees_research_type.equals(SimulationFeaturesConstants.COMPLETED_TREES_RESEARCH) &&
					!trees_research_type.equals(SimulationFeaturesConstants.SAMPLED_TREES_RESEARCH)){
				throw new FeaturesException("The " + SimulationFeaturesConstants.TREES_RESEARCH_TYPE + " value must be " + 
						SimulationFeaturesConstants.COMPLETED_TREES_RESEARCH + " or " +
						SimulationFeaturesConstants.SAMPLED_TREES_RESEARCH);
			}
			if(!networkFeatures.containsKey(CABERNETConstants.MAX_NET_TO_TEST))
				throw new FeaturesException(CABERNETConstants.MAX_NET_TO_TEST + " value must be defined.");
			this.maxNetToTest = Math.max(1, Integer.valueOf(networkFeatures.getProperty(CABERNETConstants.MAX_NET_TO_TEST)));
		}else{
			this.maxNetToTest = -1;
		}

		if(this.representativeTreeComputation || this.treeMatching){
			if(!networkFeatures.containsKey(SimulationFeaturesConstants.TREES_COMPARISON)){
				throw new FeaturesException("The " + SimulationFeaturesConstants.TREES_COMPARISON + " value must be defined");
			}
			this.comparison_type = networkFeatures.getProperty(SimulationFeaturesConstants.TREES_COMPARISON);
			if(!this.comparison_type.equals(SimulationFeaturesConstants.COMPLETE_COMPARISON) &&
					!this.comparison_type.equals(SimulationFeaturesConstants.SAMPLED_COMPARISON)){
				throw new FeaturesException("The " + SimulationFeaturesConstants.TREES_COMPARISON + " value must be " +
						SimulationFeaturesConstants.COMPLETE_COMPARISON + " or " + SimulationFeaturesConstants.SAMPLED_COMPARISON);
			}

			this.comparison_cutoff = Integer.valueOf(networkFeatures.getProperty(SimulationFeaturesConstants.TREE_COMPARISON_CUTOFF));
			if(comparison_cutoff <= 0){
				throw new NumberFormatException("The " + SimulationFeaturesConstants.TREE_COMPARISON_CUTOFF + " value must be greater than 0");
			}
		}
	}


	public void run(final TaskMonitor taskMonitor) throws Exception {
		//Gives the task a title.
		taskMonitor.setTitle("CABERNET");
		taskMonitor.setProgress(0.0);
		String networkId;
		int requiredNetworks = Integer.parseInt(this.simulationFeatures.getProperty(SimulationFeaturesConstants.MATCHING_NETWORKS));
		Simulation newSim;
		int distance = -1;
		double[] deltas = null;
		boolean match;
		int net = 0;
		String outputPath;
		CyNetwork parent;
		GraphManager cytoscapeNetwork = cytoscapeBridge.getNetworkFromCytoscape();
		int depth = 0;
		ArrayList<TesTree> representativeTrees;
		int testedNetworks = 0;

		try{
			while(net < requiredNetworks && (this.maxNetToTest == -1 || testedNetworks < this.maxNetToTest)){

				//Variables initialization
				match = true;
				deltas = null;
				parent = null;
				representativeTrees = null;
				testedNetworks = testedNetworks + 1;
				//*************************************************************
				//Task monitor message setting
				taskMonitor.setStatusMessage("Network " + (net + 1) + ": Network creation");
				//Network creation
				graphManager = cytoscapeNetwork.copy();
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();
				//Network editing
				graphManager.modify(this.simulationFeatures);
				networkId = "network_" + (net + 1);
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();

				//*************************************************************
				//Samples the network in order to find the attractors
				//Task monitor message setting
				taskMonitor.setStatusMessage("Network " + (net + 1) + ": Attractors sampling");
				//Network sampling 
				samplingManager = new SamplingManager(simulationFeatures, graphManager);
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();

				//*************************************************************
				//Creates the ATM manager and the ATM matrix (if required)
				if(this.atmComputation){
					//Task monitor message setting
					taskMonitor.setStatusMessage("Network " + (net + 1) + ": Atm creation");
					//Defines the mutation manager
					mutationManager = new MutationManager(graphManager, samplingManager, simulationFeatures);
					atmManager = new AtmManager(simulationFeatures, samplingManager, mutationManager, graphManager.getNodesNumber());
					//Forces the process conclusion in case of thread interruption
					if(Thread.interrupted())
						throw new InterruptedException();

					//*************************************************************
					//Tree matching task (if required)
					if(treeMatching){
						//Task monitor message setting
						taskMonitor.setStatusMessage("Network " + (net + 1) + ": Matching");
						//Creates the TES manager in order to match the network with the tree
						tesManager = new TesManager(atmManager, samplingManager, 
								comparison_type, comparison_cutoff);

						try{
							//Trees comparison: three comparison types are allowed
							if(this.matchingType.equals(CABERNETConstants.PERFECT_MATCH)){

								//Tries to match the network with the given differentiation tree
								try{
									distance = TimeLimitedCodeBlock.runWithTimeout(new Callable<Integer>(){
										@Override
										public Integer call() throws Exception {
											return tesManager.findCorrectTesTree(givenTree, trees_research_type);
										}
									}, trees_research_cutoff, TimeUnit.MINUTES);

									if(distance == 0){
										match = true;
										deltas = tesManager.getThresholds();
									}else{
										match = false;
									}
								}catch(InterruptedException int_exp){
									match = false;
									deltas = null;
								}

							}else if(this.matchingType.equals(CABERNETConstants.MIN_DISTANCE)){

								try{
									//Min distance comparison
									distance = TimeLimitedCodeBlock.runWithTimeout(new Callable<Integer>(){
										@Override
										public Integer call() throws Exception {
											return tesManager.findMinDistanceTesTree(givenTree, trees_research_type);
										}
									}, trees_research_cutoff, TimeUnit.MINUTES);
									if(distance == -1){
										match = false;
									}else if(distance <= threshold){
										deltas = tesManager.getThresholds();
									}
								}catch(InterruptedException int_exp){
									match = false;
									deltas = null;
								}
							}else{
								try{
									//Computes the histogram distance
									distance = TimeLimitedCodeBlock.runWithTimeout(new Callable<Integer>(){
										@Override
										public Integer call() throws Exception {
											return tesManager.findMinHistogramDistanceTesTree(givenTree, trees_research_type);
										}
									}, trees_research_cutoff, TimeUnit.MINUTES);
									if(distance <= threshold){
										deltas = tesManager.getThresholds();
										match = true;
									}else{
										match = false;
									}
								}catch(InterruptedException int_exp){
									match = false;
									deltas = null;
								}
							}
							//Forces the process conclusion in case of thread interruption
							if(Thread.interrupted())
								throw new InterruptedException();
						}catch(InterruptedException intEx){
							//Throws the exception
							throw intEx;
						}catch(Exception ex){
							match = false;
							if(ex instanceof InterruptedException)
								throw ex;
						}
					}

					//*********************************************************
					//Representative tree finding computation (if required)
					if(this.representativeTreeComputation){
						//Task monitor message setting
						taskMonitor.setStatusMessage("Network " + (net + 1) + ": Representative trees research");

						try{
							//Gets the most representative tree with a timeout of 5 minutes
							representativeTrees = TimeLimitedCodeBlock.runWithTimeout(new Callable<ArrayList<TesTree>>(){
								@Override
								public ArrayList<TesTree> call() throws Exception {
									int depth;
									//Gets the parameters
									if(representativeTreeDepthMode.equals(CABERNETConstants.ABSOLUTE_DEPTH)){
										depth = (int) representativeTreeDepthValue;	
									}else if(representativeTreeDepthMode.equals(CABERNETConstants.RELATIVE_DEPTH)){
										depth = (int) Math.floor(representativeTreeDepthValue *  samplingManager.getAttractorFinder().getAttractorsNumber());
									}else{
										depth = (int) Math.floor(Math.log10(samplingManager.getAttractorFinder().getAttractorsNumber()) / Math.log10(2.0));	
									}
									//Creates the TES manager
									TesManager tesManager = new TesManager(atmManager, samplingManager, 
											comparison_type, comparison_cutoff);

									if(requiredOutputs.getProperty(CABERNETConstants.SHOW_ALL_TREES).equals(CABERNETConstants.YES)){
										return tesManager.getRepresentativeTrees(depth, true);

									}else{
										return tesManager.getRepresentativeTrees(depth, false);
									}
								}
							},  representativeTreeCutoff, TimeUnit.MINUTES);
						}catch(TimeoutException ex){
							representativeTrees = new ArrayList<TesTree>();
							System.out.println("Time Out!");
						}finally{
							//Forces the process conclusion in case of thread interruption
							if(Thread.interrupted())
								throw new InterruptedException();
						}
					}//Representative tree computation ending
				}//Atm computation if ending

				//Storing process done only in case of matching
				if(match){

					//*************************************************************
					//Network saving in the internal structure
					//Task monitor message setting
					taskMonitor.setProgress((net + 1)/((double)requiredNetworks));
					taskMonitor.setStatusMessage("Network " + (net + 1) + ": Exporting");
					//Adds the simulation in the container
					newSim = new Simulation(networkId);
					newSim.setGraphManager(graphManager);
					newSim.setAtmManager(atmManager);
					newSim.setSamplingManager(samplingManager);
					newSim.setDistance(distance);
					newSim.setThresholds(deltas);
					this.simulationsContainer.addSimulation(networkId, newSim);

					//Forces the process conclusion in case of thread interruption
					if(Thread.interrupted())
						throw new InterruptedException();

					//*************************************************************
					//Exports the network in the Cytoscape view

					//Creates the network view on Cytoscape (if required)
					if(this.requiredOutputs.getProperty(CABERNETConstants.NETWORK_VIEW).equals(CABERNETConstants.YES)){
						parent = this.cytoscapeBridge.createNetwork(graphManager, networkId);
					}

					//Creates the attractors view on Cytoscape (if required)
					if(this.requiredOutputs.getProperty(CABERNETConstants.ATTRACTORS_NETWORK_VIEW).equals(CABERNETConstants.YES)){	
						if(parent == null){
							this.cytoscapeBridge.createAttractorGraph(samplingManager.getAttractorFinder(), networkId);
						}else{	
							this.cytoscapeBridge.createAttractorGraph(samplingManager.getAttractorFinder(), networkId, parent);
						}
					}

					//Creates the representative trees views (if required)
					int i = 0;
					if(representativeTrees != null){
						for(TesTree representativeTree : representativeTrees){
							this.cytoscapeBridge.createTreesGraph(representativeTree, "Tree_d" + depth + "_n" + i, parent);
							i = i + 1;
						}
					}

					//Forces the process conclusion in case of thread interruption
					if(Thread.interrupted())
						throw new InterruptedException();

					//*************************************************************
					//Exports to file system (if required)
					if(requiredOutputs.getProperty(OutputConstants.EXPORT_TO_FILE_SYSTEM, OutputConstants.NO).equals(OutputConstants.YES)){
						outputPath = requiredOutputs.getProperty(OutputConstants.OUTPUT_PATH, "");
						outputPath = outputPath + "/" + networkId;
						//Creates the network folder
						Output.createFolder(outputPath);
						//GRNML File
						if(requiredOutputs.getProperty(OutputConstants.GRNML_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createGRNMLFile(graphManager.getGraph(), outputPath + "/network.grnml");
						}

						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();

						//SIF File
						if(requiredOutputs.getProperty(OutputConstants.SIF_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createSIFFile(graphManager.getGraph(), outputPath + "/network.sif");
						}

						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();

						//ATM File
						if(requiredOutputs.getProperty(OutputConstants.ATM_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createATMFile(atmManager.getAtm(), outputPath + "/atm.csv");
						}

						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();

						//STATES IN EACH ATTRACTOR FILE
						if(requiredOutputs.getProperty(OutputConstants.STATES_IN_EACH_ATTRACTOR, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.saveStatesAttractorsFile(outputPath + "/states_in_each_attractor.csv", samplingManager.getAttractorFinder());
						}

						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();

						//ATTRACTORS FILE
						if(requiredOutputs.getProperty(OutputConstants.ATTRACTORS, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.saveAttractorsFile(samplingManager.getAttractorFinder(), outputPath + "/attractors.csv");
						}

						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();

						//SYNTHESIS FILE
						if(requiredOutputs.getProperty(OutputConstants.SYNTHESIS_FILE, OutputConstants.NO).equals(OutputConstants.YES)){
							Output.createSynthesisFile(newSim.getNetworkStatistics(), outputPath + "/synthesis.csv");
						}

						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();
					}	
					net = net + 1;
				}else{  //No match, so repeat the network creation process
					taskMonitor.setStatusMessage("Network " + (net + 1) + ": No match");
					//Forces the process conclusion in case of thread interruption
					if(Thread.interrupted())
						throw new InterruptedException();
				}
			} //Network while ending

			//*****************************************************************
			//COMMON STATISTICS
			DynamicalStatistics dymStats;
			//Attractors length (if required)
			if(requiredOutputs.getProperty(OutputConstants.ATTRACTOR_LENGTHS, OutputConstants.NO).equals(OutputConstants.YES)){
				HashMap<String, ArrayList<Integer>> attractorLengths = new HashMap<String, ArrayList<Integer>>();
				for(String simId : this.simulationsContainer.getSimulationsId()){
					dymStats = new DynamicalStatistics(this.simulationsContainer.getSimulation(simId).getSamplingManager());
					attractorLengths.put(simId, dymStats.getAttractorsLength(true));
				}
				Output.saveAttractorsLengths(requiredOutputs.getProperty(OutputConstants.OUTPUT_PATH, "") + "/attractor_lengths.csv", 
						attractorLengths);
			}
			//Forces the process conclusion in case of thread interruption
			if(Thread.interrupted())
				throw new InterruptedException();


			//Basins of attraction (if required)
			if(requiredOutputs.getProperty(OutputConstants.BASINS_OF_ATTRACTION, OutputConstants.NO).equals(OutputConstants.YES)){	
				HashMap<String, ArrayList<Integer>> basinsOfAttraction = new HashMap<String, ArrayList<Integer>>();
				for(String simId : this.simulationsContainer.getSimulationsId()){
					dymStats = new DynamicalStatistics(this.simulationsContainer.getSimulation(simId).getSamplingManager());
					basinsOfAttraction.put(simId, dymStats.getBasinOfAttraction(true));
				}
				Output.saveAttractorsLengths(requiredOutputs.getProperty(OutputConstants.OUTPUT_PATH, "") + "/basins_of_attraction.csv", 
						basinsOfAttraction);
			}
			//Process ending
			taskMonitor.setProgress(1.0);
		}catch(InterruptedException intExp){
			//Process finish
			System.out.println("Interrupted Exception handling");
			taskMonitor.setProgress(1.0);
		}
	}
}
