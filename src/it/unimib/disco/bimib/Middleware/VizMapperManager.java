package it.unimib.disco.bimib.Middleware;

/**
 * This class is a bridge between the app and the VizMapperManager.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

//System imports
import java.awt.Color;
import java.util.HashMap;
//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

public class VizMapperManager {

	private CySwingAppAdapter adapter;
	private HashMap<String, VisualStyle> appStyles;

	public VizMapperManager(CySwingAppAdapter adapter){
		this.adapter = adapter;
		this.appStyles = new HashMap<String, VisualStyle>();
		//Registers the TES style
		this.registerTesStyle();
		//Registers the Collapsed TES style
		this.registerCollapsedTesStyle();
		//Registers the network style
		this.registerNetworkStyle();
		//Registers the attractors style
		this.registerAttractorsStyle();
		
	}

	/**
	 * This method returns the required style
	 * @param styleName: The style name
	 * @return the required style.
	 */
	public VisualStyle getStyle(String styleName){
		if(this.appStyles.containsKey(styleName))
			return this.appStyles.get(styleName);
		else
			return null;
	}


	/**
	 * This method registers the Network style
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void registerNetworkStyle(){

		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();

		// To get references to services in CyActivator class
		VisualMappingManager vmmServiceRef = registrar.getService(VisualMappingManager.class);


		VisualStyleFactory visualStyleFactoryServiceRef = registrar.getService(VisualStyleFactory.class); 
		VisualMappingFunctionFactory vmfFactoryC = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory vmfFactoryD = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");

		// To create a new VisualStyle object and set the mapping function
		VisualStyle vs = visualStyleFactoryServiceRef.createVisualStyle("GESTODifferent Network");

		//Use pass-through mapping
		vs.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);                  

		// Set node color map to attribute "Degree"
		ContinuousMapping<Integer, Double> nodeSizeMapping = (ContinuousMapping<Integer, Double>) vmfFactoryC.createVisualMappingFunction("Degree", Integer.class, BasicVisualLexicon.NODE_SIZE);
		Integer val1 = 0;
		Integer val2 = 100;
		BoundaryRangeValues<Double> nodeWidthVal1 = new BoundaryRangeValues<Double>(30.0, 30.0, 30.0);
		BoundaryRangeValues<Double> nodeWidthVal2 = new BoundaryRangeValues<Double>(100.0, 100.0, 100.0);
		nodeSizeMapping.addPoint(val1, nodeWidthVal1);
		nodeSizeMapping.addPoint(val2, nodeWidthVal2);
		vs.addVisualMappingFunction(nodeSizeMapping);
		
		vs.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.BLUE);  
		
		DiscreteMapping<String, Color> nodesColorMapping = (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("Function", String.class, BasicVisualLexicon.NODE_FILL_COLOR);
		nodesColorMapping.putMapValue("AND", Color.BLUE);
		nodesColorMapping.putMapValue("OR", Color.RED);
		nodesColorMapping.putMapValue("Random", Color.GREEN);
		nodesColorMapping.putMapValue("Random with bias", Color.MAGENTA);
		nodesColorMapping.putMapValue("Canalizing", Color.YELLOW);
		vs.addVisualMappingFunction(nodesColorMapping);  
		
		PassthroughMapping geneNameMapping = (PassthroughMapping) vmfFactoryP.createVisualMappingFunction("name", String.class, BasicVisualLexicon.NODE_LABEL);
		vs.addVisualMappingFunction(geneNameMapping);      

		//Edges visual properties
		vs.setDefaultValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DELTA);
		vs.setDefaultValue(BasicVisualLexicon.EDGE_PAINT, Color.GREEN);
		vs.setDefaultValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.BLUE);


		// Add the new style to the VisualMappingManager
		vmmServiceRef.addVisualStyle(vs);
		this.appStyles.put("GESTODifferent Network", vs);

	}

	/**
	 * This method registers the attractor style
	 */
	private void registerAttractorsStyle(){

		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();

		// To get references to services in CyActivator class
		VisualMappingManager vmmServiceRef = registrar.getService(VisualMappingManager.class);


		VisualStyleFactory visualStyleFactoryServiceRef = registrar.getService(VisualStyleFactory.class); 
		
		// To create a new VisualStyle object and set the mapping function
		VisualStyle vs = visualStyleFactoryServiceRef.createVisualStyle("GESTODifferent Attractors");

		//Nodes style
		vs.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ROUND_RECTANGLE);
		vs.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.ORANGE);

		//Edges visual properties
		vs.setDefaultValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DELTA);
		vs.setDefaultValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.BLUE);

		// Add the new style to the VisualMappingManager
		vmmServiceRef.addVisualStyle(vs);
		this.appStyles.put("GESTODifferent Attractors", vs);

	}
	
	/**
	 * This method registers the TES style
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void registerTesStyle(){

		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();

		// To get references to services in CyActivator class
		VisualMappingManager vmmServiceRef = registrar.getService(VisualMappingManager.class);


		VisualStyleFactory visualStyleFactoryServiceRef = registrar.getService(VisualStyleFactory.class); 
		VisualMappingFunctionFactory vmfFactoryC = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory vmfFactoryD = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");

		// To create a new VisualStyle object and set the mapping function
		VisualStyle vs = visualStyleFactoryServiceRef.createVisualStyle("GESTODifferent TES");


		//Use pass-through mapping
		vs.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.OCTAGON);
		vs.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.RED);

		//Edges visual properties
		vs.setDefaultValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DELTA);
		//vs.setDefaultValue(BasicVisualLexicon.EDGE_PAINT, Color.GREEN);
		vs.setDefaultValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.GREEN);

		PassthroughMapping transitionProbabilityLabelpMapping = (PassthroughMapping) vmfFactoryP.createVisualMappingFunction("Transition probability", Double.class, BasicVisualLexicon.EDGE_LABEL);
		vs.addVisualMappingFunction(transitionProbabilityLabelpMapping);                        

		DiscreteMapping interactionEdgeColorMapping = (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
		interactionEdgeColorMapping.putMapValue("Attractors Transition", Color.BLUE);
		vs.addVisualMappingFunction(interactionEdgeColorMapping);  

		DiscreteMapping interactionEdgeLineStyleMapping = (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_LINE_TYPE);
		interactionEdgeLineStyleMapping.putMapValue("Attractors Transition", LineTypeVisualProperty.DASH_DOT);
		vs.addVisualMappingFunction(interactionEdgeLineStyleMapping);

		// Set node color map to attribute "Degree"
		ContinuousMapping<Double, Double> edgeSizeMapping = (ContinuousMapping<Double, Double>) vmfFactoryC.createVisualMappingFunction("Transition probability", Double.class, BasicVisualLexicon.EDGE_WIDTH);
		Double val1 = 0.0;
		Double val2 = 1.0;
		BoundaryRangeValues<Double> edgeWidthVal1 = new BoundaryRangeValues<Double>(1.0, 1.0, 1.0);
		BoundaryRangeValues<Double> edgeWidthVal2 = new BoundaryRangeValues<Double>(5.0, 5.0, 5.0);

		edgeSizeMapping.addPoint(val1, edgeWidthVal1);
		edgeSizeMapping.addPoint(val2, edgeWidthVal2);
		vs.addVisualMappingFunction(edgeSizeMapping);


		// Add the new style to the VisualMappingManager
		vmmServiceRef.addVisualStyle(vs);
		this.appStyles.put("GESTODifferent TES", vs);

	}
	
	/**
	 * This method registers the TES style
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void registerCollapsedTesStyle(){

		final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();

		// To get references to services in CyActivator class
		VisualMappingManager vmmServiceRef = registrar.getService(VisualMappingManager.class);


		VisualStyleFactory visualStyleFactoryServiceRef = registrar.getService(VisualStyleFactory.class); 
		VisualMappingFunctionFactory vmfFactoryC = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory vmfFactoryD = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP = registrar.getService(VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");

		// To create a new VisualStyle object and set the mapping function
		VisualStyle vs = visualStyleFactoryServiceRef.createVisualStyle("GESTODifferent Collapsed TES");


		//Use pass-through mapping
		vs.setDefaultValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
		vs.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.RED);

		//Edges visual properties
		vs.setDefaultValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DELTA);
		//vs.setDefaultValue(BasicVisualLexicon.EDGE_PAINT, Color.GREEN);
		vs.setDefaultValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, Color.GREEN);

		PassthroughMapping transitionProbabilityLabelpMapping = (PassthroughMapping) vmfFactoryP.createVisualMappingFunction("Transition probability", Double.class, BasicVisualLexicon.EDGE_LABEL);
		vs.addVisualMappingFunction(transitionProbabilityLabelpMapping);                        

		DiscreteMapping interactionEdgeColorMapping = (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
		interactionEdgeColorMapping.putMapValue("Attractors Transition", Color.BLUE);
		vs.addVisualMappingFunction(interactionEdgeColorMapping);  

		DiscreteMapping interactionEdgeLineStyleMapping = (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_LINE_TYPE);
		interactionEdgeLineStyleMapping.putMapValue("Attractors Transition", LineTypeVisualProperty.DASH_DOT);
		vs.addVisualMappingFunction(interactionEdgeLineStyleMapping);

		// Set node color map to attribute "Degree"
		ContinuousMapping<Double, Double> edgeSizeMapping = (ContinuousMapping<Double, Double>) vmfFactoryC.createVisualMappingFunction("Transition probability", Double.class, BasicVisualLexicon.EDGE_WIDTH);
		Double val1 = 0.0;
		Double val2 = 1.0;
		BoundaryRangeValues<Double> edgeWidthVal1 = new BoundaryRangeValues<Double>(1.0, 1.0, 1.0);
		BoundaryRangeValues<Double> edgeWidthVal2 = new BoundaryRangeValues<Double>(5.0, 5.0, 5.0);

		edgeSizeMapping.addPoint(val1, edgeWidthVal1);
		edgeSizeMapping.addPoint(val2, edgeWidthVal2);
		vs.addVisualMappingFunction(edgeSizeMapping);


		// Add the new style to the VisualMappingManager
		vmmServiceRef.addVisualStyle(vs);
		this.appStyles.put("GESTODifferent Collapsed TES", vs);

	}


}
