/** 
 * This class is the representation of a boolean function with only and(s) or only or(s).
 * 
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @author Giorgia Previtali (g.previtali6@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca
 * @year 2013
 * 
 */

package it.unimib.disco.bimib.IO;

//System imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//GRNSim imports
import it.unimib.disco.bimib.Atms.Atm;
import it.unimib.disco.bimib.Exceptions.*;
import it.unimib.disco.bimib.Networks.*;
import it.unimib.disco.bimib.Sampling.AttractorsFinder;
import it.unimib.disco.bimib.Sampling.BruteForceSampling;
import it.unimib.disco.bimib.Utility.OutputConstants;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;


public class Output {


	public Output() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method creates the specified folder.
	 * @param path: The path followed by the new folder name
	 */
	public static void createFolder(String path){
		new File(path).mkdir();
	}

	/**
	 * This method creates a SIF file for the specified graph.
	 * @param network: The graph to be written
	 * @param fileName: The name of the output file
	 * @throws IOException: Some errors occurred during the writing operations
	 */
	public static void createSIFFile(GeneRegulatoryNetwork network, String fileName) throws IOException{

		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null");
		if(network == null)
			throw new NullPointerException("The network must not be null");

		//Opens the output streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);
		//Gets the edges
		List<int[]> graphEdges = network.getEdges();
		ArrayList<String> nodesNames = network.getNodesNames();

		//Writes the sif file
		for(int[] edge : graphEdges){
			//Each rows likes "n	_connected	m" where n and m are node numbers 
			printer.println(nodesNames.get(edge[0]) + "\tDirectedEdge\t" + nodesNames.get(edge[1]));
			printer.flush();
		}
		//Closes the streams
		printer.close();
		writer.close();
	}

	/**
	 * This method writes the specified graph in a GML file
	 * @param network: The graph to be written
	 * @param fileName: The output file name
	 * @throws IOException: An error occurred during the writing actions
	 * @throws NullPointerException: Parameters must not be null
	 */
	public static void createGMLFile(GeneRegulatoryNetwork network, String fileName) throws IOException, NullPointerException{
		String section = ""; //File section to be written

		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null");
		if(network == null)
			throw new NullPointerException("The network must not be null");

		//Gets edges and nodes
		int[] nodes = network.getNodes();
		List<int[]> edges = network.getEdges();

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);

		//Writes the file header
		printer.println("graph [");
		printer.flush();

		//Writes the nodes
		for(int node : nodes){
			section = "\tnode [\n";
			section += "\t\tid " + node + "\n";
			section += "\t\tlabel " + "node_" + node + "\n";
			section += "\t]";
			printer.println(section);
			printer.flush();
		}

		//Writes the edges
		for(int[] edge : edges){
			section = "\tedge [\n";
			section += "\t\tsource " + edge[0] + "\n";
			section += "\t\ttarget " + edge[1] + "\n";
			section += "\t]";
			printer.println(section);
			printer.flush();
		}

		//Writes the footer
		printer.println("]");
		printer.flush();

		//closes the streams
		printer.close();
		writer.close();

	}

	/**
	 * This method creates a GRML file
	 * @param network
	 * @param fileName
	 * @throws IOException
	 */
	public static void createGRNMLFile(GeneRegulatoryNetwork network, String fileName) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null");
		if(network == null)
			throw new NullPointerException("The network must not be null");

		//Gets the GRNML file content
		String grnmlContent = network.toGRNML();

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);
		//Writes the file
		printer.write(grnmlContent);
		printer.flush();

		//Closes the streams
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the ATM in TSV format
	 * @param atm: The ATM to be saved
	 * @param fileName: The name of the file to be saved
	 * @throws IOException: I/O error
	 */
	public static void createATMFile(Atm atm, String fileName) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null");
		if(atm == null)
			throw new NullPointerException("The ATM must not be null");

		//Gets the ATM in tsv format
		String atmString = atm.getCsvAtm();

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);
		//Writes the file
		printer.write(atmString);
		printer.flush();

		//Closes the streams
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the states of the attractors in a csv file.
	 * Each line corresponds to an attractor. Each column corresponds to a state of the i-th attractor.
	 * @param attractorFinder: An attractor finder object
	 * @param fileName: The name of the file to be saved
	 * @throws IOException: An error occurred during the saving time
	 * @throws ParamDefinitionException
	 * @throws NotExistingNodeException
	 * @throws InputTypeExceptions
	 */
	public static void saveAttractorsFile(AttractorsFinder attractorFinder, String fileName) throws IOException, ParamDefinitionException, NotExistingNodeException, InputTypeException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null");
		if(attractorFinder == null)
			throw new NullPointerException("The attractor finder must not be null");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);
		//Gets the attractors: a new line for each attractor
		for(Object attractor : attractorFinder.getAttractors()){
			//Gets the states in the attractor
			for(Object state : attractorFinder.getStatesInAttractor(attractor)){
				printer.print(state.toString() + ";");
				printer.flush();
			}
			printer.println("");
			printer.flush();
		}

		printer.close();
		writer.close();	
	}

	/**
	 * This method saves the synthesis file
	 * @param results: a property object with all the statistics for the network
	 * @param fileName: The name of the file to be saved
	 * @throws Exception: This exception is thrown if the property object does not contain all the required statistics.
	 */
	public static void createSynthesisFile(Properties results, String fileName) throws Exception{

		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null");
		if(results == null)
			throw new NullPointerException("The StoredResults object must not be null");

		//Network ID
		if(!results.containsKey(OutputConstants.SIMULATION_ID))
			throw new Exception(OutputConstants.SIMULATION_ID + " key must be in the results object");
		//Clustering coefficient
		if(!results.containsKey(OutputConstants.CLUSTERING_COEFFICIENT))
			throw new Exception(OutputConstants.CLUSTERING_COEFFICIENT + " key must be in the results object");
		//Average bias value
		if(!results.containsKey(OutputConstants.AVERAGE_BIAS))
			throw new Exception(OutputConstants.AVERAGE_BIAS + " key must be in the results object");
		//Average path length
		if(!results.containsKey(OutputConstants.AVERAGE_PATH_LENGTH))
			throw new Exception(OutputConstants.AVERAGE_PATH_LENGTH + " key must be in the results object");
		//Network diameter
		if(!results.containsKey(OutputConstants.NETWORK_DIAMETER))
			throw new Exception(OutputConstants.NETWORK_DIAMETER + " key must be in the results object");
		//Number of attractors in the network
		if(!results.containsKey(OutputConstants.ATTRACTORS_NUMBER))
			throw new Exception(OutputConstants.ATTRACTORS_NUMBER + " key must be in the results object");
		//Average attractors lengts
		if(!results.containsKey(OutputConstants.ATTRACTORS_LENGTH))
			throw new Exception(OutputConstants.ATTRACTORS_LENGTH + " key must be in the results object");
		//Tree distance
		if(!results.containsKey(OutputConstants.TREE_DISTANCE))
			throw new Exception(OutputConstants.TREE_DISTANCE + " key must be in the results object");
		//Thresholds
		if(!results.containsKey(OutputConstants.THRESHOLDS))
			throw new Exception(OutputConstants.THRESHOLDS + " key must be in the results object");
		//Average attractors lengts
		if(!results.containsKey(OutputConstants.ATTRACTORS_LENGTH))
			throw new Exception(OutputConstants.ATTRACTORS_LENGTH + " key must be in the results object");
		//Not found attractors
		if(!results.containsKey(OutputConstants.NOT_FOUND_ATTRACTORS))
			throw new Exception(OutputConstants.NOT_FOUND_ATTRACTORS + " key must be in the results object");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);	

		//Header
		printer.print("Simulation_id,");
		printer.flush();
		printer.print("Clustering_coefficient,");
		printer.flush();
		printer.print("Average_bias,");
		printer.flush();
		printer.print("Average_path_length,");
		printer.flush();
		printer.print("Network_diameter,");
		printer.flush();
		printer.print("Attractors_number,");
		printer.flush();
		printer.print("Average_attractor_lengths,");
		printer.flush();
		printer.print("Tree distance,");
		printer.flush();
		printer.print("Thresholds\n");
		printer.flush();

		
		//Writes the simulation id
		printer.print(results.get(OutputConstants.SIMULATION_ID) + ",");
		printer.flush();

		//Writes the simulation id
		printer.print(results.get(OutputConstants.CLUSTERING_COEFFICIENT) + ",");
		printer.flush();

		//Writes the simulation id
		printer.print(results.get(OutputConstants.AVERAGE_BIAS) + ",");
		printer.flush();

		//Writes the average path length
		printer.print(results.get(OutputConstants.AVERAGE_PATH_LENGTH) + ",");
		printer.flush();

		//Writes the network diameter
		printer.print(results.get(OutputConstants.NETWORK_DIAMETER) + ",");
		printer.flush();

		//Writes the number of attractors
		printer.print(results.get(OutputConstants.ATTRACTORS_NUMBER) + ",");
		printer.flush();

		//Writes the attractors average length
		printer.print(results.get(OutputConstants.ATTRACTORS_LENGTH) + ",");
		printer.flush();	

		//Writes the tree distance
		printer.print(results.get(OutputConstants.TREE_DISTANCE) + ",");
		printer.flush();

		//Writes the thresholds array
		printer.print(results.get(OutputConstants.THRESHOLDS).toString().replace(',', ';') + ",");
		printer.flush();

		//Writes the number of not found attractors
		//printer.print(results.get(OutputConstants.NOT_FOUND_ATTRACTORS));
		//printer.flush();	

		printer.close();
		writer.close();
	}

	/**
	 * This method creates the thresholds and distance file.
	 * It is used only when there's a tree matching (completely o partial) task
	 * @param fileName: name/path of the thresholds-distance file
	 * @param thresholds: double array of thresholds
	 * @param distance: The distance between the created tree and the given tree. 
	 * @throws IOException 
	 */
	public static void createThresholdsFile(String fileName, double[] thresholds, int distance) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the thresholds file creation");
		if(thresholds == null)
			throw new NullPointerException("The thresholds array must be not null.");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);	

		//Writes the thresholds
		printer.print("Thresholds\t" + Arrays.toString(thresholds) + "\n");
		printer.flush();
		//Writes the tree distance
		printer.print("Tree distance\t" + distance);
		printer.flush();

		printer.close();
		writer.close();
	}

	/**
	 * This method saves the tuple (state, attractor, position) in a csv file.
	 * The first row of the file specifies the type of the attractor finder used in the simulation.
	 * @param fileName: The name of the file
	 * @param finder: The attractor finder object used
	 * @throws IOException
	 */
	public static void saveStatesAttractorsFile(String fileName, AttractorsFinder finder) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the states-attractors file");
		if(finder == null)
			throw new NullPointerException("The AttracorsFinder object must be not null for the states-attractors file");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);

		//Attractor finder type.
		if(finder instanceof BruteForceSampling)
			printer.println(SimulationFeaturesConstants.BRUTE_FORCE);
		else
			printer.println(SimulationFeaturesConstants.PARTIAL_SAMPLING);
		printer.flush();

		HashMap<String, String> statesAttractors = finder.getStatesAttractorsCouples();
		HashMap<String, Integer> statesPositions = finder.getStatesPositionsCouples();
		//Writes the tuple as state,attractor,position
		for(String state : statesAttractors.keySet()){
			printer.print(state + ",");
			printer.print(statesAttractors.get(state) + ",");
			printer.println(statesPositions.get(state));
			printer.flush();
		}

		//Closes the stream
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the avalanches distribution in a csv file. 
	 * The first line of the file is the header.
	 * @param fileName: The name of the file
	 * @param dist: avalanches distribution hash-map
	 * @throws IOException
	 */
	public static void saveAvalachesDistribution(String fileName, HashMap<Integer, Integer> distribution) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the avalanches distribution file");
		if(distribution == null)
			throw new NullPointerException("The avalanches distribution object must be not null for saving the file");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);

		//Header
		printer.println("Avalanche dimension,Frequency");
		printer.flush();

		SortedMap<Integer, Integer> sortedDistribution = new TreeMap<Integer, Integer>(distribution);

		//Writes the frequency for each avalanche dimension
		for(Integer avalancheDim : sortedDistribution.keySet()){
			printer.println(avalancheDim + "," + sortedDistribution.get(avalancheDim) );
			printer.flush();
		}

		//Closes the stream
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the avalanches in a csv file. 
	 * The first line of the file is the header.
	 * @param fileName: The name of the file
	 * @param avalanches: networks-avalanches hash-map
	 * @throws Exception 
	 */
	public static void saveAvalaches(String fileName, HashMap<String, ArrayList<Integer>> avalanches) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the avalanches distribution file");
		if(avalanches == null)
			throw new NullPointerException("The avalanches object must be not null for saving the file");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);

		for(String networkId : avalanches.keySet()){
			printer.print(networkId);
			printer.flush();
			for(Integer ava : avalanches.get(networkId)){
				printer.print("," + ava);
				printer.flush();
			}
			printer.println();
			printer.flush();
		}

		//Closes the stream
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the sensitivity in a csv file. 
	 * The first line of the file is the header.
	 * @param fileName: The name of the file
	 * @param dist: avalanches distribution hash-map
	 * @throws Exception 
	 */
	public static void saveSensitivity(String fileName, ArrayList<String> genesNames, int[] sensitivity) throws Exception{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the sensitivity file");
		if(genesNames == null)
			throw new NullPointerException("The names of the genes must be not null for saving the file");
		if(sensitivity == null)
			throw new NullPointerException("The sensitivity must be not null for saving the file");
		if(genesNames.size() != sensitivity.length)
			throw new Exception("The sensitivity and the genes names arrays must be the same dimension.");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);

		//Header
		printer.println("Gene name,Sensitivity");
		printer.flush();

		//Writes the sensitivity for each gene
		for(int i = 0; i < genesNames.size(); i++){
			printer.println(genesNames.get(i) + "," + sensitivity[i]);
			printer.flush();
		}

		//Closes the stream
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the sensitivity in a csv file. 
	 * The first line of the file is the header.
	 * @param fileName: The name of the file
	 * @param sensitivity: networks-sensitivities hash-map
	 * @throws Exception 
	 */
	public static void saveSensitivity(String fileName, ArrayList<String> genesNames, HashMap<String, int[]>sensitivity) throws Exception{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the sensitivity file");
		if(genesNames == null)
			throw new NullPointerException("The names of the genes must be not null for saving the file");
		if(sensitivity == null)
			throw new NullPointerException("The sensitivity must be not null for saving the file");

		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);

		//Header
		printer.print("NETWORK_ID");
		printer.flush();

		for(String gene_name : genesNames){
			printer.print("," + gene_name);
			printer.flush();
		}
		printer.println();

		for(String networkId : sensitivity.keySet()){
			printer.print(networkId);
			printer.flush();
			//Writes the sensitivity for each gene
			for(Integer sen : sensitivity.get(networkId)){
				printer.print("," + sen);
				printer.flush();
			}
			printer.println();
			printer.flush();
		}

		//Closes the stream
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the attractors lengths in a CSV file. 
	 * @param fileName: the file name
	 * @param lengths: The integer array list object with the lengths
	 * @throws IOException
	 */
	public static void saveAttractorsLengths(String fileName, HashMap<String,ArrayList<Integer>> lengths) throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the attractors lengths file.");
		if(lengths == null)
			throw new NullPointerException("The lengths object must be not null for the attractors lengths file.");
		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);
		for(String network_id : lengths.keySet()){
			printer.print(network_id + ",");
			printer.flush();
			for(Integer observation : lengths.get(network_id)){
				printer.print(observation + ",");
				printer.flush();
			}
			//New line
			printer.println("");
		}
		//Closes the stream
		printer.close();
		writer.close();
	}

	/**
	 * This method saves the basin of attraction dimensions in a CSV file.
	 * @param fileName: the name of the file
	 * @param dimensions: The integer array with the basin of attraction dimensions
	 * @throws IOException
	 */
	public static void saveBasinOfAttractionFile(String fileName, HashMap<String, ArrayList<Integer>> dimensions) 
			throws IOException{
		//Checks the param values
		if(fileName == null)
			throw new NullPointerException("The file name must not be null for the basin of attraction dimension file.");
		if(dimensions == null)
			throw new NullPointerException("The dimensions object must be not null for the basin of attraction dimensions file.");
		//Defines the writer streams
		FileWriter writer = new FileWriter(fileName);
		PrintWriter printer = new PrintWriter(writer);
		for(String network_id : dimensions.keySet()){
			printer.print(network_id + ",");
			printer.flush();
			for(Integer observation : dimensions.get(network_id)){
				printer.print(observation + ",");
				printer.flush();
			}
			//New line
			printer.println("");
		}
		//Closes the stream
		printer.close();
		writer.close();
	}
}
