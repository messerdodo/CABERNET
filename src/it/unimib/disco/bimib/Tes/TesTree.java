/**
 * 
 * This class is a representation of a Tes tree
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
import java.util.HashMap;
import java.util.HashSet;




//GRNSim imports
import it.unimib.disco.bimib.Exceptions.TesTreeException;
import it.unimib.disco.bimib.Utility.SCCTarjan;
import it.unimib.disco.bimib.Utility.UtilityRandom;


public class TesTree{

	private TesTreeNode root;

	private int nextNodeId;

	/**
	 * This is the default constructor 
	 * @param delta
	 * @param atm
	 * @param attractors
	 * @throws Exception
	 */
	public TesTree(double[] thresholds,double[][] atm, Object[] attractors) throws TesTreeException {
		this.root = null;
		this.nextNodeId = 0;
		createTree(thresholds, atm, attractors);
	}

	public TesTree(int nodeId){
		this.nextNodeId = nodeId + 1;
		this.root = new TesTreeNode(null, nodeId);
	}


	/**
	 * This method returns the tree root.
	 * @return The tree root
	 */
	public TesTreeNode getRoot(){
		return this.root;
	}


	private void createTree(double[] thresholds, double[][] atm, Object[] attractorsSet) throws TesTreeException{

		double[][] rootAtm = removeLinksByDelta(atm, thresholds[0]);
		double[][] subAtm;
		Tes tes = null;
		Object[] attractors = null;
		SCCTarjan sccCalculator = new SCCTarjan(rootAtm);
		ArrayList<ArrayList<Integer>> scc = sccCalculator.scc();
		int target = 0, elementsInComp;
		int si, sj;
		ArrayList<Integer> component = null;

		//Removes all the attractors in the same scc with outgoing edges to other scc
		for(ArrayList<Integer> comp : scc){
			elementsInComp = comp.size();
			int att_s = 0;
			//for(int att_s = 0; att_s < elementsInComp; att_s++){
			while(att_s < elementsInComp){
				target = 0;
				while(target < rootAtm.length && 
						((rootAtm[comp.get(att_s)][target] != 0 && comp.contains(target)) ||
								rootAtm[comp.get(att_s)][target] == 0)){
					target = target + 1;
				}
				if(target != rootAtm.length){
					comp.remove(att_s);
					elementsInComp--;
				}else{
					att_s = att_s + 1;
				}
			}
		}
		//Removes all the empty TESs.
		for(int i = 0; i < scc.size(); i++){
			if(scc.get(i).size() == 0){
				scc.remove(i);
			}
		}

		//Root definition
		if(scc.size() != 1){
			throw new TesTreeException("Multi-root tree");
		}
		component = scc.get(0);
		Collections.sort(component);
		//Creates the sub-atm matrix
		subAtm = new double[component.size()][component.size()];
		si = 0;
		for(int i : component){
			sj = 0;
			for(int j : component){
				subAtm[si][sj] = atm[i][j];
				sj = sj + 1;
			}
			si = si + 1;
		}

		//Copies the attractors that composes the TES
		attractors = new Object[component.size()];
		for(int i = 0; i < component.size(); i++){
			attractors[i] = attractorsSet[component.get(i)];
		}

		tes = new Tes(subAtm, attractors);
		this.root = new TesTreeNode(tes, this.nextNodeId);
		this.nextNodeId = this.nextNodeId + 1;
		createTree(thresholds, 1, this.root);
	}



	public void createTree(double[] thresholds, int level, TesTreeNode parent) throws TesTreeException{

		//Variable definitions
		SCCTarjan sccCalculator = null;
		ArrayList<ArrayList<Integer>> scc = null;
		int target, elementsInComp;
		double[][] atm = null;
		Object[] attractorsSet = null;
		Tes tes = null;
		TesTreeNode child = null;
		Object[] attractors;
		double[][] subAtm;
		int si, sj;

		//Param checking
		if(parent == null){
			throw new NullPointerException("Parent node must be not null in a tree structure");
		}

		//Exit clause
		if(level < thresholds.length){

			//Gets the parent's atm and attractors set.
			atm = removeLinksByDelta(parent.getTes().getAtmMatrix(), thresholds[level]);
			attractorsSet = parent.getTes().getAttractors();
			//TES Computation
			sccCalculator = new SCCTarjan(atm);
			scc = sccCalculator.scc();
			target = 0;
			int att_s;
			for(ArrayList<Integer> comp : scc){
				elementsInComp = comp.size();
				att_s = 0;
				//for(int att_s = 0; att_s < elementsInComp; att_s++){
				while(att_s < elementsInComp){
					target = 0;
					while(target < atm.length && 
							((atm[comp.get(att_s)][target] != 0 && comp.contains(target)) ||
									atm[comp.get(att_s)][target] == 0)){
						target = target + 1;
					}
					if(target != atm.length){
						comp.remove(att_s);
						elementsInComp--;
					}else{
						att_s = att_s + 1;
					}
				}
			}
			//Removes all the empty TESs.
			for(int i = 0; i < scc.size(); i++){
				if(scc.get(i).size() == 0){
					scc.remove(i);
				}
			}	

			//Creates a child for each found TES
			for(ArrayList<Integer> s_scc : scc){

				Collections.sort(s_scc);
				//Creates the sub-atm matrix
				subAtm = new double[s_scc.size()][s_scc.size()];
				si = 0;
				for(int i : s_scc){
					sj = 0;
					for(int j : s_scc){
						subAtm[si][sj] = atm[i][j];
						sj = sj + 1;
					}
					si = si + 1;
				}
				//Copies the attractors that composes the TES
				attractors = new Object[s_scc.size()];
				for(int i = 0; i < s_scc.size(); i++){
					attractors[i] = attractorsSet[s_scc.get(i)];
				}
				//Creates the TES object
				tes = new Tes(subAtm, attractors);
				//Add the child to the parent
				child = new TesTreeNode(tes, this.nextNodeId, parent);
				parent.addChild(child);
				this.nextNodeId = this.nextNodeId + 1;
				createTree(thresholds, level + 1, child);
			}
		}
	}

	/**
	 * This method returns the atm without links based on the delta's value
	 * @param delta
	 * @param atm
	 * @param n : dimension of the matrix
	 * @return the new atm
	 */
	private double[][] removeLinksByDelta(double[][] atm, double threshold){
		for(int line = 0; line < atm.length; line++){
			for(int pillar = 0; pillar < atm.length; pillar++){
				if(atm[line][pillar] <= threshold)
					atm[line][pillar] = 0;
			}
		}
		return atm;
	}

	/**
	 * This method returns the TES tree deepness
	 * @return deepness: The TES tree deepness
	 */
	public int getTreeDeppness(){
		return this.getTreeDeepness(this.root);
	}

	/**
	 * This is a service method for the TES deepness calculating.
	 * @param node: The initial TES tree node.
	 * @return: The TES tree deepness from the specified node.
	 */
	private int getTreeDeepness(TesTreeNode node){
		//Exit conditions
		if(node == null)
			return 0;
		if(node.getChildren() == null)
			return 0;
		if(node.getChildren().size() == 0)
			return 0;
		//Recursion portion
		int newDeepness = 0, childDeepness = 0;
		for(TesTreeNode child : node.getChildren()){
			childDeepness = this.getTreeDeepness(child); 
			if(newDeepness < childDeepness) 
				newDeepness = childDeepness;
		}
		return 1 + newDeepness;
	}

	/**
	 * This recursive method returns the node pointer with the specified id.
	 * @param startingLevel: The research initial level
	 * @param level: The limit level
	 * @param id: The node id to be found
	 * @return: The TesTreeNode if it is found or null if it isn0t found
	 */
	public TesTreeNode getNodeByIdAndLevel(int level, int id){
		return getNodeByIdAndLevel(this.root, 0, level, id);
	}

	/**
	 * This service recursive method returns the node pointer with the specified id.
	 * @param startingNode: The research starting node
	 * @param startingLevel: The research initial level
	 * @param level: The limit level
	 * @param id: The node id to be found
	 * @return: The TesTreeNode if it is found or null if it isn0t found
	 */
	private TesTreeNode getNodeByIdAndLevel(TesTreeNode startingNode, int startingLevel, int limit, int id){

		//Exit cases
		if(startingNode == null)
			return null;
		if(startingLevel <= limit && startingNode.getNodeId() == id){
			return startingNode;
		}

		if(startingLevel == limit && startingNode.getNodeId() != id)
			return null;

		TesTreeNode result;
		//Recursive call in each node child
		for(TesTreeNode node : startingNode.getChildren()){
			result = getNodeByIdAndLevel(node, startingLevel + 1, limit, id);

			//Node found
			if(result != null){
				return result;

			}
		}
		//Node not found
		return null;

	}


	/**
	 * This method adds a new node with the specified id to the tree.
	 * The parent node is specified with the parentId value.
	 * @param nodeId: the new node id
	 * @param level: the node level
	 * @param parrentId: the parent id
	 * @throws TesTreeException: An error occurred during the research
	 */
	public void addNodeManually(int nodeId, int level, int parentId) throws TesTreeException{
		TesTreeNode parent = this.getNodeByIdAndLevel(level - 1, parentId);
		if(parent == null)
			throw new TesTreeException("Node " + parentId + " not found");
		parent.addChild(new TesTreeNode(null, nodeId));
	}


	/**
	 * This method compares the TES tree with the specified one.
	 * @param givenTree: The TES tree to be compare with the caller one
	 * @param max_children_for_complete_test: maximum number of children for a complete test 
	 * (all the permutations are tested)
	 * @param partial_test_probability: probability of the selection of a nodes permutation.
	 * @return: True or false
	 */
	public boolean tesTreeCompare(TesTree givenTree,
			int max_children_for_complete_test, double partial_test_probability){
		return this.tesTreeCompare(givenTree, givenTree.getTreeDeppness() + 1,
				max_children_for_complete_test, partial_test_probability);
	}


	/**
	 * This method compares the TES tree with the specified one until the 'limit' level.
	 * @param givenTree: The TES tree to be compare with the caller one
	 * @param limit: The last trees deepness to be check 
	 * @param max_children_for_complete_test: maximum number of children for a complete test 
	 * (all the permutations are tested)
	 * @param partial_test_probability: probability of the selection of a nodes permutation.
	 * @return: True or false
	 */
	public boolean tesTreeCompare(TesTree givenTree, int limit,
			int max_children_for_complete_test, double partial_test_probability){

		//Check the deepness
		if(this.getTreeDeppness() != givenTree.getTreeDeppness())
			return false;
		else
			return tesTreeCompare(this.root, givenTree.root, 0, limit, 
					max_children_for_complete_test, partial_test_probability);

	}


	/**
	 * This is a recursive service method used in order to compare two TES 
	 * trees with node and givenNode roots.
	 * @param node: The first tree checking starting point
	 * @param givenNode: The second tree checking starting point
	 * @param level: The current trees level (used for the recursion)
	 * @param limit: The maximum deepness to be checked
	 * @param max_children_for_complete_test: maximum number of children for a complete test 
	 * (all the permutations are tested)
	 * @param partial_test_probability: probability of the selection of a nodes permutation.
	 * @return a boolean value
	 */
	private boolean tesTreeCompare(TesTreeNode node, TesTreeNode givenNode, int level, int limit,
			int max_children_for_complete_test, double partial_test_probability){
		//Exit cases
		if(level == limit)
			return true;
		if(node.getChildren().size() != givenNode.getChildren().size())
			return false;
		else if (node.getChildren().size() == 0)
			return true;

		//Recursive case
		boolean isEqual = false;
		int p = 0, i;
		int childrenNumber = node.getChildren().size();
		ArrayList<TesTreeNode> a = givenNode.getChildren();
		TesTreeNode[] b = new TesTreeNode[a.size()];
		for(int j = 0; j < a.size(); j++)
			b[j] = a.get(j);
		ArrayList<TesTreeNode[]> permutations;
		if(b.length <= max_children_for_complete_test)
			permutations = permutation(b, 0, childrenNumber, 1);
		else
		//Gets all the possible child permutations
			permutations = permutation(b, 0, childrenNumber, partial_test_probability);
		System.out.println("Permutations " + permutations.size());
		do{
			TesTreeNode[] A = node.getChildrenAsArray();
			TesTreeNode[] B = permutations.get(p);
			i = 0;
			//Recursive calls for each couple of nodes.
			while(i < A.length && tesTreeCompare(A[i], B[i], level + 1, limit,
					max_children_for_complete_test, partial_test_probability))
				i++;
			//The nodes are equal in each couple.
			if(i == childrenNumber)
				isEqual = true;
			p++;
		}while(!isEqual && p < factorial(childrenNumber));
		return isEqual;
	}


	/**
	 * This method is a service method used in order to swap two elements in an array
	 * @param array: The original array
	 * @param from: first item index
	 * @param to: second item index
	 */
	private static void swap(Object[] array, int from, int to){
		Object obj = array[from];
		array[from] = array[to];
		array[to] = obj;
	}


	/**
	 * This method computes all the possible permutation of a given TesTreeNode array.
	 * @param array: The original TesTreeNode array
	 * @param start: Initial position
	 * @param end: Final position
	 * @param permProbability: the probability of selecting a permutation branch
	 * @return an array list which contains a TesTreeNode permutation of the given array in each position.
	 */
	private static ArrayList<TesTreeNode[]> permutation(TesTreeNode[] array, int start, int end,
			double premProbability){
		ArrayList<TesTreeNode[]> permutations = new ArrayList<TesTreeNode[]>();
		TesTree.permutation(array, 0, array.length, permutations, premProbability);
		return permutations;	
	}


	/**
	 * This service method computes all the possible permutation of a given TesTreeNode array.
	 * @param array: The original TesTreeNode array
	 * @param start: Initial position
	 * @param end: Final position
	 * @param permutations: The array list with all the permutations
	 * @param permProbability: the probability of selecting a permutation branch
	 * @return an array list which contains a TesTreeNode permutation of the given array in each position.
	 */
	private static void permutation(TesTreeNode[] array, int start, int end, 
			ArrayList<TesTreeNode[]> permutations, double permProbability){
		int j;
		//Exit case
		if(start == end){    
			permutations.add(array.clone());
		}else{
			//Recursive case
			for(j = start; j < end; j++){
				if(UtilityRandom.randomBooleanChoice(permProbability)){
					swap(array, start, j);            
					permutation(array, start + 1, end, permutations, permProbability);  
					swap(array, start, j); 
				}
			}                                        
		}
	}

	/**
	 * This method calculates the factorial of the given number.
	 * @param n: The number used in order to perform the factorial. 
	 * This value must be greater or equal than 0.
	 * @return: The factorial of the given number.
	 */
	private int factorial(int n){
		return (n == 0 ? 1 : n * factorial(n-1));
	}

	/**
	 * This method returns the edges of the tree
	 * @return An array list containing the edges as couples
	 */
	public ArrayList<String[]> getEdges(){
		ArrayList<String[]> edges = new ArrayList<String[]>();
		getEdges(this.root, edges);
		return edges;

	}

	/**
	 * This method returns the edges of the tree
	 * @return An array list containing the edges as couples
	 */
	private void getEdges(TesTreeNode node, ArrayList<String[]> edges){
		if(node != null){
			String[] edge;
			for(TesTreeNode child : node.getChildren()){
				edge = new String[2];
				edge[0] = String.valueOf(node.getNodeId());
				edge[1] = String.valueOf(child.getNodeId());
				edges.add(edge);
				getEdges(child, edges);
			}
		}
	}

	/**
	 * This method returns the list of nodes in the tree
	 * @return
	 */
	public ArrayList<Integer> getNodes(){
		ArrayList<Integer> visited = new ArrayList<Integer>();
		getNodes(this.root, visited);
		return visited;
	}

	/**
	 * Private method for getting the nodes in the tree 
	 * @param node
	 * @param visited
	 */
	private void getNodes(TesTreeNode node, ArrayList<Integer> visited){
		visited.add(node.getNodeId());
		for(TesTreeNode child : node.getChildren()){
			getNodes(child, visited);
		}
	}

	/**
	 * This method returns the number of nodes in the given tree or subtree, given the root.
	 * The output will not count the root.
	 */
	public static int getNumberOfNodes(TesTreeNode node){
		if(node.getChildren().size() == 0){
			return 0;
		}else{
			int count = 0;
			ArrayList<TesTreeNode> children = node.getChildren();
			for(TesTreeNode child: children){
				count = count + 1 + getNumberOfNodes(child);
			}
			return count;
		}
	}

	/**
	 * This method compares the TES tree with the specified one.
	 * @param givenTree: The TES tree to be compare with the caller one
	 * @return: True or false
	 */
	public int tesTreeDistance(TesTree givenTree){
		return tesTreeDistance(this.root, givenTree.root);
	}

	/**
	 * This is a recursive service method used in order to compare two TES 
	 * trees with node and givenNode roots.
	 * @param node: The first tree checking starting point
	 * @param givenNode: The second tree checking starting point
	 * @param level: The current trees level (used for the recursion)
	 * @param limit: The maximum deepness to be checked
	 * @return a boolean value
	 */
	private int tesTreeDistance(TesTreeNode node, TesTreeNode givenNode){

		//Both the nodes don't have any children
		if(node.getChildren().size() == 0 && givenNode.getChildren().size() == 0)
			return 0;
		//Only the node in the in the given tree has children.
		if(node.getChildren().size() == 0)
			return getNumberOfNodes(givenNode);
		//Only the network-tree node has children.
		if(givenNode.getChildren().size() == 0)
			return getNumberOfNodes(node);

		//Both the nodes have some children.
		int i = 0;
		ArrayList<TesTreeNode> networkNodeChildren = node.getChildren();
		ArrayList<TesTreeNode[]> permutations = permutation(givenNode.getChildrenAsArray(), 0, givenNode.getChildren().size(), 0.5);

		int distance = -1;
		int localDistance;
		for(TesTreeNode[] nodesPermutation : permutations){

			//Counter initialization
			i = 0;
			localDistance = 0;
			while(i < networkNodeChildren.size() && i < nodesPermutation.length){
				localDistance = localDistance + tesTreeDistance(networkNodeChildren.get(i), nodesPermutation[i]);
				i++;
			}
			if(i < networkNodeChildren.size()){
				for(; i < networkNodeChildren.size(); i++){
					localDistance = localDistance + 1 + getNumberOfNodes(networkNodeChildren.get(i));
				}
			}else{
				for(; i < nodesPermutation.length; i++){
					localDistance = localDistance + 1 + getNumberOfNodes(nodesPermutation[i]);
				}
			}
			distance = (distance == -1 ? localDistance : Math.min(distance, localDistance));
		}

		return distance;
	}

	public int tesTreeHistogramComparison(TesTree givenTree){
		ArrayList<TesTreeNode> currentRoot = new ArrayList<TesTreeNode>();
		ArrayList<TesTreeNode> givenRoot = new ArrayList<TesTreeNode>();

		currentRoot.add(this.root);
		givenRoot.add(givenTree.root);

		return this.tesTreeHistogramComparison(currentRoot, givenRoot);
	}

	private int tesTreeHistogramComparison(ArrayList<TesTreeNode> nodes, ArrayList<TesTreeNode> givenNodes){

		if(nodes.size() == 0 && givenNodes.size() == 0){
			return 0;
		}


		HashMap<Integer, Integer> treeDistribution = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> givenTreeDistribution = new HashMap<Integer, Integer>();
		ArrayList<TesTreeNode> createdTreeLevelChildren = new ArrayList<TesTreeNode>();
		ArrayList<TesTreeNode> givenTreeLevelChildren = new ArrayList<TesTreeNode>();

		int size;
		//Gets the distribution for the current level for the created tree
		for(TesTreeNode currentNode : nodes){
			size = currentNode.getChildren().size();
			createdTreeLevelChildren.addAll(currentNode.getChildren());
			if(treeDistribution.containsKey(size)){
				treeDistribution.put(size, treeDistribution.get(size) + 1);
			}else{
				treeDistribution.put(size, 1); 
			}
		}

		//Gets the distribution for the current level for the given tree
		for(TesTreeNode currentNode : givenNodes){
			size = currentNode.getChildren().size();
			givenTreeLevelChildren.addAll(currentNode.getChildren());
			if(givenTreeDistribution.containsKey(size)){
				givenTreeDistribution.put(size, givenTreeDistribution.get(size) + 1);
			}else{
				givenTreeDistribution.put(size, 1); 
			}
		}

		//Calculates the histogram distance
		return histogramDistance(treeDistribution, givenTreeDistribution) +
				tesTreeHistogramComparison(createdTreeLevelChildren, givenTreeLevelChildren);



	}

	private int histogramDistance(HashMap<Integer, Integer> dist1, HashMap<Integer, Integer> dist2){
		int distance = 0;
		HashSet<Integer> values = new HashSet<Integer>();
		values.addAll(dist1.keySet());
		values.addAll(dist2.keySet());
		for(Integer value : values){
			if(dist1.containsKey(value) && dist2.containsKey(value)){
				distance = distance + Math.abs(dist1.get(value) - dist2.get(value));
			}else if(dist1.containsKey(value)){
				distance = distance + dist1.get(value);
			}else{
				distance = distance + dist2.get(value);
			}
		}

		return distance;	
	}

	/*
	public ArrayList<TesTreeNode> getTreeNodes(){
		ArrayList<TesTreeNode> nodes = new ArrayList<TesTreeNode>();
		getTreeNodes(this.root, nodes);
		return nodes;
	}

	public void getTreeNodes(TesTreeNode node, ArrayList<TesTreeNode> nodes){

		nodes.add(node);

		ArrayList<TesTreeNode> children = node.getChildren();
		if(children.size() != 0){
			for(TesTreeNode child : children){
				getTreeNodes(child, nodes);
			}
		}


	}

	 */

	/**
	 * This method returns the number of leaf nodes in the tree
	 * @return
	 */
	public int getLeafsNodesNumber(){
		return getLeafsNodesNumber(this.root);
	}

	/**
	 * Service method for the leaf nodes calculus.
	 * @param node: Node for starting
	 * @return The number of leaf nodes in the subtree
	 */
	private int getLeafsNodesNumber(TesTreeNode node){
		if(node.getChildren().size() == 0)
			return 1;
		else{
			int leafs = 0;
			for(TesTreeNode child : node.getChildren())
				leafs = leafs + getLeafsNodesNumber(child);
			return leafs;
		}
	}


	public void print(){
		print(this.root);
	}

	private void print(TesTreeNode node){
		System.out.println(node.getNodeId() + " parent: " + (node.getParent() == null ? "Root" :node.getParent().getNodeId()));
		for(TesTreeNode child : node.getChildren())
			print(child);
	}

}

