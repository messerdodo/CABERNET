
package it.unimib.disco.bimib.GUI.Actions;
//GRNSim imports
import it.unimib.disco.bimib.GESTODifferent.Simulation;
import it.unimib.disco.bimib.GESTODifferent.SimulationsContainer;
import it.unimib.disco.bimib.GUI.DynamicPerturbationsStatsView;
import it.unimib.disco.bimib.Statistics.DynamicPerturbationsStatistics;

//System imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;

//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNetworkViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;

public class DynamicPerturbationsStatsAction implements CyNetworkViewContextMenuFactory{
	
	//private CySwingAppAdapter adapter;
	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;

	
	public DynamicPerturbationsStatsAction(CySwingAppAdapter adapter, SimulationsContainer simulationsContainer){
		//this.adapter = adapter;
		this.appManager = adapter.getCyApplicationManager();
		this.simulationsContainer = simulationsContainer;
		
	}

	
	@Override
	public CyMenuItem createMenuItem(CyNetworkView netView) {
		JMenuItem menuItem = new JMenuItem("Show dynamic perturbations statistics");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				try{
					String simulationId = "";
					DynamicPerturbationsStatistics stats;
					DynamicPerturbationsStatsView dynView;
					ArrayList<String> genesNames;
					CyNetwork currentNetwork = appManager.getCurrentNetwork();
					simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
					Simulation currentSimulation = simulationsContainer.getSimulation(simulationId);
					//The simulation exists
					if(currentSimulation != null){
						//Gets the data for the charts
						stats = currentSimulation.getAtmManager().getAtm().getDynamicPerturbationsStatistics();
						genesNames = currentSimulation.getGraphManager().getGraph().getNodesNames();
						if(stats != null){
							//Shows the dynamic perturbations charts view
							dynView = new DynamicPerturbationsStatsView(stats, genesNames);
							dynView.setVisible(true);
						}	
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


