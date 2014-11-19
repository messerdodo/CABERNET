
package it.unimib.disco.bimib.GUI.Actions;

//GRNSim imports
import it.unimib.disco.bimib.GESTODifferent.SimulationsContainer;

import it.unimib.disco.bimib.GUI.TesView;

//System imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNetworkViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;

public class TesNumberComputingAction implements CyNetworkViewContextMenuFactory{

	//private CySwingAppAdapter adapter;
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;


	public TesNumberComputingAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer){
		//this.adapter = adapter;
		this.appManager = adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;

	}


	@Override
	public CyMenuItem createMenuItem(CyNetworkView netView) {
		JMenuItem menuItem = new JMenuItem("Show TES number chart");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				try{
					String simulationId = "";

					TesView tesView;
					CyNetwork currentNetwork = appManager.getCurrentNetwork();
					simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);

					//The simulation exists
					if(simulationsContainer.getSimulation(simulationId) != null){
						tesView = new TesView(simulationsContainer.getSimulation(simulationId).getAtmManager().getAtm());
						tesView.setVisible(true);
					}	

				}catch(Exception e){
					System.out.println(e.getMessage().equals("") ? e : e.getMessage());
				}
			}

		});
		float gravity = 1.0f;
		CyMenuItem addAsSource = new CyMenuItem(menuItem, gravity);
		return addAsSource;
	}
}


