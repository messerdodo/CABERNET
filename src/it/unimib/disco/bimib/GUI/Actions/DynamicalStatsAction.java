
package it.unimib.disco.bimib.GUI.Actions;

//GRNSim imports
import it.unimib.disco.bimib.GESTODifferent.SimulationsContainer;
import it.unimib.disco.bimib.GUI.DynamicalStatisticsView;


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

public class DynamicalStatsAction implements CyNetworkViewContextMenuFactory{

	//private CySwingAppAdapter adapter;
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;


	public DynamicalStatsAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer){
		//this.adapter = adapter;
		this.appManager = adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;

	}


	@Override
	public CyMenuItem createMenuItem(CyNetworkView netView) {
		JMenuItem menuItem = new JMenuItem("Show dynamical statistics");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
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

		});
		float gravity = 1.0f;
		CyMenuItem addAsSource = new CyMenuItem(menuItem, gravity);
		return addAsSource;
	}
}


