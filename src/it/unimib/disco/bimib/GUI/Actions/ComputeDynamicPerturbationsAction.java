
package it.unimib.disco.bimib.GUI.Actions;

//GRNSim imports
import it.unimib.disco.bimib.GESTODifferent.Simulation;
import it.unimib.disco.bimib.GESTODifferent.SimulationsContainer;
import it.unimib.disco.bimib.GUI.DynamicStatisticsFrame;
import it.unimib.disco.bimib.Middleware.VizMapperManager;



//System imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNetworkViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;

public class ComputeDynamicPerturbationsAction implements CyNetworkViewContextMenuFactory{
	
	private CySwingAppAdapter adapter;
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;
	private VizMapperManager vizMapperManager;

	
	public ComputeDynamicPerturbationsAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer,
			VizMapperManager vizMapperManager){
		this.adapter = adapter;
		this.appManager = adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;
		this.vizMapperManager = vizMapperManager;	
	}
	
	@Override
	public CyMenuItem createMenuItem(CyNetworkView netView) {
		JMenuItem menuItem = new JMenuItem("Compute dynamic perturbations");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				DynamicStatisticsFrame dynStatsFrame;
				try{
					String simulationId = "";
					CyNetwork currentNetwork = appManager.getCurrentNetwork();
					simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
					Simulation currentSimulation = simulationsContainer.getSimulation(simulationId);
					//The simulation exists
					if(currentSimulation != null){
						//Shows the frame
						dynStatsFrame = new DynamicStatisticsFrame(adapter, currentSimulation, currentNetwork, vizMapperManager);
						dynStatsFrame.setVisible(true);	
					}else{
						//No network is selected
						JOptionPane.showMessageDialog(null, "A simulated network must be selected.", "Error", JOptionPane.ERROR_MESSAGE, null);
					}	
				}catch(Exception ex){
					String message = (String) (ex.getMessage().equals("") ? ex : ex.getMessage());
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		});
		float gravity = 1.0f;
		CyMenuItem addAsSource = new CyMenuItem(menuItem, gravity);
		return addAsSource;
	}
}


