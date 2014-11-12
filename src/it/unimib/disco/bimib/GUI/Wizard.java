package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.GESTODifferent.GESTODifferentConstants;
import it.unimib.disco.bimib.IO.*;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;

//Swing and awt imports
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.SpringLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;







//System imports
import java.util.ArrayList;
import java.util.Properties;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Wizard extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private final JPanel contentPanel = new JPanel();
	private JPanel NetworkDefinitionPanel;
	private JPanel experimentsPanel;
	private final ButtonGroup networkDefinitionMethodGroup = new ButtonGroup();
	private JPanel networkInputMethodPanel;
	private JPanel networkInputMethodUpperPanel;
	private JPanel networkInputMethodLowerPanel;
	private JPanel networkFromFilePanel;
	private JPanel networkManualFeaturesPanel;
	private JPanel networkDefinitionSubPanel;
	private JPanel featuresFileSelectionPanel;
	private JPanel nodesPanel;
	private JList<String> networksList;
	private DefaultListModel<String> networkListModel;
	private JPanel featuresSubPanel;
	private JPanel featureInputFormPanel;
	private JTextField txtNodesNumber;
	private JPanel topologyPanel;
	private JPanel randomTopologyPanel;
	private JPanel barabasiPanel;
	private JPanel powerLawPanel;
	private JPanel smallWorldPanel;
	private JPanel functionsTypePanel;
	private JPanel functionsRatesPanel;
	private JTextField txtNi;
	private JTextField txtAvgConnBA;
	private JTextField txtavgConnPL;
	private JTextField txtGamma;
	private JTextField txtAvgConnSW;
	private JTextField txtBeta;
	private JComboBox<String> cmbSamplingType;
	private JTable networkFeaturesTable;
	private DefaultTableModel networkFeaturesTableModel;
	private JRadioButton rdbtnFeaturesFromFile;
	private JRadioButton rdbtnFeaturesFromForm;
	private JComboBox<String> cmbTopology;
	private JTextField txtFeaturesFilePath;
	private JTextField txtEdgesNumber;
	private final ButtonGroup featuresInputMethodGroup = new ButtonGroup();
	private JComboBox<String> cmbFunctionsType;
	private JButton btnNext;
	private JLabel lblFunctionsType;
	private JTextField txtRandomFunctionsType;
	private JTextField txtBiasFunctionsType;
	private JTextField txtBiasValue;
	private JTextField txtAndFunctionsType;
	private JTextField txtOrFunctionsType;
	private JTextField txtCanalizingFunctionsType;
	private JLabel lblExperiments;
	private JPanel experimentsSubPanel;
	private JPanel attractorsPanel;
	private JLabel lblInitialConditions;
	private JTextField txtInitialConditions;
	private JPanel atmPanel;
	private JPanel atmCalulationSelectionPanel;
	private JLabel lblPerformAtmCalculation;
	private JCheckBox chkAtmCalculation;
	private JPanel atmCalculationParmPanel;
	private JPanel perturbationTypePanel;
	private JPanel commonExperimentsPanel;
	private JPanel variableExperimentsPanel;
	private JLabel lblPerturbationType;
	private JComboBox<String> mutationsTypeComboBox;
	private JLabel lblNewLabel_2;
	private JTextField txtRatioStatesMutations;
	private JLabel lblRatioOfAttractors;
	private JTextField txtExperimentsNumber;
	private JPanel flipsPanel;
	private JLabel lblNumberOfNodes_1;
	private JTextField txtNumberOfFlips;
	private JLabel lblMinFlipDuration;
	private JTextField txtMinFlipTimes;
	private JLabel lblMaxFlipDuration;
	private JTextField txtMaxFlipTimes;
	private JPanel temporaryMutationsPanel;
	private JLabel lblNumberOfNodes_2;
	private JTextField txtKnockInNodes;
	private JLabel lblMinKnockinTime;
	private JLabel lblMaxKnockinTime;
	private JTextField txtMinKnockInTimes;
	private JTextField txtMaxKnockInTimes;
	private JLabel lblNumberOfNodes_3;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextField txtKnockOutNodes;
	private JTextField txtMinKnockOutTimes;
	private JTextField txtMaxKnockOutTimes;
	private JPanel outputsPanel;
	private JPanel treeMatchingPanel;
	private JLabel lblOutputs;
	private JPanel outputsSubPanel;
	private JPanel cytoscapeOutputsPanel;
	private JCheckBox chkNetworksOutput;
	private JCheckBox chkAttractorsNetworkOutput;
	private JLabel lblTreeMatching;
	private JPanel treeMatchingSubPanel;
	private JPanel treeMatchingTaskSelectPanel;
	private JCheckBox chckbxMatchNetworksWith;
	private JTextField txtTreeFilePath;
	private JCheckBox chckbxCompletelyDefinedFunctions;
	private JRadioButton rdbtnPerfectMatch;
	private JRadioButton rdbtnMinDistance;
	private JTextField txtRequiredNetworks;
	private JLabel lblSamplingMethod;
	private JTextField txtCutoff;
	private final ButtonGroup grpMatchType = new ButtonGroup();
	private JRadioButton rdoHistogramDistance;
	private JLabel lblMatchingThreshold;
	private JTextField txtThreshold;
	private JLabel lblNetworkEditing;
	private JPanel pnlInnerNetworkEditing;
	private JTextField txtEditingNodesNumber;
	private JLabel lblTotalEdges;
	private JTextField txtEditingEdgesNumber;
	private JLabel lblEachGeneName;
	private JLabel lblSourceGenes;
	private JTextArea txtNoSourceGenes;
	private JTextArea txtNoDestinationGenes;
	private JLabel lblDestinationGenes;
	private JLabel lblFixedNumberOf;
	private JTextField txtFixedInputs;
	private JTextField txtEditingRandomType;
	private JLabel lblBias;
	private JTextField txtEditingBiasType;
	private JLabel lblBiasValue_1;
	private JTextField txtEditingBiasValue;
	private JLabel lblAnd_1;
	private JTextField txtEditingAndType;
	private JLabel lblOr_1;
	private JTextField txtEditingOrType;
	private JLabel lblCanalizing_1;
	private JTextField txtEditingCanalizingType;
	private JCheckBox chckbxComplitellyDefined;
	private JPanel pnlNetworkEditing;
	private JComboBox<String> cmbEditingFunctionType;
	

	
	
	//****
	private String form;
	private String inputMethod;
	private String currentFeature;
	private boolean editing;
	
	
	//private String task;
	private Properties tasks;
	private Properties outputs;
	
	//Data variables
	private ArrayList<String> inputNetworks;
	private Properties simulationFeatures;
	private String treeFile;
	private JCheckBox chkAvalanchesAndSensitivity;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Wizard dialog = new Wizard();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Wizard(){
		
		setTitle("GESTODifferent Wizard");

		//Data initialization
		this.inputNetworks = new ArrayList<String>();
		this.simulationFeatures = new Properties();
		this.tasks = new Properties();
		this.outputs = new Properties();
		
		this.inputMethod = "New random networks from features";
		this.form = "network-input-method";

		setBounds(100, 100, 672, 462);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			NetworkDefinitionPanel = new JPanel();
			contentPanel.add(NetworkDefinitionPanel, "NetworkDefinitionPanel");
			NetworkDefinitionPanel.setLayout(new BorderLayout(0, 0));
			{
				networkDefinitionSubPanel = new JPanel();
				NetworkDefinitionPanel.add(networkDefinitionSubPanel, BorderLayout.CENTER);
				networkDefinitionSubPanel.setLayout(new CardLayout(0, 0));
				networkInputMethodPanel = new JPanel();
				networkDefinitionSubPanel.add(networkInputMethodPanel, "networkInputMethodPanel");
				SpringLayout sl_networkInputMethodPanel = new SpringLayout();
				networkInputMethodPanel.setLayout(sl_networkInputMethodPanel);
				{
					networkInputMethodUpperPanel = new JPanel();
					sl_networkInputMethodPanel.putConstraint(SpringLayout.SOUTH, networkInputMethodUpperPanel, 166, SpringLayout.NORTH, networkInputMethodPanel);
					sl_networkInputMethodPanel.putConstraint(SpringLayout.EAST, networkInputMethodUpperPanel, 652, SpringLayout.WEST, networkInputMethodPanel);
					networkInputMethodUpperPanel.setBorder(new TitledBorder(null, "Networks definition method", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					networkInputMethodPanel.add(networkInputMethodUpperPanel);
					{
						JRadioButton radioButton = new JRadioButton("New random networks from features");
						radioButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								inputMethod = "New random networks from features";
								networkInputMethodLowerPanel.setVisible(false);
							}
						});
						networkInputMethodUpperPanel.setLayout(new GridLayout(0, 1, 0, 0));
						networkDefinitionMethodGroup.add(radioButton);
						radioButton.setSelected(true);
						networkInputMethodUpperPanel.add(radioButton);
					}
					{
						JRadioButton radioButton = new JRadioButton("Complete network from GRNML file");
						radioButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								inputMethod = "Complete network from GRNML file";
								networkInputMethodLowerPanel.setVisible(true);
							}
						});
						networkDefinitionMethodGroup.add(radioButton);
						networkInputMethodUpperPanel.add(radioButton);
					}
					{
						JRadioButton radioButton = new JRadioButton("Partial network from GRNML file");
						radioButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								inputMethod = "Partial network from GRNML file";
								networkInputMethodLowerPanel.setVisible(true);
								tasks.setProperty("network-creation", "modification");
							}
						});
						networkDefinitionMethodGroup.add(radioButton);
						networkInputMethodUpperPanel.add(radioButton);
					}
					{
						JRadioButton radioButton = new JRadioButton("Partial network from Cytoscape selected view");
						radioButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								inputMethod = "Partial network from Cytoscape selected view";
								networkInputMethodLowerPanel.setVisible(false);
								tasks.setProperty("network-creation", "cytoscape");
							}
						});
						networkDefinitionMethodGroup.add(radioButton);
						networkInputMethodUpperPanel.add(radioButton);
					}
				}
				networkInputMethodLowerPanel = new JPanel();
				sl_networkInputMethodPanel.putConstraint(SpringLayout.NORTH, networkInputMethodLowerPanel, 54, SpringLayout.SOUTH, networkInputMethodUpperPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.SOUTH, networkInputMethodLowerPanel, -29, SpringLayout.SOUTH, networkInputMethodPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.WEST, networkInputMethodUpperPanel, 0, SpringLayout.WEST, networkInputMethodLowerPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.WEST, networkInputMethodLowerPanel, 10, SpringLayout.WEST, networkInputMethodPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.EAST, networkInputMethodLowerPanel, -10, SpringLayout.EAST, networkInputMethodPanel);
				networkInputMethodLowerPanel.setVisible(false);
				networkInputMethodPanel.add(networkInputMethodLowerPanel);
				networkInputMethodLowerPanel.setLayout(new CardLayout(0, 0));
				networkFromFilePanel = new JPanel();
				networkInputMethodLowerPanel.add(networkFromFilePanel, "networkFromFilePanel");

				JLabel lblNewLabel = new JLabel("GRNML file path:");

				JButton btnOpenNetwork = new JButton("Load network");

				btnOpenNetwork.addActionListener(new ActionListener() {
					//Open network action
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser fileChooser = new JFileChooser();
						int result = fileChooser.showOpenDialog(contentPanel);
						if (result == JFileChooser.APPROVE_OPTION) {
							inputNetworks.add(fileChooser.getSelectedFile().getAbsolutePath());
							networkListModel.addElement(fileChooser.getSelectedFile().getName());
						}
					}
				});
				networksList = new JList<String>();
				networkListModel = new DefaultListModel<String>();
				networksList.setModel(networkListModel);
				GroupLayout gl_networkFromFilePanel = new GroupLayout(networkFromFilePanel);
				gl_networkFromFilePanel.setHorizontalGroup(
					gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_networkFromFilePanel.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_networkFromFilePanel.createSequentialGroup()
									.addComponent(networksList, GroupLayout.PREFERRED_SIZE, 623, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_networkFromFilePanel.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnOpenNetwork)
									.addGap(39))))
				);
				gl_networkFromFilePanel.setVerticalGroup(
					gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_networkFromFilePanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(btnOpenNetwork))
							.addGap(12)
							.addComponent(networksList, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(14))
				);
				networkFromFilePanel.setLayout(gl_networkFromFilePanel);
				{
					JLabel lblNetworks = new JLabel("Networks");
					sl_networkInputMethodPanel.putConstraint(SpringLayout.NORTH, networkInputMethodUpperPanel, 6, SpringLayout.SOUTH, lblNetworks);
					networkInputMethodPanel.add(lblNetworks);
					lblNetworks.setBackground(UIManager.getColor("List.dropLineColor"));
					lblNetworks.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
				}

				currentFeature = "nodes-number";

				JPanel networkInputFeaturesMethod = new JPanel();
				networkDefinitionSubPanel.add(networkInputFeaturesMethod, "networkInputFeaturesMethod");
				SpringLayout sl_networkInputFeaturesMethod = new SpringLayout();
				networkInputFeaturesMethod.setLayout(sl_networkInputFeaturesMethod);

				JPanel featuresInputMethodGroupPanel = new JPanel();
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.NORTH, featuresInputMethodGroupPanel, 10, SpringLayout.NORTH, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.WEST, featuresInputMethodGroupPanel, 10, SpringLayout.WEST, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.SOUTH, featuresInputMethodGroupPanel, 99, SpringLayout.NORTH, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.EAST, featuresInputMethodGroupPanel, 652, SpringLayout.WEST, networkInputFeaturesMethod);
				featuresInputMethodGroupPanel.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				networkInputFeaturesMethod.add(featuresInputMethodGroupPanel);

				featuresFileSelectionPanel = new JPanel();
				featuresFileSelectionPanel.setVisible(false); //Default state: not visible.
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.NORTH, featuresFileSelectionPanel, 30, SpringLayout.SOUTH, featuresInputMethodGroupPanel);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.WEST, featuresFileSelectionPanel, 10, SpringLayout.WEST, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.SOUTH, featuresFileSelectionPanel, 108, SpringLayout.SOUTH, featuresInputMethodGroupPanel);

				rdbtnFeaturesFromForm = new JRadioButton("Features from input form");
				rdbtnFeaturesFromForm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						featuresFileSelectionPanel.setVisible(false);
					}
				});
				featuresInputMethodGroupPanel.setLayout(new GridLayout(0, 1, 0, 0));
				rdbtnFeaturesFromForm.setSelected(true);
				featuresInputMethodGroup.add(rdbtnFeaturesFromForm);
				featuresInputMethodGroupPanel.add(rdbtnFeaturesFromForm);

				rdbtnFeaturesFromFile = new JRadioButton("Features from file");
				rdbtnFeaturesFromFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						featuresFileSelectionPanel.setVisible(true);
					}
				});
				featuresInputMethodGroup.add(rdbtnFeaturesFromFile);
				featuresInputMethodGroupPanel.add(rdbtnFeaturesFromFile);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.EAST, featuresFileSelectionPanel, 652, SpringLayout.WEST, networkInputFeaturesMethod);
				networkInputFeaturesMethod.add(featuresFileSelectionPanel);

				JLabel lblFeaturesFile = new JLabel("Features file:");

				txtFeaturesFilePath = new JTextField();
				txtFeaturesFilePath.setColumns(10);

				JButton btnOpenFeatures = new JButton("Open");
				btnOpenFeatures.addActionListener(new ActionListener() {
					//Allows to select the features file
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						int result = fileChooser.showOpenDialog(contentPanel);
						if (result == JFileChooser.APPROVE_OPTION) {
							txtFeaturesFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
						}
					}
				});
				GroupLayout gl_featuresFileSelectionPanel = new GroupLayout(featuresFileSelectionPanel);
				gl_featuresFileSelectionPanel.setHorizontalGroup(
					gl_featuresFileSelectionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblFeaturesFile)
							.addGap(8)
							.addComponent(txtFeaturesFilePath, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOpenFeatures)
							.addGap(7))
				);
				gl_featuresFileSelectionPanel.setVerticalGroup(
					gl_featuresFileSelectionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
							.addGroup(gl_featuresFileSelectionPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
									.addGap(38)
									.addComponent(lblFeaturesFile))
								.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
									.addGap(31)
									.addGroup(gl_featuresFileSelectionPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnOpenFeatures)
										.addComponent(txtFeaturesFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(17, Short.MAX_VALUE))
				);
				featuresFileSelectionPanel.setLayout(gl_featuresFileSelectionPanel);


				JPanel networkFeaturesPanel = new JPanel();
				networkDefinitionSubPanel.add(networkFeaturesPanel, "networkFeaturesPanel");
				networkFeaturesPanel.setLayout(new BorderLayout(0, 0));
				networkManualFeaturesPanel = new JPanel();
				networkFeaturesPanel.add(networkManualFeaturesPanel);
				networkManualFeaturesPanel.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				SpringLayout sl_networkManualFeaturesPanel = new SpringLayout();
				networkManualFeaturesPanel.setLayout(sl_networkManualFeaturesPanel);
				featuresSubPanel = new JPanel();
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.NORTH, featuresSubPanel, 10, SpringLayout.NORTH, networkManualFeaturesPanel);
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.SOUTH, featuresSubPanel, 88, SpringLayout.NORTH, networkManualFeaturesPanel);
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.EAST, featuresSubPanel, 644, SpringLayout.WEST, networkManualFeaturesPanel);
				networkManualFeaturesPanel.add(featuresSubPanel);
				featuresSubPanel.setLayout(new BorderLayout(0, 0));

				featureInputFormPanel = new JPanel();
				featuresSubPanel.add(featureInputFormPanel, BorderLayout.CENTER);
				featureInputFormPanel.setLayout(new CardLayout(0, 0));

				nodesPanel = new JPanel();
				featureInputFormPanel.add(nodesPanel, "nodesPanel");

				JLabel lblNumberOfNodes = new JLabel("Number of nodes:");

				txtNodesNumber = new JTextField();
				txtNodesNumber.setText("100");
				txtNodesNumber.setColumns(10);
				GroupLayout gl_nodesPanel = new GroupLayout(nodesPanel);
				gl_nodesPanel.setHorizontalGroup(
					gl_nodesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_nodesPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblNumberOfNodes)
							.addGap(8)
							.addComponent(txtNodesNumber, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
							.addContainerGap())
				);
				gl_nodesPanel.setVerticalGroup(
					gl_nodesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_nodesPanel.createSequentialGroup()
							.addGroup(gl_nodesPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_nodesPanel.createSequentialGroup()
									.addGap(11)
									.addComponent(lblNumberOfNodes))
								.addGroup(gl_nodesPanel.createSequentialGroup()
									.addGap(5)
									.addComponent(txtNodesNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				nodesPanel.setLayout(gl_nodesPanel);

				topologyPanel = new JPanel();
				featureInputFormPanel.add(topologyPanel, "topologyPanel");

				JLabel lblNetworkTopology = new JLabel("Network Topology:");

				cmbTopology = new JComboBox<String>();
				cmbTopology.setModel(new DefaultComboBoxModel<String>(new String[] {"Random (Erdos-Renyi)", "Scale Free (Barabasi-Albertz) {in}", "Scale Free (Power law) {out}", "Small World (Watts-Strogatz)"}));
				cmbTopology.setSelectedIndex(0);
				GroupLayout gl_topologyPanel = new GroupLayout(topologyPanel);
				gl_topologyPanel.setHorizontalGroup(
					gl_topologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topologyPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblNetworkTopology)
							.addGap(8)
							.addComponent(cmbTopology, GroupLayout.PREFERRED_SIZE, 498, GroupLayout.PREFERRED_SIZE))
				);
				gl_topologyPanel.setVerticalGroup(
					gl_topologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topologyPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNetworkTopology))
						.addGroup(gl_topologyPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(cmbTopology, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
				topologyPanel.setLayout(gl_topologyPanel);
				
				randomTopologyPanel = new JPanel();
				featureInputFormPanel.add(randomTopologyPanel, "randomTopologyPanel");
				
				JLabel lblNumberOfEdges = new JLabel("Number of edges:");
				txtEdgesNumber = new JTextField();
				txtEdgesNumber.setText("200");
				txtEdgesNumber.setColumns(10);
				GroupLayout gl_randomTopologyPanel = new GroupLayout(randomTopologyPanel);
				gl_randomTopologyPanel.setHorizontalGroup(
					gl_randomTopologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_randomTopologyPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblNumberOfEdges)
							.addGap(38)
							.addComponent(txtEdgesNumber, GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE))
				);
				gl_randomTopologyPanel.setVerticalGroup(
					gl_randomTopologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_randomTopologyPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblNumberOfEdges))
						.addGroup(gl_randomTopologyPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtEdgesNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
				randomTopologyPanel.setLayout(gl_randomTopologyPanel);
				
				barabasiPanel = new JPanel();
				featureInputFormPanel.add(barabasiPanel, "barabasiPanel");
				
				JLabel lblInitialNodesNumber = new JLabel("Initial nodes number:");
				
				txtNi = new JTextField();
				txtNi.setText("1");
				txtNi.setColumns(10);
				
				JLabel lblAverageConnectivity = new JLabel("Average connectivity:");
				
				txtAvgConnBA = new JTextField();
				txtAvgConnBA.setText("2");
				txtAvgConnBA.setColumns(10);
				GroupLayout gl_barabasiPanel = new GroupLayout(barabasiPanel);
				gl_barabasiPanel.setHorizontalGroup(
					gl_barabasiPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_barabasiPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblInitialNodesNumber)
							.addGap(8)
							.addComponent(txtNi, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(lblAverageConnectivity)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtAvgConnBA, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
				gl_barabasiPanel.setVerticalGroup(
					gl_barabasiPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_barabasiPanel.createSequentialGroup()
							.addGroup(gl_barabasiPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_barabasiPanel.createSequentialGroup()
									.addGap(11)
									.addComponent(lblInitialNodesNumber))
								.addGroup(gl_barabasiPanel.createSequentialGroup()
									.addGap(5)
									.addComponent(txtNi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_barabasiPanel.createSequentialGroup()
									.addGap(5)
									.addGroup(gl_barabasiPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtAvgConnBA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAverageConnectivity))))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				barabasiPanel.setLayout(gl_barabasiPanel);
				
				powerLawPanel = new JPanel();
				featureInputFormPanel.add(powerLawPanel, "powerLawPanel");
				
				JLabel lblGamma = new JLabel("Gamma:");
				
				txtGamma = new JTextField();
				txtGamma.setText("2.3");
				txtGamma.setColumns(10);
				
				JLabel lblPLAverageConnectivity = new JLabel("Average connectivity");
				
				txtavgConnPL = new JTextField();
				txtavgConnPL.setText("2");
				txtavgConnPL.setColumns(10);
				GroupLayout gl_powerLawPanel = new GroupLayout(powerLawPanel);
				gl_powerLawPanel.setHorizontalGroup(
					gl_powerLawPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_powerLawPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblGamma)
							.addGap(8)
							.addComponent(txtGamma, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(lblPLAverageConnectivity)
							.addGap(8)
							.addComponent(txtavgConnPL, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
				);
				gl_powerLawPanel.setVerticalGroup(
					gl_powerLawPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_powerLawPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblGamma))
						.addGroup(gl_powerLawPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtGamma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_powerLawPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblPLAverageConnectivity))
						.addGroup(gl_powerLawPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtavgConnPL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
				powerLawPanel.setLayout(gl_powerLawPanel);
				
				smallWorldPanel = new JPanel();
				featureInputFormPanel.add(smallWorldPanel, "smallWorldPanel");
				
				JLabel lblBeta = new JLabel("Beta:");
				
				txtBeta = new JTextField();
				txtBeta.setText("0.5");
				txtBeta.setColumns(10);
				
				JLabel lblAverageConnectivitySW = new JLabel("Average connectivity:");
				
				txtAvgConnSW = new JTextField();
				txtAvgConnSW.setText("2");
				txtAvgConnSW.setColumns(10);
				GroupLayout gl_smallWorldPanel = new GroupLayout(smallWorldPanel);
				gl_smallWorldPanel.setHorizontalGroup(
					gl_smallWorldPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblBeta)
							.addGap(8)
							.addComponent(txtBeta, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(lblAverageConnectivitySW)
							.addGap(8)
							.addComponent(txtAvgConnSW, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
				);
				gl_smallWorldPanel.setVerticalGroup(
					gl_smallWorldPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblBeta))
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtBeta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblAverageConnectivitySW))
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtAvgConnSW, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
				smallWorldPanel.setLayout(gl_smallWorldPanel);
				
				functionsTypePanel = new JPanel();
				featureInputFormPanel.add(functionsTypePanel, "functionsTypePanel");
				
				lblFunctionsType = new JLabel("Functions type:");
				
				cmbFunctionsType = new JComboBox<String>();
				cmbFunctionsType.setModel(new DefaultComboBoxModel<String>(new String[] {"Boolean"}));
				
				chckbxCompletelyDefinedFunctions = new JCheckBox("Completely defined functions");
				chckbxCompletelyDefinedFunctions.setSelected(true);
				GroupLayout gl_functionsTypePanel = new GroupLayout(functionsTypePanel);
				gl_functionsTypePanel.setHorizontalGroup(
					gl_functionsTypePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_functionsTypePanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblFunctionsType)
							.addGap(38)
							.addComponent(cmbFunctionsType, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
							.addGap(75)
							.addComponent(chckbxCompletelyDefinedFunctions)
							.addContainerGap())
				);
				gl_functionsTypePanel.setVerticalGroup(
					gl_functionsTypePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_functionsTypePanel.createSequentialGroup()
							.addGroup(gl_functionsTypePanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_functionsTypePanel.createSequentialGroup()
									.addGap(10)
									.addComponent(lblFunctionsType))
								.addGroup(gl_functionsTypePanel.createSequentialGroup()
									.addGap(5)
									.addGroup(gl_functionsTypePanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(cmbFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(chckbxCompletelyDefinedFunctions))))
							.addContainerGap(7, Short.MAX_VALUE))
				);
				functionsTypePanel.setLayout(gl_functionsTypePanel);
				
				functionsRatesPanel = new JPanel();
				featureInputFormPanel.add(functionsRatesPanel, "functionsRatesPanel");
				
				JLabel lblRandom = new JLabel("Random:");
				
				txtRandomFunctionsType = new JTextField();
				txtRandomFunctionsType.setText("0.2");
				txtRandomFunctionsType.setColumns(10);
				
				JLabel lblBias_1 = new JLabel("Bias:");
				
				txtBiasFunctionsType = new JTextField();
				txtBiasFunctionsType.setText("0.2");
				txtBiasFunctionsType.setColumns(10);
				
				JLabel lblBiasValue = new JLabel("Bias value:");
				
				txtBiasValue = new JTextField();
				txtBiasValue.setText("0.7");
				txtBiasValue.setColumns(10);
				
				JLabel lblAnd = new JLabel("And:");
				
				txtAndFunctionsType = new JTextField();
				txtAndFunctionsType.setText("0.2");
				txtAndFunctionsType.setColumns(10);
				
				JLabel lblOr = new JLabel("Or:");
				
				txtOrFunctionsType = new JTextField();
				txtOrFunctionsType.setText("0.2");
				txtOrFunctionsType.setColumns(10);
				
				JLabel lblCanalizing = new JLabel("Canalizing:");
				
				txtCanalizingFunctionsType = new JTextField();
				txtCanalizingFunctionsType.setText("0.2");
				txtCanalizingFunctionsType.setColumns(10);
				GroupLayout gl_functionsRatesPanel = new GroupLayout(functionsRatesPanel);
				gl_functionsRatesPanel.setHorizontalGroup(
					gl_functionsRatesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(8)
							.addComponent(lblRandom)
							.addGap(8)
							.addComponent(txtRandomFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(lblBias_1)
							.addGap(8)
							.addComponent(txtBiasFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(lblBiasValue)
							.addGap(8)
							.addComponent(txtBiasValue, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(lblAnd)
							.addGap(8)
							.addComponent(txtAndFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(lblOr)
							.addGap(8)
							.addComponent(txtOrFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(lblCanalizing)
							.addGap(8)
							.addComponent(txtCanalizingFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
				);
				gl_functionsRatesPanel.setVerticalGroup(
					gl_functionsRatesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblRandom))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtRandomFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblBias_1))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtBiasFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblBiasValue))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtBiasValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblAnd))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtAndFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblOr))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtOrFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblCanalizing))
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(txtCanalizingFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
				functionsRatesPanel.setLayout(gl_functionsRatesPanel);
				

				JPanel featuresNextPreviousPanel = new JPanel();
				FlowLayout fl_featuresNextPreviousPanel = (FlowLayout) featuresNextPreviousPanel.getLayout();
				fl_featuresNextPreviousPanel.setAlignment(FlowLayout.RIGHT);
				featuresSubPanel.add(featuresNextPreviousPanel, BorderLayout.SOUTH);

				JButton btnAddFeature = new JButton("Add feature");
				btnAddFeature.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(currentFeature.equals("nodes-number")){
							if(!txtNodesNumber.getText().equals("")){
								//Sets the number of nodes in the simulation property list.
								simulationFeatures.setProperty(SimulationFeaturesConstants.NODES, txtNodesNumber.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.NODES, txtNodesNumber.getText()});
								//Next step: Topology
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "topologyPanel");	
								currentFeature = "topology";
							}
						}else if(currentFeature.equals("topology")){
							
							if(cmbTopology.getSelectedItem().equals("Random (Erdos-Renyi)")){
								//Sets the network topology
								simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.RANDOM_TOPOLOGY);
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.RANDOM_TOPOLOGY});
								//Next step: Erdos-Renyi parameters
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "randomTopologyPanel");	
								currentFeature = "random-topology-parameters";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Random topology parameters (Erdos-Renyi)");
								networkManualFeaturesPanel.repaint();
							}else if(cmbTopology.getSelectedItem().equals("Scale Free (Barabasi-Albertz)")){
								//Sets the network topology
								simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
								simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM);
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM});
								//Next step: Barabasi-Albertz parameters
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "barabasiPanel");	
								currentFeature = "barabasi-parameters";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Scale Free topology parameters (Barabasi-Albertz)");
								networkManualFeaturesPanel.repaint();
							}else if(cmbTopology.getSelectedItem().equals("Scale Free (Power law)")){
								//Sets the network topology
								simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
								simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.POWER_LAW_ALGORITHM);
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.POWER_LAW_ALGORITHM});
								//Next step: Power Law parameters
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "powerLawPanel");	
								currentFeature = "power-law-parameters";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Scale Free topology parameters (Power Law)");
								networkManualFeaturesPanel.repaint();
							}else if(cmbTopology.getSelectedItem().equals("Small World (Watts-Strogatz)")){
								//Sets the network topology
								simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SMALL_WORLD_TOPOLOGY);
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SMALL_WORLD_TOPOLOGY});
								//Next step: Small World parameters
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "smallWorldPanel");	
								currentFeature = "small-world-parameters";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Small world topology parameters (Watts Strogatz)");
								networkManualFeaturesPanel.repaint();
							}
						}else if(currentFeature.equals("random-topology-parameters")){
							if(!txtEdgesNumber.getText().equals("")){
								//Sets the number of edges
								simulationFeatures.setProperty(SimulationFeaturesConstants.EDGES, txtEdgesNumber.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.EDGES, txtEdgesNumber.getText()});
								//Next step: Functions type
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
								currentFeature = "functions-type";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Functions");
								networkManualFeaturesPanel.repaint();
							}
						}else if(currentFeature.equals("barabasi-parameters")){
							if(!txtNi.getText().equals("") && !txtAvgConnBA.getText().equals("")){
								//Sets the ni and k parameters
								simulationFeatures.setProperty(SimulationFeaturesConstants.NI, txtNi.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnBA.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.NI, txtNi.getText()});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnBA.getText()});
								//Next step: Functions type
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
								currentFeature = "functions-type";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Functions");
								networkManualFeaturesPanel.repaint();
							}
						}else if(currentFeature.equals("power-law-parameters")){
							if(!txtGamma.getText().equals("") && !txtavgConnPL.getText().equals("")){
								//Sets the gamma and k parameters
								simulationFeatures.setProperty(SimulationFeaturesConstants.GAMMA, txtGamma.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtavgConnPL.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.GAMMA, txtGamma.getText()});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtavgConnPL.getText()});
								//Next step: Functions type
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
								currentFeature = "functions-type";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Functions");
								networkManualFeaturesPanel.repaint();
							}
						}else if(currentFeature.equals("small-world-parameters")){
							if(!txtBeta.getText().equals("") && !txtAvgConnSW.getText().equals("")){
								//Sets the beta and k parameters
								simulationFeatures.setProperty(SimulationFeaturesConstants.BETA, txtBeta.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnSW.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.BETA, txtBeta.getText()});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnSW.getText()});
								//Next step: Functions type
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
								currentFeature = "functions-type";
								((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Functions");
								networkManualFeaturesPanel.repaint();
							}
						}else if(currentFeature.equals("functions-type")){
							//Sets the functions type
							simulationFeatures.setProperty(SimulationFeaturesConstants.FUNCTION_TYPE, cmbFunctionsType.getSelectedItem().toString());
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.FUNCTION_TYPE, cmbFunctionsType.getSelectedItem().toString()});
							if(chckbxCompletelyDefinedFunctions.isSelected()){
								simulationFeatures.setProperty(SimulationFeaturesConstants.COMPLETELY_DEFINED_FUNCTIONS, SimulationFeaturesConstants.YES);
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.COMPLETELY_DEFINED_FUNCTIONS, SimulationFeaturesConstants.YES});
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.COMPLETELY_DEFINED_FUNCTIONS, SimulationFeaturesConstants.NO);
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.COMPLETELY_DEFINED_FUNCTIONS, SimulationFeaturesConstants.NO});
							}
							//Next step: Functions type
							((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsRatesPanel");	
							currentFeature = "functions-rates";
							((TitledBorder)networkManualFeaturesPanel.getBorder()).setTitle("Functions type ratio");
							networkManualFeaturesPanel.repaint();
						}else if(currentFeature.equals("functions-rates")){
							//Sets the random type
							if(txtRandomFunctionsType.getText().equals("")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.RANDOM_TYPE, "0.0");
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.RANDOM_TYPE, txtRandomFunctionsType.getText());
							}
							
							//Sets the bias type functions and the bias value
							if(txtBiasFunctionsType.getText().equals("")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_TYPE, "0.0");
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_TYPE, txtBiasFunctionsType.getText());
							}
							
							if(txtBiasValue.getText().equals("")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_VALUE, "0.0");
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_VALUE, txtBiasValue.getText());
							}
							
							//Sets the And type functions
							if(txtAndFunctionsType.getText().equals("")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.AND_FUNCTION_TYPE, "0.0");
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.AND_FUNCTION_TYPE, txtAndFunctionsType.getText());
							}
							
							//Sets the Or type functions
							if(txtOrFunctionsType.getText().equals("")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.OR_FUNCTION_TYPE, "0.0");
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.OR_FUNCTION_TYPE, txtOrFunctionsType.getText());
							}
							
							//Sets the canalizing type functions
							if(txtCanalizingFunctionsType.getText().equals("")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.CANALIZED_TYPE, "0.0");
							}else{
								simulationFeatures.setProperty(SimulationFeaturesConstants.CANALIZED_TYPE, txtCanalizingFunctionsType.getText());
							}
							
							//Shows the features in the table.
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.RANDOM_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.RANDOM_TYPE)});
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.BIAS_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.BIAS_TYPE)});
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.BIAS_VALUE, simulationFeatures.getProperty(SimulationFeaturesConstants.BIAS_VALUE)});
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AND_FUNCTION_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.AND_FUNCTION_TYPE)});
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.OR_FUNCTION_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.OR_FUNCTION_TYPE)});
							networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.CANALIZED_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.CANALIZED_TYPE)});
							
							//Next step: experiments page
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "experimentsPanel");	
							form = "attractors-params";
							btnNext.setEnabled(true);
						}
					}
				});
				featuresNextPreviousPanel.add(btnAddFeature);

				JPanel networkFeaturesTablePanel = new JPanel();
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.WEST, featuresSubPanel, 0, SpringLayout.WEST, networkFeaturesTablePanel);
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.NORTH, networkFeaturesTablePanel, 121, SpringLayout.NORTH, networkManualFeaturesPanel);
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.WEST, networkFeaturesTablePanel, 10, SpringLayout.WEST, networkManualFeaturesPanel);
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.SOUTH, networkFeaturesTablePanel, -18, SpringLayout.SOUTH, networkManualFeaturesPanel);
				sl_networkManualFeaturesPanel.putConstraint(SpringLayout.EAST, networkFeaturesTablePanel, -6, SpringLayout.EAST, networkManualFeaturesPanel);
				
				//Creates and sets the network features table
				networkManualFeaturesPanel.add(networkFeaturesTablePanel);
				networkFeaturesTable = new JTable();
				networkFeaturesTable.setRowSelectionAllowed(false);
				networkFeaturesTable.setBackground(SystemColor.window);
				networkFeaturesTable.setFillsViewportHeight(true);
				networkFeaturesTableModel = new DefaultTableModel();
				String[] networkFeaturesTableModelHeaders = {"Feature", "Value"};
				networkFeaturesTableModel.setColumnIdentifiers(networkFeaturesTableModelHeaders);
				networkFeaturesTablePanel.setLayout(new BorderLayout(0, 0));
				networkFeaturesTable.setModel(networkFeaturesTableModel);
				networkFeaturesTablePanel.add(new JScrollPane(networkFeaturesTable));
			}
		
			pnlNetworkEditing = new JPanel();
			contentPanel.add(pnlNetworkEditing, "pnlNetworkEditing");
			pnlNetworkEditing.setLayout(new BorderLayout(0, 0));
			
			lblNetworkEditing = new JLabel("Network editing");
			lblNetworkEditing.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			pnlNetworkEditing.add(lblNetworkEditing, BorderLayout.NORTH);
			
			pnlInnerNetworkEditing = new JPanel();
			pnlNetworkEditing.add(pnlInnerNetworkEditing, BorderLayout.CENTER);
			SpringLayout sl_pnlInnerNetworkEditing = new SpringLayout();
			pnlInnerNetworkEditing.setLayout(sl_pnlInnerNetworkEditing);
			
			JLabel lblTotalNodes = new JLabel("Total nodes:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblTotalNodes, 10, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblTotalNodes, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblTotalNodes);
			
			txtEditingNodesNumber = new JTextField();
			txtEditingNodesNumber.setText("0");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingNodesNumber, -6, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingNodesNumber, 6, SpringLayout.EAST, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingNodesNumber, 102, SpringLayout.EAST, lblTotalNodes);
			pnlInnerNetworkEditing.add(txtEditingNodesNumber);
			txtEditingNodesNumber.setColumns(10);
			
			lblTotalEdges = new JLabel("Total edges:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblTotalEdges, 0, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblTotalEdges, 6, SpringLayout.EAST, txtEditingNodesNumber);
			pnlInnerNetworkEditing.add(lblTotalEdges);
			
			txtEditingEdgesNumber = new JTextField();
			txtEditingEdgesNumber.setText("0");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingEdgesNumber, -6, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingEdgesNumber, 6, SpringLayout.EAST, lblTotalEdges);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingEdgesNumber, -277, SpringLayout.EAST, pnlInnerNetworkEditing);
			txtEditingEdgesNumber.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingEdgesNumber);
			
			JLabel lblExcludeAsSource = new JLabel("Exclude the following genes from source or destination genes set.");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblExcludeAsSource, 16, SpringLayout.SOUTH, txtEditingNodesNumber);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblExcludeAsSource, 0, SpringLayout.WEST, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblExcludeAsSource, -10, SpringLayout.EAST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblExcludeAsSource);
			
			txtNoSourceGenes = new JTextArea();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtNoSourceGenes, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, txtNoSourceGenes, -168, SpringLayout.SOUTH, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(txtNoSourceGenes);
			
			lblEachGeneName = new JLabel("Each gene name must be separated by a ,");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblEachGeneName, 6, SpringLayout.SOUTH, lblExcludeAsSource);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblEachGeneName, 0, SpringLayout.WEST, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblEachGeneName, 572, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblEachGeneName);
			
			lblSourceGenes = new JLabel("Source genes:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, lblSourceGenes, -264, SpringLayout.SOUTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtNoSourceGenes, 5, SpringLayout.SOUTH, lblSourceGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblSourceGenes, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblSourceGenes, 123, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblSourceGenes);
			
			txtNoDestinationGenes = new JTextArea();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtNoDestinationGenes, 353, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtNoDestinationGenes, -10, SpringLayout.EAST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtNoSourceGenes, -44, SpringLayout.WEST, txtNoDestinationGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtNoDestinationGenes, 0, SpringLayout.NORTH, txtNoSourceGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, txtNoDestinationGenes, 0, SpringLayout.SOUTH, txtNoSourceGenes);
			pnlInnerNetworkEditing.add(txtNoDestinationGenes);
			
			lblDestinationGenes = new JLabel("Target genes:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblDestinationGenes, 6, SpringLayout.SOUTH, lblEachGeneName);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblDestinationGenes, 0, SpringLayout.WEST, txtNoDestinationGenes);
			pnlInnerNetworkEditing.add(lblDestinationGenes);
			
			lblFixedNumberOf = new JLabel("Fixed number of inputs:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblFixedNumberOf, 0, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblFixedNumberOf, 6, SpringLayout.EAST, txtEditingEdgesNumber);
			pnlInnerNetworkEditing.add(lblFixedNumberOf);
			
			txtFixedInputs = new JTextField();
			txtFixedInputs.setText("-1");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtFixedInputs, -6, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtFixedInputs, 6, SpringLayout.EAST, lblFixedNumberOf);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtFixedInputs, 0, SpringLayout.EAST, lblExcludeAsSource);
			txtFixedInputs.setColumns(10);
			pnlInnerNetworkEditing.add(txtFixedInputs);
			
			cmbEditingFunctionType = new JComboBox<String>();
			cmbEditingFunctionType.setModel(new DefaultComboBoxModel<String>(new String[] {"Boolean"}));
			pnlInnerNetworkEditing.add(cmbEditingFunctionType);
			
			JLabel lblReplaceTheUndefined = new JLabel("Replace the undefined functions with:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, cmbEditingFunctionType, 11, SpringLayout.SOUTH, lblReplaceTheUndefined);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblReplaceTheUndefined, 16, SpringLayout.SOUTH, txtNoSourceGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblReplaceTheUndefined, 0, SpringLayout.WEST, lblTotalNodes);
			pnlInnerNetworkEditing.add(lblReplaceTheUndefined);
			
			JLabel lblFunctionsType_1 = new JLabel("Functions type:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, cmbEditingFunctionType, 6, SpringLayout.EAST, lblFunctionsType_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblFunctionsType_1, 15, SpringLayout.SOUTH, lblReplaceTheUndefined);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblFunctionsType_1, 0, SpringLayout.WEST, lblTotalNodes);
			pnlInnerNetworkEditing.add(lblFunctionsType_1);
			
			chckbxComplitellyDefined = new JCheckBox("Complitely defined");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, cmbEditingFunctionType, -29, SpringLayout.WEST, chckbxComplitellyDefined);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, chckbxComplitellyDefined, 0, SpringLayout.NORTH, cmbEditingFunctionType);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, chckbxComplitellyDefined, 0, SpringLayout.EAST, lblExcludeAsSource);
			pnlInnerNetworkEditing.add(chckbxComplitellyDefined);
			
			JLabel lblRandom_1 = new JLabel("Random:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblRandom_1, 13, SpringLayout.SOUTH, lblFunctionsType_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblRandom_1, 0, SpringLayout.WEST, lblTotalNodes);
			pnlInnerNetworkEditing.add(lblRandom_1);
			
			txtEditingRandomType = new JTextField();
			txtEditingRandomType.setText("0.2");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingRandomType, 7, SpringLayout.SOUTH, lblFunctionsType_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingRandomType, 6, SpringLayout.EAST, lblRandom_1);
			pnlInnerNetworkEditing.add(txtEditingRandomType);
			txtEditingRandomType.setColumns(10);
			
			lblBias = new JLabel("Bias:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblBias, 0, SpringLayout.NORTH, lblRandom_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblBias, 10, SpringLayout.WEST, cmbEditingFunctionType);
			pnlInnerNetworkEditing.add(lblBias);
			
			txtEditingBiasType = new JTextField();
			txtEditingBiasType.setText("0.2");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingRandomType, -46, SpringLayout.WEST, txtEditingBiasType);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingBiasType, 159, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingBiasType, -461, SpringLayout.EAST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingBiasType, 0, SpringLayout.SOUTH, cmbEditingFunctionType);
			txtEditingBiasType.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingBiasType);
			
			lblBiasValue_1 = new JLabel("Bias value:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblBiasValue_1, 6, SpringLayout.SOUTH, cmbEditingFunctionType);
			pnlInnerNetworkEditing.add(lblBiasValue_1);
			
			txtEditingBiasValue = new JTextField();
			txtEditingBiasValue.setText("0.5");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingBiasValue, 286, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblBiasValue_1, -6, SpringLayout.WEST, txtEditingBiasValue);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingBiasValue, -6, SpringLayout.NORTH, lblRandom_1);
			txtEditingBiasValue.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingBiasValue);
			
			lblAnd_1 = new JLabel("And:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingBiasValue, -18, SpringLayout.WEST, lblAnd_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblAnd_1, 0, SpringLayout.NORTH, lblRandom_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblAnd_1, 0, SpringLayout.WEST, txtNoDestinationGenes);
			pnlInnerNetworkEditing.add(lblAnd_1);
			
			txtEditingAndType = new JTextField();
			txtEditingAndType.setText("0.2");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingAndType, -6, SpringLayout.NORTH, lblRandom_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingAndType, 6, SpringLayout.EAST, lblAnd_1);
			pnlInnerNetworkEditing.add(txtEditingAndType);
			txtEditingAndType.setColumns(10);
			
			lblOr_1 = new JLabel("Or:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingAndType, -6, SpringLayout.WEST, lblOr_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblOr_1, 0, SpringLayout.NORTH, lblRandom_1);
			pnlInnerNetworkEditing.add(lblOr_1);
			
			txtEditingOrType = new JTextField();
			txtEditingOrType.setText("0.2");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingOrType, 468, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblOr_1, -6, SpringLayout.WEST, txtEditingOrType);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingOrType, 4, SpringLayout.SOUTH, chckbxComplitellyDefined);
			txtEditingOrType.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingOrType);
			
			lblCanalizing_1 = new JLabel("Canalizing:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingOrType, -10, SpringLayout.WEST, lblCanalizing_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblCanalizing_1, 0, SpringLayout.NORTH, lblRandom_1);
			pnlInnerNetworkEditing.add(lblCanalizing_1);
			
			txtEditingCanalizingType = new JTextField();
			txtEditingCanalizingType.setText("0.2");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingCanalizingType, 603, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingCanalizingType, -10, SpringLayout.EAST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblCanalizing_1, -6, SpringLayout.WEST, txtEditingCanalizingType);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingCanalizingType, 6, SpringLayout.SOUTH, chckbxComplitellyDefined);
			txtEditingCanalizingType.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingCanalizingType);
			
			experimentsPanel = new JPanel();
			contentPanel.add(experimentsPanel, "experimentsPanel");
			experimentsPanel.setLayout(new BorderLayout(0, 0));
			
			lblExperiments = new JLabel("Experiments");
			lblExperiments.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			experimentsPanel.add(lblExperiments, BorderLayout.NORTH);
			
			experimentsSubPanel = new JPanel();
			experimentsSubPanel.setBorder(new TitledBorder(null, "Attractors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			experimentsPanel.add(experimentsSubPanel, BorderLayout.CENTER);
			experimentsSubPanel.setLayout(new CardLayout(0, 0));
			
			attractorsPanel = new JPanel();
			experimentsSubPanel.add(attractorsPanel, "attractorsPanel");
			
			lblInitialConditions = new JLabel("Initial conditions:");
			
			txtInitialConditions = new JTextField();
			txtInitialConditions.setText("100");
			txtInitialConditions.setColumns(10);
			
			lblSamplingMethod = new JLabel("Sampling method:");
			
			cmbSamplingType = new JComboBox<String>();
			cmbSamplingType.addItemListener(new ItemListener() {
				//Shows and hides the initial conditions form.
				public void itemStateChanged(ItemEvent e) {
					if(cmbSamplingType.getSelectedItem().equals(SimulationFeaturesConstants.BRUTE_FORCE)){
						txtInitialConditions.setVisible(false);
						lblInitialConditions.setVisible(false);
					}else{
						txtInitialConditions.setVisible(true);
						lblInitialConditions.setVisible(true);
					}
				}
			});
		
			cmbSamplingType.setModel(new DefaultComboBoxModel<String>(new String[] {SimulationFeaturesConstants.PARTIAL_SAMPLING, SimulationFeaturesConstants.BRUTE_FORCE}));
			
			JLabel lblSimulationStepsCutoff = new JLabel("Simulation steps cutoff (-1 means no cutoff)");
			
			txtCutoff = new JTextField();
			txtCutoff.setText("-1");
			txtCutoff.setColumns(10);
			GroupLayout gl_attractorsPanel = new GroupLayout(attractorsPanel);
			gl_attractorsPanel.setHorizontalGroup(
				gl_attractorsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_attractorsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_attractorsPanel.createSequentialGroup()
								.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblSamplingMethod)
									.addComponent(lblInitialConditions))
								.addGap(18)
								.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(cmbSamplingType, GroupLayout.PREFERRED_SIZE, 486, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(txtCutoff, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
										.addGroup(Alignment.LEADING, gl_attractorsPanel.createSequentialGroup()
											.addGap(6)
											.addComponent(txtInitialConditions, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE)))))
							.addComponent(lblSimulationStepsCutoff))
						.addContainerGap(26, Short.MAX_VALUE))
			);
			gl_attractorsPanel.setVerticalGroup(
				gl_attractorsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_attractorsPanel.createSequentialGroup()
						.addGap(14)
						.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSamplingMethod)
							.addComponent(cmbSamplingType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblInitialConditions)
							.addComponent(txtInitialConditions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSimulationStepsCutoff)
							.addComponent(txtCutoff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(233, Short.MAX_VALUE))
			);
			attractorsPanel.setLayout(gl_attractorsPanel);
			
			atmPanel = new JPanel();
			experimentsSubPanel.add(atmPanel, "atmPanel");
			atmPanel.setLayout(new BorderLayout(0, 0));
			
			atmCalulationSelectionPanel = new JPanel();
			atmPanel.add(atmCalulationSelectionPanel, BorderLayout.NORTH);
			atmCalulationSelectionPanel.setLayout(new GridLayout(0, 2, 0, 0));
			
			chkAtmCalculation = new JCheckBox("Perform ATM calculation");
			chkAtmCalculation.setHorizontalAlignment(SwingConstants.LEFT);
			chkAtmCalculation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Shows the param selection panel only if the checkbox is selected
					if(chkAtmCalculation.isSelected()){
						atmCalculationParmPanel.setVisible(true);
						chkAvalanchesAndSensitivity.setEnabled(true);
					}else{
						atmCalculationParmPanel.setVisible(false);
						chkAvalanchesAndSensitivity.setEnabled(false);
					}
					
				}
			});
			chkAtmCalculation.setSelected(true);
			atmCalulationSelectionPanel.add(chkAtmCalculation);
			
			lblPerformAtmCalculation = new JLabel("");
			atmCalulationSelectionPanel.add(lblPerformAtmCalculation);
			
			chkAvalanchesAndSensitivity = new JCheckBox("Compute avalanches and sensitivity");
			atmCalulationSelectionPanel.add(chkAvalanchesAndSensitivity);
			
			atmCalculationParmPanel = new JPanel();
			atmPanel.add(atmCalculationParmPanel, BorderLayout.CENTER);
			atmCalculationParmPanel.setLayout(new BorderLayout(0, 0));
			
			perturbationTypePanel = new JPanel();
			atmCalculationParmPanel.add(perturbationTypePanel, BorderLayout.NORTH);
			perturbationTypePanel.setLayout(new GridLayout(0, 2, 0, 0));
			
			lblPerturbationType = new JLabel("Perturbation type:");
			perturbationTypePanel.add(lblPerturbationType);
			
			mutationsTypeComboBox = new JComboBox<String>();
			mutationsTypeComboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(mutationsTypeComboBox.getSelectedItem().equals("Flip")){
						((CardLayout)variableExperimentsPanel.getLayout()).show(variableExperimentsPanel, "flipsPanel");	
					}else{
						((CardLayout)variableExperimentsPanel.getLayout()).show(variableExperimentsPanel, "temporaryMutationsPanel");
					}
				}
			});
			
			mutationsTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {SimulationFeaturesConstants.FLIP_MUTATIONS, SimulationFeaturesConstants.KNOCKIN_KNOCKOUT_MUTATIONS}));
			perturbationTypePanel.add(mutationsTypeComboBox);
			
			commonExperimentsPanel = new JPanel();
			atmCalculationParmPanel.add(commonExperimentsPanel, BorderLayout.SOUTH);
			
			lblRatioOfAttractors = new JLabel("Ratio of attractors states to perturb:");
			
			txtRatioStatesMutations = new JTextField();
			txtRatioStatesMutations.setText("0.5");
			txtRatioStatesMutations.setColumns(10);
			
			lblNewLabel_2 = new JLabel("Number of experiments for the same node:");
			
			txtExperimentsNumber = new JTextField();
			txtExperimentsNumber.setText("1");
			txtExperimentsNumber.setColumns(10);
			GroupLayout gl_commonExperimentsPanel = new GroupLayout(commonExperimentsPanel);
			gl_commonExperimentsPanel.setHorizontalGroup(
				gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_commonExperimentsPanel.createSequentialGroup()
						.addComponent(lblRatioOfAttractors, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
						.addGap(8)
						.addComponent(txtRatioStatesMutations, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_commonExperimentsPanel.createSequentialGroup()
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
						.addGap(8)
						.addComponent(txtExperimentsNumber, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE))
			);
			gl_commonExperimentsPanel.setVerticalGroup(
				gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_commonExperimentsPanel.createSequentialGroup()
						.addGroup(gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblRatioOfAttractors, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtRatioStatesMutations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtExperimentsNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
			);
			commonExperimentsPanel.setLayout(gl_commonExperimentsPanel);
			
			variableExperimentsPanel = new JPanel();
			variableExperimentsPanel.setForeground(new Color(0, 0, 0));
			variableExperimentsPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			atmCalculationParmPanel.add(variableExperimentsPanel, BorderLayout.CENTER);
			variableExperimentsPanel.setLayout(new CardLayout(0, 0));
			
			flipsPanel = new JPanel();
			variableExperimentsPanel.add(flipsPanel, "flipsPanel");
			
			lblNumberOfNodes_1 = new JLabel("Number of nodes to flip:");
			
			txtNumberOfFlips = new JTextField();
			txtNumberOfFlips.setText("1");
			txtNumberOfFlips.setColumns(10);
			
			lblMinFlipDuration = new JLabel("Min flip duration:");
			
			txtMinFlipTimes = new JTextField();
			txtMinFlipTimes.setText("1");
			txtMinFlipTimes.setColumns(10);
			
			lblMaxFlipDuration = new JLabel("Max flip duration:");
			
			txtMaxFlipTimes = new JTextField();
			txtMaxFlipTimes.setText("1");
			txtMaxFlipTimes.setColumns(10);
			GroupLayout gl_flipsPanel = new GroupLayout(flipsPanel);
			gl_flipsPanel.setHorizontalGroup(
				gl_flipsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_flipsPanel.createSequentialGroup()
						.addGap(8)
						.addComponent(lblNumberOfNodes_1)
						.addGap(8)
						.addComponent(txtNumberOfFlips, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_flipsPanel.createSequentialGroup()
						.addGap(54)
						.addComponent(lblMinFlipDuration)
						.addGap(8)
						.addComponent(txtMinFlipTimes, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_flipsPanel.createSequentialGroup()
						.addGap(51)
						.addComponent(lblMaxFlipDuration)
						.addGap(8)
						.addComponent(txtMaxFlipTimes, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE))
			);
			gl_flipsPanel.setVerticalGroup(
				gl_flipsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_flipsPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_flipsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_flipsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblNumberOfNodes_1))
							.addComponent(txtNumberOfFlips, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_flipsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_flipsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblMinFlipDuration))
							.addComponent(txtMinFlipTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_flipsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_flipsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblMaxFlipDuration))
							.addComponent(txtMaxFlipTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
			);
			flipsPanel.setLayout(gl_flipsPanel);
			
			temporaryMutationsPanel = new JPanel();
			variableExperimentsPanel.add(temporaryMutationsPanel, "temporaryMutationsPanel");
			
			lblNumberOfNodes_2 = new JLabel("Number of nodes to knock-in:");
			
			txtKnockInNodes = new JTextField();
			txtKnockInNodes.setText("1");
			txtKnockInNodes.setColumns(10);
			
			lblMinKnockinTime = new JLabel("Min knock-in time:");
			
			txtMinKnockInTimes = new JTextField();
			txtMinKnockInTimes.setText("1");
			txtMinKnockInTimes.setColumns(10);
			
			lblMaxKnockinTime = new JLabel("Max knock-in time:");
			
			txtMaxKnockInTimes = new JTextField();
			txtMaxKnockInTimes.setText("1");
			txtMaxKnockInTimes.setColumns(10);
			
			lblNumberOfNodes_3 = new JLabel("Number of nodes to knock-out:");
			
			txtKnockOutNodes = new JTextField();
			txtKnockOutNodes.setText("1");
			txtKnockOutNodes.setColumns(10);
			
			lblNewLabel_3 = new JLabel("Min knock-out times:");
			
			txtMinKnockOutTimes = new JTextField();
			txtMinKnockOutTimes.setText("1");
			txtMinKnockOutTimes.setColumns(10);
			
			lblNewLabel_4 = new JLabel("Max knock-out times:");
			
			txtMaxKnockOutTimes = new JTextField();
			txtMaxKnockOutTimes.setText("1");
			txtMaxKnockOutTimes.setColumns(10);
			GroupLayout gl_temporaryMutationsPanel = new GroupLayout(temporaryMutationsPanel);
			gl_temporaryMutationsPanel.setHorizontalGroup(
				gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(17)
						.addComponent(lblNumberOfNodes_2)
						.addGap(8)
						.addComponent(txtKnockInNodes, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(88)
						.addComponent(lblMinKnockinTime)
						.addGap(8)
						.addComponent(txtMinKnockInTimes, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(85)
						.addComponent(lblMaxKnockinTime)
						.addGap(8)
						.addComponent(txtMaxKnockInTimes, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(8)
						.addComponent(lblNumberOfNodes_3)
						.addGap(8)
						.addComponent(txtKnockOutNodes, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(72)
						.addComponent(lblNewLabel_3)
						.addGap(8)
						.addComponent(txtMinKnockOutTimes, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(69)
						.addComponent(lblNewLabel_4)
						.addGap(8)
						.addComponent(txtMaxKnockOutTimes, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
			);
			gl_temporaryMutationsPanel.setVerticalGroup(
				gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblNumberOfNodes_2))
							.addComponent(txtKnockInNodes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblMinKnockinTime))
							.addComponent(txtMinKnockInTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblMaxKnockinTime))
							.addComponent(txtMaxKnockInTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(32)
						.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblNumberOfNodes_3))
							.addComponent(txtKnockOutNodes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblNewLabel_3))
							.addComponent(txtMinKnockOutTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
								.addGap(6)
								.addComponent(lblNewLabel_4))
							.addComponent(txtMaxKnockOutTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
			);
			temporaryMutationsPanel.setLayout(gl_temporaryMutationsPanel);
			
			treeMatchingPanel = new JPanel();
			contentPanel.add(treeMatchingPanel, "treeMatchingPanel");
			treeMatchingPanel.setLayout(new BorderLayout(0, 0));
			
			lblTreeMatching = new JLabel("Tree matching");
			treeMatchingPanel.add(lblTreeMatching, BorderLayout.NORTH);
			
			treeMatchingSubPanel = new JPanel();
			treeMatchingPanel.add(treeMatchingSubPanel, BorderLayout.CENTER);
			SpringLayout sl_treeMatchingSubPanel = new SpringLayout();
			treeMatchingSubPanel.setLayout(sl_treeMatchingSubPanel);
			
			treeMatchingTaskSelectPanel = new JPanel();
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, treeMatchingTaskSelectPanel, 5, SpringLayout.NORTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, treeMatchingTaskSelectPanel, 10, SpringLayout.WEST, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, treeMatchingTaskSelectPanel, 652, SpringLayout.WEST, treeMatchingSubPanel);
			treeMatchingSubPanel.add(treeMatchingTaskSelectPanel);
			GridBagLayout gbl_treeMatchingTaskSelectPanel = new GridBagLayout();
			gbl_treeMatchingTaskSelectPanel.columnWidths = new int[]{220, 202, 0};
			gbl_treeMatchingTaskSelectPanel.rowHeights = new int[]{23, 0};
			gbl_treeMatchingTaskSelectPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_treeMatchingTaskSelectPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			treeMatchingTaskSelectPanel.setLayout(gbl_treeMatchingTaskSelectPanel);
			
			chckbxMatchNetworksWith = new JCheckBox("Match networks with a tree");
			chckbxMatchNetworksWith.setSelected(true);
			GridBagConstraints gbc_chckbxMatchNetworksWith = new GridBagConstraints();
			gbc_chckbxMatchNetworksWith.insets = new Insets(0, 0, 0, 5);
			gbc_chckbxMatchNetworksWith.anchor = GridBagConstraints.NORTHWEST;
			gbc_chckbxMatchNetworksWith.gridx = 0;
			gbc_chckbxMatchNetworksWith.gridy = 0;
			treeMatchingTaskSelectPanel.add(chckbxMatchNetworksWith, gbc_chckbxMatchNetworksWith);
			
			JPanel InputTreePanel = new JPanel();
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, InputTreePanel, 6, SpringLayout.SOUTH, treeMatchingTaskSelectPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, InputTreePanel, 10, SpringLayout.WEST, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.SOUTH, InputTreePanel, 337, SpringLayout.SOUTH, treeMatchingTaskSelectPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, InputTreePanel, 652, SpringLayout.WEST, treeMatchingSubPanel);
			treeMatchingSubPanel.add(InputTreePanel);
			
			JLabel lblTreeFile = new JLabel("Tree file:");
			
			txtTreeFilePath = new JTextField();
			txtTreeFilePath.setColumns(10);
			
			JButton btnOpen = new JButton("Open");
			btnOpen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Opens the frame for imports the tree from file.
					JFileChooser fileChooser = new JFileChooser();
					int result = fileChooser.showOpenDialog(contentPanel);
					if (result == JFileChooser.APPROVE_OPTION) {
						txtTreeFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					}
				}
			});
			
			rdbtnPerfectMatch = new JRadioButton("Perfect match");
			rdbtnPerfectMatch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtThreshold.setVisible(false);
					lblMatchingThreshold.setVisible(false);
				}
			});
			grpMatchType.add(rdbtnPerfectMatch);
			
			rdbtnMinDistance = new JRadioButton("Min distance");
			rdbtnMinDistance.setSelected(true);
			rdbtnMinDistance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtThreshold.setVisible(true);
					lblMatchingThreshold.setVisible(true);
				}
			});
			grpMatchType.add(rdbtnMinDistance);
			
			JLabel lblDistance = new JLabel("Match type:");
			
			rdoHistogramDistance = new JRadioButton("Histogram distance");
			grpMatchType.add(rdoHistogramDistance);
			rdoHistogramDistance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtThreshold.setVisible(true);
					lblMatchingThreshold.setVisible(true);
				}
			});
			
			lblMatchingThreshold = new JLabel("Matching threshold:");
			
			txtThreshold = new JTextField();
			txtThreshold.setText("0");
			txtThreshold.setColumns(10);
			GroupLayout gl_InputTreePanel = new GroupLayout(InputTreePanel);
			gl_InputTreePanel.setHorizontalGroup(
				gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_InputTreePanel.createSequentialGroup()
						.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_InputTreePanel.createSequentialGroup()
								.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_InputTreePanel.createSequentialGroup()
										.addGap(8)
										.addComponent(lblTreeFile))
									.addGroup(gl_InputTreePanel.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblDistance)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_InputTreePanel.createSequentialGroup()
										.addComponent(rdbtnPerfectMatch)
										.addGap(18)
										.addComponent(rdbtnMinDistance)
										.addGap(18)
										.addComponent(rdoHistogramDistance, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(txtTreeFilePath, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnOpen))
							.addGroup(gl_InputTreePanel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblMatchingThreshold, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtThreshold, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)))
						.addGap(40))
			);
			gl_InputTreePanel.setVerticalGroup(
				gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_InputTreePanel.createSequentialGroup()
						.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_InputTreePanel.createSequentialGroup()
								.addGap(11)
								.addComponent(lblTreeFile))
							.addGroup(gl_InputTreePanel.createSequentialGroup()
								.addGap(5)
								.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtTreeFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnOpen))))
						.addGap(18)
						.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDistance)
							.addComponent(rdbtnPerfectMatch)
							.addComponent(rdoHistogramDistance)
							.addComponent(rdbtnMinDistance))
						.addGap(18)
						.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblMatchingThreshold)
							.addComponent(txtThreshold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(209, Short.MAX_VALUE))
			);
			InputTreePanel.setLayout(gl_InputTreePanel);
			
			outputsPanel = new JPanel();
			contentPanel.add(outputsPanel, "outputsPanel");
			outputsPanel.setLayout(new BorderLayout(0, 0));
			
			lblOutputs = new JLabel("Outputs");
			outputsPanel.add(lblOutputs, BorderLayout.NORTH);
			
			outputsSubPanel = new JPanel();
			outputsPanel.add(outputsSubPanel, BorderLayout.CENTER);
			outputsSubPanel.setLayout(new CardLayout(0, 0));
			
			cytoscapeOutputsPanel = new JPanel();
			outputsSubPanel.add(cytoscapeOutputsPanel, "cytoscapeOutputsPanel");
			
			chkNetworksOutput = new JCheckBox("Networks");
			chkNetworksOutput.setSelected(true);
			
			chkAttractorsNetworkOutput = new JCheckBox("Attractors Network");
			chkAttractorsNetworkOutput.setSelected(true);
			
			txtRequiredNetworks = new JTextField();
			txtRequiredNetworks.setText("10");
			txtRequiredNetworks.setColumns(10);
			
			JLabel lblRequiredNetworks = new JLabel("Required networks:");
			
			JLabel lblCytoscapeViews = new JLabel("Cytoscape views");
			GroupLayout gl_cytoscapeOutputsPanel = new GroupLayout(cytoscapeOutputsPanel);
			gl_cytoscapeOutputsPanel.setHorizontalGroup(
				gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
								.addComponent(lblRequiredNetworks)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
								.addComponent(lblCytoscapeViews)
								.addGap(23)))
						.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(txtRequiredNetworks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(chkNetworksOutput)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(chkAttractorsNetworkOutput)))
						.addContainerGap(279, Short.MAX_VALUE))
			);
			gl_cytoscapeOutputsPanel.setVerticalGroup(
				gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
						.addGap(16)
						.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblRequiredNetworks)
							.addComponent(txtRequiredNetworks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCytoscapeViews)
							.addComponent(chkAttractorsNetworkOutput)
							.addComponent(chkNetworksOutput))
						.addContainerGap(302, Short.MAX_VALUE))
			);
			cytoscapeOutputsPanel.setLayout(gl_cytoscapeOutputsPanel);
		}
		
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnNext = new JButton("Next");
		btnNext.addActionListener(new NextButtonAction(this));
		buttonPane.add(btnNext);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
	
	/**
	 * This method show the dialog.
	 * @return the exit code: 1 for correct ending
	 */
	public int showWizard(){
        this.setModal(true);
        this.setVisible(true);
        return 1;
        
    }

	/**
	 * This method returns the differentiation tree file path
	 * @return the file of the tree path.
	 */
	public String getDifferentiationTree(){
		return this.treeFile;
	}
	
	/**
	 * This method returns the simulation features.
	 * @return the simulation features as a Properties object.
	 */
	public Properties getSimulationFeatures(){
		return this.simulationFeatures;
	}
	
	/**
	 * This method returns the required outputs.
	 * @return the required outputs list
	 */
	public Properties getOutputs(){
		return this.outputs;
	}
	
	
	public Properties getTaskToDo(){
		return tasks;
	}
	
	/**
	 * This action changes the form
	 *
	 */
	public class NextButtonAction implements ActionListener {
		
		private Wizard parent;
		
		public NextButtonAction(Wizard wizardDialog){
			this.parent = wizardDialog;
		}
		
		public void actionPerformed(ActionEvent e) {
			try{
				//Shows features input method form
				if(form.equals("network-input-method")){
					if(inputMethod.equals("Complete network from GRNML file")){
						tasks.setProperty(GESTODifferentConstants.NETWORK_CREATION, GESTODifferentConstants.OPEN);
						((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "networkInputFeaturesMethod");	
						form = "attractors-params";
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "experimentsPanel");	
					}else{
						if(inputMethod.equals("New random networks from features")){
							tasks.setProperty(GESTODifferentConstants.NETWORK_CREATION, GESTODifferentConstants.NEW);
						}else if(inputMethod.equals("Partial network from GRNML file")){
							tasks.setProperty(GESTODifferentConstants.NETWORK_CREATION, GESTODifferentConstants.EDIT);
							editing = true;
						}else if(inputMethod.equals("Partial network from Cytoscape selected view")){
							editing = true;
							tasks.setProperty(GESTODifferentConstants.NETWORK_CREATION, GESTODifferentConstants.CYTOSCAPE_EDIT);
						}
						
						((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "networkInputFeaturesMethod");	
						form = "features-input-method";	
					
					}
				}else if(form.equals("features-input-method")){
					//Features manually added
					if(rdbtnFeaturesFromForm.isSelected()){
						if(editing){
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "pnlNetworkEditing");	
							form = "editing-features";
						}else{
							((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "networkFeaturesPanel");	
							form = "features-for-editing";
							//Disables the next button for the features insertion
							btnNext.setEnabled(false);
						}
						//Features from file
					}else{
						//Loads the features from file
						if(!txtFeaturesFilePath.getText().equals("")){
							simulationFeatures = Input.readSimulationFeatures(txtFeaturesFilePath.getText());
							//New step: outputs page
							form = "output-form";
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");	
						}
					}
				//Network editing form
				}else if(form.equals("editing-features")){
					if(!txtEditingNodesNumber.getText().equals("") && !txtEditingEdgesNumber.getText().equals("") &&
						!txtFixedInputs.getText().equals("") && !txtEditingRandomType.getText().equals("") &&
						!txtEditingBiasType.getText().equals("") && !txtEditingBiasValue.getText().equals("") &&
						!txtEditingOrType.getText().equals("") && !txtEditingAndType.getText().equals("") &&
						!txtEditingCanalizingType.getText().equals("")){
						
						//Sets the properties
						simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.RANDOM_TOPOLOGY);
						simulationFeatures.setProperty(SimulationFeaturesConstants.NODES, txtEditingNodesNumber.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.EDGES, txtEditingEdgesNumber.getText());
						if(!txtFixedInputs.getText().equals("-1"))
							simulationFeatures.setProperty(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER, txtFixedInputs.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.FUNCTION_TYPE, cmbEditingFunctionType.getSelectedItem().toString());
						simulationFeatures.setProperty(SimulationFeaturesConstants.RANDOM_TYPE, txtEditingRandomType.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_TYPE, txtEditingBiasType.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_VALUE, txtEditingBiasValue.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.AND_FUNCTION_TYPE, txtEditingAndType.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.OR_FUNCTION_TYPE, txtEditingOrType.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.CANALIZED_TYPE, txtEditingCanalizingType.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.COMPLETELY_DEFINED_FUNCTIONS, chckbxComplitellyDefined.isSelected()?SimulationFeaturesConstants.YES:SimulationFeaturesConstants.NO);
						
						ArrayList<String> noTarget = null;
						ArrayList<String> noSource = null;
						
						if(!txtNoSourceGenes.getText().equals("")){
							noSource = new ArrayList<String>();
							String[] parsed = txtNoSourceGenes.getText().split(",");
							for(String gene_name : parsed)
								noSource.add(gene_name);
							simulationFeatures.put(SimulationFeaturesConstants.EXCLUDES_SOURCE_GENES, noSource);
						}
						
						if(!txtNoDestinationGenes.getText().equals("")){
							noTarget = new ArrayList<String>();
							String[] parsed = txtNoDestinationGenes.getText().split(",");
							for(String gene_name : parsed)
								noTarget.add(gene_name);
							simulationFeatures.put(SimulationFeaturesConstants.EXCLUDES_TARGET_GENES, noTarget);
						}
						
						form = "attractors-params";
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "experimentsPanel");	
						
					}
				}else if(form.equals("attractors-params")){
					if(cmbSamplingType.getSelectedItem().equals(SimulationFeaturesConstants.BRUTE_FORCE)){
						simulationFeatures.setProperty(SimulationFeaturesConstants.SAMPLING_METHOD, SimulationFeaturesConstants.BRUTE_FORCE);
					}else if(!txtInitialConditions.getText().equals("") && !txtCutoff.getText().equals("")){
						//Adds the features about the attractors finding.
						simulationFeatures.setProperty(SimulationFeaturesConstants.SAMPLING_METHOD, SimulationFeaturesConstants.PARTIAL_SAMPLING);
						simulationFeatures.setProperty(SimulationFeaturesConstants.INITIAL_CONDITIONS, txtInitialConditions.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_SIMULATION_TIMES, txtCutoff.getText());
						form = "atm-params";
						((CardLayout)experimentsSubPanel.getLayout()).show(experimentsSubPanel, "atmPanel");
					}
				}else if(form.equals("atm-params")){
					//Adds the features about the atm creation
					if(chkAtmCalculation.isSelected()){
						tasks.setProperty(GESTODifferentConstants.ATM_COMPUTATION, GESTODifferentConstants.YES);
						//Sets the avalanches distribution and sensitivity computation.
						simulationFeatures.setProperty(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY,
								chkAvalanchesAndSensitivity.isSelected() ? SimulationFeaturesConstants.YES :
									SimulationFeaturesConstants.NO);
						if(!txtRatioStatesMutations.getText().equals("") && !txtExperimentsNumber.getText().equals("")){
							//Adds the RATIO_OF_STATES_TO_PERTURB and HOW_MANY_PERTURB_EXP features
							simulationFeatures.setProperty(SimulationFeaturesConstants.RATIO_OF_STATES_TO_PERTURB, txtRatioStatesMutations.getText());
							simulationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP, txtExperimentsNumber.getText());

							if(mutationsTypeComboBox.getSelectedItem().equals("Flip")){
								if(!txtNumberOfFlips.getText().equals("") && !txtMinFlipTimes.getText().equals("") && !txtMaxFlipTimes.getText().equals("")){
									//Adds the flip mutations features
									simulationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.FLIP_MUTATIONS);
									simulationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB, txtNumberOfFlips.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB, txtMinFlipTimes.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB, txtMaxFlipTimes.getText());

									form = "tree-matching";
									((CardLayout)contentPanel.getLayout()).show(contentPanel, "treeMatchingPanel");	

								}
							}else{
								if(!txtKnockInNodes.getText().equals("") && !txtMinKnockInTimes.getText().equals("") && !txtMaxKnockInTimes.getText().equals("") &&
										!txtKnockOutNodes.getText().equals("") && !txtMinKnockOutTimes.getText().equals("") && !txtMaxKnockOutTimes.getText().equals("")){
									//Adds the knock-in/knock-out mutations features
									simulationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.KNOCKIN_KNOCKOUT_MUTATIONS);
									simulationFeatures.setProperty(SimulationFeaturesConstants.KNOCKIN_NODES, txtKnockInNodes.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION, txtMinKnockInTimes.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION, txtMaxKnockInTimes.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.KNOCKOUT_NODES, txtKnockOutNodes.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION, txtMinKnockOutTimes.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION, txtMaxKnockOutTimes.getText());

									form = "tree-matching";
									((CardLayout)contentPanel.getLayout()).show(contentPanel, "treeMatchingPanel");	
								}
							}
						}
					}else{
						//No atm computation.
						tasks.setProperty(GESTODifferentConstants.ATM_COMPUTATION, GESTODifferentConstants.NO);
						//Next step: Output elements chosen
						form = "output-form";
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");
					}
					//Next action: Tree matching
				}else if(form.equals("tree-matching")){
					//Reads the tree from file
					if(chckbxMatchNetworksWith.isSelected()){
						tasks.setProperty(GESTODifferentConstants.TREE_MATCHING, GESTODifferentConstants.YES);
						//Gets the matching type
						if(rdbtnPerfectMatch.isSelected()){
							tasks.setProperty(GESTODifferentConstants.MATCHING_TYPE, GESTODifferentConstants.PERFECT_MATCH);
						}else if(rdbtnMinDistance.isSelected()){
							tasks.setProperty(GESTODifferentConstants.MATCHING_TYPE, GESTODifferentConstants.MIN_DISTANCE);
							tasks.setProperty(GESTODifferentConstants.MATCHING_THRESHOLD, txtThreshold.getText());
						}else{
							tasks.setProperty(GESTODifferentConstants.MATCHING_TYPE, GESTODifferentConstants.HISTOGRAM_DISTANCE);
							tasks.setProperty(GESTODifferentConstants.MATCHING_THRESHOLD, txtThreshold.getText());
						}
						if(!txtTreeFilePath.getText().equals("")){
							treeFile = txtTreeFilePath.getText();
							//New step: outputs page
							form = "output-form";
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");
						}
					}else{
						tasks.setProperty(GESTODifferentConstants.TREE_MATCHING, GESTODifferentConstants.NO);
						//New step: outputs page
						form = "output-form";
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");	
					}
				}else if(form.equals("output-form")){
					if(!txtRequiredNetworks.getText().equals("")){
						simulationFeatures.setProperty(SimulationFeaturesConstants.MATCHING_NETWORKS, txtRequiredNetworks.getText());
						//Network view on Cytoscape
						if(chkNetworksOutput.isSelected())
							outputs.setProperty(GESTODifferentConstants.NETWORK_VIEW, GESTODifferentConstants.YES);
						else
							outputs.setProperty(GESTODifferentConstants.NETWORK_VIEW, GESTODifferentConstants.NO);
						//Attractors network view on Cytoscape
						if(chkAttractorsNetworkOutput.isSelected())
							outputs.setProperty(GESTODifferentConstants.ATTRACTORS_NETWORK_VIEW, GESTODifferentConstants.YES);
						else
							outputs.setProperty(GESTODifferentConstants.ATTRACTORS_NETWORK_VIEW, GESTODifferentConstants.NO);
						
						parent.dispose();
					}
					
				}
			}catch(Exception ex){

			}
		}
	}
}
