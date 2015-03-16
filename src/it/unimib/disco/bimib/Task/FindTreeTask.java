/**
 * Most frequently found tree task class.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.Task;

//System imports
import java.util.ArrayList;




//CABERNET imports
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Middleware.NetworkManagment;
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Tes.TesManager;
import it.unimib.disco.bimib.Tes.TesTree;




import org.cytoscape.model.CyNetwork;
//Cytoscape imports
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class FindTreeTask extends AbstractTask{

	private int depth;
	private AtmManager atmManager;
	private SamplingManager samplingManager;
	private ArrayList<TesTree> foundTrees;
	private NetworkManagment cytoscapeBridge;
	private CyNetwork parent;
	
	public FindTreeTask(int depth, AtmManager atmManager, SamplingManager samplingManager,
			NetworkManagment cytoscapeBridge, CyNetwork parent){
		this.depth = depth;
		this.samplingManager = samplingManager;
		this.atmManager = atmManager;
		this.cytoscapeBridge = cytoscapeBridge;
		this.parent = parent;
	}
	
	@Override
	public void run(final TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setStatusMessage("Processing");
		TesManager tesManager = new TesManager(this.atmManager, this.samplingManager);
		this.foundTrees = tesManager.getMostFrequentTrees(this.depth);
		if(foundTrees.size() == 0)
			throw new Exception("No representative tree found.");
		for(int i = 0; i < this.foundTrees.size(); i++){
			cytoscapeBridge.createTreesGraph(this.foundTrees.get(i), "Tree_d" + this.depth + "_n" + i , this.parent);
		}
	}
}
