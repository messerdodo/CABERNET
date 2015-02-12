/**
 * This class contains all the constants used in CABERNET app.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.CABERNET;

public class CABERNETConstants {

	/**
	 * Yes value
	 */
	public static final String YES = "yes";
	
	/**
	 * No value
	 */
	public static final String NO ="no";   
	
	/**
	 * Network view key. It used in order to specify if create Cytoscape view for the network.
	 */
	public static final String NETWORK_VIEW = "network-view";
	
	/**
	 * Attractors network view key. 
	 * It used in order to specify if create Cytoscape view for the attractors of the network.
	 */
	public static final String ATTRACTORS_NETWORK_VIEW = "attractors-network-view";
	
	/**
	 * This key is used in order to specify the network creation task type.
	 * Possible values are new, open-and-edit, open, from-cytoscape
	 */
	public static final String NETWORK_CREATION = "network-creation";
	
	/**
	 * This key is used on order to specify the network creation by features.
	 * In other words it means completely new network.
	 */
	public static final String NEW = "new";
	
	/**
	 * This key is used on order to specify the network creation by importing from file.
	 * In other words it means read network as is.
	 */
	public static final String OPEN = "open";
	
	/**
	 * This key is used on order to specify the network creation by importing from file and editing.
	 * In other words it means read network and edit it.
	 */
	public static final String EDIT = "edit";
	
	/**
	 * This key is used on order to specify the network creation by importing from cytoscape and editing.
	 * In other words it means read network from the Cytoscape view and edit it.
	 */
	public static final String CYTOSCAPE_EDIT = "cytoscape-edit";
	
	/**
	 * ATM performing key. It is used in order to specify if the ATM computation is required.
	 */
	public static final String ATM_COMPUTATION = "atm-computation";
	
	/**
	 * Tree matching key. It is used in order to require the matching of the network with the given tree.
	 */
	public static final String TREE_MATCHING = "tree-matching";
	
	/**
	 * Matching type key. It is used in order to specify the matching type (E.g. perfect match, min distance).
	 */
	public static final String MATCHING_TYPE = "matching-type";
	
	/**
	 * Perfect matching value for MATCHING_TYPE key.
	 */
	public static final String PERFECT_MATCH = "perfect-match";
	
	/**
	 * Min distance matching value for MATCHING_TYPE key.
	 */
	public static final String MIN_DISTANCE = "min-distance";
	
	/**
	 * Histogram distance matching value for MATCHING_TYPE key.
	 */
	public static final String HISTOGRAM_DISTANCE = "histogram-distance";
	
	/**
	 * Matching threshold key.
	 */
	public static final String MATCHING_THRESHOLD = "matching-threshold";
}
