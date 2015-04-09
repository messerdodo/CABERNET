/**
 * Most frequently found tree task class.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.Task;

//System imports
import java.util.ArrayList;





import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import javax.swing.JOptionPane;

//CABERNET imports
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Middleware.NetworkManagment;
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Tes.TesManager;
import it.unimib.disco.bimib.Tes.TesTree;




//Cytoscape imports
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.model.CyNetwork;

public class FindTreeTask extends AbstractTask{

	private int depth;
	private int cutoff;
	private AtmManager atmManager;
	private SamplingManager samplingManager;
	private ArrayList<TesTree> foundTrees;
	private NetworkManagment cytoscapeBridge;
	private CyNetwork parent;
	private int max_children_for_complete_test;
	private double partial_test_probability;

	public FindTreeTask(int depth, int cutoff, AtmManager atmManager, 
			SamplingManager samplingManager,
			NetworkManagment cytoscapeBridge, CyNetwork parent,
			int max_children_for_complete_test, double partial_test_probability){
		this.depth = depth;
		this.cutoff = cutoff;
		this.samplingManager = samplingManager;
		this.atmManager = atmManager;
		this.cytoscapeBridge = cytoscapeBridge;
		this.parent = parent;
		this.max_children_for_complete_test = max_children_for_complete_test;
		this.partial_test_probability = partial_test_probability;
	}

	@Override
	public void run(final TaskMonitor taskMonitor) throws Exception {
		taskMonitor.setStatusMessage("Processing");
	
		try{
		foundTrees = TimeLimitedCodeBlock.runWithTimeout(new Callable<ArrayList<TesTree>>(){

			@Override
			public ArrayList<TesTree> call()
					throws Exception {

				TesManager tesManager = new TesManager(atmManager, samplingManager, max_children_for_complete_test,
						partial_test_probability);

				return tesManager.getRepresentativeTrees(depth, cutoff, true);
			}
		},  5, TimeUnit.MINUTES);



		if(foundTrees.size() == 0)
			throw new Exception("No representative tree found.");
		for(int i = 0; i < this.foundTrees.size(); i++){
			cytoscapeBridge.createTreesGraph(this.foundTrees.get(i), "Tree_d" + this.depth + "_n" + i , this.parent);
		}
		
		}catch(TimeoutException to){
			JOptionPane.showMessageDialog(null, "Time Out!", "Error", JOptionPane.ERROR_MESSAGE, null);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
		}
	}
}
