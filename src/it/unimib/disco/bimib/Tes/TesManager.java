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
import it.unimib.disco.bimib.Atms.Atm;
import it.unimib.disco.bimib.Atms.AtmManager;

public class TesManager {

	private Atm atm;
	private AttractorsFinder attractorsFinder;
	private TesTree createdTree;
	private double[] thresholds;
	private int  max_children_for_complete_test;
	private double partial_test_probability;

	/**
	 * Default constructor 
	 * @param atm
	 * @param attractorsFinder
	 * @throws Exception
	 */
	public TesManager(AtmManager atm, SamplingManager sampling,
			int max_children_for_complete_test, 
			double partial_test_probability)throws Exception {

		this.atm = atm.getAtm();
		this.attractorsFinder = sampling.getAttractorFinder();	
		this.thresholds = null;
		this.max_children_for_complete_test = max_children_for_complete_test;
		this.partial_test_probability = partial_test_probability;
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

	public int findCorrectTesTree(TesTree givenTree) throws TesTreeException, InterruptedException {
		int k = this.attractorsFinder.getAttractors().length;
		int givenTreeDeepness = givenTree.getTreeDeppness();
		TesTree createdTree = null;
		ArrayList<double[]> combinations = null;

		//Preconditions checking
		if(givenTree.getLeafsNodesNumber() > k){
			return -1;
		}

		//Searches every possible combination of delta in the matrix 
		combinations = deltaCombinations(this.atm.copyAtm(), givenTreeDeepness);		
		//Combinations checking
		if(combinations == null)
			return -1;

		//Creates a new TES tree for each delta-values combination
		for(double[] testing_thresholds : combinations){
			try{
				createdTree = new TesTree(testing_thresholds, this.atm.copyAtm(), this.attractorsFinder.getAttractors());
				//Checks if the created tree is equal to the given differentiation tree.
				if(createdTree.tesTreeCompare(givenTree, max_children_for_complete_test, partial_test_probability)){
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
	 */
	public int findMinDistanceTesTree(TesTree givenTree) throws TesTreeException, InterruptedException {
		int k = this.attractorsFinder.getAttractors().length;
		int givenTreeDeepness = givenTree.getTreeDeppness();

		//Checks if the number of attractor is enough
		if(k < givenTreeDeepness){
			return -1;
		}

		//Searches every possible combination of delta in the matrix 
		ArrayList<double[]> combinations = deltaCombinations(this.atm.copyAtm(), givenTreeDeepness);		

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
	 */
	public int findMinHistogramDistanceTesTree(TesTree givenTree) throws TesTreeException, InterruptedException {
		int k = this.attractorsFinder.getAttractors().length;
		int givenTreeDeepness = givenTree.getTreeDeppness();

		//Checks if the number of attractor is enough
		if(k < givenTreeDeepness){
			return -1;
		}

		//Searches every possible combination of delta in the matrix 
		ArrayList<double[]> combinations = deltaCombinations(givenTreeDeepness);		
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



	public ArrayList<double[]> deltaCombinations(int requiredTreeDeepness)throws TesTreeException, InterruptedException{
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
			//Gets the unstructured combinations 
			ArrayList<Double> e = combinationCreator(values, requiredTreeDeepness);
			for(int i = 0; i < e.size(); i += requiredTreeDeepness){
				
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();
				
				double[] singleCombination = new double[requiredTreeDeepness + 1];
				singleCombination[0] = 0.0;
				for(int j = 1; j < requiredTreeDeepness + 1; j++){
					singleCombination[j] = e.get(i+j-1);
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
	 */
	public static ArrayList<double[]> deltaCombinations(double[][] atm, int requiredTreeDeepness) throws TesTreeException, InterruptedException{	
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
			//Gets the unstructured combinations 
			ArrayList<Double> e = TesManager.combinationCreator(values, requiredTreeDeepness);
			for(int i = 0; i < e.size(); i += requiredTreeDeepness){
				
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();
				
				double[] singleCombination = new double[requiredTreeDeepness + 1];
				singleCombination[0] = 0.0;
				for(int j = 1; j < requiredTreeDeepness + 1; j++){
					singleCombination[j] = e.get(i+j-1);
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
	private static ArrayList<Double> combinationCreator(ArrayList<Double> values, int k) throws InterruptedException {
		//Initializes all the ArrayList needed
		ArrayList<Double> head = new ArrayList<Double>();
		ArrayList<Double> temp =new ArrayList<Double>();
		ArrayList<Double> tailcombs = new ArrayList<Double>();
		ArrayList<Double> combs = new ArrayList<Double>();
		int j = 0;
		
		//Forces the process conclusion in case of thread interruption
		if(Thread.interrupted())
			throw new InterruptedException();

		//If k is equal to the length of the ArrayList returns its
		if (k == values.size()) {
			return values;
		}
		//If k is equal to 1 adds all the values in the ArrayList combs
		if (k == 1) {
			for (int i = 0; i < values.size() ; i++) {
				combs.add(values.get(i));
			}
			return combs;
		}
		//Searches the k combinations
		for (int i = 0; i < values.size() - k  + 1; i++) {
			
			//Forces the process conclusion in case of thread interruption
			if(Thread.interrupted())
				throw new InterruptedException();
			
			//Adds the first element of the ArrayList values into head
			head.add(values.get(i));

			//Adds all the remain values in a temporary ArrayList
			for(int index = i + 1; index < values.size(); index++)
				temp.add(values.get(index));

			//If the temporary ArrayList isn't empty makes the recursive call
			if(temp.isEmpty() != true)
				tailcombs = combinationCreator(temp, k - 1);

			//Pass all the element of the ArrayList tailcombs
			while ( j < tailcombs.size()) {
				
				//Forces the process conclusion in case of thread interruption
				if(Thread.interrupted())
					throw new InterruptedException();
				
				//Checks if the specified values of the ArrayList is different from the ith head's element
				//and if it is bigger.
				if(tailcombs.get(j) != head.get(0) && tailcombs.get(j) > head.get(i)){
					//Adds the ith head's element in the ArraList combs
					combs.add(head.get(i));
					//Adds all the k-element in the ArraList combs
					for(int valueK = 0 ; valueK < k - 1; valueK ++){
						
						//Forces the process conclusion in case of thread interruption
						if(Thread.interrupted())
							throw new InterruptedException();
						
						combs.add(tailcombs.get(j));
						j++;
					}
				}

			}
			//Resets the index and the temporary ArrayList
			j = 0;
			temp.clear();
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
			if(temporaryTesSet[t] == true){
				tes = scc.get(t);
				for(int a1 = 0; a1 < tes.size(); a1++)
					for(int a2 = 0; a2 < tes.size(); a2++)
						tesGraphMatrix[tes.get(a1)][tes.get(a2)] = atm[tes.get(a1)][tes.get(a2)];
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
	 * @throws TesTreeException
	 * @throws InterruptedException 
	 */
	public ArrayList<TesTree> getRepresentativeTrees(int requiredDepth, int cutoff, boolean most) throws TesTreeException, InterruptedException{
		ArrayList<TesTree> foundTrees = new ArrayList<TesTree>();
		ArrayList<Integer> treesOccurence = new ArrayList<Integer>();
		ArrayList<TesTree> mostFrequentTree = new ArrayList<TesTree>();
		double[][] atm = this.atm.getAtm();

		ArrayList<double[]> combinations = deltaCombinations(atm, requiredDepth);
		TesTree createdTree;
		int i;
		int times = 0;
		double[] currentCombination = null;
		boolean found = false;
		//Tests 'cutoff' trees (or combinations.size() if cutoff is too big)
		//If cutoff is -1 tests all the possible trees
		while(times < combinations.size() && (cutoff == -1 || times < cutoff)){

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
							max_children_for_complete_test, partial_test_probability)){
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
