/**
 * 
 * This class contains all the methods for the TESes management.
 * 
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @author Giorgia Previtali (g.previtali6@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca
 * @year 2013
 * 
 */
package it.unimib.disco.bimib.Tes;

//System imports
import java.util.ArrayList;
import java.util.Collections;



//GRNSim imports
import it.unimib.disco.bimib.Exceptions.TesTreeException;
import it.unimib.disco.bimib.Sampling.AttractorsFinder;
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Utility.SCCTarjan;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;
import it.unimib.disco.bimib.Utility.UtilityRandom;
import it.unimib.disco.bimib.Atms.Atm;
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Exceptions.ParamDefinitionException;


public class TesManager {

	private Atm atm;
	private AttractorsFinder attractorsFinder;
	private TesTree createdTree;
	private double[] thresholds;
	private String comparison_type;
	private int comparison_cutoff;

	/**
	 * Default constructor 
	 * @param atm
	 * @param attractorsFinder
	 * @throws Exception
	 */
	public TesManager(AtmManager atm, SamplingManager sampling,
			String comparison_type, int comparison_cutoff)throws Exception {

		this.atm = atm.getAtm();
		this.attractorsFinder = sampling.getAttractorFinder();	
		this.thresholds = null;
		this.comparison_type = comparison_type;
		this.comparison_cutoff = comparison_cutoff;
		this.setCreatedTree(null);
	}

	/**
	 * @return the createdTree
	 */
	public TesTree getCreatedTree() {
		return createdTree;
	}

	/**
	 * @param createdTree the createdTree to set
	 */
	private void setCreatedTree(TesTree createdTree) {
		this.createdTree = createdTree;
	}

	/**
	 * This function returns the thresholds sequence for the matching.
	 * @return the ordered thresholds sequence
	 */
	public double[] getThresholds(){
		return this.thresholds;
	}

	public int findCorrectTesTree(TesTree givenTree, String research_type) throws Exception {
		int k = this.attractorsFinder.getAttractors().length;
		int givenTreeDeepness = givenTree.getTreeDeppness();
		TesTree createdTree = null;
		ArrayList<double[]> combinations = null;

		//Preconditions checking
		if(givenTree.getLeafsNodesNumber() > k){
			return -1;
		}

		//Searches every possible combination of delta in the matrix 
		combinations = deltaCombinations(this.atm.copyAtm(), givenTreeDeepness, research_type);	
		combinations = UtilityRandom.randomDoublePermutation(combinations);
		//Combinations checking
		if(combinations == null)
			return -1;

		//Creates a new TES tree for each delta-values combination
		for(double[] testing_thresholds : combinations){
			try{
				createdTree = new TesTree(testing_thresholds, this.atm.copyAtm(), this.attractorsFinder.getAttractors());
				//Checks if the created tree is equal to the given differentiation tree.
				if(createdTree.tesTreeCompare(givenTree, comparison_type, comparison_cutoff)){
					this.thresholds = testing_thresholds;
					//Tree fund: the method returns 0.
					return 0;
				}		
			}catch(TesTreeException e){
				//Nope
			}
		}
		//No tree found, so the method returns -1.
		return -1;
	}

	/**
	 * This method tries to create a TES tree with the specified network. 
	 * @param givenTree: The required differentiation tree
	 * @return this method returns the created TES tree which is equal to the specified one. 
	 * If it's impossible to create the given tree, the method returns the null value. 
	 * @throws TesTreeException: An error occurred during the tree creation
	 * @throws InterruptedException 
	 * @throws ParamDefinitionException 
	 */
	public int findMinDistanceTesTree(TesTree givenTree, String research_type) 
			throws TesTreeException, InterruptedException, ParamDefinitionException {
		int k = this.attractorsFinder.getAttractors().length;
		int givenTreeDeepness = givenTree.getTreeDeppness();

		//Checks if the number of attractor is enough
		if(k < givenTreeDeepness){
			return -1;
		}

		//Searches every possible combination of delta in the matrix 
		ArrayList<double[]> combinations = deltaCombinations(this.atm.copyAtm(), givenTreeDeepness, research_type);		

		TesTree createdTree = null;
		int minDistance = -1, currentDistance;
		//Creates a new TES tree for each delta values combination
		for(double[] deltas : combinations){
			createdTree = new TesTree(deltas, this.atm.copyAtm(), this.attractorsFinder.getAttractors());
			//Calculates the min distance between the created tree and the given tree
			currentDistance = createdTree.tesTreeDistance(givenTree);
			if(currentDistance == 0){
				minDistance = currentDistance;
				this.thresholds = deltas;
				break;
				//Calculates the min distance.
			}else if(minDistance == -1 || currentDistance < minDistance){
				minDistance = currentDistance;
				this.thresholds = deltas;
			}
		}

		//Returns the min distance between the given tree and the created tree with the selected deltas
		return minDistance;
	}

	/**
	 * This method tries to create a TES tree with the specified network. 
	 * @param givenTree: The required differentiation tree
	 * @return this method returns the created TES tree which is equal to the specified one. 
	 * If it's impossible to create the given tree, the method returns the null value. 
	 * @throws TesTreeException: An error occurred during the tree creation
	 * @throws InterruptedException 
	 * @throws ParamDefinitionException 
	 */
	public int findMinHistogramDistanceTesTree(TesTree givenTree, 
			String research_type) throws TesTreeException, InterruptedException, ParamDefinitionException {
		int k = this.attractorsFinder.getAttractors().length;
		int givenTreeDeepness = givenTree.getTreeDeppness();

		//Checks if the number of attractor is enough
		if(k < givenTreeDeepness){
			return -1;
		}

		//Searches every possible combination of delta in the matrix 
		ArrayList<double[]> combinations = deltaCombinations(givenTreeDeepness, research_type);		
		TesTree createdTree = null;
		int minDistance = -1, currentDistance;
		//Creates a new TES tree for each delta values combination
		double deltas[];
		int i = 0;
		while(minDistance != 0 && i < combinations.size()){
			deltas = combinations.get(i);

			try{
				createdTree = new TesTree(deltas, this.atm.copyAtm(), this.attractorsFinder.getAttractors());
			}catch(TesTreeException e){
				return -1;
			}
			//Calculates the min distance between the created tree and the given tree
			currentDistance = createdTree.tesTreeHistogramComparison(givenTree);
			if(currentDistance == 0){
				this.thresholds = deltas;
				minDistance = currentDistance;
			}else if(minDistance == -1 || minDistance > currentDistance){
				minDistance = currentDistance;
				this.thresholds = deltas;
			}
			i++;
		}

		//Returns the min distance between the given tree and the created tree with the selected deltas
		return minDistance;
	}



	public ArrayList<double[]> deltaCombinations(int requiredTreeDeepness, String research_type)
			throws TesTreeException, InterruptedException, ParamDefinitionException{
		//Initializes the ArraList
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<double[]> combinations = new ArrayList<double[]>();
		for(double delta = 0.01; delta < 0.15; delta = delta + 0.01){
			values.add(delta);
		}

		//Forces the process conclusion in case of thread interruption
		if(Thread.interrupted())
			throw new InterruptedException();

		//Sorts all the value in a crescent order
		Collections.sort(values); 

		if(requiredTreeDeepness > values.size())
			return null;
		else{
			ArrayList<ArrayList<Double>> e;
			if(research_type.equals(SimulationFeaturesConstants.COMPLETED_TREES_RESEARCH)){
				e = TesManager.combinationCreator(values, 0, requiredTreeDeepness, 1.0);
			}else{
				//Gets the combinations 
				double prob = Math.min(1.0, (2.0 * requiredTreeDeepness) / values.size());
				e = TesManager.combinationCreator(values, 0, requiredTreeDeepness, prob);
			}
			for(ArrayList<Double> comb: e){
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();

				double[] singleCombination = new double[requiredTreeDeepness + 1];
				singleCombination[0] = 0.0;
				Collections.sort(comb);
				for(int j = 1; j < requiredTreeDeepness + 1; j++){
					singleCombination[j] = comb.get(j - 1);
				}
				//Adds the combination in the array list "Combinations"
				combinations.add(singleCombination);
			}
		}
		//Returns all k-sized combinations 
		return combinations;
	}

	/**
	 * This method return all the possible delta values combinations. Each combination is ascending sorted. 
	 * @param atm: The attractors threshold matrix.
	 * @param requiredTreeDeepness: The number of elements in each combination.
	 * @return an arrayList which contains an array with the delta values for each combination.
	 * @throws TesTreeException: An error occurred during the combinations calculation.
	 * @throws InterruptedException 
	 * @throws ParamDefinitionException 
	 */
	public static ArrayList<double[]> deltaCombinations(double[][] atm, int requiredTreeDeepness,
			String research_type) throws TesTreeException, InterruptedException, ParamDefinitionException{	
		//Initializes the ArraList
		ArrayList<Double> values = new ArrayList<Double>();
		ArrayList<double[]> combinations = new ArrayList<double[]>();
		//Puts into the ArrayList all the distinct values of the Atm matrix
		for(int line = 0; line < atm.length; line++){
			for(int pillar = 0; pillar < atm.length; pillar++){
				if(values.contains(atm[line][pillar]) == false)
					values.add(atm[line][pillar]);	
			}
		}

		//Forces the process conclusion in case of thread interruption
		if(Thread.interrupted())
			throw new InterruptedException();

		//Sorts all the value in a crescent order
		Collections.sort(values); 

		//Removes the zero if it is present.
		if(values.contains(0.0))
			values.remove(new Double(0.0));

		if(requiredTreeDeepness > values.size())
			throw new TesTreeException("k ("+requiredTreeDeepness+") is bigger then the threshold's values ("+values.size() +")");
		else{
			ArrayList<ArrayList<Double>> e;
			if(research_type.equals(SimulationFeaturesConstants.COMPLETED_TREES_RESEARCH)){
				e = TesManager.combinationCreator(values, 0, requiredTreeDeepness, 1.0);
			}else{
				double prob = Math.min(1.0, (2.0 * requiredTreeDeepness) / values.size());
				e = TesManager.combinationCreator(values, 0, requiredTreeDeepness, prob);
			}
			for(ArrayList<Double> comb: e){
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();

				double[] singleCombination = new double[requiredTreeDeepness + 1];
				singleCombination[0] = 0.0;
				Collections.sort(comb);
				for(int j = 1; j < requiredTreeDeepness + 1; j++){
					singleCombination[j] = comb.get(j - 1);
				}
				//Adds the combination in the array list "Combinations"
				combinations.add(singleCombination);
			}
		}
		//Returns all k-sized combinations 
		return combinations;

	}

	/**
	 * This is a service method for the combinations generation.
	 * @param values: The set with all the values that must be used in the combinations generation.
	 * @param k: The number of values in each combination
	 * @return Returns an arrayList with the unformatted combinations. 
	 * Each set of k values should be explained as a single combination.
	 * @throws InterruptedException 
	 */
	public static ArrayList<ArrayList<Double>> combinationCreator(ArrayList<Double> values, 
			int start, int k, double probability) throws ParamDefinitionException, InterruptedException {
		ArrayList<ArrayList<Double>> combs;
		ArrayList<Double> tail;
		ArrayList<ArrayList<Double>> tails;
		if(k == 1){
			combs = new ArrayList<ArrayList<Double>>();
			for(int i = start; i < values.size() - k + 1; i++){
				tail = new ArrayList<Double>();
				tail.add(values.get(i));
				combs.add(tail);
			}
		}else{
			combs = new ArrayList<ArrayList<Double>>();
			ArrayList<Integer> sampling = UtilityRandom.randomSubset(start, 
					values.size() - k, 
					(int)Math.ceil(((values.size() - k - start + 1) * probability)));
			for(int i : sampling){
				if(Thread.interrupted()){
					throw new InterruptedException();
				}
				tails = combinationCreator(values, i + 1, k - 1, probability);
				for(ArrayList<Double> c : tails){
					c.add(values.get(i));
					combs.add(c);
				}
			}
		}
		return combs;
	}

	/**
	 * Creates the given tree
	 * @param rootId: root node id
	 * @throws TesTreeException 
	 * @throws NumberFormatException 
	 */
	public static TesTree createTesTreeFromFile(ArrayList<String[]> tree) throws NumberFormatException, TesTreeException{
		TesTree givenTree;

		if(!tree.get(0)[0].equals("0"))
			throw new TesTreeException("Root not specified");

		givenTree = new TesTree(Integer.parseInt(tree.get(0)[1]));

		for(int i = 1; i < tree.size(); i++){
			addNodeManually(Integer.parseInt(tree.get(i)[1]),
					Integer.parseInt(tree.get(i)[0]),
					Integer.parseInt(tree.get(i)[2]),
					givenTree);
		}

		return givenTree;
	}

	/**
	 * Adds a new node in the given tree
	 * @param nodeId: The new node id
	 * @param level: The new node level
	 * @param parentId: The new node parent id
	 * @throws TesTreeException: An error occurred during the research
	 */
	private static void addNodeManually(int nodeId, int level, int parentId, TesTree tree) throws TesTreeException{
		tree.addNodeManually(nodeId, level, parentId);
	}

	/**
	 * This static function returns the TES graph adjacency matrix
	 * given the atm
	 * @param atm: the Attractors Transition Matrix
	 * @return the TES graph adjacency matrix
	 */
	public static double[][] getTesGraph(double[][] atm){
		//Gets the Strongly Connected Components
		SCCTarjan sccCalculator = new SCCTarjan(atm);
		ArrayList<ArrayList<Integer>> scc = sccCalculator.scc();
	
		//Each position of the array contains the number of the scc of the element.
		int[] assignments = new int[atm.length];
		for(int i  = 0; i < scc.size(); i++){
			for(Integer att : scc.get(i))
				assignments[att] = i;
		}

		//All the scc are teses at the beginning.
		boolean[] temporaryTesSet = new boolean[scc.size()];
		for(int i = 0; i < scc.size(); i++)
			temporaryTesSet[i] = true;

		int j = 0;
		//Removes the scc that are not tes.
		for(int i = 0; i < atm.length; i++){
			j = 0;
			while((j < atm.length) && (atm[i][j] == 0 || (!((atm[i][j] >= 0) 
					&& (assignments[i] != assignments[j])))) ){
				j = j + 1;
			}
			if(j < atm.length){
				temporaryTesSet[assignments[i]] = false;
			}
		}
		//Initialize the TES graph adjacency matrix
		double[][] tesGraphMatrix = new double[atm.length][atm.length]; 
		for(int i = 0; i < atm.length; i++){
			for(int k = 0; k < atm.length; k++){
				tesGraphMatrix[i][k] = 0.0;
			}
		}
		//Creates the TES graph adjacency matrix:
		//Only the links between attractors in a TES are copied
		ArrayList<Integer> tes;
		for(int t = 0; t < temporaryTesSet.length; t++){

			tes = scc.get(t);
			for(int a1 = 0; a1 < tes.size(); a1++){
				for(int a2 = 0; a2 < tes.size(); a2++){
					if(temporaryTesSet[t] == true){
						tesGraphMatrix[tes.get(a1)][tes.get(a2)] = atm[tes.get(a1)][tes.get(a2)];
					}else{
						tesGraphMatrix[tes.get(a1)][tes.get(a2)] = -1;
					}
				}
			}
		}
		return tesGraphMatrix;
	}

	/**
	 * This method returns the number of TESes for a given threshold
	 * @param threshold: The transition probability threshold. It must be between 0 and 1;
	 * @return The number of TES.
	 */
	public static int getTesNumber(double[][]atm, double threshold){
		int tes = 0;
		int[] assignments;
		//Param checking
		if(threshold < 0.0){
			threshold = 0.0;
		}else if(threshold > 1.0){
			threshold = 1.0;
		}

		//Computes the SCC
		SCCTarjan sccCalculator = new SCCTarjan(atm);
		ArrayList<ArrayList<Integer>> scc = sccCalculator.scc();

		//Each position of the array contains the number of the scc of the element.
		assignments = new int[atm.length];
		for(int i  = 0; i < scc.size(); i++){
			for(Integer att : scc.get(i))
				assignments[att] = i;
		}

		//All the scc are teses at the beginning.
		boolean[] temporaryTesSet = new boolean[scc.size()];
		for(int i = 0; i < scc.size(); i++)
			temporaryTesSet[i] = true;

		int j = 0;
		//Removes the scc that are not TESs.
		for(int i = 0; i < atm.length; i++){
			j = 0;
			while((j < atm.length) && (atm[i][j] == 0 || (!((atm[i][j] >= 0) 
					&& (assignments[i] != assignments[j])))) ){
				j = j + 1;
			}
			if(j < atm.length){
				temporaryTesSet[assignments[i]] = false;
			}
		}

		//Computes the number of teses.
		for(int i = 0; i < temporaryTesSet.length; i++){
			if(temporaryTesSet[i]){
				tes = tes + 1;
			}
		}

		return tes;
	}


	/**
	 * This method returns the found trees array.
	 * If no trees are found, it returns an empty array list.
	 * If the param most is true it returns only the most frequently found trees
	 * @param atm: the atm matrix
	 * @param requiredDepth: tree depth.
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<TesTree> getRepresentativeTrees(int requiredDepth, boolean most) throws Exception{
		ArrayList<TesTree> foundTrees = new ArrayList<TesTree>();
		ArrayList<Integer> treesOccurence = new ArrayList<Integer>();
		ArrayList<TesTree> mostFrequentTree = new ArrayList<TesTree>();
		double[][] atm = this.atm.getAtm();

		ArrayList<double[]> combinations = deltaCombinations(atm, requiredDepth, 
				SimulationFeaturesConstants.COMPLETED_TREES_RESEARCH);
		combinations = UtilityRandom.randomDoublePermutation(combinations);
		TesTree createdTree;
		int i;
		int times = 0;
		double[] currentCombination = null;
		boolean found = false;
		//Tests 'cutoff' trees (or combinations.size() if cutoff is too big)
		//If cutoff is -1 tests all the possible trees
		while(times < combinations.size()){

			//Forces the process conclusion in case of thread interruption
			if(Thread.interrupted())
				throw new InterruptedException();

			try{
				currentCombination = combinations.get(times);
				createdTree = new TesTree(currentCombination, this.atm.copyAtm(), this.attractorsFinder.getAttractors());
				//Checks if the created tree is equal to one of the already found trees.
				i = 0;
				found = false;
				while(i < foundTrees.size() && !found){

					//Forces the process conclusion in case of thread interruption
					if(Thread.interrupted())
						throw new InterruptedException();

					if(createdTree.tesTreeCompare(foundTrees.get(i), 
							comparison_type, comparison_cutoff)){
						found = true;
						createdTree = null;
						System.gc();
					}else{
						i = i + 1;
					}
				}		
				if(i < foundTrees.size()){
					treesOccurence.set(i, treesOccurence.get(i) + 1);
				}else{
					foundTrees.add(createdTree);
					treesOccurence.add(1);
				}
			}catch(TesTreeException e){
				//Nope
			}
			times = times + 1;

		}

		//Forces the process conclusion in case of thread interruption
		if(Thread.interrupted())
			throw new InterruptedException();

		//Return all the trees if required
		if(!most)
			return foundTrees;

		//Selects the most frequently found trees
		int max = 0;
		for(int value : treesOccurence){
			max = Math.max(max, value);
		}
		for(i = 0; i < treesOccurence.size(); i++){
			if(treesOccurence.get(i) == max){
				mostFrequentTree.add(foundTrees.get(i));
			}
		}
		foundTrees = null;
		System.gc();

		//Forces the process conclusion in case of thread interruption
		if(Thread.interrupted())
			throw new InterruptedException();

		//Returns the most obtained trees
		return mostFrequentTree;
	}

}
