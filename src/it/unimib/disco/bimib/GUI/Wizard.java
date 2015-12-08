/**
 * Wizard GUI
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.GUI;


//GRNSim imports
import it.unimib.disco.bimib.CABERNET.CABERNETConstants;
import it.unimib.disco.bimib.Exceptions.FeaturesException;
import it.unimib.disco.bimib.IO.*;
import it.unimib.disco.bimib.Middleware.NetworkManagment;
import it.unimib.disco.bimib.Tes.TesManager;
import it.unimib.disco.bimib.Tes.TesTree;
import it.unimib.disco.bimib.Utility.OutputConstants;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;







//Swing and awt imports
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.SpringLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
//System imports
import java.util.ArrayList;
import java.util.Properties;




//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;

import javax.swing.border.EtchedBorder;




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
	private JPanel fixedRandomPanel;
	private JPanel barabasiPanel;
	private JPanel powerLawPanel;
	private JPanel fixedPowerLawPanel;
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
	private JTextField txtBiasFunctionsType;
	private JTextField txtBiasValue;
	private JTextField txtAndFunctionsType;
	private JTextField txtOrFunctionsType;
	private JTextField txtCanalizingFunctionsType;
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
	private JPanel treeMatchingSubPanel;
	private JCheckBox chckbxMatchNetworksWith;
	private JTextField txtTreeFilePath;
	private JCheckBox chckbxCompletelyDefinedFunctions;
	private JRadioButton rdbtnPerfectMatch;
	private JRadioButton rdbtnMinDistance;
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
	private JPanel pnlList;
	private JLabel lblTopologyAndFunctionsList;
	private JLabel lblExperimentsList;
	private JLabel lblTreeMatchingList;
	private JLabel lblOutputsList;
	private JRadioButton radioTreeFromFile;
	private JRadioButton radioTreeFromCytoscape;
	private JPanel pnlExport;
	private JTextField txtFixedRandomInputs;
	private JLabel lblGamma_1;
	private JTextField txtFixedPLGamma;
	private JLabel lblAverageConettivity;
	private JTextField txtFixedPLK;
	private JLabel lblFixedInputs_1;
	private JTextField txtFixedPLInputs;
	private JLabel lblNetworkGenerationMode;
	private JLabel lblSamplingList;
	private JLabel lblNumberOfDifferent;
	private JTextField txtRequiredNetworks;
	private JLabel lblInoutProbability;
	private JTextField txtInOutProbability;
	private final ButtonGroup treeFromGroup = new ButtonGroup();
	private JLabel lblExportPath;
	private JTextField txtOutputPath;
	private JCheckBox chkGrnml;
	private JCheckBox chkSif;
	private JCheckBox chkStates;
	private JCheckBox chkAttractors;
	private JCheckBox chkAtm;
	private JCheckBox chkSynthesis;
	private JCheckBox chkAttractorLenghts;
	private JCheckBox chkBasins;
	private JCheckBox chkExportToFileSystem;
	private JPanel InputTreePanel;
	private JRadioButton rdbtnAbsolute;
	private JRadioButton rdbtnRatioOfAttractors;
	private JRadioButton rdbtnLogn;
	private JTextField txtDepthValue;
	private final ButtonGroup grpDepth;
	private JCheckBox chckbxComputeRepresentativeTree;
	private JCheckBox chkCompleteFlipExp;
	private JCheckBox chckbxAllTrees;
	private JPanel pnlRepresentativeTree;

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
	private TesTree tree;
	private NetworkManagment netManagment;
	private JTextField txtRepresentativeTreeCutoff;
	private JLabel lblMaximumNumberOf;
	private JTextField txtMaxChildren;
	private JLabel lblPermutationsCutoffProbability;
	private JTextField txtPermProb;
	private JLabel lblAlgorithm;
	private JTextField txtAugmentedAvgConnectivity;
	private JTextField txtInOutProbAug;
	private JTextField txtAugPLGamma;
	private JComboBox<String> cmbAugmentedTopology;
	private JComboBox<String> cmbSFAugmentedAlgorithm;


	private JCheckBox chkIngoingPowerLaw;
	private JLabel lblAverageConnectivity_2;
	private JTextField txtAvgConPLAug;





	/**
	 * Create the dialog.
	 */
	public Wizard(CySwingAppAdapter adapter){

		this.netManagment = new NetworkManagment(adapter, adapter.getCyApplicationManager());
		this.tree = null;

		setTitle("CABERNET task editor");

		//Data initialization
		this.inputNetworks = new ArrayList<String>();
		this.simulationFeatures = new Properties();
		this.tasks = new Properties();
		this.outputs = new Properties();

		this.inputMethod = "New random networks from features";
		this.form = "network-input-method";

		setBounds(100, 100, 950, 586);
		getContentPane().setLayout(new BorderLayout());

		pnlList = new JPanel();
		pnlList.setBorder(null);
		getContentPane().add(pnlList, BorderLayout.WEST);

		JLabel lblLogo = new JLabel(new ImageIcon(Wizard.class.getResource("/it/unimib/disco/bimib/resources/logo.png")));
		lblLogo.setText("");
		lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

		lblNetworkGenerationMode = new JLabel("Network generation mode");
		lblNetworkGenerationMode.setOpaque(true);
		lblNetworkGenerationMode.setHorizontalAlignment(SwingConstants.CENTER);
		lblNetworkGenerationMode.setForeground(Color.BLACK);
		lblNetworkGenerationMode.setBackground(UIManager.getColor("Button.background"));

		lblTopologyAndFunctionsList = new JLabel("<html>Topology and Functions</html>");
		lblTopologyAndFunctionsList.setOpaque(true);
		lblTopologyAndFunctionsList.setForeground(Color.BLACK);
		lblTopologyAndFunctionsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopologyAndFunctionsList.setBackground(Color.WHITE);

		lblSamplingList = new JLabel("Sampling");
		lblSamplingList.setOpaque(true);
		lblSamplingList.setHorizontalAlignment(SwingConstants.CENTER);
		lblSamplingList.setForeground(Color.BLACK);
		lblSamplingList.setBackground(Color.WHITE);

		lblExperimentsList = new JLabel("Experiment settings");
		lblExperimentsList.setOpaque(true);
		lblExperimentsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblExperimentsList.setBackground(Color.WHITE);

		lblTreeMatchingList = new JLabel("<html>Differentiation tree comparison</html>");
		lblTreeMatchingList.setOpaque(true);
		lblTreeMatchingList.setHorizontalAlignment(SwingConstants.CENTER);
		lblTreeMatchingList.setBackground(Color.WHITE);

		lblOutputsList = new JLabel("Outputs");
		lblOutputsList.setOpaque(true);
		lblOutputsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutputsList.setBackground(Color.WHITE);
		pnlList.setLayout(new GridLayout(0, 1, 0, 0));
		pnlList.add(lblLogo);
		pnlList.add(lblNetworkGenerationMode);
		pnlList.add(lblTopologyAndFunctionsList);
		pnlList.add(lblSamplingList);
		pnlList.add(lblExperimentsList);
		pnlList.add(lblTreeMatchingList);
		pnlList.add(lblOutputsList);
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
					sl_networkInputMethodPanel.putConstraint(SpringLayout.NORTH, networkInputMethodUpperPanel, 10, SpringLayout.NORTH, networkInputMethodPanel);
					networkInputMethodUpperPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Select the generation mode of the Gene Regulatory Network (GRN) model:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
					networkInputMethodPanel.add(networkInputMethodUpperPanel);
					{
						JRadioButton rdbtnGenerateRandomNetworks = new JRadioButton("<html>Generate random networks (NRBNs) by explicitly specifying\nthe structural features (either via file or form)</html>");
						rdbtnGenerateRandomNetworks.setVerticalAlignment(SwingConstants.TOP);
						rdbtnGenerateRandomNetworks.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								inputMethod = "New random networks from features";
								networkInputMethodLowerPanel.setVisible(false);
							}
						});
						networkInputMethodUpperPanel.setLayout(new GridLayout(0, 1, 0, 0));
						networkDefinitionMethodGroup.add(rdbtnGenerateRandomNetworks);
						rdbtnGenerateRandomNetworks.setSelected(true);
						networkInputMethodUpperPanel.add(rdbtnGenerateRandomNetworks);
					}
					{
						JRadioButton rdbtngenerateNetworksCompletely = new JRadioButton("<html>Generate networks completely defined via *.grnml file(s)</html>");
						rdbtngenerateNetworksCompletely.setVerticalAlignment(SwingConstants.TOP);
						rdbtngenerateNetworksCompletely.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								inputMethod = "Complete network from GRNML file";
								networkInputMethodLowerPanel.setVisible(true);
							}
						});
						networkDefinitionMethodGroup.add(rdbtngenerateNetworksCompletely);
						networkInputMethodUpperPanel.add(rdbtngenerateNetworksCompletely);
					}
					{
						JRadioButton rdbtnaugmentTheTopology = new JRadioButton("<html>Augment the topology and functions of networks partially defined via *.gnrml files, by explicitly specifying the structural features (either via file or form)\n</html>");
						rdbtnaugmentTheTopology.setVerticalAlignment(SwingConstants.TOP);
						rdbtnaugmentTheTopology.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								inputMethod = "Partial network from GRNML file";
								networkInputMethodLowerPanel.setVisible(true);
								tasks.setProperty("network-creation", "modification");
							}
						});
						networkDefinitionMethodGroup.add(rdbtnaugmentTheTopology);
						networkInputMethodUpperPanel.add(rdbtnaugmentTheTopology);
					}
					{
						JRadioButton rdbtnaugmentTheTopology_1 = new JRadioButton("<html>Augment the topology and functions of partially defined networks retrieved from the Cytoscape network view</html>");
						rdbtnaugmentTheTopology_1.setVerticalAlignment(SwingConstants.TOP);
						rdbtnaugmentTheTopology_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								inputMethod = "Partial network from Cytoscape selected view";
								networkInputMethodLowerPanel.setVisible(false);
								tasks.setProperty("network-creation", "cytoscape");
							}
						});
						networkDefinitionMethodGroup.add(rdbtnaugmentTheTopology_1);
						networkInputMethodUpperPanel.add(rdbtnaugmentTheTopology_1);
					}
				}
				networkInputMethodLowerPanel = new JPanel();
				sl_networkInputMethodPanel.putConstraint(SpringLayout.SOUTH, networkInputMethodUpperPanel, -23, SpringLayout.NORTH, networkInputMethodLowerPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.NORTH, networkInputMethodLowerPanel, 220, SpringLayout.NORTH, networkInputMethodPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.SOUTH, networkInputMethodLowerPanel, -29, SpringLayout.SOUTH, networkInputMethodPanel);
				sl_networkInputMethodPanel.putConstraint(SpringLayout.EAST, networkInputMethodUpperPanel, 0, SpringLayout.EAST, networkInputMethodLowerPanel);
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

				JButton btnRemoveSelectedNetwork = new JButton("Remove selected network");
				//Removes the selected network.
				btnRemoveSelectedNetwork.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!networksList.isSelectionEmpty()){
							inputNetworks.remove(networksList.getSelectedIndex());
							networkListModel.remove(networksList.getSelectedIndex());
						}else{
							JOptionPane.showMessageDialog(null, "A network must be selected from the list below", "Error", JOptionPane.ERROR_MESSAGE, null);
						}
					}
				});

				GroupLayout gl_networkFromFilePanel = new GroupLayout(networkFromFilePanel);
				gl_networkFromFilePanel.setHorizontalGroup(
						gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_networkFromFilePanel.createSequentialGroup()
								.addGap(8)
								.addGroup(gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
										.addComponent(networksList, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_networkFromFilePanel.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addPreferredGap(ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
												.addComponent(btnOpenNetwork)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnRemoveSelectedNetwork)))
								.addContainerGap())
						);
				gl_networkFromFilePanel.setVerticalGroup(
						gl_networkFromFilePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_networkFromFilePanel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_networkFromFilePanel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_networkFromFilePanel.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addGap(25))
										.addGroup(gl_networkFromFilePanel.createSequentialGroup()
												.addGroup(gl_networkFromFilePanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(btnRemoveSelectedNetwork)
														.addComponent(btnOpenNetwork))
												.addPreferredGap(ComponentPlacement.UNRELATED)))
								.addComponent(networksList, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
								.addGap(37))
						);
				networkFromFilePanel.setLayout(gl_networkFromFilePanel);

				currentFeature = "nodes-number";

				JPanel networkInputFeaturesMethod = new JPanel();
				networkDefinitionSubPanel.add(networkInputFeaturesMethod, "networkInputFeaturesMethod");
				SpringLayout sl_networkInputFeaturesMethod = new SpringLayout();
				networkInputFeaturesMethod.setLayout(sl_networkInputFeaturesMethod);

				JPanel featuresInputMethodGroupPanel = new JPanel();
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.NORTH, featuresInputMethodGroupPanel, 10, SpringLayout.NORTH, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.WEST, featuresInputMethodGroupPanel, 10, SpringLayout.WEST, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.SOUTH, featuresInputMethodGroupPanel, 99, SpringLayout.NORTH, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.EAST, featuresInputMethodGroupPanel, 732, SpringLayout.WEST, networkInputFeaturesMethod);
				featuresInputMethodGroupPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Define the structural features of networks (NRBNs)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				networkInputFeaturesMethod.add(featuresInputMethodGroupPanel);

				featuresFileSelectionPanel = new JPanel();
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.NORTH, featuresFileSelectionPanel, 30, SpringLayout.SOUTH, featuresInputMethodGroupPanel);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.SOUTH, featuresFileSelectionPanel, -308, SpringLayout.SOUTH, networkInputFeaturesMethod);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.EAST, featuresFileSelectionPanel, 0, SpringLayout.EAST, featuresInputMethodGroupPanel);
				featuresFileSelectionPanel.setVisible(false);
				sl_networkInputFeaturesMethod.putConstraint(SpringLayout.WEST, featuresFileSelectionPanel, 10, SpringLayout.WEST, networkInputFeaturesMethod);

				rdbtnFeaturesFromForm = new JRadioButton("From input form");
				rdbtnFeaturesFromForm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						featuresFileSelectionPanel.setVisible(false);
					}
				});
				featuresInputMethodGroupPanel.setLayout(new GridLayout(0, 1, 0, 0));
				rdbtnFeaturesFromForm.setSelected(true);
				featuresInputMethodGroup.add(rdbtnFeaturesFromForm);
				featuresInputMethodGroupPanel.add(rdbtnFeaturesFromForm);

				rdbtnFeaturesFromFile = new JRadioButton("From *.txt text file");
				rdbtnFeaturesFromFile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						featuresFileSelectionPanel.setVisible(true);
					}
				});
				featuresInputMethodGroup.add(rdbtnFeaturesFromFile);
				featuresInputMethodGroupPanel.add(rdbtnFeaturesFromFile);
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
								.addComponent(txtFeaturesFilePath, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnOpenFeatures)
								.addContainerGap())
						);
				gl_featuresFileSelectionPanel.setVerticalGroup(
						gl_featuresFileSelectionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
								.addGroup(gl_featuresFileSelectionPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
												.addGap(38)
												.addComponent(lblFeaturesFile))
										.addGroup(gl_featuresFileSelectionPanel.createSequentialGroup()
												.addGap(32)
												.addGroup(gl_featuresFileSelectionPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtFeaturesFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnOpenFeatures))))
								.addContainerGap(18, Short.MAX_VALUE))
						);
				featuresFileSelectionPanel.setLayout(gl_featuresFileSelectionPanel);

				JPanel requiredNetworksPanel = new JPanel();
				networkDefinitionSubPanel.add(requiredNetworksPanel, "requiredNetworksPanel");

				lblNumberOfDifferent = new JLabel("<html>Number of different networks with common structural features to generate (in case of augmented networks, the partially defined structure is fixed) </html>");
				lblNumberOfDifferent.setVerticalAlignment(SwingConstants.TOP);

				txtRequiredNetworks = new JTextField();
				txtRequiredNetworks.setHorizontalAlignment(SwingConstants.CENTER);
				txtRequiredNetworks.setText("10");
				txtRequiredNetworks.setColumns(10);
				GroupLayout gl_requiredNetworksPanel = new GroupLayout(requiredNetworksPanel);
				gl_requiredNetworksPanel.setHorizontalGroup(
						gl_requiredNetworksPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_requiredNetworksPanel.createSequentialGroup()
								.addGap(15)
								.addComponent(lblNumberOfDifferent, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(txtRequiredNetworks, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
								.addGap(60))
						);
				gl_requiredNetworksPanel.setVerticalGroup(
						gl_requiredNetworksPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_requiredNetworksPanel.createSequentialGroup()
								.addGap(26)
								.addGroup(gl_requiredNetworksPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNumberOfDifferent, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtRequiredNetworks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(447, Short.MAX_VALUE))
						);
				requiredNetworksPanel.setLayout(gl_requiredNetworksPanel);


				JPanel networkFeaturesPanel = new JPanel();
				networkDefinitionSubPanel.add(networkFeaturesPanel, "networkFeaturesPanel");
				networkFeaturesPanel.setLayout(new BorderLayout(0, 0));
				networkManualFeaturesPanel = new JPanel();
				networkFeaturesPanel.add(networkManualFeaturesPanel);
				networkManualFeaturesPanel.setBorder(new TitledBorder(null, "Features", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				featuresSubPanel = new JPanel();

				featureInputFormPanel = new JPanel();
				featureInputFormPanel.setLayout(new CardLayout(0, 0));

				nodesPanel = new JPanel();
				featureInputFormPanel.add(nodesPanel, "nodesPanel");

				JLabel lblNumberOfNodes = new JLabel("Number of nodes:");

				txtNodesNumber = new JTextField();
				txtNodesNumber.setHorizontalAlignment(SwingConstants.CENTER);
				txtNodesNumber.setText("100");
				txtNodesNumber.setColumns(10);
				GroupLayout gl_nodesPanel = new GroupLayout(nodesPanel);
				gl_nodesPanel.setHorizontalGroup(
						gl_nodesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_nodesPanel.createSequentialGroup()
								.addGap(8)
								.addComponent(lblNumberOfNodes)
								.addGap(8)
								.addComponent(txtNodesNumber, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(478, Short.MAX_VALUE))
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
								.addContainerGap(67, Short.MAX_VALUE))
						);
				nodesPanel.setLayout(gl_nodesPanel);

				topologyPanel = new JPanel();
				featureInputFormPanel.add(topologyPanel, "topologyPanel");

				JLabel lblNetworkTopology = new JLabel("Network Topology:");

				cmbTopology = new JComboBox<String>();
				cmbTopology.setModel(new DefaultComboBoxModel<String>(new String[] {
						"1. Erd\u00f6s-Rényi random ingoing topology, Erd\u00f6s-Rényi random outgoing topology", 
						"2. Fixed number of inputs, Erd\u00f6s-Rényi random outgoing topology", 
						"3. Barabasi-Albert’s preferential attachment (Scale-free)", 
						"4. Erd\u00f6s-Rényi random ingoing topology, Power-law-based outgoing topology (Scale-free)", 
						"5. Fixed number of inputs, Power-law-based outgoint topology (Scale-free)", 
				"6. Watts-Strogatz small-world topology"}));
				cmbTopology.setSelectedIndex(0);
				GroupLayout gl_topologyPanel = new GroupLayout(topologyPanel);
				gl_topologyPanel.setHorizontalGroup(
						gl_topologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topologyPanel.createSequentialGroup()
								.addGap(8)
								.addComponent(lblNetworkTopology)
								.addGap(8)
								.addComponent(cmbTopology, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
								.addGap(14))
						);
				gl_topologyPanel.setVerticalGroup(
						gl_topologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_topologyPanel.createSequentialGroup()
								.addGroup(gl_topologyPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_topologyPanel.createSequentialGroup()
												.addGap(10)
												.addComponent(lblNetworkTopology))
										.addGroup(gl_topologyPanel.createSequentialGroup()
												.addGap(5)
												.addComponent(cmbTopology, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(68, Short.MAX_VALUE))
						);
				topologyPanel.setLayout(gl_topologyPanel);

				randomTopologyPanel = new JPanel();
				featureInputFormPanel.add(randomTopologyPanel, "randomTopologyPanel");

				JLabel lblNumberOfEdges = new JLabel("Number of edges:");
				txtEdgesNumber = new JTextField();
				txtEdgesNumber.setHorizontalAlignment(SwingConstants.CENTER);
				txtEdgesNumber.setText("200");
				txtEdgesNumber.setColumns(10);
				GroupLayout gl_randomTopologyPanel = new GroupLayout(randomTopologyPanel);
				gl_randomTopologyPanel.setHorizontalGroup(
						gl_randomTopologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_randomTopologyPanel.createSequentialGroup()
								.addGap(8)
								.addComponent(lblNumberOfEdges)
								.addGap(38)
								.addComponent(txtEdgesNumber, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addGap(373))
						);
				gl_randomTopologyPanel.setVerticalGroup(
						gl_randomTopologyPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_randomTopologyPanel.createSequentialGroup()
								.addGroup(gl_randomTopologyPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_randomTopologyPanel.createSequentialGroup()
												.addGap(11)
												.addComponent(lblNumberOfEdges))
										.addGroup(gl_randomTopologyPanel.createSequentialGroup()
												.addGap(5)
												.addComponent(txtEdgesNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(51, Short.MAX_VALUE))
						);
				randomTopologyPanel.setLayout(gl_randomTopologyPanel);

				fixedRandomPanel = new JPanel();
				featureInputFormPanel.add(fixedRandomPanel, "fixedRandomPanel");

				JLabel lblFixedInputs = new JLabel("Number of fixed inputs:");

				txtFixedRandomInputs = new JTextField();
				txtFixedRandomInputs.setHorizontalAlignment(SwingConstants.CENTER);
				txtFixedRandomInputs.setText("2");
				txtFixedRandomInputs.setColumns(10);
				GroupLayout gl_fixedRandomPanel = new GroupLayout(fixedRandomPanel);
				gl_fixedRandomPanel.setHorizontalGroup(
						gl_fixedRandomPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_fixedRandomPanel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblFixedInputs, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
								.addGap(1)
								.addComponent(txtFixedRandomInputs, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
								.addGap(418))
						);
				gl_fixedRandomPanel.setVerticalGroup(
						gl_fixedRandomPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_fixedRandomPanel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_fixedRandomPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtFixedRandomInputs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblFixedInputs, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(45, Short.MAX_VALUE))
						);
				fixedRandomPanel.setLayout(gl_fixedRandomPanel);

				barabasiPanel = new JPanel();
				featureInputFormPanel.add(barabasiPanel, "barabasiPanel");

				JLabel lblInitialNodesNumber = new JLabel("Initial number of nodes:");

				txtNi = new JTextField();
				txtNi.setHorizontalAlignment(SwingConstants.CENTER);
				txtNi.setText("3");
				txtNi.setColumns(10);

				JLabel lblAverageConnectivity = new JLabel("Average connectivity:");

				txtAvgConnBA = new JTextField();
				txtAvgConnBA.setHorizontalAlignment(SwingConstants.CENTER);
				txtAvgConnBA.setText("2");
				txtAvgConnBA.setColumns(10);

				lblInoutProbability = new JLabel("In/Out probability:");

				txtInOutProbability = new JTextField();
				txtInOutProbability.setText("0.5");
				txtInOutProbability.setHorizontalAlignment(SwingConstants.CENTER);
				txtInOutProbability.setColumns(10);
				GroupLayout gl_barabasiPanel = new GroupLayout(barabasiPanel);
				gl_barabasiPanel.setHorizontalGroup(
						gl_barabasiPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_barabasiPanel.createSequentialGroup()
								.addGap(8)
								.addComponent(lblInitialNodesNumber)
								.addGap(8)
								.addComponent(txtNi, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblAverageConnectivity)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtAvgConnBA, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
								.addGap(32)
								.addComponent(lblInoutProbability, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(txtInOutProbability, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
								.addGap(15))
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
												.addGroup(gl_barabasiPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtNi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAverageConnectivity)
														.addComponent(txtAvgConnBA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblInoutProbability)
														.addComponent(txtInOutProbability, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(73, Short.MAX_VALUE))
						);
				barabasiPanel.setLayout(gl_barabasiPanel);

				powerLawPanel = new JPanel();
				featureInputFormPanel.add(powerLawPanel, "powerLawPanel");

				JLabel lblGamma = new JLabel("Power-law exponent:");

				txtGamma = new JTextField();
				txtGamma.setHorizontalAlignment(SwingConstants.CENTER);
				txtGamma.setText("2.3");
				txtGamma.setColumns(10);

				JLabel lblPLAverageConnectivity = new JLabel("Average connectivity:");

				txtavgConnPL = new JTextField();
				txtavgConnPL.setHorizontalAlignment(SwingConstants.CENTER);
				txtavgConnPL.setText("2");
				txtavgConnPL.setColumns(10);
				GroupLayout gl_powerLawPanel = new GroupLayout(powerLawPanel);
				gl_powerLawPanel.setHorizontalGroup(
						gl_powerLawPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_powerLawPanel.createSequentialGroup()
								.addGap(8)
								.addComponent(lblGamma)
								.addGap(8)
								.addComponent(txtGamma, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
								.addGap(127)
								.addComponent(lblPLAverageConnectivity)
								.addGap(34)
								.addComponent(txtavgConnPL, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
								.addGap(24))
						);
				gl_powerLawPanel.setVerticalGroup(
						gl_powerLawPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_powerLawPanel.createSequentialGroup()
								.addGroup(gl_powerLawPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_powerLawPanel.createSequentialGroup()
												.addGap(11)
												.addComponent(lblGamma))
										.addGroup(gl_powerLawPanel.createSequentialGroup()
												.addGap(5)
												.addGroup(gl_powerLawPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtGamma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblPLAverageConnectivity)
														.addComponent(txtavgConnPL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(67, Short.MAX_VALUE))
						);
				powerLawPanel.setLayout(gl_powerLawPanel);

				fixedPowerLawPanel = new JPanel();
				featureInputFormPanel.add(fixedPowerLawPanel, "fixedPowerLawPanel");

				lblGamma_1 = new JLabel("Power-law exponent:");

				txtFixedPLGamma = new JTextField();
				txtFixedPLGamma.setText("2.3");
				txtFixedPLGamma.setColumns(7);

				lblAverageConettivity = new JLabel("Average connectivity:");

				txtFixedPLK = new JTextField();
				txtFixedPLK.setText("2");
				txtFixedPLK.setColumns(7);

				lblFixedInputs_1 = new JLabel("Number of fixed inputs");

				txtFixedPLInputs = new JTextField();
				txtFixedPLInputs.setText("2");
				txtFixedPLInputs.setColumns(7);
				GroupLayout gl_fixedPowerLawPanel = new GroupLayout(fixedPowerLawPanel);
				gl_fixedPowerLawPanel.setHorizontalGroup(
						gl_fixedPowerLawPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_fixedPowerLawPanel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblGamma_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtFixedPLGamma, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblAverageConettivity)
								.addGap(18)
								.addComponent(txtFixedPLK, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addGap(140)
								.addComponent(lblFixedInputs_1)
								.addGap(18)
								.addComponent(txtFixedPLInputs, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addGap(35))
						);
				gl_fixedPowerLawPanel.setVerticalGroup(
						gl_fixedPowerLawPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_fixedPowerLawPanel.createSequentialGroup()
								.addGroup(gl_fixedPowerLawPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_fixedPowerLawPanel.createSequentialGroup()
												.addGap(5)
												.addGroup(gl_fixedPowerLawPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtFixedPLGamma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(txtFixedPLK, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAverageConettivity)
														.addComponent(txtFixedPLInputs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblFixedInputs_1)))
										.addGroup(gl_fixedPowerLawPanel.createSequentialGroup()
												.addGap(11)
												.addComponent(lblGamma_1)))
								.addContainerGap(73, Short.MAX_VALUE))
						);
				fixedPowerLawPanel.setLayout(gl_fixedPowerLawPanel);

				smallWorldPanel = new JPanel();
				featureInputFormPanel.add(smallWorldPanel, "smallWorldPanel");

				JLabel lblBeta = new JLabel("Edge switching probability:");

				txtBeta = new JTextField();
				txtBeta.setHorizontalAlignment(SwingConstants.CENTER);
				txtBeta.setText("0.5");
				txtBeta.setColumns(10);

				JLabel lblAverageConnectivitySW = new JLabel("Average connectivity:");

				txtAvgConnSW = new JTextField();
				txtAvgConnSW.setHorizontalAlignment(SwingConstants.CENTER);
				txtAvgConnSW.setText("2");
				txtAvgConnSW.setColumns(10);
				GroupLayout gl_smallWorldPanel = new GroupLayout(smallWorldPanel);
				gl_smallWorldPanel.setHorizontalGroup(
						gl_smallWorldPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
								.addGap(8)
								.addComponent(lblBeta)
								.addGap(8)
								.addComponent(txtBeta, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
								.addGap(146)
								.addComponent(lblAverageConnectivitySW)
								.addGap(18)
								.addComponent(txtAvgConnSW, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addGap(16))
						);
				gl_smallWorldPanel.setVerticalGroup(
						gl_smallWorldPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_smallWorldPanel.createSequentialGroup()
								.addGroup(gl_smallWorldPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_smallWorldPanel.createSequentialGroup()
												.addGap(11)
												.addComponent(lblBeta))
										.addGroup(gl_smallWorldPanel.createSequentialGroup()
												.addGap(5)
												.addGroup(gl_smallWorldPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(txtBeta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(txtAvgConnSW, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblAverageConnectivitySW))))
								.addContainerGap(51, Short.MAX_VALUE))
						);
				smallWorldPanel.setLayout(gl_smallWorldPanel);

				functionsTypePanel = new JPanel();
				featureInputFormPanel.add(functionsTypePanel, "functionsTypePanel");

				lblFunctionsType = new JLabel("Function type:");

				cmbFunctionsType = new JComboBox<String>();
				cmbFunctionsType.setModel(new DefaultComboBoxModel<String>(new String[] {"Boolean"}));

				chckbxCompletelyDefinedFunctions = new JCheckBox("Completely defined functions");
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

				JLabel lblBias_1 = new JLabel("Random bias-based:");

				txtBiasFunctionsType = new JTextField();
				txtBiasFunctionsType.setHorizontalAlignment(SwingConstants.CENTER);
				txtBiasFunctionsType.setText("0.4");
				txtBiasFunctionsType.setColumns(10);

				JLabel lblBiasValue = new JLabel("Bias value:");

				txtBiasValue = new JTextField();
				txtBiasValue.setHorizontalAlignment(SwingConstants.CENTER);
				txtBiasValue.setText("0.5");
				txtBiasValue.setColumns(10);

				JLabel lblAnd = new JLabel("Logical AND:");

				txtAndFunctionsType = new JTextField();
				txtAndFunctionsType.setHorizontalAlignment(SwingConstants.CENTER);
				txtAndFunctionsType.setText("0.2");
				txtAndFunctionsType.setColumns(10);

				JLabel lblOr = new JLabel("Logical OR:");

				txtOrFunctionsType = new JTextField();
				txtOrFunctionsType.setHorizontalAlignment(SwingConstants.CENTER);
				txtOrFunctionsType.setText("0.2");
				txtOrFunctionsType.setColumns(10);

				JLabel lblCanalizing = new JLabel("Random canalizing:");

				txtCanalizingFunctionsType = new JTextField();
				txtCanalizingFunctionsType.setHorizontalAlignment(SwingConstants.CENTER);
				txtCanalizingFunctionsType.setText("0.2");
				txtCanalizingFunctionsType.setColumns(10);

				JLabel lblNewLabel_1 = new JLabel("Proportion of function types randomly associated to nodes\n");
				GroupLayout gl_functionsRatesPanel = new GroupLayout(functionsRatesPanel);
				gl_functionsRatesPanel.setHorizontalGroup(
						gl_functionsRatesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_functionsRatesPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_functionsRatesPanel.createSequentialGroup()
												.addGroup(gl_functionsRatesPanel.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_functionsRatesPanel.createSequentialGroup()
																.addComponent(lblBias_1)
																.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(txtBiasFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_functionsRatesPanel.createSequentialGroup()
																.addComponent(lblBiasValue)
																.addPreferredGap(ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
																.addComponent(txtBiasValue, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
												.addPreferredGap(ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
												.addComponent(lblAnd)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtAndFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblOr)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtOrFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblCanalizing)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtCanalizingFunctionsType, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
												.addGap(160))
										.addGroup(gl_functionsRatesPanel.createSequentialGroup()
												.addComponent(lblNewLabel_1)
												.addContainerGap(253, Short.MAX_VALUE))))
						);
				gl_functionsRatesPanel.setVerticalGroup(
						gl_functionsRatesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_functionsRatesPanel.createSequentialGroup()
								.addGap(10)
								.addComponent(lblNewLabel_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_functionsRatesPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtBiasFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblAnd)
										.addComponent(lblCanalizing)
										.addComponent(txtCanalizingFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblBias_1)
										.addComponent(txtAndFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblOr)
										.addComponent(txtOrFunctionsType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_functionsRatesPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(txtBiasValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblBiasValue))
								.addContainerGap())
						);
				functionsRatesPanel.setLayout(gl_functionsRatesPanel);


				JPanel featuresNextPreviousPanel = new JPanel();
				FlowLayout fl_featuresNextPreviousPanel = (FlowLayout) featuresNextPreviousPanel.getLayout();
				fl_featuresNextPreviousPanel.setAlignment(FlowLayout.RIGHT);

				JButton btnAddFeature = new JButton("Add feature");
				btnAddFeature.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							if(currentFeature.equals("nodes-number")){
								if(!txtNodesNumber.getText().equals("")){
									if(Integer.parseInt(txtNodesNumber.getText()) <= 0)
										throw new FeaturesException(SimulationFeaturesConstants.NODES + " value must be greater than 0");
									//Sets the number of nodes in the simulation property list.
									simulationFeatures.setProperty(SimulationFeaturesConstants.NODES, txtNodesNumber.getText());
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.NODES, txtNodesNumber.getText()});
									//Next step: Topology
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "topologyPanel");	
									currentFeature = "topology";
								}
							}else if(currentFeature.equals("topology")){

								//Erd\00f6s-Rényi random ingoing topology, Erd\00f6s-Rényi random outogoing topology
								if(cmbTopology.getSelectedIndex() == 0){	
									//Sets the network topology
									simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.RANDOM_TOPOLOGY);
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, 
									"Erd\u00f6s-Rényi random ingoing topology, Erd\u00f6s-Rényi random outogoing topology"});
									//Next step: Erdos-Renyi parameters
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "randomTopologyPanel");	
									currentFeature = "random-topology-parameters";
									networkManualFeaturesPanel.repaint();
									//Fixed number of inputs, Erd\00f6s-Rényi random outgoing topology
								}else if(cmbTopology.getSelectedIndex() == 1){
									//Sets the network topology
									simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.PARTIALLY_RANDOM_TOPOLOGY);
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, 
									"Fixed number of inputs, Erd\u00f6s-Rényi random outgoing topology"});
									//Next step: Barabasi-Albertz parameters
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "fixedRandomPanel");	
									currentFeature = "fixed-random-parameters";
									//Barabasi-Alberts’s preferential attachment (Scale-free) 
								}else if(cmbTopology.getSelectedIndex() == 2){
									//Sets the network topology
									simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
									simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM);
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, 
									"Barabasi-Alberts’s preferential attachment (Scale-free)"});
									//Next step: Barabasi-Albertz parameters
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "barabasiPanel");	
									currentFeature = "barabasi-parameters";
									//Erd\u00f6s-Rényi random ingoing topology, Power-law-based outgoing topology (Scale-free)
								}else if(cmbTopology.getSelectedIndex() == 3){
									//Sets the network topology
									simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
									simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.POWER_LAW_ALGORITHM);
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, 
									"Erd\u00f6s-Rényi random ingoing topology, Power-law-based outgoing topology (Scale-free)"});
									//Next step: Power Law parameters
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "powerLawPanel");	
									currentFeature = "power-law-parameters";
									//Fixed number of inputs, Power-law-based outgoint topology (Scale-free)
								}else if(cmbTopology.getSelectedIndex() == 4){
									//Sets the network topology
									simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
									simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.FIXED_POWER_LAW_ALGORITHM);
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, 
									"Fixed number of inputs, Power-law-based outgoint topology (Scale-free)"});
									//Next step: Fixed Power Law parameters
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "fixedPowerLawPanel");	
									currentFeature = "fixed-power-law-parameters";
									//Watts-Strogatz small-world topology
								}else if(cmbTopology.getSelectedIndex() == 5){
									//Sets the network topology
									simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SMALL_WORLD_TOPOLOGY);
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.TOPOLOGY, 
									"Watts-Strogatz small-world topology"});
									//Next step: Small World parameters
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "smallWorldPanel");	
									currentFeature = "small-world-parameters";
								}
							}else if(currentFeature.equals("random-topology-parameters")){
								if(!txtEdgesNumber.getText().equals("")){
									//Sets the number of edges
									if(Integer.parseInt(txtEdgesNumber.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.EDGES + " value must be greater than 0");
									}else if(Integer.parseInt(txtEdgesNumber.getText()) >= Math.pow(Integer.parseInt(txtNodesNumber.getText()) - 1, 2)){
										throw new FeaturesException(SimulationFeaturesConstants.EDGES + " value must be less than (nodes - 1)^2");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.EDGES, txtEdgesNumber.getText());
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.EDGES, txtEdgesNumber.getText()});
									//Next step: Functions type
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
									currentFeature = "functions-type";
								}
							}else if(currentFeature.equals("fixed-random-parameters")){
								//Sets the number of required inputs
								if(Integer.parseInt(txtFixedRandomInputs.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER + " value must be greater than 0");
								}else if(Integer.parseInt(txtFixedRandomInputs.getText()) >= Math.pow(Integer.parseInt(txtNodesNumber.getText()) - 1, 2)){
									throw new FeaturesException(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER + " value must be less than (nodes - 1)^2");
								}
								simulationFeatures.setProperty(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER, txtFixedRandomInputs.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.FIXED_INPUTS_NUMBER, txtFixedRandomInputs.getText()});
								//Next step: Functions type
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
								currentFeature = "functions-type";

							}else if(currentFeature.equals("barabasi-parameters")){
								if(!txtNi.getText().equals("") && !txtAvgConnBA.getText().equals("") && !txtInOutProbability.getText().equals("")){
									//Sets the ni and k parameters
									if(Integer.parseInt(txtNi.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.NI + " value must be greater than 0");
									}else if(Integer.parseInt(txtFixedRandomInputs.getText()) >= Integer.parseInt(txtNodesNumber.getText())){
										throw new FeaturesException(SimulationFeaturesConstants.NI + " value must be less than nodes");
									}
									if(Integer.parseInt(txtAvgConnBA.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be greater than 0");
									}else if(Integer.parseInt(txtAvgConnBA.getText()) >= Integer.parseInt(txtNodesNumber.getText())){
										throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be less than nodes");
									}
									if(Integer.parseInt(txtNi.getText()) < Integer.parseInt(txtAvgConnBA.getText()))
										throw new FeaturesException(SimulationFeaturesConstants.NI + " value must be greater or equal than " + 
												SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value");

									if(Double.parseDouble(txtInOutProbability.getText()) < 0 || 
											Double.parseDouble(txtInOutProbability.getText()) > 1){
										throw new FeaturesException(SimulationFeaturesConstants.INGOING_OUTGOING_PROBABILITY + " value must be between 0 and 1");
									}

									simulationFeatures.setProperty(SimulationFeaturesConstants.NI, txtNi.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnBA.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.INGOING_OUTGOING_PROBABILITY, txtInOutProbability.getText());
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.NI, txtNi.getText()});
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnBA.getText()});
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.INGOING_OUTGOING_PROBABILITY, 
											txtInOutProbability.getText()});
									//Next step: Functions type
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
									currentFeature = "functions-type";
								}
							}else if(currentFeature.equals("power-law-parameters")){
								if(!txtGamma.getText().equals("") && !txtavgConnPL.getText().equals("")){
									//Sets the gamma and k parameters
									if(Double.parseDouble(txtGamma.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.GAMMA + " value must be greater than 0");
									}
									if(Integer.parseInt(txtavgConnPL.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be greater than 0");
									}else if(Integer.parseInt(txtavgConnPL.getText()) >= Integer.parseInt(txtNodesNumber.getText())){
										throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be less than nodes");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.GAMMA, txtGamma.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtavgConnPL.getText());
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.GAMMA, txtGamma.getText()});
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtavgConnPL.getText()});
									//Next step: Functions type
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
									currentFeature = "functions-type";
								}
							}else if(currentFeature.equals("fixed-power-law-parameters")){
								//Sets the gamma and k parameters
								if(Double.parseDouble(txtFixedPLGamma.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.GAMMA + " value must be greater than 0");
								}
								if(Integer.parseInt(txtFixedPLK.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be greater than 0");
								}else if(Integer.parseInt(txtFixedPLK.getText()) >= Integer.parseInt(txtNodesNumber.getText())){
									throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be less than nodes");
								}
								if(Integer.parseInt(txtFixedPLInputs.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER + " value must be greater than 0");
								}else if(Integer.parseInt(txtFixedPLInputs.getText()) >= Integer.parseInt(txtNodesNumber.getText())){
									throw new FeaturesException(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER + " value must be less than nodes");
								}
								simulationFeatures.setProperty(SimulationFeaturesConstants.GAMMA, txtFixedPLGamma.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtFixedPLK.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER, txtFixedPLInputs.getText());
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.GAMMA, txtFixedPLGamma.getText()});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtFixedPLK.getText()});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.FIXED_INPUTS_NUMBER, txtFixedPLInputs.getText()});
								//Next step: Functions type
								((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
								currentFeature = "functions-type";

							}else if(currentFeature.equals("small-world-parameters")){
								if(!txtBeta.getText().equals("") && !txtAvgConnSW.getText().equals("")){
									//Sets the beta and k parameters
									if(Double.parseDouble(txtBeta.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.BETA + " value must be greater than 0");
									}else if(Double.parseDouble(txtBeta.getText()) > 1.0){
										throw new FeaturesException(SimulationFeaturesConstants.BETA + " value must be less or equal than 1.0");
									}
									if(Integer.parseInt(txtAvgConnSW.getText()) <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be greater than 0");
									}else if(Integer.parseInt(txtAvgConnSW.getText()) >= Integer.parseInt(txtNodesNumber.getText())){
										throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be less than nodes");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.BETA, txtBeta.getText());
									simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnSW.getText());
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.BETA, txtBeta.getText()});
									networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConnSW.getText()});
									//Next step: Functions type
									((CardLayout)featureInputFormPanel.getLayout()).show(featureInputFormPanel, "functionsTypePanel");	
									currentFeature = "functions-type";
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
							}else if(currentFeature.equals("functions-rates")){

								Double biased, bias, and, or, canalizing;
								//Sets the random type
								simulationFeatures.setProperty(SimulationFeaturesConstants.RANDOM_TYPE, "0");

								//Sets the bias type functions and the bias value
								biased = Double.parseDouble(txtBiasFunctionsType.getText());
								if(biased < 0 || biased > 1.0)
									throw new FeaturesException(SimulationFeaturesConstants.BIAS_TYPE + " value must be between 0 and 1");
								simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_TYPE, txtBiasFunctionsType.getText());

								//Set the bias value
								bias = Double.parseDouble(txtBiasValue.getText());
								if(bias < 0 || bias > 1.0)
									throw new FeaturesException(SimulationFeaturesConstants.BIAS_VALUE + " value must be between 0 and 1");

								simulationFeatures.setProperty(SimulationFeaturesConstants.BIAS_VALUE, txtBiasValue.getText());

								//Sets the And type functions
								and = Double.parseDouble(txtAndFunctionsType.getText());
								if(and < 0 || and > 1.0)
									throw new FeaturesException(SimulationFeaturesConstants.AND_FUNCTION_TYPE + " value must be between 0 and 1");
								simulationFeatures.setProperty(SimulationFeaturesConstants.AND_FUNCTION_TYPE, txtAndFunctionsType.getText());

								//Sets the Or type functions
								or = Double.parseDouble(txtOrFunctionsType.getText());
								if(or < 0 || or > 1.0)
									throw new FeaturesException(SimulationFeaturesConstants.OR_FUNCTION_TYPE + " value must be between 0 and 1");
								simulationFeatures.setProperty(SimulationFeaturesConstants.OR_FUNCTION_TYPE, txtOrFunctionsType.getText());

								//Sets the canalizing type functions
								canalizing = Double.parseDouble(txtCanalizingFunctionsType.getText());
								if(canalizing < 0 || canalizing > 1.0)
									throw new FeaturesException(SimulationFeaturesConstants.CANALIZED_TYPE + " value must be between 0 and 1");
								simulationFeatures.setProperty(SimulationFeaturesConstants.CANALIZED_TYPE, txtCanalizingFunctionsType.getText());

								if(biased + and + or + canalizing != 1.0)
									throw new FeaturesException("The sum of " + SimulationFeaturesConstants.BIAS_TYPE + "," +
											SimulationFeaturesConstants.AND_FUNCTION_TYPE + "," +
											SimulationFeaturesConstants.OR_FUNCTION_TYPE + "," +
											SimulationFeaturesConstants.CANALIZED_TYPE + " must be 1.0");

								//Shows the features in the table.
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.BIAS_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.BIAS_TYPE)});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.BIAS_VALUE, simulationFeatures.getProperty(SimulationFeaturesConstants.BIAS_VALUE)});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.AND_FUNCTION_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.AND_FUNCTION_TYPE)});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.OR_FUNCTION_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.OR_FUNCTION_TYPE)});
								networkFeaturesTableModel.addRow(new String[] {SimulationFeaturesConstants.CANALIZED_TYPE, simulationFeatures.getProperty(SimulationFeaturesConstants.CANALIZED_TYPE)});

								//Next step: experiments page
								lblSamplingList.setBackground(UIManager.getColor("Button.background"));
								lblTopologyAndFunctionsList.setBackground(Color.WHITE);
								((CardLayout)contentPanel.getLayout()).show(contentPanel, "experimentsPanel");	
								form = "attractors-params";
								btnNext.setEnabled(true);
							}
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
						}
					}


				});
				featuresNextPreviousPanel.add(btnAddFeature);

				JPanel networkFeaturesTablePanel = new JPanel();
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
				GroupLayout gl_networkManualFeaturesPanel = new GroupLayout(networkManualFeaturesPanel);
				gl_networkManualFeaturesPanel.setHorizontalGroup(
						gl_networkManualFeaturesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_networkManualFeaturesPanel.createSequentialGroup()
								.addGap(10)
								.addGroup(gl_networkManualFeaturesPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(networkFeaturesTablePanel, GroupLayout.PREFERRED_SIZE, 706, GroupLayout.PREFERRED_SIZE)
										.addComponent(featuresSubPanel, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE))
								.addContainerGap())
						);
				gl_networkManualFeaturesPanel.setVerticalGroup(
						gl_networkManualFeaturesPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_networkManualFeaturesPanel.createSequentialGroup()
								.addGap(10)
								.addComponent(featuresSubPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(33)
								.addComponent(networkFeaturesTablePanel, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
								.addGap(7))
						);
				GroupLayout gl_featuresSubPanel = new GroupLayout(featuresSubPanel);
				gl_featuresSubPanel.setHorizontalGroup(
						gl_featuresSubPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_featuresSubPanel.createSequentialGroup()
								.addGroup(gl_featuresSubPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_featuresSubPanel.createSequentialGroup()
												.addContainerGap()
												.addComponent(featuresNextPreviousPanel, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
										.addComponent(featureInputFormPanel, GroupLayout.PREFERRED_SIZE, 708, Short.MAX_VALUE))
								.addContainerGap())
						);
				gl_featuresSubPanel.setVerticalGroup(
						gl_featuresSubPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_featuresSubPanel.createSequentialGroup()
								.addComponent(featureInputFormPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(featuresNextPreviousPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						);
				featuresSubPanel.setLayout(gl_featuresSubPanel);
				networkManualFeaturesPanel.setLayout(gl_networkManualFeaturesPanel);
			}

			pnlNetworkEditing = new JPanel();
			contentPanel.add(pnlNetworkEditing, "pnlNetworkEditing");
			pnlNetworkEditing.setLayout(new BorderLayout(0, 0));

			lblNetworkEditing = new JLabel("Network augmentation: provide the features of the network to be augmended. ");
			lblNetworkEditing.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			pnlNetworkEditing.add(lblNetworkEditing, BorderLayout.NORTH);

			pnlInnerNetworkEditing = new JPanel();
			pnlNetworkEditing.add(pnlInnerNetworkEditing, BorderLayout.CENTER);
			SpringLayout sl_pnlInnerNetworkEditing = new SpringLayout();
			pnlInnerNetworkEditing.setLayout(sl_pnlInnerNetworkEditing);

			JLabel lblTotalNodes = new JLabel("Total number of nodes :");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblTotalNodes, 10, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblTotalNodes, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblTotalNodes);

			txtEditingNodesNumber = new JTextField();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingNodesNumber, -5, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingNodesNumber, 6, SpringLayout.EAST, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingNodesNumber, -509, SpringLayout.EAST, pnlInnerNetworkEditing);
			txtEditingNodesNumber.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingNodesNumber.setText("0");
			pnlInnerNetworkEditing.add(txtEditingNodesNumber);
			txtEditingNodesNumber.setColumns(10);

			JLabel lblExcludeAsSource = new JLabel("Exclude the following nodes from source or destination node sets.");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblExcludeAsSource, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblExcludeAsSource);

			txtNoSourceGenes = new JTextArea();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtNoSourceGenes, 177, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtNoSourceGenes, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(txtNoSourceGenes);

			lblEachGeneName = new JLabel("Each gene name must be separated by a comma");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, lblExcludeAsSource, -6, SpringLayout.NORTH, lblEachGeneName);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblEachGeneName, 0, SpringLayout.WEST, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblEachGeneName, -170, SpringLayout.EAST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblEachGeneName);

			lblSourceGenes = new JLabel("Source genes:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblSourceGenes, 158, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, lblEachGeneName, 0, SpringLayout.NORTH, lblSourceGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblSourceGenes, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblSourceGenes);

			txtNoDestinationGenes = new JTextArea();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblExcludeAsSource, 0, SpringLayout.EAST, txtNoDestinationGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtNoSourceGenes, -113, SpringLayout.WEST, txtNoDestinationGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtNoDestinationGenes, 0, SpringLayout.NORTH, txtNoSourceGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtNoDestinationGenes, 423, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtNoDestinationGenes, -10, SpringLayout.EAST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(txtNoDestinationGenes);

			lblDestinationGenes = new JLabel("Target genes:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblSourceGenes, -300, SpringLayout.WEST, lblDestinationGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblDestinationGenes, 0, SpringLayout.NORTH, lblSourceGenes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblDestinationGenes, 0, SpringLayout.WEST, txtNoDestinationGenes);
			pnlInnerNetworkEditing.add(lblDestinationGenes);

			cmbEditingFunctionType = new JComboBox<String>();
			cmbEditingFunctionType.setModel(new DefaultComboBoxModel<String>(new String[] {"Boolean"}));
			pnlInnerNetworkEditing.add(cmbEditingFunctionType);

			JLabel lblReplaceTheUndefined = new JLabel("Replace the undefined functions with:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, txtNoSourceGenes, -12, SpringLayout.NORTH, lblReplaceTheUndefined);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblReplaceTheUndefined, 279, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblReplaceTheUndefined, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, cmbEditingFunctionType, 6, SpringLayout.SOUTH, lblReplaceTheUndefined);
			pnlInnerNetworkEditing.add(lblReplaceTheUndefined);

			JLabel lblFunctionsType_1 = new JLabel("Function type:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblFunctionsType_1, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, cmbEditingFunctionType, 11, SpringLayout.EAST, lblFunctionsType_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblFunctionsType_1, 10, SpringLayout.SOUTH, lblReplaceTheUndefined);
			pnlInnerNetworkEditing.add(lblFunctionsType_1);

			chckbxComplitellyDefined = new JCheckBox("Completely defined functions\n");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, txtNoDestinationGenes, -34, SpringLayout.NORTH, chckbxComplitellyDefined);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, chckbxComplitellyDefined, 423, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, cmbEditingFunctionType, -41, SpringLayout.WEST, chckbxComplitellyDefined);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, chckbxComplitellyDefined, 0, SpringLayout.NORTH, cmbEditingFunctionType);
			pnlInnerNetworkEditing.add(chckbxComplitellyDefined);

			lblBias = new JLabel("Random bias-based:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblBias, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblBias);

			txtEditingBiasType = new JTextField();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingBiasType, 6, SpringLayout.EAST, lblBias);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, txtEditingBiasType, -97, SpringLayout.SOUTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingBiasType, -551, SpringLayout.EAST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblBias, 6, SpringLayout.NORTH, txtEditingBiasType);
			txtEditingBiasType.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingBiasType.setText("0.4");
			txtEditingBiasType.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingBiasType);

			lblBiasValue_1 = new JLabel("Bias value:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblBiasValue_1, 408, SpringLayout.NORTH, pnlInnerNetworkEditing);
			pnlInnerNetworkEditing.add(lblBiasValue_1);

			txtEditingBiasValue = new JTextField();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingBiasValue, 402, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingBiasValue, 146, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingBiasValue, -551, SpringLayout.EAST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblBiasValue_1, -6, SpringLayout.WEST, txtEditingBiasValue);
			txtEditingBiasValue.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingBiasValue.setText("0.5");
			txtEditingBiasValue.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingBiasValue);

			lblAnd_1 = new JLabel("Logical AND:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblAnd_1, 0, SpringLayout.NORTH, lblBias);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblAnd_1, 18, SpringLayout.EAST, txtEditingBiasType);
			pnlInnerNetworkEditing.add(lblAnd_1);

			txtEditingAndType = new JTextField();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingAndType, -6, SpringLayout.NORTH, lblBias);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingAndType, 6, SpringLayout.EAST, lblAnd_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingAndType, -400, SpringLayout.EAST, pnlInnerNetworkEditing);
			txtEditingAndType.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingAndType.setText("0.2");
			pnlInnerNetworkEditing.add(txtEditingAndType);
			txtEditingAndType.setColumns(10);

			lblOr_1 = new JLabel("Logical OR:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblOr_1, 0, SpringLayout.NORTH, lblBias);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblOr_1, 14, SpringLayout.EAST, txtEditingAndType);
			pnlInnerNetworkEditing.add(lblOr_1);

			txtEditingOrType = new JTextField();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingOrType, -6, SpringLayout.NORTH, lblBias);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingOrType, 6, SpringLayout.EAST, lblOr_1);
			txtEditingOrType.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingOrType.setText("0.2");
			txtEditingOrType.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingOrType);

			lblCanalizing_1 = new JLabel("Random canalyzing:");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblCanalizing_1, 495, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingOrType, -17, SpringLayout.WEST, lblCanalizing_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblCanalizing_1, 0, SpringLayout.NORTH, lblBias);
			pnlInnerNetworkEditing.add(lblCanalizing_1);

			txtEditingCanalizingType = new JTextField();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingCanalizingType, -6, SpringLayout.NORTH, lblBias);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingCanalizingType, 6, SpringLayout.EAST, lblCanalizing_1);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingCanalizingType, -70, SpringLayout.EAST, pnlInnerNetworkEditing);
			txtEditingCanalizingType.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingCanalizingType.setText("0.2");
			txtEditingCanalizingType.setColumns(10);
			pnlInnerNetworkEditing.add(txtEditingCanalizingType);

			JPanel pnlAugmentedParams = new JPanel();
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, pnlAugmentedParams, 1, SpringLayout.SOUTH, txtEditingNodesNumber);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, pnlAugmentedParams, 10, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.SOUTH, pnlAugmentedParams, -403, SpringLayout.SOUTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, pnlAugmentedParams, 484, SpringLayout.EAST, lblReplaceTheUndefined);
			pnlInnerNetworkEditing.add(pnlAugmentedParams);
			pnlAugmentedParams.setLayout(new CardLayout(0, 0));
			
			JPanel pnlRandomAugmentation = new JPanel();
			pnlAugmentedParams.add(pnlRandomAugmentation, "pnlRandomAugmentation");

			JPanel pnlScalefreeAugmentation = new JPanel();
			pnlScalefreeAugmentation.setSize(pnlScalefreeAugmentation.getSize().width, pnlScalefreeAugmentation.getSize().height + 10);
			FlowLayout flowLayout_1 = (FlowLayout) pnlScalefreeAugmentation.getLayout();
			flowLayout_1.setAlignment(FlowLayout.LEFT);
			pnlAugmentedParams.add(pnlScalefreeAugmentation, "pnlScalefreeAugmentation");
		
			this.cmbAugmentedTopology = new JComboBox<>();
			cmbAugmentedTopology.addItem(SimulationFeaturesConstants.RANDOM_TOPOLOGY);
			cmbAugmentedTopology.addItem(SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
			
			cmbAugmentedTopology.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(cmbAugmentedTopology.getSelectedItem().equals(SimulationFeaturesConstants.RANDOM_TOPOLOGY)){
						((CardLayout)pnlAugmentedParams.getLayout()).show(pnlAugmentedParams, "pnlRandomAugmentation");	
					}else{
						((CardLayout)pnlAugmentedParams.getLayout()).show(pnlAugmentedParams, "pnlScalefreeAugmentation");	
					}
				}
			});
			
			lblTotalEdges = new JLabel("Total number of edges:");
			pnlRandomAugmentation.add(lblTotalEdges);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblTotalEdges, 0, SpringLayout.NORTH, lblTotalNodes);

			txtEditingEdgesNumber = new JTextField();
			pnlRandomAugmentation.add(txtEditingEdgesNumber);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtEditingEdgesNumber, 423, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtEditingEdgesNumber, -6, SpringLayout.NORTH, lblTotalNodes);
			txtEditingEdgesNumber.setHorizontalAlignment(SwingConstants.CENTER);
			txtEditingEdgesNumber.setText("0");
			txtEditingEdgesNumber.setColumns(6);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblTotalEdges, -17, SpringLayout.WEST, txtEditingEdgesNumber);

			lblFixedNumberOf = new JLabel("Fixed number of inputs:");
			pnlRandomAugmentation.add(lblFixedNumberOf);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblFixedNumberOf, 0, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtEditingEdgesNumber, -24, SpringLayout.WEST, lblFixedNumberOf);

			txtFixedInputs = new JTextField();
			pnlRandomAugmentation.add(txtFixedInputs);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, txtFixedInputs, 666, SpringLayout.WEST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, txtFixedInputs, -10, SpringLayout.EAST, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, txtFixedInputs, -6, SpringLayout.NORTH, lblTotalNodes);
			txtFixedInputs.setHorizontalAlignment(SwingConstants.CENTER);
			txtFixedInputs.setText("-1");
			txtFixedInputs.setColumns(6);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblFixedNumberOf, -6, SpringLayout.WEST, txtFixedInputs);

			JLabel lblNewLabel_5 = new JLabel("Note: -1 not considered");
			pnlRandomAugmentation.add(lblNewLabel_5);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblNewLabel_5, 51, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, lblNewLabel_5, -10, SpringLayout.EAST, pnlInnerNetworkEditing);
			
			
			lblAlgorithm = new JLabel("Algorithm: ");
			cmbSFAugmentedAlgorithm = new JComboBox<>();
			cmbSFAugmentedAlgorithm.addItem(SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM);
			cmbSFAugmentedAlgorithm.addItem(SimulationFeaturesConstants.POWER_LAW_ALGORITHM);


			pnlScalefreeAugmentation.add(lblAlgorithm);
			pnlScalefreeAugmentation.add(cmbSFAugmentedAlgorithm);

			JPanel pnlSubSFAugmented = new JPanel();
			JPanel pnlAugmentedBarabasi = new JPanel();
			JPanel pnlAugmentedPowerLaw = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnlAugmentedPowerLaw.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);

			pnlSubSFAugmented.setLayout(new CardLayout());

			pnlSubSFAugmented.add(pnlAugmentedBarabasi, "pnlAugmentedBarabasi");
			pnlSubSFAugmented.add(pnlAugmentedPowerLaw, "pnlAugmentedPowerLaw");

			cmbSFAugmentedAlgorithm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(cmbSFAugmentedAlgorithm.getSelectedItem().equals(SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM)){
						((CardLayout)pnlSubSFAugmented.getLayout()).show(pnlSubSFAugmented, "pnlAugmentedBarabasi");	
					}else{
						((CardLayout)pnlSubSFAugmented.getLayout()).show(pnlSubSFAugmented, "pnlAugmentedPowerLaw");	
					}
				}
			});

			JLabel lblGamma_2 = new JLabel("Gamma: ");
			pnlAugmentedPowerLaw.add(lblGamma_2);

			txtAugPLGamma = new JTextField();
			txtAugPLGamma.setHorizontalAlignment(SwingConstants.CENTER);
			txtAugPLGamma.setText("1.0");
			pnlAugmentedPowerLaw.add(txtAugPLGamma);
			txtAugPLGamma.setColumns(4);
			
			lblAverageConnectivity_2 = new JLabel("Average connectivity: ");
			pnlAugmentedPowerLaw.add(lblAverageConnectivity_2);
			
			txtAvgConPLAug = new JTextField();
			txtAvgConPLAug.setHorizontalAlignment(SwingConstants.CENTER);
			txtAvgConPLAug.setText("1.0");
			pnlAugmentedPowerLaw.add(txtAvgConPLAug);
			txtAvgConPLAug.setColumns(5);

			chkIngoingPowerLaw = new JCheckBox("Ingoing Power Law");
			chkIngoingPowerLaw.setSelected(true);
			pnlAugmentedPowerLaw.add(chkIngoingPowerLaw);
			pnlScalefreeAugmentation.add(pnlSubSFAugmented);


			JLabel lblAverageConnectivity_1 = new JLabel("Average Connectivity: ");

			txtAugmentedAvgConnectivity = new JTextField();
			txtAugmentedAvgConnectivity.setHorizontalAlignment(SwingConstants.CENTER);
			txtAugmentedAvgConnectivity.setText("1");
			txtAugmentedAvgConnectivity.setColumns(6);

			JLabel lblInoutGoingProbability = new JLabel("In/outgoing probability:");

			txtInOutProbAug = new JTextField();
			txtInOutProbAug.setHorizontalAlignment(SwingConstants.CENTER);
			txtInOutProbAug.setText("0.5");
			txtInOutProbAug.setColumns(6);

			pnlAugmentedBarabasi.add(lblAverageConnectivity_1);
			pnlAugmentedBarabasi.add(txtAugmentedAvgConnectivity);
			pnlAugmentedBarabasi.add(lblInoutGoingProbability);
			pnlAugmentedBarabasi.add(txtInOutProbAug);




			JLabel lblTopology = new JLabel("Topology: ");
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, lblTopology, 0, SpringLayout.NORTH, lblTotalNodes);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, lblTopology, 31, SpringLayout.EAST, txtEditingNodesNumber);
			pnlInnerNetworkEditing.add(lblTopology);
			
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.NORTH, cmbAugmentedTopology, 4, SpringLayout.NORTH, pnlInnerNetworkEditing);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.WEST, cmbAugmentedTopology, 21, SpringLayout.EAST, lblTopology);
			sl_pnlInnerNetworkEditing.putConstraint(SpringLayout.EAST, cmbAugmentedTopology, 364, SpringLayout.EAST, lblTopology);
			pnlInnerNetworkEditing.add(cmbAugmentedTopology);

			experimentsPanel = new JPanel();
			contentPanel.add(experimentsPanel, "experimentsPanel");
			experimentsPanel.setLayout(new BorderLayout(0, 0));

			experimentsSubPanel = new JPanel();
			experimentsSubPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Sampling", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			experimentsPanel.add(experimentsSubPanel, BorderLayout.CENTER);
			experimentsSubPanel.setLayout(new CardLayout(0, 0));

			attractorsPanel = new JPanel();
			experimentsSubPanel.add(attractorsPanel, "attractorsPanel");

			lblInitialConditions = new JLabel("Initial configuratons to simulate:");

			txtInitialConditions = new JTextField();
			txtInitialConditions.setHorizontalAlignment(SwingConstants.CENTER);
			txtInitialConditions.setText("100");
			txtInitialConditions.setColumns(10);

			lblSamplingMethod = new JLabel("Sampling of the initial configurations:");

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

			JLabel lblSimulationStepsCutoff = new JLabel("<html>Maximum number of simulation steps (simulation cutoff) (-1 means unrestricted search)</html>");

			txtCutoff = new JTextField();
			txtCutoff.setHorizontalAlignment(SwingConstants.CENTER);
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
													.addComponent(cmbSamplingType, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_attractorsPanel.createSequentialGroup()
															.addGap(6)
															.addComponent(txtInitialConditions, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
											.addContainerGap(12, Short.MAX_VALUE))
									.addGroup(Alignment.TRAILING, gl_attractorsPanel.createSequentialGroup()
											.addComponent(lblSimulationStepsCutoff, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
											.addGap(18)
											.addComponent(txtCutoff, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
											.addGap(34))))
					);
			gl_attractorsPanel.setVerticalGroup(
					gl_attractorsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_attractorsPanel.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblSamplingMethod)
									.addComponent(cmbSamplingType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_attractorsPanel.createSequentialGroup()
											.addGroup(gl_attractorsPanel.createParallelGroup(Alignment.BASELINE)
													.addComponent(lblInitialConditions)
													.addComponent(txtInitialConditions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblSimulationStepsCutoff))
									.addComponent(txtCutoff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(372, Short.MAX_VALUE))
					);
			attractorsPanel.setLayout(gl_attractorsPanel);

			atmPanel = new JPanel();
			experimentsSubPanel.add(atmPanel, "atmPanel");
			atmPanel.setLayout(new BorderLayout(0, 0));

			atmCalulationSelectionPanel = new JPanel();
			atmPanel.add(atmCalulationSelectionPanel, BorderLayout.NORTH);

			chkAtmCalculation = new JCheckBox("Compute the stability matrix of the attractors (Attractor Transition Matrix, ATM) \n");
			chkAtmCalculation.setHorizontalAlignment(SwingConstants.LEFT);
			chkAtmCalculation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Shows the param selection panel only if the checkbox is selected
					if(chkAtmCalculation.isSelected()){
						atmCalculationParmPanel.setVisible(true);
					}else{
						atmCalculationParmPanel.setVisible(false);
					}

				}
			});
			chkAtmCalculation.setSelected(true);

			lblPerformAtmCalculation = new JLabel("");
			GroupLayout gl_atmCalulationSelectionPanel = new GroupLayout(atmCalulationSelectionPanel);
			gl_atmCalulationSelectionPanel.setHorizontalGroup(
					gl_atmCalulationSelectionPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_atmCalulationSelectionPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chkAtmCalculation, GroupLayout.PREFERRED_SIZE, 627, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPerformAtmCalculation, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
					);
			gl_atmCalulationSelectionPanel.setVerticalGroup(
					gl_atmCalulationSelectionPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_atmCalulationSelectionPanel.createSequentialGroup()
							.addGroup(gl_atmCalulationSelectionPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblPerformAtmCalculation, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_atmCalulationSelectionPanel.createSequentialGroup()
											.addGap(14)
											.addComponent(chkAtmCalculation)))
							.addContainerGap(15, Short.MAX_VALUE))
					);
			atmCalulationSelectionPanel.setLayout(gl_atmCalulationSelectionPanel);

			atmCalculationParmPanel = new JPanel();
			atmPanel.add(atmCalculationParmPanel, BorderLayout.CENTER);
			atmCalculationParmPanel.setLayout(new BorderLayout(0, 0));

			perturbationTypePanel = new JPanel();
			atmCalculationParmPanel.add(perturbationTypePanel, BorderLayout.NORTH);

			lblPerturbationType = new JLabel("Perturbation type:");

			mutationsTypeComboBox = new JComboBox<String>();
			mutationsTypeComboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(mutationsTypeComboBox.getSelectedItem().equals("Node Flip: 1—> 0, 0—> 1")){
						((CardLayout)variableExperimentsPanel.getLayout()).show(variableExperimentsPanel, "flipsPanel");	
					}else{
						((CardLayout)variableExperimentsPanel.getLayout()).show(variableExperimentsPanel, "temporaryMutationsPanel");
						txtExperimentsNumber.setEnabled(true);
						txtRatioStatesMutations.setEnabled(true);
					}
				}
			});

			mutationsTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
					"Node Flip: 1—> 0, 0—> 1", "Node KnockIn-KnockOut"}));
			GroupLayout gl_perturbationTypePanel = new GroupLayout(perturbationTypePanel);
			gl_perturbationTypePanel.setHorizontalGroup(
					gl_perturbationTypePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_perturbationTypePanel.createSequentialGroup()
							.addComponent(lblPerturbationType, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
							.addComponent(mutationsTypeComboBox, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
					);
			gl_perturbationTypePanel.setVerticalGroup(
					gl_perturbationTypePanel.createParallelGroup(Alignment.LEADING)
					.addComponent(lblPerturbationType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addComponent(mutationsTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					);
			perturbationTypePanel.setLayout(gl_perturbationTypePanel);

			commonExperimentsPanel = new JPanel();
			atmCalculationParmPanel.add(commonExperimentsPanel, BorderLayout.SOUTH);

			lblRatioOfAttractors = new JLabel("Ratio of randomly selected attractor states in which performing the perturbations:");

			lblNewLabel_2 = new JLabel("Number of randomly selected single/multiple node perturbations for each attractor state:\n");

			txtExperimentsNumber = new JTextField();
			txtExperimentsNumber.setHorizontalAlignment(SwingConstants.CENTER);
			txtExperimentsNumber.setText("1");
			txtExperimentsNumber.setColumns(10);

			txtRatioStatesMutations = new JTextField();
			txtRatioStatesMutations.setHorizontalAlignment(SwingConstants.CENTER);
			txtRatioStatesMutations.setText("0.5");
			txtRatioStatesMutations.setColumns(10);
			GroupLayout gl_commonExperimentsPanel = new GroupLayout(commonExperimentsPanel);
			gl_commonExperimentsPanel.setHorizontalGroup(
					gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_commonExperimentsPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 571, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblRatioOfAttractors, GroupLayout.PREFERRED_SIZE, 527, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(txtRatioStatesMutations, 0, 0, Short.MAX_VALUE)
									.addComponent(txtExperimentsNumber, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
							.addGap(21))
					);
			gl_commonExperimentsPanel.setVerticalGroup(
					gl_commonExperimentsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_commonExperimentsPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_commonExperimentsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtExperimentsNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_commonExperimentsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblRatioOfAttractors, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtRatioStatesMutations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
					);
			commonExperimentsPanel.setLayout(gl_commonExperimentsPanel);

			variableExperimentsPanel = new JPanel();
			variableExperimentsPanel.setForeground(new Color(0, 0, 0));
			variableExperimentsPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			atmCalculationParmPanel.add(variableExperimentsPanel, BorderLayout.CENTER);
			variableExperimentsPanel.setLayout(new CardLayout(0, 0));

			flipsPanel = new JPanel();
			variableExperimentsPanel.add(flipsPanel, "flipsPanel");

			lblNumberOfNodes_1 = new JLabel("Number of random nodes to flip in each perturbation experiment:");

			txtNumberOfFlips = new JTextField();
			txtNumberOfFlips.setHorizontalAlignment(SwingConstants.CENTER);
			txtNumberOfFlips.setText("1");
			txtNumberOfFlips.setColumns(10);

			lblMinFlipDuration = new JLabel("Mininum duration of the perturbation:");

			txtMinFlipTimes = new JTextField();
			txtMinFlipTimes.setHorizontalAlignment(SwingConstants.CENTER);
			txtMinFlipTimes.setText("1");
			txtMinFlipTimes.setColumns(10);

			lblMaxFlipDuration = new JLabel("Maximum duration of the perturbation ");

			txtMaxFlipTimes = new JTextField();
			txtMaxFlipTimes.setHorizontalAlignment(SwingConstants.CENTER);
			txtMaxFlipTimes.setText("1");
			txtMaxFlipTimes.setColumns(10);

			chkCompleteFlipExp = new JCheckBox("Complete Flip experiment (One Filp for each node and state)");
			chkCompleteFlipExp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtExperimentsNumber.setEnabled(!chkCompleteFlipExp.isSelected());
					txtRatioStatesMutations.setEnabled(!chkCompleteFlipExp.isSelected());
				}
			});
			GroupLayout gl_flipsPanel = new GroupLayout(flipsPanel);
			gl_flipsPanel.setHorizontalGroup(
					gl_flipsPanel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_flipsPanel.createSequentialGroup()
							.addGroup(gl_flipsPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_flipsPanel.createSequentialGroup()
											.addGap(8)
											.addComponent(lblNumberOfNodes_1)
											.addGap(27)
											.addComponent(txtNumberOfFlips, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_flipsPanel.createSequentialGroup()
											.addContainerGap()
											.addGroup(gl_flipsPanel.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_flipsPanel.createSequentialGroup()
															.addComponent(lblMinFlipDuration)
															.addGap(18)
															.addComponent(txtMinFlipTimes, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_flipsPanel.createSequentialGroup()
															.addComponent(lblMaxFlipDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
															.addGap(12)
															.addComponent(txtMaxFlipTimes, 0, 55, Short.MAX_VALUE)))
											.addGap(169)))
							.addGap(292))
					.addGroup(Alignment.LEADING, gl_flipsPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chkCompleteFlipExp)
							.addContainerGap(592, Short.MAX_VALUE))
					);
			gl_flipsPanel.setVerticalGroup(
					gl_flipsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_flipsPanel.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_flipsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNumberOfNodes_1)
									.addComponent(txtNumberOfFlips, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_flipsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtMinFlipTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblMinFlipDuration))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_flipsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtMaxFlipTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblMaxFlipDuration, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chkCompleteFlipExp)
							.addGap(198))
					);
			flipsPanel.setLayout(gl_flipsPanel);

			temporaryMutationsPanel = new JPanel();
			variableExperimentsPanel.add(temporaryMutationsPanel, "temporaryMutationsPanel");

			lblNumberOfNodes_2 = new JLabel("Number of random nodes to knock-in:");

			txtKnockInNodes = new JTextField();
			txtKnockInNodes.setHorizontalAlignment(SwingConstants.CENTER);
			txtKnockInNodes.setText("1");
			txtKnockInNodes.setColumns(10);

			lblMinKnockinTime = new JLabel("Minimum duration of the knock-in:");

			txtMinKnockInTimes = new JTextField();
			txtMinKnockInTimes.setHorizontalAlignment(SwingConstants.CENTER);
			txtMinKnockInTimes.setText("1");
			txtMinKnockInTimes.setColumns(10);

			lblMaxKnockinTime = new JLabel("Maximum duration of the knock-in:");

			txtMaxKnockInTimes = new JTextField();
			txtMaxKnockInTimes.setHorizontalAlignment(SwingConstants.CENTER);
			txtMaxKnockInTimes.setText("1");
			txtMaxKnockInTimes.setColumns(10);

			lblNumberOfNodes_3 = new JLabel("Number of random nodes to knock-out:");

			txtKnockOutNodes = new JTextField();
			txtKnockOutNodes.setHorizontalAlignment(SwingConstants.CENTER);
			txtKnockOutNodes.setText("1");
			txtKnockOutNodes.setColumns(10);

			lblNewLabel_3 = new JLabel("Minimum duration of the knock-out:");

			txtMinKnockOutTimes = new JTextField();
			txtMinKnockOutTimes.setHorizontalAlignment(SwingConstants.CENTER);
			txtMinKnockOutTimes.setText("1");
			txtMinKnockOutTimes.setColumns(10);

			lblNewLabel_4 = new JLabel("Maximum duration of the knock-out:");

			txtMaxKnockOutTimes = new JTextField();
			txtMaxKnockOutTimes.setHorizontalAlignment(SwingConstants.CENTER);
			txtMaxKnockOutTimes.setText("1");
			txtMaxKnockOutTimes.setColumns(10);
			GroupLayout gl_temporaryMutationsPanel = new GroupLayout(temporaryMutationsPanel);
			gl_temporaryMutationsPanel.setHorizontalGroup(
					gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_temporaryMutationsPanel.createSequentialGroup()
							.addGap(17)
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
											.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
													.addComponent(lblMinKnockinTime)
													.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
															.addPreferredGap(ComponentPlacement.RELATED)
															.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
																	.addComponent(lblMaxKnockinTime, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
																	.addComponent(lblNumberOfNodes_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																	.addComponent(lblNewLabel_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																	.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
											.addGap(18))
									.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
											.addComponent(lblNumberOfNodes_2)
											.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(txtKnockOutNodes, 0, 0, Short.MAX_VALUE)
									.addComponent(txtMinKnockOutTimes, 0, 0, Short.MAX_VALUE)
									.addComponent(txtKnockInNodes, 0, 0, Short.MAX_VALUE)
									.addComponent(txtMaxKnockInTimes, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
									.addComponent(txtMinKnockInTimes, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
									.addComponent(txtMaxKnockOutTimes, Alignment.TRAILING, 0, 0, Short.MAX_VALUE))
							.addGap(327))
					);
			gl_temporaryMutationsPanel.setVerticalGroup(
					gl_temporaryMutationsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
											.addGap(11)
											.addComponent(txtKnockInNodes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
											.addContainerGap()
											.addComponent(lblNumberOfNodes_2, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblMinKnockinTime)
									.addComponent(txtMinKnockInTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblMaxKnockinTime, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
									.addGroup(gl_temporaryMutationsPanel.createSequentialGroup()
											.addGap(5)
											.addComponent(txtMaxKnockInTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(32)
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtKnockOutNodes)
									.addComponent(lblNumberOfNodes_3))
							.addGap(5)
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtMinKnockOutTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_3))
							.addGap(5)
							.addGroup(gl_temporaryMutationsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtMaxKnockOutTimes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(102))
					);
			temporaryMutationsPanel.setLayout(gl_temporaryMutationsPanel);

			treeMatchingPanel = new JPanel();
			contentPanel.add(treeMatchingPanel, "treeMatchingPanel");
			treeMatchingPanel.setLayout(new BorderLayout(0, 0));

			treeMatchingSubPanel = new JPanel();
			treeMatchingPanel.add(treeMatchingSubPanel, BorderLayout.CENTER);
			SpringLayout sl_treeMatchingSubPanel = new SpringLayout();
			treeMatchingSubPanel.setLayout(sl_treeMatchingSubPanel);

			InputTreePanel = new JPanel();
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, InputTreePanel, 10, SpringLayout.WEST, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, InputTreePanel, 732, SpringLayout.WEST, treeMatchingSubPanel);
			treeMatchingSubPanel.add(InputTreePanel);

			final JLabel lblTreeFile = new JLabel("Differentiation tree input file (any format):");

			txtTreeFilePath = new JTextField();
			txtTreeFilePath.setColumns(10);

			final JButton btnOpen = new JButton("Open");
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
			txtThreshold.setHorizontalAlignment(SwingConstants.CENTER);
			txtThreshold.setText("0");
			txtThreshold.setColumns(10);

			radioTreeFromFile = new JRadioButton("Tree from file");
			radioTreeFromFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtTreeFilePath.setVisible(true);
					lblTreeFile.setVisible(true);
					btnOpen.setVisible(true);
				}
			});
			treeFromGroup.add(radioTreeFromFile);
			radioTreeFromFile.setSelected(true);

			radioTreeFromCytoscape = new JRadioButton("Tree from Cytoscape");
			radioTreeFromCytoscape.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtTreeFilePath.setVisible(false);
					lblTreeFile.setVisible(false);
					btnOpen.setVisible(false);
				}
			});
			treeFromGroup.add(radioTreeFromCytoscape);

			JLabel lblDistancebasedSelection = new JLabel("Distance-based selection:");


			GroupLayout gl_InputTreePanel = new GroupLayout(InputTreePanel);
			gl_InputTreePanel.setHorizontalGroup(
					gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_InputTreePanel.createSequentialGroup()
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_InputTreePanel.createSequentialGroup()
											.addContainerGap()
											.addComponent(radioTreeFromFile)
											.addGap(43)
											.addComponent(radioTreeFromCytoscape, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_InputTreePanel.createSequentialGroup()
											.addGap(18)
											.addComponent(lblMatchingThreshold, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
											.addGap(20)
											.addComponent(txtThreshold, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_InputTreePanel.createSequentialGroup()
											.addGap(17)
											.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
													.addComponent(lblDistance)
													.addGroup(gl_InputTreePanel.createSequentialGroup()
															.addComponent(lblTreeFile)
															.addPreferredGap(ComponentPlacement.UNRELATED)
															.addComponent(txtTreeFilePath, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)))))
							.addGap(24)
							.addComponent(btnOpen))
					.addGroup(gl_InputTreePanel.createSequentialGroup()
							.addGap(57)
							.addComponent(lblDistancebasedSelection, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
									.addComponent(rdbtnPerfectMatch, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_InputTreePanel.createSequentialGroup()
											.addComponent(rdbtnMinDistance)
											.addGap(31)
											.addComponent(rdoHistogramDistance, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)))
							.addContainerGap(125, GroupLayout.PREFERRED_SIZE))
					);
			gl_InputTreePanel.setVerticalGroup(
					gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_InputTreePanel.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(radioTreeFromFile)
									.addComponent(radioTreeFromCytoscape))
							.addGap(13)
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnOpen)
									.addComponent(lblTreeFile)
									.addComponent(txtTreeFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_InputTreePanel.createSequentialGroup()
											.addGap(18)
											.addComponent(rdbtnPerfectMatch))
									.addGroup(gl_InputTreePanel.createSequentialGroup()
											.addGap(18)
											.addComponent(lblDistance)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblDistancebasedSelection)
									.addComponent(rdbtnMinDistance)
									.addComponent(rdoHistogramDistance))
							.addGap(18)
							.addGroup(gl_InputTreePanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblMatchingThreshold)
									.addComponent(txtThreshold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(93))
					);
			InputTreePanel.setLayout(gl_InputTreePanel);

			JPanel panel = new JPanel();
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, panel, 272, SpringLayout.NORTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.SOUTH, InputTreePanel, -6, SpringLayout.NORTH, panel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, panel, 732, SpringLayout.WEST, treeMatchingSubPanel);
			treeMatchingSubPanel.add(panel);

			chckbxComputeRepresentativeTree = new JCheckBox("Compute representative tree");
			chckbxComputeRepresentativeTree.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pnlRepresentativeTree.setVisible(chckbxComputeRepresentativeTree.isSelected());
				}
			});
			grpDepth = new ButtonGroup();

			pnlRepresentativeTree = new JPanel();
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(pnlRepresentativeTree, GroupLayout.PREFERRED_SIZE, 619, GroupLayout.PREFERRED_SIZE)
									.addComponent(chckbxComputeRepresentativeTree))
							.addContainerGap(97, Short.MAX_VALUE))
					);
			gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxComputeRepresentativeTree)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlRepresentativeTree, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(22, Short.MAX_VALUE))
					);

			txtDepthValue = new JTextField();
			txtDepthValue.setText("1");
			txtDepthValue.setHorizontalAlignment(SwingConstants.CENTER);
			txtDepthValue.setColumns(10);

			rdbtnLogn = new JRadioButton("log2(n)");
			rdbtnLogn.setSelected(true);
			rdbtnLogn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDepthValue.setVisible(false);
				}
			});
			grpDepth.add(rdbtnLogn);

			rdbtnRatioOfAttractors = new JRadioButton("Ratio of attractors");
			rdbtnRatioOfAttractors.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDepthValue.setVisible(true);
				}
			});
			grpDepth.add(rdbtnRatioOfAttractors);
			rdbtnAbsolute = new JRadioButton("Absolute");
			rdbtnAbsolute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtDepthValue.setVisible(true);
				}
			});
			grpDepth.add(rdbtnAbsolute);

			JLabel lblTreeDepth = new JLabel("Tree depth:");

			JLabel lblMaximumTreeTo = new JLabel("Maximum tree to test:");

			txtRepresentativeTreeCutoff = new JTextField();
			txtRepresentativeTreeCutoff.setText("-1");
			txtRepresentativeTreeCutoff.setHorizontalAlignment(SwingConstants.CENTER);
			txtRepresentativeTreeCutoff.setColumns(10);

			JLabel lblMeansNo = new JLabel("(-1 means no cutoff)");

			JLabel lblforBothAbsolute = new JLabel("(For both Absolute and Ratio)");
			GroupLayout gl_pnlRepresentativeTree = new GroupLayout(pnlRepresentativeTree);
			gl_pnlRepresentativeTree.setHorizontalGroup(
					gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
							.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
											.addContainerGap()
											.addComponent(lblTreeDepth))
									.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
											.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
															.addGap(30)
															.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
																	.addComponent(rdbtnLogn)
																	.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
																			.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
																					.addComponent(rdbtnRatioOfAttractors)
																					.addComponent(rdbtnAbsolute))
																			.addGap(38)
																			.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
																					.addComponent(txtRepresentativeTreeCutoff, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
																					.addComponent(txtDepthValue, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))))
													.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
															.addContainerGap()
															.addComponent(lblMaximumTreeTo)))
											.addGap(18)
											.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
													.addComponent(lblforBothAbsolute, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblMeansNo))))
							.addContainerGap(86, Short.MAX_VALUE))
					);
			gl_pnlRepresentativeTree.setVerticalGroup(
					gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
							.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
											.addGap(7)
											.addComponent(lblTreeDepth)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(rdbtnLogn)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(rdbtnAbsolute)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(rdbtnRatioOfAttractors))
									.addGroup(gl_pnlRepresentativeTree.createSequentialGroup()
											.addGap(69)
											.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.BASELINE)
													.addComponent(txtDepthValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(lblforBothAbsolute))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlRepresentativeTree.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblMaximumTreeTo)
									.addComponent(txtRepresentativeTreeCutoff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblMeansNo))
							.addContainerGap(38, Short.MAX_VALUE))
					);
			pnlRepresentativeTree.setLayout(gl_pnlRepresentativeTree);
			pnlRepresentativeTree.setVisible(false);
			panel.setLayout(gl_panel);

			chckbxMatchNetworksWith = new JCheckBox("Select only the networks in which the emergent differentiation tree matches with an input tree");
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.SOUTH, chckbxMatchNetworksWith, -451, SpringLayout.SOUTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, InputTreePanel, 6, SpringLayout.SOUTH, chckbxMatchNetworksWith);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, chckbxMatchNetworksWith, 10, SpringLayout.WEST, treeMatchingSubPanel);
			treeMatchingSubPanel.add(chckbxMatchNetworksWith);
			chckbxMatchNetworksWith.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					InputTreePanel.setVisible(chckbxMatchNetworksWith.isSelected());
				}
			});
			chckbxMatchNetworksWith.setSelected(true);

			lblMaximumNumberOf = new JLabel("Maximum number of children for a complete test:");
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, lblMaximumNumberOf, 10, SpringLayout.NORTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, lblMaximumNumberOf, 10, SpringLayout.WEST, treeMatchingSubPanel);
			treeMatchingSubPanel.add(lblMaximumNumberOf);

			txtMaxChildren = new JTextField();
			txtMaxChildren.setText("5");
			txtMaxChildren.setHorizontalAlignment(SwingConstants.CENTER);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, txtMaxChildren, 4, SpringLayout.NORTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, txtMaxChildren, 6, SpringLayout.EAST, lblMaximumNumberOf);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, txtMaxChildren, 66, SpringLayout.EAST, lblMaximumNumberOf);
			treeMatchingSubPanel.add(txtMaxChildren);
			txtMaxChildren.setColumns(10);

			lblPermutationsCutoffProbability = new JLabel("Permutations cutoff probability:");
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, lblPermutationsCutoffProbability, 0, SpringLayout.NORTH, lblMaximumNumberOf);
			treeMatchingSubPanel.add(lblPermutationsCutoffProbability);

			txtPermProb = new JTextField();
			txtPermProb.setHorizontalAlignment(SwingConstants.CENTER);
			txtPermProb.setText("0.5");
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, lblPermutationsCutoffProbability, -6, SpringLayout.WEST, txtPermProb);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.NORTH, txtPermProb, 4, SpringLayout.NORTH, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.WEST, txtPermProb, -70, SpringLayout.EAST, treeMatchingSubPanel);
			sl_treeMatchingSubPanel.putConstraint(SpringLayout.EAST, txtPermProb, -10, SpringLayout.EAST, treeMatchingSubPanel);
			txtPermProb.setColumns(10);
			treeMatchingSubPanel.add(txtPermProb);

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
			chkNetworksOutput.setEnabled(false);
			chkNetworksOutput.setSelected(true);

			chkAttractorsNetworkOutput = new JCheckBox("Attractors Network");
			chkAttractorsNetworkOutput.setSelected(true);

			JLabel lblCytoscapeViews = new JLabel("Cytoscape views");

			chkExportToFileSystem = new JCheckBox("Select the files to export:");
			chkExportToFileSystem.setSelected(true);
			chkExportToFileSystem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Shows the export box only if the export option is selected
					pnlExport.setVisible(chkExportToFileSystem.isSelected());

				}
			});

			pnlExport = new JPanel();

			JLabel lblInfoCABERNETFunctions = new JLabel("<html>Note that all the other CABERNET functions are acessibled from the Cytoscape application menu bar.</html>\n");
			lblInfoCABERNETFunctions.setFont(new Font("Lucida Grande", Font.BOLD, 13));

			chckbxAllTrees = new JCheckBox("All trees");
			GroupLayout gl_cytoscapeOutputsPanel = new GroupLayout(cytoscapeOutputsPanel);
			gl_cytoscapeOutputsPanel.setHorizontalGroup(
					gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
							.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
											.addGap(55)
											.addComponent(chkNetworksOutput)
											.addGap(18)
											.addComponent(chkAttractorsNetworkOutput)
											.addGap(34)
											.addComponent(chckbxAllTrees))
									.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
											.addContainerGap()
											.addComponent(lblCytoscapeViews))
									.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
											.addContainerGap()
											.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
													.addComponent(chkExportToFileSystem)
													.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
															.addGap(8)
															.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.TRAILING)
																	.addComponent(lblInfoCABERNETFunctions)
																	.addComponent(pnlExport, GroupLayout.PREFERRED_SIZE, 671, GroupLayout.PREFERRED_SIZE))))))
							.addContainerGap(39, Short.MAX_VALUE))
					);
			gl_cytoscapeOutputsPanel.setVerticalGroup(
					gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_cytoscapeOutputsPanel.createSequentialGroup()
							.addGap(16)
							.addComponent(lblCytoscapeViews)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_cytoscapeOutputsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(chkNetworksOutput)
									.addComponent(chkAttractorsNetworkOutput)
									.addComponent(chckbxAllTrees))
							.addGap(24)
							.addComponent(chkExportToFileSystem)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlExport, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
							.addGap(47)
							.addComponent(lblInfoCABERNETFunctions)
							.addContainerGap(108, Short.MAX_VALUE))
					);

			JButton btnSelectDirectory = new JButton("Select directory");
			btnSelectDirectory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Opens the form for the directory selection
					//Opens the frame for imports the tree from file.
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = fileChooser.showOpenDialog(contentPanel);
					if (result == JFileChooser.APPROVE_OPTION) {
						txtOutputPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					}
				}
			});

			lblExportPath = new JLabel("Output directory");

			txtOutputPath = new JTextField();
			txtOutputPath.setColumns(10);

			chkGrnml = new JCheckBox("Networks ( *.grnml)");
			chkSif = new JCheckBox("Networks ( *.sif)");
			chkStates = new JCheckBox("States in each attractor(*.csv)");
			chkAttractors = new JCheckBox("Attractors (*.csv)");
			chkAtm = new JCheckBox("ATM (*.csv)");
			chkSynthesis = new JCheckBox("Synthesis file (*.csv)");
			chkAttractorLenghts = new JCheckBox("Attractor lenghts (*.csv)");
			chkBasins = new JCheckBox("Basins of attraction (*.csv)");

			GroupLayout gl_pnlExport = new GroupLayout(pnlExport);
			gl_pnlExport.setHorizontalGroup(
					gl_pnlExport.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlExport.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlExport.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlExport.createSequentialGroup()
											.addComponent(lblExportPath)
											.addGap(12)
											.addComponent(txtOutputPath, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
											.addGap(12)
											.addComponent(btnSelectDirectory))
									.addGroup(gl_pnlExport.createSequentialGroup()
											.addComponent(chkGrnml)
											.addGap(18)
											.addComponent(chkSif, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
									.addComponent(chkAtm)
									.addGroup(gl_pnlExport.createSequentialGroup()
											.addComponent(chkAttractors)
											.addGap(35)
											.addComponent(chkStates, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE))
									.addComponent(chkSynthesis, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlExport.createSequentialGroup()
											.addComponent(chkAttractorLenghts, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
											.addGap(6)
											.addComponent(chkBasins, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(75, Short.MAX_VALUE))
					);
			gl_pnlExport.setVerticalGroup(
					gl_pnlExport.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_pnlExport.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_pnlExport.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pnlExport.createSequentialGroup()
											.addGap(6)
											.addComponent(lblExportPath))
									.addComponent(txtOutputPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_pnlExport.createSequentialGroup()
											.addGap(1)
											.addComponent(btnSelectDirectory)))
							.addGap(18)
							.addGroup(gl_pnlExport.createParallelGroup(Alignment.LEADING)
									.addComponent(chkGrnml)
									.addComponent(chkSif))
							.addGap(6)
							.addComponent(chkAtm)
							.addGap(6)
							.addGroup(gl_pnlExport.createParallelGroup(Alignment.LEADING)
									.addComponent(chkAttractors)
									.addComponent(chkStates))
							.addGap(6)
							.addComponent(chkSynthesis)
							.addGap(6)
							.addGroup(gl_pnlExport.createParallelGroup(Alignment.LEADING)
									.addComponent(chkAttractorLenghts)
									.addComponent(chkBasins))
							.addContainerGap())
					);
			pnlExport.setLayout(gl_pnlExport);
			cytoscapeOutputsPanel.setLayout(gl_cytoscapeOutputsPanel);
		}



		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnNext = new JButton("Next");
		btnNext.addActionListener(new NextButtonAction(this));
		buttonPane.add(btnNext);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Closes the window
				closeWindow();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}

	/**
	 * This method show the dialog.
	 * @return the exit code: 1 for correct ending
	 */
	public int showWizard(){
		System.out.println("SHOW WIZARD");
		this.setModal(true);
		this.setVisible(true);
		return 1;

	}

	/**
	 * This method closes the wizard window.
	 */
	public void closeWindow(){
		this.setVisible(false);
	}

	/**
	 * This method returns the differentiation tree
	 * @return the read tree.
	 */
	public TesTree getDifferentiationTree(){
		return this.tree;
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
	 * Returns the loaded networks paths
	 * @return the networks pathes
	 */
	public ArrayList<String> getInputNetworks(){
		return inputNetworks;
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
						if(inputNetworks.isEmpty())
							throw new Exception("At least one network must be loaded.");
						tasks.setProperty(CABERNETConstants.NETWORK_CREATION, CABERNETConstants.OPEN);
						((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "requiredNetworksPanel");	
						form = "required-networks";		
					}else{
						if(inputMethod.equals("New random networks from features")){
							tasks.setProperty(CABERNETConstants.NETWORK_CREATION, CABERNETConstants.NEW);
						}else if(inputMethod.equals("Partial network from GRNML file")){
							tasks.setProperty(CABERNETConstants.NETWORK_CREATION, CABERNETConstants.EDIT);
							editing = true;
						}else if(inputMethod.equals("Partial network from Cytoscape selected view")){
							//Checks if a view exist...
							if(!netManagment.isNetworkSelected())
								throw new NullPointerException("No networks selected.");
							editing = true;
							tasks.setProperty(CABERNETConstants.NETWORK_CREATION, CABERNETConstants.CYTOSCAPE_EDIT);
						}
						((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "networkInputFeaturesMethod");	
						form = "features-input-method";	
					}
				}else if(form.equals("features-input-method")){

					//Features manually added
					if(rdbtnFeaturesFromForm.isSelected()){
						((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "requiredNetworksPanel");	
						form = "required-networks";

						//Features from file
					}else{
						//Loads the features from file
						if(!txtFeaturesFilePath.getText().equals("")){
							Properties readFeatures = Input.readSimulationFeatures(txtFeaturesFilePath.getText());
							if(!readFeatures.containsKey(CABERNETConstants.ATM_COMPUTATION)){
								throw new FeaturesException(CABERNETConstants.ATM_COMPUTATION + " key missed");
							}
							tasks.setProperty(CABERNETConstants.ATM_COMPUTATION, readFeatures.getProperty(CABERNETConstants.ATM_COMPUTATION));

							if(!readFeatures.containsKey(CABERNETConstants.TREE_MATCHING)){
								throw new FeaturesException(CABERNETConstants.TREE_MATCHING + " key missed");
							}
							tasks.setProperty(CABERNETConstants.TREE_MATCHING, readFeatures.getProperty(CABERNETConstants.TREE_MATCHING));
							if(readFeatures.getProperty(CABERNETConstants.TREE_MATCHING).equals(CABERNETConstants.YES)){
								if(!readFeatures.containsKey(CABERNETConstants.MATCHING_TYPE)){
									throw new FeaturesException(CABERNETConstants.MATCHING_TYPE + " key missed");
								}else if(!readFeatures.getProperty(CABERNETConstants.MATCHING_TYPE).equals(CABERNETConstants.PERFECT_MATCH) &&
										!readFeatures.getProperty(CABERNETConstants.MATCHING_TYPE).equals(CABERNETConstants.MIN_DISTANCE) &&	
										!readFeatures.getProperty(CABERNETConstants.MATCHING_TYPE).equals(CABERNETConstants.HISTOGRAM_DISTANCE)){
									throw new FeaturesException("The " + CABERNETConstants.MATCHING_TYPE + " value must be " +
											CABERNETConstants.PERFECT_MATCH + " or " + CABERNETConstants.MIN_DISTANCE + " or " + 
											CABERNETConstants.HISTOGRAM_DISTANCE);	
								}else if(readFeatures.getProperty(CABERNETConstants.MATCHING_TYPE).equals(CABERNETConstants.MIN_DISTANCE) ||	
										readFeatures.getProperty(CABERNETConstants.MATCHING_TYPE).equals(CABERNETConstants.HISTOGRAM_DISTANCE)){
									if(!readFeatures.containsKey(CABERNETConstants.MATCHING_THRESHOLD)){
										throw new FeaturesException(CABERNETConstants.MATCHING_THRESHOLD + " key missed");
									}else{
										tasks.setProperty(CABERNETConstants.MATCHING_THRESHOLD, readFeatures.getProperty(CABERNETConstants.MATCHING_THRESHOLD));
									}
								}
								tasks.setProperty(CABERNETConstants.MATCHING_TYPE, readFeatures.getProperty(CABERNETConstants.MATCHING_TYPE));
							}
							//Simulation features checking
							if(!readFeatures.containsKey(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY)){
								throw new FeaturesException(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY + " key missed");
							}
							//							simulationFeatures.setProperty(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY, 
							//									readFeatures.getProperty(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY));

							simulationFeatures = readFeatures;

							//New step: outputs page
							lblNetworkGenerationMode.setBackground(Color.WHITE);
							lblTreeMatchingList.setBackground(UIManager.getColor("Button.background"));

							form = "tree-matching";
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "treeMatchingPanel");	
						}
					}
					//Required networks
				}else if(form.equals("required-networks")){
					if(!txtRequiredNetworks.getText().equals("")){
						if(Integer.parseInt(txtRequiredNetworks.getText()) <= 0)
							throw new FeaturesException(SimulationFeaturesConstants.MATCHING_NETWORKS + " value must be greater than 0");
						simulationFeatures.setProperty(SimulationFeaturesConstants.MATCHING_NETWORKS, txtRequiredNetworks.getText());

						if(tasks.containsKey(CABERNETConstants.NETWORK_CREATION) &&
								tasks.getProperty(CABERNETConstants.NETWORK_CREATION).equals(CABERNETConstants.OPEN)){
							form = "attractors-params";
							lblNetworkGenerationMode.setBackground(Color.WHITE);
							lblSamplingList.setBackground(UIManager.getColor("Button.background"));
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "experimentsPanel");
						}else if(editing){
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "pnlNetworkEditing");	
							lblTopologyAndFunctionsList.setBackground(UIManager.getColor("Button.background"));
							lblNetworkGenerationMode.setBackground(Color.WHITE);
							form = "editing-features";
						}else{
							((CardLayout)networkDefinitionSubPanel.getLayout()).show(networkDefinitionSubPanel, "networkFeaturesPanel");	
							lblTopologyAndFunctionsList.setBackground(UIManager.getColor("Button.background"));
							lblNetworkGenerationMode.setBackground(Color.WHITE);
							form = "features-for-editing";
							//Disables the next button for the features insertion
							btnNext.setEnabled(false);
						}
					}
					//Network editing form
				}else if(form.equals("editing-features")){
					if(!txtEditingNodesNumber.getText().equals("") &&
							!txtEditingBiasType.getText().equals("") && !txtEditingBiasValue.getText().equals("") &&
							!txtEditingOrType.getText().equals("") && !txtEditingAndType.getText().equals("") &&
							!txtEditingCanalizingType.getText().equals("")){

						Integer nodes;
						Double and, or, biased, bias, canalizing;

						//Sets the properties
						nodes = Integer.parseInt(txtEditingNodesNumber.getText());
						if(nodes  <= 0){
							throw new FeaturesException(SimulationFeaturesConstants.NODES + " value must be greater than 0");
						}
						simulationFeatures.setProperty(SimulationFeaturesConstants.NODES, txtEditingNodesNumber.getText());

						if(cmbAugmentedTopology.getSelectedItem().equals(SimulationFeaturesConstants.RANDOM_TOPOLOGY)){
							simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.RANDOM_TOPOLOGY);
							if(Integer.parseInt(txtEditingEdgesNumber.getText())  <= 0){
								throw new FeaturesException(SimulationFeaturesConstants.EDGES + " value must be greater than 0");
							}
							simulationFeatures.setProperty(SimulationFeaturesConstants.EDGES, txtEditingEdgesNumber.getText());							
							if(Integer.parseInt(txtFixedInputs.getText())  < -1 ||
									Integer.parseInt(txtFixedInputs.getText())  == 0 ||
									Integer.parseInt(txtFixedInputs.getText()) > Math.pow(nodes - 1, 2)){
								throw new FeaturesException(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER + " value must be between 1 and (nodes - 1)^2 or -1");
							}
							if(!txtFixedInputs.getText().equals("-1"))
								simulationFeatures.setProperty(SimulationFeaturesConstants.FIXED_INPUTS_NUMBER, txtFixedInputs.getText());
						}else if(cmbAugmentedTopology.getSelectedItem().equals(SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY)){
							simulationFeatures.setProperty(SimulationFeaturesConstants.TOPOLOGY, SimulationFeaturesConstants.SCALE_FREE_TOPOLOGY);
							if(cmbSFAugmentedAlgorithm.getSelectedItem().equals(SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM)){
								
								int avgCon = Integer.valueOf(txtAugmentedAvgConnectivity.getText());
								double inOutProb = Double.valueOf(txtInOutProbAug.getText());
								if(avgCon <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be grater than 0.");
								}
								if(inOutProb < 0 || inOutProb > 1){
									throw new FeaturesException(SimulationFeaturesConstants.INGOING_OUTGOING_PROBABILITY + " value must be betwee 0 and 1");
								}
								simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.BARABASI_ALBERTZ_ALGORITHM);
								simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAugmentedAvgConnectivity.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.INGOING_SCALE_FREE, txtInOutProbAug.getText());
								
							}else if(cmbSFAugmentedAlgorithm.getSelectedItem().equals(SimulationFeaturesConstants.POWER_LAW_ALGORITHM)){
								if(Double.valueOf(txtAugPLGamma.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.GAMMA + " value must be greater than 0");
								}
								if(Double.valueOf(txtAvgConPLAug.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY + " value must be greater than 0");
								}
								simulationFeatures.setProperty(SimulationFeaturesConstants.ALGORITHM, SimulationFeaturesConstants.POWER_LAW_ALGORITHM);
								simulationFeatures.setProperty(SimulationFeaturesConstants.GAMMA, txtAugPLGamma.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.AVERAGE_CONNECTIVITY, txtAvgConPLAug.getText());
								simulationFeatures.setProperty(SimulationFeaturesConstants.INGOING_SCALE_FREE, chkIngoingPowerLaw.isSelected() ? SimulationFeaturesConstants.YES : SimulationFeaturesConstants.NO);
							}
						}
					
						simulationFeatures.setProperty(SimulationFeaturesConstants.FUNCTION_TYPE, cmbEditingFunctionType.getSelectedItem().toString());

						canalizing = Double.parseDouble(txtEditingCanalizingType.getText());
						if(canalizing < 0 || canalizing > 1){
							throw new FeaturesException(SimulationFeaturesConstants.CANALIZED_TYPE + " value must be between 0 and 1.");
						}

						biased = Double.parseDouble(txtEditingBiasType.getText());
						if(biased < 0 || biased > 1){
							throw new FeaturesException(SimulationFeaturesConstants.BIAS_TYPE + " value must be between 0 and 1.");
						}

						bias = Double.parseDouble(txtEditingBiasValue.getText());
						if(bias < 0 || bias > 1){
							throw new FeaturesException(SimulationFeaturesConstants.BIAS_VALUE + " value must be between 0 and 1.");
						}

						and = Double.parseDouble(txtEditingAndType.getText());
						if(and < 0 || and > 1){
							throw new FeaturesException(SimulationFeaturesConstants.AND_FUNCTION_TYPE + " value must be between 0 and 1.");
						}

						or = Double.parseDouble(txtEditingOrType.getText());
						if(or < 0 || or > 1){
							throw new FeaturesException(SimulationFeaturesConstants.OR_FUNCTION_TYPE + " value must be between 0 and 1.");
						}

						if(biased + and + or + canalizing != 1){
							throw new FeaturesException("The sum of " + SimulationFeaturesConstants.BIAS_TYPE + ", " +
									SimulationFeaturesConstants.AND_FUNCTION_TYPE + ", " +
									SimulationFeaturesConstants.OR_FUNCTION_TYPE + ", " +
									SimulationFeaturesConstants.CANALIZED_TYPE + " values must be 1.");
						}

						simulationFeatures.setProperty(SimulationFeaturesConstants.RANDOM_TYPE, "0");
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
						lblTopologyAndFunctionsList.setBackground(Color.WHITE);
						lblSamplingList.setBackground(UIManager.getColor("Button.background"));
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "experimentsPanel");

					}
				}else if(form.equals("attractors-params")){
					if(cmbSamplingType.getSelectedItem().equals(SimulationFeaturesConstants.BRUTE_FORCE) && !txtCutoff.getText().equals("")){
						simulationFeatures.setProperty(SimulationFeaturesConstants.SAMPLING_METHOD, SimulationFeaturesConstants.BRUTE_FORCE);
						if(Integer.parseInt(txtCutoff.getText()) < -1){
							throw new FeaturesException(SimulationFeaturesConstants.MAX_SIMULATION_TIMES + " value must be greater than 0 or -1");
						}
						simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_SIMULATION_TIMES, txtCutoff.getText());
						form = "atm-params";
						lblSamplingList.setBackground(Color.WHITE);
						lblExperimentsList.setBackground(UIManager.getColor("Button.background"));
						((CardLayout)experimentsSubPanel.getLayout()).show(experimentsSubPanel, "atmPanel");

					}else if(!txtInitialConditions.getText().equals("") && !txtCutoff.getText().equals("")){
						//Sets the beta and k parameters
						if(Integer.parseInt(txtInitialConditions.getText()) <= 0){
							throw new FeaturesException(SimulationFeaturesConstants.INITIAL_CONDITIONS + " value must be greater than 0");
						}
						if(Integer.parseInt(txtCutoff.getText()) < -1){
							throw new FeaturesException(SimulationFeaturesConstants.MAX_SIMULATION_TIMES + " value must be greater than 0 or -1");
						}
						//Adds the features about the attractors finding.
						simulationFeatures.setProperty(SimulationFeaturesConstants.SAMPLING_METHOD, SimulationFeaturesConstants.PARTIAL_SAMPLING);
						simulationFeatures.setProperty(SimulationFeaturesConstants.INITIAL_CONDITIONS, txtInitialConditions.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_SIMULATION_TIMES, txtCutoff.getText());
						form = "atm-params";
						lblSamplingList.setBackground(Color.WHITE);
						lblExperimentsList.setBackground(UIManager.getColor("Button.background"));
						((CardLayout)experimentsSubPanel.getLayout()).show(experimentsSubPanel, "atmPanel");
					}
				}else if(form.equals("atm-params")){
					//Adds the features about the atm creation
					if(chkAtmCalculation.isSelected()){
						tasks.setProperty(CABERNETConstants.ATM_COMPUTATION, CABERNETConstants.YES);
						//Sets the avalanches distribution and sensitivity computation.
						simulationFeatures.setProperty(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY,
								SimulationFeaturesConstants.NO);
						if(!txtRatioStatesMutations.getText().equals("") && !txtExperimentsNumber.getText().equals("")){
							//Adds the RATIO_OF_STATES_TO_PERTURB and HOW_MANY_PERTURB_EXP features
							if(chkCompleteFlipExp.isSelected() && mutationsTypeComboBox.getSelectedItem().equals("Node Flip: 1—> 0, 0—> 1")){
								simulationFeatures.setProperty(SimulationFeaturesConstants.COMPLETE_FLIP_EXP, 
										SimulationFeaturesConstants.YES);
							}else{
								if(Double.parseDouble(txtRatioStatesMutations.getText()) < 0 ||
										Double.parseDouble(txtRatioStatesMutations.getText()) > 1){
									throw new FeaturesException(SimulationFeaturesConstants.RATIO_OF_STATES_TO_PERTURB + " value must be between 0 and 1.");
								}
								simulationFeatures.setProperty(SimulationFeaturesConstants.RATIO_OF_STATES_TO_PERTURB, txtRatioStatesMutations.getText());
								if(Integer.parseInt(txtExperimentsNumber.getText()) <= 0){
									throw new FeaturesException(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP + " value must be greater than 0.");
								}
								simulationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP, txtExperimentsNumber.getText());
							}
							if(mutationsTypeComboBox.getSelectedItem().equals("Node Flip: 1—> 0, 0—> 1")){
								if(!txtNumberOfFlips.getText().equals("") && !txtMinFlipTimes.getText().equals("") && !txtMaxFlipTimes.getText().equals("")){
									Integer minT, maxT;
									//Adds the flip mutations features
									simulationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.FLIP_MUTATIONS);
									if(Integer.parseInt(txtNumberOfFlips.getText()) <= 0 ||
											Integer.parseInt(txtNumberOfFlips.getText()) > Integer.parseInt(txtNodesNumber.getText())){
										throw new FeaturesException(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB + " value must be between 0 and the number of nodes.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB, txtNumberOfFlips.getText());
									minT = Integer.parseInt(txtMinFlipTimes.getText());
									if(minT <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB + " value must be greater than 0.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB, txtMinFlipTimes.getText());
									maxT = Integer.parseInt(txtMaxFlipTimes.getText());
									if(maxT <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB + " value must be greater than 0.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB, txtMaxFlipTimes.getText());
									if(minT > maxT)
										throw new FeaturesException(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB + " value must be smaller than " +
												SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB + " value");

									form = "tree-matching";
									lblExperimentsList.setBackground(Color.WHITE);
									lblTreeMatchingList.setBackground(UIManager.getColor("Button.background"));
									((CardLayout)contentPanel.getLayout()).show(contentPanel, "treeMatchingPanel");	

								}
							}else{
								if(!txtKnockInNodes.getText().equals("") && !txtMinKnockInTimes.getText().equals("") && !txtMaxKnockInTimes.getText().equals("") &&
										!txtKnockOutNodes.getText().equals("") && !txtMinKnockOutTimes.getText().equals("") && !txtMaxKnockOutTimes.getText().equals("")){
									Integer minTKI, maxTKI, minTKO, maxTKO, kin, kon;
									//Adds the knock-in/knock-out mutations features
									simulationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.KNOCKIN_KNOCKOUT_MUTATIONS);
									kin = Integer.parseInt(txtKnockInNodes.getText());
									if(kin < 0)
										throw new FeaturesException(SimulationFeaturesConstants.KNOCKIN_NODES + " value must be greater or equal than 0.");
									simulationFeatures.setProperty(SimulationFeaturesConstants.KNOCKIN_NODES, txtKnockInNodes.getText());

									minTKI = Integer.parseInt(txtMinKnockInTimes.getText());
									if(minTKI <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION + " value must be greater than 0.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION, txtMinKnockInTimes.getText());
									maxTKI = Integer.parseInt(txtMaxKnockInTimes.getText());
									if(maxTKI <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION + " value must be greater than 0.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION, txtMaxKnockInTimes.getText());
									if(minTKI > maxTKI)
										throw new FeaturesException(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION + " value must be smaller than " +
												SimulationFeaturesConstants.MAX_KNOCKIN_DURATION + " value");

									kon = Integer.parseInt(txtKnockOutNodes.getText());
									if(kon < 0)
										throw new FeaturesException(SimulationFeaturesConstants.KNOCKOUT_NODES + " value must be greater or equal than 0.");
									simulationFeatures.setProperty(SimulationFeaturesConstants.KNOCKOUT_NODES, txtKnockOutNodes.getText());

									minTKO = Integer.parseInt(txtMinKnockOutTimes.getText());
									if(minTKO <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION + " value must be greater than 0.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION, txtMinKnockOutTimes.getText());
									maxTKO = Integer.parseInt(txtMaxKnockOutTimes.getText());
									if(maxTKO <= 0){
										throw new FeaturesException(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION + " value must be greater than 0.");
									}
									simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION, txtMaxKnockOutTimes.getText());
									if(minTKO > maxTKO)
										throw new FeaturesException(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION + " value must be smaller than " +
												SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION + " value");
									if(kin + kon > Integer.parseInt(txtNodesNumber.getText()))
										throw new FeaturesException("The sum of " + SimulationFeaturesConstants.KNOCKIN_NODES + " value and " +
												SimulationFeaturesConstants.KNOCKOUT_NODES + " value must be smaller or equal than the number of nodes.");
									form = "tree-matching";
									lblExperimentsList.setBackground(Color.WHITE);
									lblTreeMatchingList.setBackground(UIManager.getColor("Button.background"));
									((CardLayout)contentPanel.getLayout()).show(contentPanel, "treeMatchingPanel");	
								}
							}
						}
					}else{
						//No atm computation.
						tasks.setProperty(CABERNETConstants.ATM_COMPUTATION, CABERNETConstants.NO);
						//Next step: Output elements chosen
						form = "output-form";
						lblOutputsList.setBackground(Color.LIGHT_GRAY);
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");
					}
					//Next action: Tree matching
				}else if(form.equals("tree-matching")){

					//Representative tree 
					double depthValue;
					int depth;
					int maxChildren;
					double permProb;
					//Sets the cutoff for the comparison
					if(chckbxComputeRepresentativeTree.isSelected() || chckbxMatchNetworksWith.isSelected()){
						maxChildren = Integer.valueOf(txtMaxChildren.getText());
						if(maxChildren < 0){
							throw new NumberFormatException("The maximum number of children for a complete test must be greater than 0.");
						}

						permProb = Double.valueOf(txtPermProb.getText());
						if(permProb < 0 || permProb > 1){
							throw new NumberFormatException("The permutation probability must be bertwwen 0 and 1.");
						}
						simulationFeatures.setProperty(SimulationFeaturesConstants.MAX_CHILDREN_FOR_COMPLETE_TEST, txtMaxChildren.getText());
						simulationFeatures.setProperty(SimulationFeaturesConstants.PARTIAL_TEST_PROBABILITY, txtPermProb.getText());
					}

					if(chckbxComputeRepresentativeTree.isSelected()){
						chckbxAllTrees.setVisible(true);
						tasks.setProperty(CABERNETConstants.COMPUTE_REPRESENTATIVE_TREE, CABERNETConstants.YES);
						if(rdbtnAbsolute.isSelected()){
							tasks.setProperty(CABERNETConstants.TREE_DEPTH_MODE, CABERNETConstants.ABSOLUTE_DEPTH);
							depth = Integer.valueOf(txtDepthValue.getText());
							if(depth < 0)
								throw new FeaturesException("The depth must be greater than 0");
							tasks.setProperty(CABERNETConstants.TREE_DEPTH_VALUE, txtDepthValue.getText());
						}else if(rdbtnRatioOfAttractors.isSelected()){
							tasks.setProperty(CABERNETConstants.TREE_DEPTH_MODE, CABERNETConstants.RELATIVE_DEPTH);
							depthValue = Double.valueOf(txtDepthValue.getText());
							if(depthValue < 0 || depthValue > 1)
								throw new FeaturesException("The depth must be between 0 and 1.");
							tasks.setProperty(CABERNETConstants.TREE_DEPTH_VALUE, txtDepthValue.getText());
						}else{
							tasks.setProperty(CABERNETConstants.TREE_DEPTH_MODE, CABERNETConstants.LOGN_DEPTH);
						}
						if(Integer.valueOf(txtRepresentativeTreeCutoff.getText()) < -1)
							throw new NumberFormatException("The " + CABERNETConstants.REPRESENTATIVE_TREE_CUTOFF + "value must be greater than -1.");
						simulationFeatures.setProperty(CABERNETConstants.REPRESENTATIVE_TREE_CUTOFF, txtRepresentativeTreeCutoff.getText());

					}else{
						tasks.setProperty(CABERNETConstants.COMPUTE_REPRESENTATIVE_TREE, CABERNETConstants.NO);
						chckbxAllTrees.setVisible(false);
					}

					//Reads the tree from file
					if(chckbxMatchNetworksWith.isSelected()){
						tasks.setProperty(CABERNETConstants.TREE_MATCHING, CABERNETConstants.YES);
						//Gets the matching type
						if(rdbtnPerfectMatch.isSelected()){
							tasks.setProperty(CABERNETConstants.MATCHING_TYPE, CABERNETConstants.PERFECT_MATCH);
						}else if(rdbtnMinDistance.isSelected()){
							tasks.setProperty(CABERNETConstants.MATCHING_TYPE, CABERNETConstants.MIN_DISTANCE);
							if(Integer.parseInt(txtThreshold.getText()) < 0)
								throw new FeaturesException(CABERNETConstants.MATCHING_THRESHOLD + " value must be greater or equal than 0");
							tasks.setProperty(CABERNETConstants.MATCHING_THRESHOLD, txtThreshold.getText());
						}else{
							if(Integer.parseInt(txtThreshold.getText()) < 0)
								throw new FeaturesException(CABERNETConstants.MATCHING_THRESHOLD + " value must be greater or equal than 0");
							tasks.setProperty(CABERNETConstants.MATCHING_TYPE, CABERNETConstants.HISTOGRAM_DISTANCE);
							tasks.setProperty(CABERNETConstants.MATCHING_THRESHOLD, txtThreshold.getText());
						}
						if(radioTreeFromFile.isSelected()){
							if(txtTreeFilePath.getText().equals(""))
								throw new Exception("Tree file path missed.");
							tree = TesManager.createTesTreeFromFile(Input.readTree(txtTreeFilePath.getText()));	
							//New step: outputs page
							form = "output-form";
							lblTreeMatchingList.setBackground(Color.WHITE);
							lblOutputsList.setBackground(UIManager.getColor("Button.background"));
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");
						}else{
							//New step: outputs page
							tree = netManagment.getTreeFromCytoscape();
							if(tree == null)
								throw new FeaturesException("Tree not defined in the Cytoscape view.");
							form = "output-form";
							lblTreeMatchingList.setBackground(Color.WHITE);
							lblOutputsList.setBackground(UIManager.getColor("Button.background"));
							((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");
						}
					}else{
						tasks.setProperty(CABERNETConstants.TREE_MATCHING, CABERNETConstants.NO);
						//New step: outputs page
						form = "output-form";
						lblTreeMatchingList.setBackground(Color.WHITE);
						lblOutputsList.setBackground(UIManager.getColor("Button.background"));
						((CardLayout)contentPanel.getLayout()).show(contentPanel, "outputsPanel");	
					}
				}else if(form.equals("output-form")){

					//Network view on Cytoscape
					if(chkNetworksOutput.isSelected())
						outputs.setProperty(CABERNETConstants.NETWORK_VIEW, CABERNETConstants.YES);
					else
						outputs.setProperty(CABERNETConstants.NETWORK_VIEW, CABERNETConstants.NO);
					//Attractors network view on Cytoscape
					if(chkAttractorsNetworkOutput.isSelected())
						outputs.setProperty(CABERNETConstants.ATTRACTORS_NETWORK_VIEW, CABERNETConstants.YES);
					else
						outputs.setProperty(CABERNETConstants.ATTRACTORS_NETWORK_VIEW, CABERNETConstants.NO);

					if(chckbxAllTrees.isVisible() && chckbxAllTrees.isSelected()){
						outputs.setProperty(CABERNETConstants.SHOW_ALL_TREES, CABERNETConstants.NO);
					}else{
						outputs.setProperty(CABERNETConstants.SHOW_ALL_TREES, CABERNETConstants.YES);
					}

					if(chkExportToFileSystem.isSelected()){
						if(txtOutputPath.getText().equals("")){
							throw new FeaturesException("Invalid input folder");
						}
						File outputFolder = new File(txtOutputPath.getText());
						if(!outputFolder.isDirectory()){
							throw new FeaturesException("Invalid input folder");
						}
						//Sets the output parameters
						outputs.setProperty(OutputConstants.EXPORT_TO_FILE_SYSTEM, OutputConstants.YES);
						outputs.setProperty(OutputConstants.OUTPUT_PATH, txtOutputPath.getText());
						outputs.setProperty(OutputConstants.GRNML_FILE, 
								chkGrnml.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.SIF_FILE, 
								chkSif.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.ATM_FILE, 
								chkAtm.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.STATES_IN_EACH_ATTRACTOR, 
								chkStates.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.ATTRACTORS, 
								chkAttractors.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.SYNTHESIS_FILE, 
								chkSynthesis.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.ATTRACTOR_LENGTHS, 
								chkAttractorLenghts.isSelected() ? OutputConstants.YES : OutputConstants.NO);
						outputs.setProperty(OutputConstants.BASINS_OF_ATTRACTION, 
								chkBasins.isSelected() ? OutputConstants.YES : OutputConstants.NO);


					}else{
						outputs.setProperty(OutputConstants.EXPORT_TO_FILE_SYSTEM, OutputConstants.NO);
					}

					parent.dispose();
				}

			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}
	}
}
