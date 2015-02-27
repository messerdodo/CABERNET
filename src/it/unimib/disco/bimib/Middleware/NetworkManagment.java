/**
 * This class is a bridge between the app and the Cytoscape's networks management service.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.Middleware;

//System imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Cytoscape packages
import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CySubNetwork;



//GRNSim packages
import it.unimib.disco.bimib.Networks.GraphManager;
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Exceptions.*;
import it.unimib.disco.bimib.Networks.GeneRegulatoryNetwork;
import it.unimib.disco.bimib.Sampling.AttractorsFinder;
import it.unimib.disco.bimib.Tes.TesManager;
import it.unimib.disco.bimib.Tes.TesTree;

public class NetworkManagment {

	private final boolean DIRECTED_EDGE = true;
	private final int SOURCE = 0;
	private final int DESTINATION = 1;

	private final CyAppAdapter adapter;
	private final CyApplicationManager appManager;

	public NetworkManagment(CyAppAdapter adapter, CyApplicationManager appManager){
		this.adapter = adapter;
		this.appManager = appManager;
	}

	/**
	 * Creates a Cytoscape network from the given CABERNET RBN
	 * @param graphManager: the CABERNET RBN
	 * @param rbnId: the name of the rbn
	 */
	public CyNetwork createNetwork(GraphManager graphManager, String rbnId) throws NotExistingNodeException{

		int genesNumber = graphManager.getNodesNumber();
		CyNode[] genes = new CyNode[genesNumber];
		GeneRegulatoryNetwork rbn = graphManager.getGraph();
		ArrayList<String> genesNames = rbn.getNodesNames();

		//Gets the reference of CyNetworkFactory at CyActivator class of the App
		CyNetworkFactory networkFactory = adapter.getCyNetworkFactory();

		// Create a new network
		CyNetwork newRBN = networkFactory.createNetwork();

		// Set name for network
		newRBN.getRow(newRBN).set(CyNetwork.NAME, rbnId);

		//Adds the attributes 
		CyTable nodeTable = newRBN.getDefaultNodeTable();
		nodeTable.createColumn("Gene number", Integer.class, true);
		nodeTable.createColumn("Function", String.class, true);
		nodeTable.createColumn("Incoming edges", Integer.class, true);
		nodeTable.createColumn("Outcoming edges", Integer.class, true);
		nodeTable.createColumn("Degree", Integer.class, true);
		nodeTable.createColumn("Normalized Degree", Double.class, true);

		double maxDegree = 1.0;

		//Adds the genes
		for(int i = 0; i < genesNumber; i++){
			//Creates the gene
			genes[i] = newRBN.addNode();
			//Sets the gene name and the other properties
			newRBN.getRow(genes[i]).set(CyNetwork.NAME, genesNames.get(i));
			newRBN.getRow(genes[i]).set("Gene number", i);
			newRBN.getRow(genes[i]).set("Function", rbn.getFunction(i).getType());
			newRBN.getRow(genes[i]).set("Incoming edges", rbn.getIncomingNodes(i).size());
			newRBN.getRow(genes[i]).set("Outcoming edges", rbn.getOutcomingNodes(i).size());
			newRBN.getRow(genes[i]).set("Degree", rbn.getNodeDegree(i));
			maxDegree = Math.max(maxDegree, rbn.getNodeDegree(i));
		}

		//Set the normalized degree
		for(int i = 0; i < genesNumber; i++){
			newRBN.getRow(genes[i]).set("Normalized Degree", rbn.getNodeDegree(i)/maxDegree);
		}

		for(int[] edge : rbn.getEdges()){
			newRBN.addEdge(genes[edge[SOURCE]], genes[edge[DESTINATION]], DIRECTED_EDGE);
		}


		// Add the network to Cytoscape
		CyNetworkManager networkManager = adapter.getCyNetworkManager();
		networkManager.addNetwork(newRBN);

		return newRBN;
	}

	//Creates the attractors graph in the Cytoscape format from a given network
	public CyNetwork createAttractorGraph(AttractorsFinder attractorsFinder, String rbnId) 
			throws ParamDefinitionException, NotExistingNodeException, InputTypeException{

		//Gets the reference of CyNetworkFactory at CyActivator class of the App
		CyNetworkFactory networkFactory = adapter.getCyNetworkFactory();

		// Create a new network
		CyNetwork attractorGraph = networkFactory.createNetwork();

		// Set name for network
		attractorGraph.getRow(attractorGraph).set(CyNetwork.NAME, "Attractor_graph_" + rbnId);

		//Adds the attributes 
		CyTable nodeTable = attractorGraph.getDefaultNodeTable();
		if(nodeTable.getColumn("State") == null)
			nodeTable.createColumn("State", String.class, true);

		Object[] states;
		CyNode[] statesInAttractor;

		//Gets all the attractors
		Object[] attractors = attractorsFinder.getAttractors();

		//Gets all the states in each attractor
		for(Object attractor : attractors){
			states = attractorsFinder.getStatesInAttractor(attractor);
			statesInAttractor = new CyNode[states.length];

			//Adds all the states in the attractor and sets its value
			for(int state = 0; state < states.length; state++){
				statesInAttractor[state] = attractorGraph.addNode();
				attractorGraph.getRow(statesInAttractor[state]).set("State", states[state].toString());
			}

			//Sets the edges
			for(int state = 0; state < states.length; state++){
				attractorGraph.addEdge(statesInAttractor[state], statesInAttractor[(state + 1)%states.length], true);
			}	
		}

		// Add the network to Cytoscape
		CyNetworkManager networkManager = adapter.getCyNetworkManager();
		networkManager.addNetwork(attractorGraph);

		return attractorGraph;
	}

	//Creates the attractors graph in the Cytoscape format from a given network and put it in the network collenction.
	public CyNetwork createAttractorGraph(AttractorsFinder attractorsFinder, String rbnId, CyNetwork parent) 
			throws ParamDefinitionException, NotExistingNodeException, InputTypeException{

		//Get the parent group
		CyRootNetwork root = ((CySubNetwork) parent).getRootNetwork();
		//Adds a new sub network
		CyNetwork attractorGraph = root.addSubNetwork();

		// Set name for network
		attractorGraph.getRow(attractorGraph).set(CyNetwork.NAME, "Attractor_graph_" + rbnId);

		//Adds the attributes 
		CyTable nodeTable = attractorGraph.getDefaultNodeTable();
		if(nodeTable.getColumn("State") == null)
			nodeTable.createColumn("State", String.class, true);

		Object[] states;
		CyNode[] statesInAttractor;

		//Gets all the attractors
		Object[] attractors = attractorsFinder.getAttractors();

		//Gets all the states in each attractor
		for(Object attractor : attractors){
			states = attractorsFinder.getStatesInAttractor(attractor);
			statesInAttractor = new CyNode[states.length];

			//Adds all the states in the attractor and sets its value
			for(int state = 0; state < states.length; state++){
				statesInAttractor[state] = attractorGraph.addNode();
				attractorGraph.getRow(statesInAttractor[state]).set("State", states[state].toString());
			}

			//Sets the edges
			for(int state = 0; state < states.length; state++){
				attractorGraph.addEdge(statesInAttractor[state], statesInAttractor[(state + 1)%states.length], true);
			}	
		}

		// Add the network to Cytoscape
		CyNetworkManager networkManager = adapter.getCyNetworkManager();
		networkManager.addNetwork(attractorGraph);

		return attractorGraph;
	}

	/**
	 * This method creates the complete TES graph in the Cytoscape format from a given network
	 * @param attractorsFinder: the AttractorFinder object
	 * @param atmManager: the AtmManager object
	 * @param networkId: the network identifier
	 * @param threshold: the threshold value. It must be between 0 and 1.
	 * @param parent: the parent network
	 * @return: The created network
	 * @throws ParamDefinitionException
	 * @throws NotExistingNodeException
	 * @throws InputTypeException
	 */
	public CyNetwork createTesGraph(AttractorsFinder attractorsFinder, AtmManager atmManager, String networkId, double threshold, CyNetwork parent) throws ParamDefinitionException, NotExistingNodeException, InputTypeException{
		double[][] tesChartMatrix = TesManager.getTesGraph(atmManager.getAtm().copyAtmMatrixWithDeltaRemoval(threshold));

		int nodes = tesChartMatrix.length;
		int k = 0;
		//Gets the real number of attractors related with a TES
		for(int i = 0; i < tesChartMatrix.length; i++){
			k = 0;
			while(k < tesChartMatrix.length && tesChartMatrix[i][k] == 0.0)
				k++;
			if(k == tesChartMatrix.length)
				nodes = nodes - 1;
		}
		double[][] restrictedTesMatrix = new double[nodes][nodes];
		int[] attractorAssignments = new int[nodes];

		//Restricted TES matrix initialization
		for(int i = 0; i < nodes; i++)
			for(int j = 0; j < nodes; j++)
				restrictedTesMatrix[i][j] = 0;

		//Restricted TES matrix population
		int ri = 0, rj;
		for(int i = 0; i < tesChartMatrix.length; i++){
			rj = 0;
			for(int j = 0; j < tesChartMatrix.length; j++){
				if(tesChartMatrix[i][j] != 0){
					restrictedTesMatrix[ri][rj] = tesChartMatrix[i][j];
					rj = rj + 1;
				}
			}
			if(rj != 0){
				attractorAssignments[ri] = i;
				ri = ri + 1;
			}
		}

		//Get the parent group
		CyRootNetwork root = ((CySubNetwork) parent).getRootNetwork();
		//Adds a new sub network
		CyNetwork tesGraph = root.addSubNetwork();

		// Set name for network
		tesGraph.getRow(tesGraph).set(CyNetwork.NAME, "TES_graph_" + networkId + "_threshold_" + threshold);

		//Adds the attributes for the nodes
		CyTable nodeTable = tesGraph.getDefaultNodeTable();
		if(nodeTable.getColumn("State") == null)
			nodeTable.createColumn("State", String.class, true);
		if(nodeTable.getColumn("Name") == null)
			nodeTable.createColumn("Name", String.class, true);

		//Adds the attributes for the edges
		CyTable edgeTable = tesGraph.getDefaultEdgeTable();
		if(edgeTable.getColumn("Transition probability") == null)
			edgeTable.createColumn("Transition probability", Double.class, true);

		Object[] states;
		CyNode[] statesInAttractor;
		CyNode[] attractorsNodes;

		//Gets all the attractors
		Object[] attractors = attractorsFinder.getAttractors();
		attractorsNodes = new CyNode[nodes];
		int virtual = 0;
		//Gets all the states in each attractor
		for(int att_pos : attractorAssignments){
			states = attractorsFinder.getStatesInAttractor(attractors[att_pos]);
			statesInAttractor = new CyNode[states.length];

			//Adds all the states in the attractor and sets its value
			for(int state = 0; state < states.length; state++){
				statesInAttractor[state] = tesGraph.addNode();
				tesGraph.getRow(statesInAttractor[state]).set("State", states[state].toString());
				tesGraph.getRow(statesInAttractor[state]).set("Name", "A" + (virtual + 1));
			}

			//Sets the edges
			for(int state = 0; state < states.length; state++){
				tesGraph.addEdge(statesInAttractor[state], statesInAttractor[(state + 1)%states.length], true);
			}
			//Sets the virtual attractor node.
			attractorsNodes[virtual] = statesInAttractor[0];
			virtual++;
		}
		//Attractor to attractor edges
		CyEdge transitionEdge;
		for(int i = 0; i < restrictedTesMatrix.length; i++){
			for(int j = 0; j < restrictedTesMatrix.length; j++){
				if(restrictedTesMatrix[i][j] != 0.0){
					transitionEdge = tesGraph.addEdge(attractorsNodes[i], attractorsNodes[j], true);
					tesGraph.getRow(transitionEdge).set("Transition probability", restrictedTesMatrix[i][j]);
					tesGraph.getRow(transitionEdge).set("Interaction", "Attractors Transition");
				}
			}
		}

		// Add the network to Cytoscape
		CyNetworkManager networkManager = adapter.getCyNetworkManager();
		networkManager.addNetwork(tesGraph);

		return tesGraph;

	}



	/**
	 * This method creates the complete TES graph in the Cytoscape format from a given network
	 * @param atmManager: the atm manager object
	 * @param networkId: the network identifier
	 * @param threshold: the threshold value. It must be between 0 and 1.
	 * @param parent: the parent network.
	 * @return the created network.
	 * @throws ParamDefinitionException
	 * @throws NotExistingNodeException
	 * @throws InputTypeException
	 */
	public CyNetwork createCollapsedTesGraph(AtmManager atmManager, String networkId, double threshold, CyNetwork parent) throws ParamDefinitionException, NotExistingNodeException, InputTypeException{
		double[][] tesChartMatrix = TesManager.getTesGraph(atmManager.getAtm().copyAtmMatrixWithDeltaRemoval(threshold));
		int nodes = tesChartMatrix.length;
		int k = 0;
		//Gets the real number of attractors related with a TES
		for(int i = 0; i < tesChartMatrix.length; i++){
			k = 0;
			while(k < tesChartMatrix.length && tesChartMatrix[i][k] == 0.0)
				k++;
			if(k == tesChartMatrix.length)
				nodes = nodes - 1;
		}
		double[][] restrictedTesMatrix = new double[nodes][nodes];

		//Restricted TES matrix initialization
		for(int i = 0; i < nodes; i++)
			for(int j = 0; j < nodes; j++)
				restrictedTesMatrix[i][j] = 0;

		//Restricted TES matrix population
		int ri = 0, rj;
		for(int i = 0; i < tesChartMatrix.length; i++){
			rj = 0;
			for(int j = 0; j < tesChartMatrix.length; j++){
				if(tesChartMatrix[i][j] != 0){
					restrictedTesMatrix[ri][rj] = tesChartMatrix[i][j];
					rj = rj + 1;
				}
			}
			if(rj != 0){
				ri = ri + 1;
			}
		}

		//Get the parent group
		CyRootNetwork root = ((CySubNetwork) parent).getRootNetwork();
		//Adds a new sub network
		CyNetwork tesGraph = root.addSubNetwork();

		// Set name for network
		tesGraph.getRow(tesGraph).set(CyNetwork.NAME, "Collapsed_TES_graph_" + networkId + "_threshold_" + threshold);

		//Adds the attributes for the edges
		CyTable edgeTable = tesGraph.getDefaultEdgeTable();
		if(edgeTable.getColumn("Transition probability") == null)
			edgeTable.createColumn("Transition probability", Double.class, true);
		//Adds the attributes for the nodes
		CyTable nodeTable = tesGraph.getDefaultNodeTable();
		if(nodeTable.getColumn("Name") == null)
			nodeTable.createColumn("Name", String.class, true);

		CyNode[] attractorsNodes = new CyNode[nodes];

		//Adds the collapsed attractors
		for(int i = 0; i < nodes; i++){
			//Sets the virtual attractor node.
			attractorsNodes[i] = tesGraph.addNode();
			tesGraph.getRow(attractorsNodes[i]).set("Name", "A" + (i + 1));
		}

		CyEdge transitionEdge;
		for(int i = 0; i < restrictedTesMatrix.length; i++){
			for(int j = 0; j < restrictedTesMatrix.length; j++){
				if(restrictedTesMatrix[i][j] != 0.0){
					transitionEdge = tesGraph.addEdge(attractorsNodes[i], attractorsNodes[j], true);
					tesGraph.getRow(transitionEdge).set("Transition probability", restrictedTesMatrix[i][j]);
					tesGraph.getRow(transitionEdge).set("Interaction", "Attractors Transition");
				}
			}
		}

		// Add the network to Cytoscape
		CyNetworkManager networkManager = adapter.getCyNetworkManager();
		networkManager.addNetwork(tesGraph);

		return tesGraph;

	}

	/**
	 * This method returns true if a Cytoscape network is selected
	 * @return
	 */
	public boolean isNetworkSelected(){
		return appManager.getCurrentNetwork() != null;
	}

	/**
	 * This method returns a GRNSim GraphManager from the current Cytoscape view.
	 * @return The Cytoscape current view network as a GraphManager object
	 * @throws ParamDefinitionException
	 * @throws NotExistingNodeException
	 */
	public GraphManager getNetworkFromCytoscape() throws ParamDefinitionException, NotExistingNodeException{
		GraphManager graphManager = new GraphManager();
		CyNetwork currentNetwork = appManager.getCurrentNetwork();
		if(currentNetwork == null)
			throw new NullPointerException("No networks selected");
		List<CyNode> nodes = currentNetwork.getNodeList();
		List<CyEdge> edges = currentNetwork.getEdgeList();
		HashMap<CyNode, Integer> mapping = new HashMap<CyNode, Integer>();

		String[] genesNames = new String[nodes.size()];
		int[][] edgesMatrix = new int[edges.size()][2];
		//Gets the names
		for(int i = 0; i < nodes.size(); i++){
			genesNames[i] = currentNetwork.getRow(nodes.get(i)).get("name", String.class);
			mapping.put(nodes.get(i), i);
		}
		//Gets the edges
		for(int i = 0; i < edges.size(); i++){
			edgesMatrix[i][0] = mapping.get(edges.get(i).getSource());
			edgesMatrix[i][1] = mapping.get(edges.get(i).getTarget());
		}

		graphManager.createGraph(genesNames, edgesMatrix, "Random");

		return graphManager;

	}


	public TesTree getTreeFromCytoscape() throws TesTreeException{
		CyNetwork currentNetwork = appManager.getCurrentNetwork();
		List<CyNode> nodes = currentNetwork.getNodeList();
		List<CyEdge> edges = currentNetwork.getEdgeList();

		if(nodes.size() != edges.size() + 1)
			return null;


		boolean [][] treeMatrix = new boolean[nodes.size()][nodes.size()];
		for(int i = 0; i < nodes.size(); i++){
			for(int j = 0; j < nodes.size(); j++){
				treeMatrix[i][j] = false;
			}
		}
		//Populates the tree matrix
		for(CyEdge edge : edges)
			treeMatrix[nodes.indexOf(edge.getSource())][nodes.indexOf(edge.getTarget())] = true;

		//Finds the potential root
		int rootIndex  = - 1, potentialRoots = 0;
		int i = 0;
		for(int j = 0; j < nodes.size(); j++){
			i = 0;
			while(i < nodes.size() && treeMatrix[i][j] == false){
				i = i + 1;
			}
			if(i == nodes.size()){
				rootIndex = j;
				potentialRoots = potentialRoots + 1;
			}
		}

		if(potentialRoots != 1){
			return null;
		}

		//Cycles detection
		boolean[] visited = new boolean[nodes.size()];
		for(int h = 0; h < nodes.size(); h++){
			visited[h] = false;
		}
		visited[rootIndex] = true;
		boolean isTree = treeVisit(rootIndex, treeMatrix, visited);

		if(!isTree)
			return null;


		TesTree tree = new TesTree(rootIndex);
		createTesTree(rootIndex, treeMatrix, 0, tree);
		tree.print();
		return tree;




	}

	private boolean treeVisit(int rootIndex, boolean[][] treeMatrix, boolean[] visited){

		boolean result = true;

		for(int i = 0; i < visited.length; i++){
			if(treeMatrix[rootIndex][i]){
				if(visited[i]){
					return false;
				}else{
					visited[i] = true;
					result = result && treeVisit(i, treeMatrix, visited);
					if(!result)
						return false;
				}
			}
		}

		return result;
	}

	private void createTesTree(int rootIndex, boolean[][] treeMatrix, int level, TesTree tree) throws TesTreeException{
		for(int i = 0; i < treeMatrix.length; i++){
			if(treeMatrix[rootIndex][i]){
				tree.addNodeManually(i, level + 1, rootIndex);
				createTesTree(i, treeMatrix, level + 1, tree);
			}
		}
	}

}
