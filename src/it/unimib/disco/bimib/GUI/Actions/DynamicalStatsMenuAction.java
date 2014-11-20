package it.unimib.disco.bimib.GUI.Actions;

//GESTODifferent imports
import it.unimib.disco.bimib.GESTODifferent.SimulationsContainer;
import it.unimib.disco.bimib.GUI.DynamicalStatisticsView;

//System imports
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;


public class DynamicalStatsMenuAction extends AbstractCyAction{

	private static final long serialVersionUID = 1L;
	
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;


	public DynamicalStatsMenuAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer) {

		super("Show dynamical statistics", 
				adapter.getCyApplicationManager(),
	            "network",
	            adapter.getCyNetworkViewManager());

		this.appManager = adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;

		
		setPreferredMenu("Apps.GESTODifferent");
	}

	//Menu action pressed
	public void actionPerformed(ActionEvent e) {
		try{
			String simulationId = "";
			DynamicalStatisticsView dynStatView;
			CyNetwork currentNetwork = appManager.getCurrentNetwork();
			simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);

			//The simulation exists
			if(simulationsContainer.getSimulation(simulationId) != null){
				dynStatView = new DynamicalStatisticsView(simulationId, simulationsContainer);
				dynStatView.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "A simulated network must be selected.", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}catch(Exception ex){
			String message = (String) (ex.getMessage().equals("") ? ex : ex.getMessage());
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
		}
	}

}
