/**
 * 
 * SCC calculator.
 * Method taken from http://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm
 * 
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca
 * @year 2013
 * 
 */
package it.unimib.disco.bimib.Utility;

import java.util.*;

//optimized version of http://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm
public class SCCTarjan {

	private ArrayList<ArrayList<Integer>> graph;
	private boolean[] visited;
	private Stack<Integer> stack;
	private int time;
	private int[] lowlink;
	private ArrayList<ArrayList<Integer>> components;

	/**
	 * Generic constructor
	 * Creates the graph in the correct format from the
	 * given adjacency matrix
	 * @param adjacencyMatrix: a n by n adjacency matrix.
	 */
	public SCCTarjan(double[][] adjacencyMatrix){
		int n = adjacencyMatrix.length;
		this.graph = new ArrayList<ArrayList<Integer>>(n);
		for (int i = 0; i < n; i++)
			this.graph.add(new ArrayList<Integer>());

		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(adjacencyMatrix[i][j] > 0.0){
					this.graph.get(i).add(j);
				}
			}
		}
		System.out.println(this.graph);
	}

	public ArrayList<ArrayList<Integer>> scc() {
		int n = graph.size();
		visited = new boolean[n];
		stack = new Stack<Integer>();
		time = 0;
		lowlink = new int[n];
		components = new ArrayList<ArrayList<Integer>>();

		for (int u = 0; u < n; u++)
			if (!visited[u])
				dfs(u);

		return components;
	}

	void dfs(int u) {
		lowlink[u] = time++;
		visited[u] = true;
		stack.add(u);
		boolean isComponentRoot = true;

		for (int v : graph.get(u)) {
			if (!visited[v])
				dfs(v);
			if (lowlink[u] > lowlink[v]) {
				lowlink[u] = lowlink[v];
				isComponentRoot = false;
			}
		}

		if (isComponentRoot) {
			ArrayList<Integer> component = new ArrayList<Integer>();
			while (true) {
				int x = stack.pop();
				component.add(x);
				lowlink[x] = Integer.MAX_VALUE;
				if (x == u)
					break;
			}
			components.add(component);
		}
	}



	// Usage example
	public static void main(String[] args) {

		double[][] adj_mat = new double[4][4];
		adj_mat[0][0] = 0.0;
		adj_mat[0][1] = 1.0;
		adj_mat[0][2] = 1.0;
		adj_mat[0][3] = 1.0;
		adj_mat[1][0] = 0.0;
		adj_mat[1][1] = 0.0;
		adj_mat[1][2] = 1.0;
		adj_mat[1][3] = 0.0;
		adj_mat[2][0] = 0.0;
		adj_mat[2][1] = 1.0;
		adj_mat[2][2] = 0.0;
		adj_mat[2][3] = 0.0;
		adj_mat[3][0] = 0.0;
		adj_mat[3][1] = 0.0;
		adj_mat[3][2] = 0.0;
		adj_mat[3][3] = 0.0;

		ArrayList<ArrayList<Integer>> components = new SCCTarjan(adj_mat).scc();
		System.out.println("SCC graph " + components);

		//Each position of the array contains the number of the scc of the element.
		int[] assignments = new int[adj_mat.length];
		for(int i  = 0; i < components.size(); i++){
			for(Integer att : components.get(i))
				assignments[att] = i;
		}
		System.out.println(Arrays.toString(assignments));

		//All the scc are teses at the beginning.
		int[] temporaryTesSet = new int[components.size()];
		for(int i = 0; i < components.size(); i++)
			temporaryTesSet[i] = 1;
		int j = 0;
		System.out.println("TES " + Arrays.toString(temporaryTesSet));
		//Removes the scc that are not tes.
		for(int i = 0; i < adj_mat.length; i++){
			j = 0;
			while((j < adj_mat.length) && (adj_mat[i][j] == 0 || (!((adj_mat[i][j] >= 0) && (assignments[i] != assignments[j])))) ){
				j = j + 1;
			}
			if(j < adj_mat.length){
				temporaryTesSet[assignments[i]] = 0;
			}
		}
		System.out.println("TES " + Arrays.toString(temporaryTesSet));
		
		
	}
}