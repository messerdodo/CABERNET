/**
 * TesNumberComputingMenuAction class.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.GUI.Actions;

//CABERNET imports
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
import it.unimib.disco.bimib.GUI.TesView;

//System imports
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;


public class TesNumberComputingMenuAction extends AbstractCyAction{

	private static final long serialVersionUID = 1L;

	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;


	public TesNumberComputingMenuAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer) {

		super("Show threshold-dependent TES variation chart", 
				adapter.getCyApplicationManager(),
				"network",
				adapter.getCyNetworkViewManager());

		this.appManager = adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;


		setPreferredMenu("Apps.CABERNET.Functions.Show attractor stability analysis");
	}

	//Menu action pressed
	public void actionPerformed(ActionEvent e) {
		try{
			String simulationId = "";

			TesView tesView;
			CyNetwork currentNetwork = appManager.getCurrentNetwork();
			simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);

			//The simulation exists
			if(simulationsContainer.getSimulation(simulationId) != null){
				tesView = new TesView(simulationsContainer.getSimulation(simulationId).getAtmManager().getAtm());
				tesView.setVisible(true);
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
