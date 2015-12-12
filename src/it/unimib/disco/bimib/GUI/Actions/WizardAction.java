/**
 * WizardAction class.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.GUI.Actions;

//CABERNET imports
import it.unimib.disco.bimib.CABERNET.CABERNETConstants;
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
import it.unimib.disco.bimib.GUI.Wizard;
import it.unimib.disco.bimib.Task.NetworkCreation;
import it.unimib.disco.bimib.Task.NetworkEditingFromCytoscape;
import it.unimib.disco.bimib.Task.NetworkSimulationsFromFiles;


//System imports
import java.awt.event.ActionEvent;
import java.util.Properties;
import javax.swing.JOptionPane;


//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;


public class WizardAction extends AbstractCyAction{

	private static final long serialVersionUID = 1L;
	private SimulationsContainer simulationsContainer;
	private CyApplicationManager appManager;
	private final CySwingAppAdapter adapter;

	public WizardAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer) {
		super("Wizard");
		this.adapter = adapter;
		this.appManager = this.adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;
		setPreferredMenu("Apps.CABERNET");
	}


	/**
	 * CABERNET entry point.
	 * This method opens the wizard view.
	 */
	public void actionPerformed(ActionEvent e) {
		// Get a Cytoscape service 'DialogTaskManager' in CyActivator class
		DialogTaskManager dialogTaskManager = adapter.getCyServiceRegistrar().getService(DialogTaskManager.class);
		
		Wizard wizard = new Wizard(adapter);
		Properties simulationFeatures;
		Properties tasks;
		Properties outputs;
		boolean atm_computation, tree_matching;
		String matching_type;
		int threshold;
		boolean representativeTreeComputation;
		double representativeTreeDepthValue = 1.0;
		String representativeTreeDepthMode;

		int response = wizard.showWizard();
		if(response == 1){
			try{
				simulationFeatures = wizard.getSimulationFeatures();
				tasks = wizard.getTaskToDo();
				outputs = wizard.getOutputs();
				atm_computation = tasks.getProperty(CABERNETConstants.ATM_COMPUTATION, CABERNETConstants.NO).equals(CABERNETConstants.YES);
				tree_matching = tasks.getProperty(CABERNETConstants.TREE_MATCHING, CABERNETConstants.NO).equals(CABERNETConstants.YES);
				representativeTreeComputation = tasks.getProperty(CABERNETConstants.COMPUTE_REPRESENTATIVE_TREE, CABERNETConstants.NO).equals(CABERNETConstants.YES);
				if(representativeTreeComputation){
					representativeTreeDepthMode = tasks.getProperty(CABERNETConstants.TREE_DEPTH_MODE);
					if(representativeTreeDepthMode.equals(CABERNETConstants.ABSOLUTE_DEPTH) ||
							representativeTreeDepthMode.equals(CABERNETConstants.RELATIVE_DEPTH)){
						representativeTreeDepthValue = Double.valueOf(tasks.getProperty(CABERNETConstants.TREE_DEPTH_VALUE));
					}
				}else{
					representativeTreeDepthMode = null;
					representativeTreeDepthValue = -1;
				}
				//Network Creation from features
				//Create the network randomly
				if(tasks.getProperty(CABERNETConstants.NETWORK_CREATION).equals(CABERNETConstants.NEW)){
					if(!tree_matching){
						dialogTaskManager.execute(new TaskIterator(new NetworkCreation(simulationFeatures, outputs, this.adapter, 
								this.appManager, this.simulationsContainer, atm_computation, representativeTreeComputation, 
								representativeTreeDepthMode, representativeTreeDepthValue)));
					}else{
						matching_type = tasks.getProperty(CABERNETConstants.MATCHING_TYPE);
						if(matching_type.equals(CABERNETConstants.PERFECT_MATCH)){
							dialogTaskManager.execute(new TaskIterator(new NetworkCreation(simulationFeatures, outputs, this.adapter, 
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(),
									representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
						}else{
							threshold = Integer.parseInt(tasks.getProperty(CABERNETConstants.MATCHING_THRESHOLD));

							dialogTaskManager.execute(new TaskIterator(new NetworkCreation(simulationFeatures, outputs, this.adapter, 
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(),
									matching_type, threshold, representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
						}
					}
					//Reads the networks from the GRNML files
				}else if(tasks.getProperty(CABERNETConstants.NETWORK_CREATION).equals(CABERNETConstants.OPEN)){
					if(!tree_matching){
						dialogTaskManager.execute(new TaskIterator(new NetworkSimulationsFromFiles(simulationFeatures, outputs, this.adapter, 
								this.appManager, this.simulationsContainer, atm_computation, wizard.getInputNetworks(), false,
								representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
					}else{
						matching_type = tasks.getProperty(CABERNETConstants.MATCHING_TYPE);
						if(matching_type.equals(CABERNETConstants.PERFECT_MATCH)){
							dialogTaskManager.execute(new TaskIterator(new NetworkSimulationsFromFiles(simulationFeatures, outputs, this.adapter,
									this.simulationsContainer, atm_computation, tree_matching, 
									wizard.getDifferentiationTree(), wizard.getInputNetworks(), false,
									representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
						}else{
							threshold = Integer.parseInt(tasks.getProperty(CABERNETConstants.MATCHING_THRESHOLD));
							dialogTaskManager.execute(new TaskIterator(new NetworkSimulationsFromFiles(simulationFeatures, outputs, this.adapter,
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(),
									matching_type, threshold, wizard.getInputNetworks(), false,
									representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
						}
					}
					//Gets the original network from file and complete it.
				}else if(tasks.getProperty(CABERNETConstants.NETWORK_CREATION).equals(CABERNETConstants.EDIT)){
					if(!tree_matching){
						dialogTaskManager.execute(new TaskIterator(new NetworkSimulationsFromFiles(simulationFeatures, outputs, this.adapter, 
								this.appManager, this.simulationsContainer, atm_computation, wizard.getInputNetworks(), true,
								representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
					}else{
						matching_type = tasks.getProperty(CABERNETConstants.MATCHING_TYPE);
						if(matching_type.equals(CABERNETConstants.PERFECT_MATCH)){
							dialogTaskManager.execute(new TaskIterator(new NetworkSimulationsFromFiles(simulationFeatures, outputs, this.adapter,
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(), 
									wizard.getInputNetworks(), true, representativeTreeComputation, 
									representativeTreeDepthMode, representativeTreeDepthValue)));
						}else{
							threshold = Integer.parseInt(tasks.getProperty(CABERNETConstants.MATCHING_THRESHOLD));
							dialogTaskManager.execute(new TaskIterator(new NetworkSimulationsFromFiles(simulationFeatures, outputs, this.adapter,
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(),
									matching_type, threshold, wizard.getInputNetworks(), true,
									representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
						}
					}
					//Gets the original network from Cytoscape
				}else if(tasks.getProperty(CABERNETConstants.NETWORK_CREATION).equals(CABERNETConstants.CYTOSCAPE_EDIT)){
					if(!tree_matching){
						dialogTaskManager.execute(new TaskIterator(new NetworkEditingFromCytoscape(simulationFeatures, outputs, this.adapter, 
								this.appManager, this.simulationsContainer, atm_computation, representativeTreeComputation, 
								representativeTreeDepthMode, representativeTreeDepthValue)));
					}else{
						matching_type = tasks.getProperty(CABERNETConstants.MATCHING_TYPE);
						if(matching_type.equals(CABERNETConstants.PERFECT_MATCH)){
							dialogTaskManager.execute(new TaskIterator(new NetworkEditingFromCytoscape(simulationFeatures, outputs, this.adapter, 
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(), 
									representativeTreeComputation, representativeTreeDepthMode, representativeTreeDepthValue)));
						}else{
							threshold = Integer.parseInt(tasks.getProperty(CABERNETConstants.MATCHING_THRESHOLD));
							dialogTaskManager.execute(new TaskIterator(new NetworkEditingFromCytoscape(simulationFeatures, outputs, this.adapter, 
									this.simulationsContainer, atm_computation, tree_matching, wizard.getDifferentiationTree(),
									matching_type, threshold, representativeTreeComputation, representativeTreeDepthMode, 
									representativeTreeDepthValue)));

						}
					}
				}
			}catch(Exception ex){
				System.err.println("Error " + ex);
				JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}
}
