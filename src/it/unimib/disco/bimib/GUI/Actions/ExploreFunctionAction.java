/**
 * ExploreFunctionAction class
 * This class is the function exploration event handler.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.GUI.Actions;



//GRNSim imports
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
import it.unimib.disco.bimib.GUI.ExploresFunctionView;
//GRNSim imports
import it.unimib.disco.bimib.Exceptions.NotExistingNodeException;
import it.unimib.disco.bimib.Functions.Function;

//System imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//Cytoscape imports
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;



public class ExploreFunctionAction implements CyNodeViewContextMenuFactory{

	private CyApplicationManager appManager;
	private SimulationsContainer simulationsContainer;

	/**
	 * Default constructor
	 * @param appManager: the CyApplicationManager object
	 * @param simulationsContainer: The simulations container
	 */
	public ExploreFunctionAction(CyApplicationManager appManager, 
			SimulationsContainer simulationsContainer){
		this.appManager = appManager;
		this.simulationsContainer = simulationsContainer;
	}


	@Override
	public CyMenuItem createMenuItem(CyNetworkView netView, View<CyNode> nodeView) {
		//Defines the menu 
		JMenuItem menuItem = new JMenuItem("Explores function (CABERNET)");
		menuItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent event)
			{
				Function function = null;
				int currentNode;
				String simulationId = "";
				ArrayList<String> genesNames;

				//Gets the simulation id connected with the selected network.
				CyNetwork currentNetwork = appManager.getCurrentNetwork();
				simulationId = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
				//Gets the selected nodes
				List<CyNode> selectedNodes = CyTableUtil.getNodesInState(currentNetwork,"selected",true);
				try {
					for(CyNode node : selectedNodes){
						//Gets the function of the selected node and show it in the correct view

						currentNode = currentNetwork.getRow(node).get("Gene number", Integer.class);
						function = simulationsContainer.getSimulation(simulationId).getGraphManager().getGraph().getFunction(currentNode);
						genesNames = simulationsContainer.getSimulation(simulationId).getGraphManager().getGraph().getNodesNames();
						ExploresFunctionView exploresFunctionFrame = new ExploresFunctionView(currentNode, genesNames, function);
						exploresFunctionFrame.setVisible(true);
					}
				} catch (NotExistingNodeException e) {
					//A node must be selected: error message
					JOptionPane.showMessageDialog(null, "A node must be selected before its function showing.", "Error", JOptionPane.ERROR_MESSAGE, null);
				}

			}
		});
		float gravity = 1.0f;
		CyMenuItem addAsSource = new CyMenuItem(menuItem, gravity);
		return addAsSource;
	}
}


