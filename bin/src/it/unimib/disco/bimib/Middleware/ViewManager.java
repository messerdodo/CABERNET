package it.unimib.disco.bimib.Middleware;

/**
 * This class is a bridge between the app and the Cytoscape view service.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualStyle;

public class ViewManager {

	private CySwingAppAdapter adapter;

	/**
	 * Default constructor
	 * @param adapter: The CySwingAppAdapter object
	 */
	public ViewManager(CySwingAppAdapter adapter){
		this.adapter = adapter;
	}

	/**
	 * This method show the network in a view and applies the style.
	 * @param network: the network to be shown
	 * @param style: The style to be applied
	 */
	public void createView(CyNetwork network, VisualStyle style){
		// Create a new network view

		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		CyNetworkViewManager networkViewManager = registrar.getService(CyNetworkViewManager.class);

		// Get a CyNetworkViewFactory
		CyNetworkViewFactory networkViewFactory = registrar.getService(CyNetworkViewFactory.class);

		// Create a new network view
		CyNetworkView networkView = networkViewFactory.createNetworkView(network);
		
		// Add view to Cytoscape and applies the style
		networkViewManager.addNetworkView(networkView);
		style.apply(networkView);
		networkView.updateView();
	}

}
