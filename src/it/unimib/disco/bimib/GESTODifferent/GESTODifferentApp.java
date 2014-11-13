package it.unimib.disco.bimib.GESTODifferent;

//GESTODifferent imports
import it.unimib.disco.bimib.GUI.Actions.ComputeDynamicPerturbationsAction;
import it.unimib.disco.bimib.GUI.Actions.DynamicPerturbationsStatsAction;
import it.unimib.disco.bimib.GUI.Actions.NetworkRightClickAtmAction;
import it.unimib.disco.bimib.GUI.Actions.NodeRightClickAction;
import it.unimib.disco.bimib.GUI.Actions.WizardAction;
import it.unimib.disco.bimib.Middleware.VizMapperManager;

//System imports
import java.util.Properties;

//Cytoscape imports
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyNetworkViewContextMenuFactory;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.service.util.CyServiceRegistrar;

/**
 * The app entry point.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

public class GESTODifferentApp extends AbstractCySwingApp{

	private SimulationsContainer simulationsContainer;

	public GESTODifferentApp(CySwingAppAdapter swingAdapter) {
		super(swingAdapter);

		//Creates a new simulations container
		simulationsContainer = new SimulationsContainer();

		//Defines the wizard
		swingAdapter.getCySwingApplication().addAction(new WizardAction(swingAdapter, this.simulationsContainer));
		VizMapperManager viz = new VizMapperManager(this.swingAdapter);

		//Registers the function exploration service
		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		registrar.registerService(new NodeRightClickAction(swingAdapter.getCyApplicationManager(), this.simulationsContainer),
				CyNodeViewContextMenuFactory.class,
				new Properties());

		//Registers the atm exploration service
		registrar.registerService(new NetworkRightClickAtmAction(swingAdapter.getCyApplicationManager(), swingAdapter, this.simulationsContainer, viz),
				CyNetworkViewContextMenuFactory.class,
				new Properties());

		//Registers the dynamic perturbations statistics view service
		registrar.registerService(new DynamicPerturbationsStatsAction(swingAdapter, this.simulationsContainer), CyNetworkViewContextMenuFactory.class,
				new Properties());

		//Registers the dynamic perturbations statistics computation service
		registrar.registerService(new ComputeDynamicPerturbationsAction(swingAdapter, this.simulationsContainer, viz), CyNetworkViewContextMenuFactory.class,
				new Properties());

	}


}
