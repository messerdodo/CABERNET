/**
 * MostFrequentlyTreeViewMenuAction class.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.GUI.Actions;

//CABERNET imports
import it.unimib.disco.bimib.CABERNET.Simulation;
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
import it.unimib.disco.bimib.GUI.TreeFrame;

//System imports
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;


public class MostFrequentlyTreeViewMenuAction extends AbstractCyAction{

	private static final long serialVersionUID = 1L;

	private CySwingAppAdapter adapter;
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;


	//Constructor
	public MostFrequentlyTreeViewMenuAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer) {

		super("Compute representative tree", 
				adapter.getCyApplicationManager(),
				"network",
				adapter.getCyNetworkViewManager());

		this.appManager = adapter.getCyApplicationManager();
		this.adapter = adapter;
		this.simulationsContainer = simulationsContainer;
		setPreferredMenu("Apps.CABERNET.Functions");
	}

	//Menu action pressed
	public void actionPerformed(ActionEvent e) {
		try{
			String simulationId = "";

			CyNetwork currentNetwork = appManager.getCurrentNetwork();
			simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
			Simulation currentSimulation = simulationsContainer.getSimulation(simulationId);
			//The simulation exists
			if(currentSimulation != null){
				TreeFrame treeFrame = new TreeFrame(currentSimulation.getAtmManager(), currentSimulation.getSamplingManager(),
						simulationId, currentNetwork, adapter, appManager);
				treeFrame.setVisible(true);
			}else{
				//The simulation does not exist. Shows error message.
				JOptionPane.showMessageDialog(null, "A simulated network must be selected.", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}catch(Exception ex){
			String message = (String) (ex.getMessage().equals("") ? ex : ex.getMessage());
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
