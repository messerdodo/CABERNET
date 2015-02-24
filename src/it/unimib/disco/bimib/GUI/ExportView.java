/**
 * This view is used in order to select the objects that have to be exported.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */
package it.unimib.disco.bimib.GUI;

//CABERNET imports
import it.unimib.disco.bimib.CABERNET.Simulation;
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
//GRNSim imports
import it.unimib.disco.bimib.IO.Output;
import it.unimib.disco.bimib.Statistics.DynamicalStatistics;


//System imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.swing.DialogTaskManager;


public class ExportView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SimulationsContainer simulations;
	private Simulation currentSimulation;
	private CySwingAppAdapter adapter;

	private JPanel contentPane;
	private JTextField txtPath;
	private final ButtonGroup ExportGroup = new ButtonGroup();
	private JCheckBox chkGRNML;
	private JCheckBox chkSif;
	private JCheckBox chkAtm;
	private JCheckBox chkAttractors;
	private JCheckBox chkStatesInAttractor;
	private JCheckBox chkSynthesis;
	private JCheckBox chkAttractorsLengths;
	private JCheckBox chkBasinsOfAttraction;
	private JRadioButton rdbtnSelectedNetwork;
	private JRadioButton rdbtnAllTheNetworks;


	/**
	 * Create the frame.
	 */
	public ExportView(SimulationsContainer simulations, Simulation currentSimulation, CySwingAppAdapter adapter) {

		this.simulations = simulations;
		this.currentSimulation = currentSimulation;
		this.adapter = adapter;

		setTitle("Export");
		setBounds(100, 100, 701, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JLabel lblExportPath = new JLabel("Export path:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblExportPath, 16, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblExportPath, -603, SpringLayout.EAST, contentPane);
		contentPane.add(lblExportPath);

		txtPath = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtPath, -6, SpringLayout.NORTH, lblExportPath);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtPath, 6, SpringLayout.EAST, lblExportPath);
		contentPane.add(txtPath);
		txtPath.setColumns(10);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Set the export path
				JFileChooser pathChooser = new JFileChooser();
				pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(pathChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					txtPath.setText(pathChooser.getCurrentDirectory().getPath());
				}
			}
		});
		sl_contentPane.putConstraint(SpringLayout.EAST, txtPath, -11, SpringLayout.WEST, btnBrowse);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnBrowse, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnBrowse, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnBrowse);

		JLabel lblExportTheFollowing = new JLabel("Export the following items:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblExportTheFollowing, 22, SpringLayout.SOUTH, lblExportPath);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblExportTheFollowing, 0, SpringLayout.WEST, lblExportPath);
		contentPane.add(lblExportTheFollowing);

		chkGRNML = new JCheckBox("Network (GRNML File)");
		chkGRNML.setSelected(true);
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkGRNML, 6, SpringLayout.SOUTH, lblExportTheFollowing);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkGRNML, 0, SpringLayout.WEST, lblExportPath);
		contentPane.add(chkGRNML);

		chkSif = new JCheckBox("Network (SIF File)");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkSif, 38, SpringLayout.SOUTH, txtPath);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkSif, 29, SpringLayout.EAST, chkGRNML);
		contentPane.add(chkSif);

		chkAtm = new JCheckBox("ATM");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkAtm, 6, SpringLayout.SOUTH, chkGRNML);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkAtm, 0, SpringLayout.WEST, lblExportPath);
		contentPane.add(chkAtm);

		chkAttractors = new JCheckBox("Attractors");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkAttractors, 6, SpringLayout.SOUTH, chkAtm);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkAttractors, 10, SpringLayout.WEST, contentPane);
		contentPane.add(chkAttractors);

		chkStatesInAttractor = new JCheckBox("States in each attractor");
		sl_contentPane.putConstraint(SpringLayout.WEST, chkStatesInAttractor, 0, SpringLayout.WEST, chkSif);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, chkStatesInAttractor, 0, SpringLayout.SOUTH, chkAttractors);
		contentPane.add(chkStatesInAttractor);

		chkSynthesis = new JCheckBox("Synthesis file");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkSynthesis, 6, SpringLayout.SOUTH, chkAttractors);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkSynthesis, 0, SpringLayout.WEST, lblExportPath);
		contentPane.add(chkSynthesis);

		chkAttractorsLengths = new JCheckBox("Attractors lengths");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkAttractorsLengths, 6, SpringLayout.SOUTH, chkSynthesis);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkAttractorsLengths, 0, SpringLayout.WEST, lblExportPath);
		contentPane.add(chkAttractorsLengths);

		chkBasinsOfAttraction = new JCheckBox("Basins of attraction");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chkBasinsOfAttraction, 0, SpringLayout.NORTH, chkAttractorsLengths);
		sl_contentPane.putConstraint(SpringLayout.WEST, chkBasinsOfAttraction, 0, SpringLayout.WEST, chkSif);
		contentPane.add(chkBasinsOfAttraction);

		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ExportButtonPressed(this));
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnExport, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnExport, 0, SpringLayout.EAST, contentPane);
		contentPane.add(btnExport);

		JLabel lblExportFor = new JLabel("Export for:");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblExportFor, 0, SpringLayout.WEST, lblExportPath);
		contentPane.add(lblExportFor);

		rdbtnSelectedNetwork = new JRadioButton("Selected network");
		ExportGroup.add(rdbtnSelectedNetwork);
		rdbtnSelectedNetwork.setSelected(true);
		sl_contentPane.putConstraint(SpringLayout.NORTH, rdbtnSelectedNetwork, 16, SpringLayout.SOUTH, chkAttractorsLengths);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblExportFor, 4, SpringLayout.NORTH, rdbtnSelectedNetwork);
		sl_contentPane.putConstraint(SpringLayout.WEST, rdbtnSelectedNetwork, 0, SpringLayout.WEST, txtPath);
		contentPane.add(rdbtnSelectedNetwork);

		rdbtnAllTheNetworks = new JRadioButton("All the networks");
		ExportGroup.add(rdbtnAllTheNetworks);
		sl_contentPane.putConstraint(SpringLayout.NORTH, rdbtnAllTheNetworks, -4, SpringLayout.NORTH, lblExportFor);
		sl_contentPane.putConstraint(SpringLayout.WEST, rdbtnAllTheNetworks, 44, SpringLayout.EAST, rdbtnSelectedNetwork);
		contentPane.add(rdbtnAllTheNetworks);
	}

	public class ExportButtonPressed implements ActionListener {

		private JFrame parent;

		public ExportButtonPressed(JFrame parent){
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtPath.getText().equals("")){
				DialogTaskManager dialogTaskManager = adapter.getCyServiceRegistrar().getService(DialogTaskManager.class);
				dialogTaskManager.execute(new TaskIterator(new SavingTask()));
				this.parent.setVisible(false);
			}

		}

	}

	public class SavingTask extends AbstractTask{


		@Override
		public void run(TaskMonitor taskMonitor) throws Exception {
			taskMonitor.setTitle("CABERNET - Export network");
			//Selected network
			if(rdbtnSelectedNetwork.isSelected()){
				taskMonitor.setStatusMessage("Esporting network: "  + currentSimulation.getNetworkId() );
				this.saveSingleSimulation(currentSimulation, false);
				//All the networks
			}else{
				Set<String> simIds = simulations.getSimulationsId();
				double current = 0, total = simIds.size(); 
				for(String simId : simIds){
					taskMonitor.setStatusMessage("Esporting network: "  + simId );
					taskMonitor.setProgress(current/total);
					this.saveSingleSimulation(simulations.getSimulation(simId), true);
					current = current + 1;
				}
			}
			taskMonitor.setProgress(1.0);
		}

		/**
		 * This method saves the files about a single simulation.
		 * @param sim the simulation to be store
		 * @throws Exception
		 */
		private void saveSingleSimulation(Simulation sim, boolean createFolder) throws Exception{
			String networkName;
			if(createFolder){
				Output.createFolder(txtPath.getText() + "/" + sim.getNetworkId());
				networkName = txtPath.getText() + "/" + sim.getNetworkId() + "/" + sim.getNetworkId();
			}else{
				networkName = txtPath.getText() + "/" + sim.getNetworkId();
			}

			//GRNML file
			if(chkGRNML.isSelected()){
				Output.createGRNMLFile(sim.getGraphManager().getGraph(), networkName + ".grnml");
			}
			//Sif file
			if(chkSif.isSelected()){
				Output.createSIFFile(sim.getGraphManager().getGraph(), networkName + ".sif");
			}
			//ATM csv file
			if(chkAtm.isSelected()){
				Output.createATMFile(sim.getAtmManager().getAtm(), networkName + "_atm.csv");
			}
			//Attractors
			if(chkAttractors.isSelected()){
				Output.saveAttractorsFile(sim.getSamplingManager().getAttractorFinder(), networkName + "_attractors.csv");
			}
			//States in each attractor
			if(chkStatesInAttractor.isSelected()){
				Output.saveStatesAttractorsFile(networkName + "_states_in_attractors.csv", sim.getSamplingManager().getAttractorFinder());
			}
			//Synthesis file
			if(chkSynthesis.isSelected()){
				Output.createSynthesisFile(sim.getNetworkStatistics(), networkName + "_synthesis.csv");
			}
			//Basins of attraction
			if(chkBasinsOfAttraction.isSelected()){
				DynamicalStatistics dynStats = new DynamicalStatistics(sim.getSamplingManager());
				HashMap<String, ArrayList<Integer>> basins = new HashMap<String, ArrayList<Integer>>();
				basins.put(sim.getNetworkId(), dynStats.getBasinOfAttraction(true));
				Output.saveBasinOfAttractionFile(networkName + "_basins_of_attraction.csv", basins);
			}
			//Attractors lengths
			if(chkAttractorsLengths.isSelected()){
				DynamicalStatistics dynStats = new DynamicalStatistics(sim.getSamplingManager());
				HashMap<String, ArrayList<Integer>> lengths = new HashMap<String, ArrayList<Integer>>();
				lengths.put(sim.getNetworkId(), dynStats.getAttractorsLength(true));
				Output.saveAttractorsLengths(networkName + "_attractors_lengths.csv", lengths);
			}
		}

	}

}
