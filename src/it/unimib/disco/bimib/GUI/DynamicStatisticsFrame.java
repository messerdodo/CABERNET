/**
 * This view is used in order to set the perturbations that have to be applied to the system.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */
package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.CABERNET.Simulation;
import it.unimib.disco.bimib.Exceptions.FeaturesException;
import it.unimib.disco.bimib.Middleware.VizMapperManager;
import it.unimib.disco.bimib.Task.DynamicStatisticsComputationTask;
import it.unimib.disco.bimib.Utility.SimulationFeaturesConstants;




//System imports
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.CardLayout;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Properties;




//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class DynamicStatisticsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JPanel contentPane;
	private JPanel pnlPerturbation;
	private JPanel pnlFlips;
	private JComboBox<String> cmbPerturbationType;
	private JCheckBox chckbxUseSpecifiedGenes;
	private JTextField txtRatioOfStatesToPerturb;
	private JTextField txtNumberOfExperiments;
	private JTextField txtMinFlipTimes;
	private JTextField txtMaxFlipTimes;
	private JTextField txtNumberFlipNodes;
	private JTextArea txtSpecificFlipGenes;
	private JTextArea txtPermanentKnockIn;
	private JTextArea txtPermanentKnockOut;
	private JPanel pnlKnockInKnockOut;
	private JTextField txtMinKnockInTimes;
	private JTextField txtMaxKnockInTimes;
	private JTextField txtMinKnockOutTimes;
	private JTextField txtMaxKnockOutTimes;
	private JTextField txtKnockInNodes;
	private JTextField txtKnockOutNodes;
	private JCheckBox chckbxEnableSpecificGenesKnockInKnockOut;
	private JTextArea txtKnockInSpecifics;
	private JTextArea txtKnockOutSpecifics;
	private JCheckBox chkPermanentSpecific;
	private JTextField txtPermRandomKnockIn;
	private JTextField txtPermRandomKnockOut;

	//Cytoscape app objects
	private CySwingAppAdapter adapter;
	private CyNetwork currentNetwork;

	//CABERNET Objects
	private Simulation simulation;
	private VizMapperManager vizMapperManager;



	/**
	 * Create the frame.
	 */
	public DynamicStatisticsFrame(CySwingAppAdapter adapter, Simulation simulation, CyNetwork currentNetwork, 
			VizMapperManager vizMapperManager) {

		this.adapter = adapter;
		this.simulation = simulation;
		this.currentNetwork = currentNetwork;
		this.vizMapperManager = vizMapperManager;

		setTitle("Dynamic Statistics");
		setResizable(false);
		setBounds(100, 100, 716, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new CancelButtonPressed(this));
		bottomPanel.add(btnCancel);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new OkButtonPressed(this));
		bottomPanel.add(btnOk);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel pnlTemporaryMutations = new JPanel();
		tabbedPane.addTab("Temporary mutations", null, pnlTemporaryMutations, null);
		pnlTemporaryMutations.setLayout(new BorderLayout(0, 0));

		JPanel experimentsPanel = new JPanel();
		pnlTemporaryMutations.add(experimentsPanel, BorderLayout.SOUTH);
		
				JLabel lblNewLabel_1 = new JLabel("Number of randomly selected single/multiple node perturbations for each attractor state");
		
				txtNumberOfExperiments = new JTextField();
				txtNumberOfExperiments.setHorizontalAlignment(SwingConstants.CENTER);
				txtNumberOfExperiments.setText("1");
				txtNumberOfExperiments.setColumns(10);

		JLabel lblNewLabel = new JLabel("Ratio of randomly selected attractor states in which performing the perturbations ");

		txtRatioOfStatesToPerturb = new JTextField();
		txtRatioOfStatesToPerturb.setHorizontalAlignment(SwingConstants.CENTER);
		txtRatioOfStatesToPerturb.setText("0.5");
		txtRatioOfStatesToPerturb.setColumns(10);
		GroupLayout gl_experimentsPanel = new GroupLayout(experimentsPanel);
		gl_experimentsPanel.setHorizontalGroup(
			gl_experimentsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_experimentsPanel.createSequentialGroup()
					.addGroup(gl_experimentsPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_experimentsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNumberOfExperiments, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRatioOfStatesToPerturb, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
					.addGap(36))
		);
		gl_experimentsPanel.setVerticalGroup(
			gl_experimentsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_experimentsPanel.createSequentialGroup()
					.addGroup(gl_experimentsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNumberOfExperiments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_experimentsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtRatioOfStatesToPerturb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		experimentsPanel.setLayout(gl_experimentsPanel);

		JPanel pnlMutationType = new JPanel();
		pnlTemporaryMutations.add(pnlMutationType, BorderLayout.NORTH);
		pnlMutationType.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblPerturbationsType = new JLabel("Perturbations type:");
		pnlMutationType.add(lblPerturbationsType);

		cmbPerturbationType = new JComboBox<String>();
		cmbPerturbationType.addItemListener(new ItemListener() {
			//Changes the input panel
			public void itemStateChanged(ItemEvent e) {
				if(cmbPerturbationType.getSelectedIndex() == 0){
					((CardLayout)pnlPerturbation.getLayout()).show(pnlPerturbation, "Flips");	
				}else{
					((CardLayout)pnlPerturbation.getLayout()).show(pnlPerturbation, "KnockInKnockOut");	
				}
			}
		});
		cmbPerturbationType.setModel(new DefaultComboBoxModel<String>(new String[] {"Node Flip: 1—> 0, 0—> 1", "Node KnockIn-KnockOut"}));
		pnlMutationType.add(cmbPerturbationType);

		pnlPerturbation = new JPanel();
		pnlTemporaryMutations.add(pnlPerturbation, BorderLayout.CENTER);
		pnlPerturbation.setLayout(new CardLayout(0, 0));

		pnlFlips = new JPanel();
		pnlPerturbation.add(pnlFlips, "Flips");
		SpringLayout sl_pnlFlips = new SpringLayout();
		pnlFlips.setLayout(sl_pnlFlips);

		chckbxUseSpecifiedGenes = new JCheckBox("Use specific genes");
		//Enables/Disables the specific flip genes box
		chckbxUseSpecifiedGenes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtSpecificFlipGenes.setEnabled(chckbxUseSpecifiedGenes.isSelected());
			}
		});

		pnlFlips.add(chckbxUseSpecifiedGenes);

		JLabel lblPerturbationDuration = new JLabel("Perturbation duration:");
		sl_pnlFlips.putConstraint(SpringLayout.WEST, lblPerturbationDuration, 10, SpringLayout.WEST, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, lblPerturbationDuration, 16, SpringLayout.NORTH, pnlFlips);
		pnlFlips.add(lblPerturbationDuration);

		JLabel lblMin = new JLabel("Min:");
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, lblMin, 0, SpringLayout.NORTH, lblPerturbationDuration);
		pnlFlips.add(lblMin);

		txtMinFlipTimes = new JTextField();
		txtMinFlipTimes.setText("1");
		sl_pnlFlips.putConstraint(SpringLayout.EAST, lblMin, -6, SpringLayout.WEST, txtMinFlipTimes);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, txtMinFlipTimes, -6, SpringLayout.NORTH, lblPerturbationDuration);
		pnlFlips.add(txtMinFlipTimes);
		txtMinFlipTimes.setColumns(10);

		JLabel lblMax = new JLabel("Max:");
		sl_pnlFlips.putConstraint(SpringLayout.EAST, txtMinFlipTimes, -50, SpringLayout.WEST, lblMax);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, lblMax, 0, SpringLayout.NORTH, lblPerturbationDuration);
		pnlFlips.add(lblMax);

		txtMaxFlipTimes = new JTextField();
		txtMaxFlipTimes.setText("1");
		sl_pnlFlips.putConstraint(SpringLayout.EAST, lblMax, -6, SpringLayout.WEST, txtMaxFlipTimes);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, txtMaxFlipTimes, -6, SpringLayout.NORTH, lblPerturbationDuration);
		sl_pnlFlips.putConstraint(SpringLayout.EAST, txtMaxFlipTimes, -10, SpringLayout.EAST, pnlFlips);
		pnlFlips.add(txtMaxFlipTimes);
		txtMaxFlipTimes.setColumns(10);

		JLabel lblNumberOfNodes = new JLabel("Number of nodes:");
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, chckbxUseSpecifiedGenes, -4, SpringLayout.NORTH, lblNumberOfNodes);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, lblNumberOfNodes, 12, SpringLayout.SOUTH, lblPerturbationDuration);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, lblNumberOfNodes, 10, SpringLayout.WEST, pnlFlips);
		pnlFlips.add(lblNumberOfNodes);

		txtNumberFlipNodes = new JTextField();
		txtNumberFlipNodes.setText("1");
		sl_pnlFlips.putConstraint(SpringLayout.WEST, chckbxUseSpecifiedGenes, 34, SpringLayout.EAST, txtNumberFlipNodes);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, txtNumberFlipNodes, 6, SpringLayout.SOUTH, lblPerturbationDuration);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, txtNumberFlipNodes, 18, SpringLayout.EAST, lblNumberOfNodes);
		pnlFlips.add(txtNumberFlipNodes);
		txtNumberFlipNodes.setColumns(10);

		txtSpecificFlipGenes = new JTextArea();
		txtSpecificFlipGenes.setEnabled(false);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, txtSpecificFlipGenes, 10, SpringLayout.WEST, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.EAST, txtSpecificFlipGenes, 0, SpringLayout.EAST, txtMaxFlipTimes);
		pnlFlips.add(txtSpecificFlipGenes);

		JLabel lblSetTheSpecific = new JLabel("Specify the name of the nodes to perturb, separated by a comma symbol");
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, txtSpecificFlipGenes, 6, SpringLayout.SOUTH, lblSetTheSpecific);
		sl_pnlFlips.putConstraint(SpringLayout.SOUTH, txtSpecificFlipGenes, 106, SpringLayout.SOUTH, lblSetTheSpecific);
		sl_pnlFlips.putConstraint(SpringLayout.SOUTH, lblSetTheSpecific, -195, SpringLayout.SOUTH, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, lblSetTheSpecific, 0, SpringLayout.WEST, lblPerturbationDuration);
		pnlFlips.add(lblSetTheSpecific);

		pnlKnockInKnockOut = new JPanel();
		pnlPerturbation.add(pnlKnockInKnockOut, "KnockInKnockOut");
		SpringLayout sl_pnlKnockInKnockOut = new SpringLayout();
		pnlKnockInKnockOut.setLayout(sl_pnlKnockInKnockOut);

		JLabel lblKnockinDuration = new JLabel("Knock-In:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblKnockinDuration, 10, SpringLayout.NORTH, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, lblKnockinDuration, 10, SpringLayout.WEST, pnlKnockInKnockOut);
		pnlKnockInKnockOut.add(lblKnockinDuration);

		JLabel lblMin_1 = new JLabel("Min:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblMin_1, 32, SpringLayout.NORTH, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblMin_1, -551, SpringLayout.EAST, pnlKnockInKnockOut);
		pnlKnockInKnockOut.add(lblMin_1);

		txtMinKnockInTimes = new JTextField();
		txtMinKnockInTimes.setHorizontalAlignment(SwingConstants.CENTER);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtMinKnockInTimes, 71, SpringLayout.EAST, lblMin_1);
		txtMinKnockInTimes.setText("1");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtMinKnockInTimes, -6, SpringLayout.NORTH, lblMin_1);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtMinKnockInTimes, 8, SpringLayout.EAST, lblMin_1);
		pnlKnockInKnockOut.add(txtMinKnockInTimes);
		txtMinKnockInTimes.setColumns(10);

		JLabel lblMax_1 = new JLabel("Max:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblMax_1, 20, SpringLayout.SOUTH, lblMin_1);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, lblMax_1, 0, SpringLayout.WEST, lblMin_1);
		pnlKnockInKnockOut.add(lblMax_1);

		txtMaxKnockInTimes = new JTextField();
		txtMaxKnockInTimes.setHorizontalAlignment(SwingConstants.CENTER);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtMaxKnockInTimes, 6, SpringLayout.SOUTH, txtMinKnockInTimes);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtMaxKnockInTimes, 5, SpringLayout.EAST, lblMax_1);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtMaxKnockInTimes, 68, SpringLayout.EAST, lblMax_1);
		txtMaxKnockInTimes.setText("1");
		txtMaxKnockInTimes.setColumns(10);
		pnlKnockInKnockOut.add(txtMaxKnockInTimes);

		JLabel lblDuration = new JLabel("Duration:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblDuration, 6, SpringLayout.SOUTH, lblKnockinDuration);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblDuration, -17, SpringLayout.WEST, lblMin_1);
		pnlKnockInKnockOut.add(lblDuration);

		JLabel lblKnockout_1 = new JLabel("Knock-Out:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblKnockout_1, 0, SpringLayout.NORTH, lblKnockinDuration);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblKnockout_1, -245, SpringLayout.EAST, pnlKnockInKnockOut);
		pnlKnockInKnockOut.add(lblKnockout_1);

		JLabel label = new JLabel("Duration:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, lblMin_1);
		pnlKnockInKnockOut.add(label);

		JLabel label_1 = new JLabel("Min:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, lblMin_1);
		pnlKnockInKnockOut.add(label_1);

		txtMinKnockOutTimes = new JTextField();
		txtMinKnockOutTimes.setHorizontalAlignment(SwingConstants.CENTER);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, label_1, -6, SpringLayout.WEST, txtMinKnockOutTimes);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtMinKnockOutTimes, -169, SpringLayout.EAST, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtMinKnockOutTimes, -106, SpringLayout.EAST, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtMinKnockOutTimes, -6, SpringLayout.NORTH, lblMin_1);
		txtMinKnockOutTimes.setText("1");
		txtMinKnockOutTimes.setColumns(10);
		pnlKnockInKnockOut.add(txtMinKnockOutTimes);

		JLabel label_2 = new JLabel("Max:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, label_2, 0, SpringLayout.NORTH, lblMax_1);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, label_2, 0, SpringLayout.EAST, label_1);
		pnlKnockInKnockOut.add(label_2);

		txtMaxKnockOutTimes = new JTextField();
		txtMaxKnockOutTimes.setHorizontalAlignment(SwingConstants.CENTER);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtMaxKnockOutTimes, 6, SpringLayout.SOUTH, txtMinKnockOutTimes);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtMaxKnockOutTimes, 6, SpringLayout.EAST, label_2);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtMaxKnockOutTimes, 69, SpringLayout.EAST, label_2);
		txtMaxKnockOutTimes.setText("1");
		txtMaxKnockOutTimes.setColumns(10);
		pnlKnockInKnockOut.add(txtMaxKnockOutTimes);

		JLabel lblNewLabel_2 = new JLabel("Nodes:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 48, SpringLayout.SOUTH, lblDuration);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, lblNewLabel_2, 0, SpringLayout.WEST, lblDuration);
		pnlKnockInKnockOut.add(lblNewLabel_2);

		JLabel lblNodes = new JLabel("Nodes:");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, lblNodes);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, lblNodes, 0, SpringLayout.NORTH, lblNewLabel_2);
		pnlKnockInKnockOut.add(lblNodes);

		txtKnockInNodes = new JTextField();
		txtKnockInNodes.setHorizontalAlignment(SwingConstants.CENTER);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtKnockInNodes, 81, SpringLayout.EAST, lblNewLabel_2);
		txtKnockInNodes.setText("1");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtKnockInNodes, 6, SpringLayout.SOUTH, lblMax_1);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtKnockInNodes, 18, SpringLayout.EAST, lblNewLabel_2);
		pnlKnockInKnockOut.add(txtKnockInNodes);
		txtKnockInNodes.setColumns(10);

		txtKnockOutNodes = new JTextField();
		txtKnockOutNodes.setHorizontalAlignment(SwingConstants.CENTER);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblNodes, -20, SpringLayout.WEST, txtKnockOutNodes);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtKnockOutNodes, 463, SpringLayout.WEST, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtKnockOutNodes, 526, SpringLayout.WEST, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtKnockOutNodes, 6, SpringLayout.SOUTH, label_2);
		txtKnockOutNodes.setText("1");
		txtKnockOutNodes.setColumns(10);
		pnlKnockInKnockOut.add(txtKnockOutNodes);

		txtKnockInSpecifics = new JTextArea();
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtKnockInSpecifics, 31, SpringLayout.WEST, pnlKnockInKnockOut);
		txtKnockInSpecifics.setEnabled(false);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.SOUTH, txtKnockInSpecifics, -21, SpringLayout.SOUTH, pnlKnockInKnockOut);
		pnlKnockInKnockOut.add(txtKnockInSpecifics);

		txtKnockOutSpecifics = new JTextArea();
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, txtKnockOutSpecifics, 399, SpringLayout.WEST, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtKnockOutSpecifics, -35, SpringLayout.EAST, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, txtKnockInSpecifics, -123, SpringLayout.WEST, txtKnockOutSpecifics);
		txtKnockOutSpecifics.setEnabled(false);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtKnockOutSpecifics, 0, SpringLayout.NORTH, txtKnockInSpecifics);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.SOUTH, txtKnockOutSpecifics, 0, SpringLayout.SOUTH, txtKnockInSpecifics);
		pnlKnockInKnockOut.add(txtKnockOutSpecifics);

		JLabel lblNewLabel_3 = new JLabel("Specify the name of the nodes to perturb, separated by a comma symbol");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblNewLabel_3, -117, SpringLayout.EAST, pnlKnockInKnockOut);
		pnlKnockInKnockOut.add(lblNewLabel_3);

		JLabel lblKnockin_1 = new JLabel("Knock-In");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.SOUTH, lblKnockin_1, -91, SpringLayout.SOUTH, pnlKnockInKnockOut);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.NORTH, txtKnockInSpecifics, 6, SpringLayout.SOUTH, lblKnockin_1);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblKnockin_1, 0, SpringLayout.EAST, lblDuration);
		pnlKnockInKnockOut.add(lblKnockin_1);

		JLabel lblKnockout_2 = new JLabel("Knock-Out");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.SOUTH, lblNewLabel_3, -6, SpringLayout.NORTH, lblKnockout_2);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.SOUTH, lblKnockout_2, -6, SpringLayout.NORTH, txtKnockOutSpecifics);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, lblKnockout_2, -216, SpringLayout.EAST, pnlKnockInKnockOut);
		pnlKnockInKnockOut.add(lblKnockout_2);

		chckbxEnableSpecificGenesKnockInKnockOut = new JCheckBox("Enable specific genes definition");
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.WEST, chckbxEnableSpecificGenesKnockInKnockOut, 0, SpringLayout.WEST, lblDuration);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.SOUTH, chckbxEnableSpecificGenesKnockInKnockOut, -6, SpringLayout.NORTH, lblNewLabel_3);
		sl_pnlKnockInKnockOut.putConstraint(SpringLayout.EAST, chckbxEnableSpecificGenesKnockInKnockOut, -417, SpringLayout.EAST, pnlKnockInKnockOut);
		chckbxEnableSpecificGenesKnockInKnockOut.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtKnockInSpecifics.setEnabled(chckbxEnableSpecificGenesKnockInKnockOut.isSelected());
				txtKnockOutSpecifics.setEnabled(chckbxEnableSpecificGenesKnockInKnockOut.isSelected());

			}
		});
		pnlKnockInKnockOut.add(chckbxEnableSpecificGenesKnockInKnockOut);


		JPanel pnlPerpetualMutations = new JPanel();
		tabbedPane.addTab("Permanent mutations", null, pnlPerpetualMutations, null);
		SpringLayout sl_pnlPerpetualMutations = new SpringLayout();
		pnlPerpetualMutations.setLayout(sl_pnlPerpetualMutations);

		JLabel lblSetTheNames = new JLabel("<html>Specify the name of the nodes to either permanently knockin or knockout\nUse the comma as delimiter </html>");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblSetTheNames, 10, SpringLayout.WEST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(lblSetTheNames);

		txtPermanentKnockIn = new JTextArea();
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, txtPermanentKnockIn, 10, SpringLayout.WEST, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, txtPermanentKnockIn, -72, SpringLayout.SOUTH, pnlPerpetualMutations);
		pnlPerpetualMutations.add(txtPermanentKnockIn);

		txtPermanentKnockOut = new JTextArea();
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, txtPermanentKnockIn, -165, SpringLayout.WEST, txtPermanentKnockOut);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, txtPermanentKnockOut, 0, SpringLayout.SOUTH, txtPermanentKnockIn);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, txtPermanentKnockOut, 0, SpringLayout.NORTH, txtPermanentKnockIn);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, txtPermanentKnockOut, 21, SpringLayout.EAST, lblSetTheNames);
		pnlPerpetualMutations.add(txtPermanentKnockOut);

		JLabel lblKnockin = new JLabel("Knock-In:");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, txtPermanentKnockIn, 6, SpringLayout.SOUTH, lblKnockin);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, lblKnockin, 18, SpringLayout.SOUTH, lblSetTheNames);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblKnockin, 0, SpringLayout.WEST, lblSetTheNames);
		pnlPerpetualMutations.add(lblKnockin);

		JLabel lblKnockout = new JLabel("Knock-Out:");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, txtPermanentKnockOut, 0, SpringLayout.WEST, lblKnockout);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblKnockout, 352, SpringLayout.EAST, lblKnockin);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, lblKnockout, 0, SpringLayout.NORTH, lblKnockin);
		pnlPerpetualMutations.add(lblKnockout);

		JLabel lblNumberOfNodes_1 = new JLabel("Number of nodes to knock-in:");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, lblNumberOfNodes_1, 10, SpringLayout.NORTH, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblNumberOfNodes_1, 10, SpringLayout.WEST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(lblNumberOfNodes_1);

		JLabel lblNumberOfNodes_2 = new JLabel("Number of nodes to knock-out:");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblNumberOfNodes_2, 10, SpringLayout.WEST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(lblNumberOfNodes_2);

		txtPermRandomKnockIn = new JTextField();
		txtPermRandomKnockIn.setHorizontalAlignment(SwingConstants.CENTER);
		txtPermRandomKnockIn.setText("0");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, lblNumberOfNodes_2, 17, SpringLayout.SOUTH, txtPermRandomKnockIn);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, txtPermRandomKnockIn, -6, SpringLayout.NORTH, lblNumberOfNodes_1);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, txtPermRandomKnockIn, 6, SpringLayout.EAST, lblNumberOfNodes_1);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, txtPermRandomKnockIn, 272, SpringLayout.WEST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(txtPermRandomKnockIn);
		txtPermRandomKnockIn.setColumns(10);

		txtPermRandomKnockOut = new JTextField();
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, lblSetTheNames, 30, SpringLayout.SOUTH, txtPermRandomKnockOut);
		txtPermRandomKnockOut.setHorizontalAlignment(SwingConstants.CENTER);
		txtPermRandomKnockOut.setText("0");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, txtPermRandomKnockOut, -6, SpringLayout.NORTH, lblNumberOfNodes_2);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, txtPermRandomKnockOut, 6, SpringLayout.EAST, lblNumberOfNodes_2);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, txtPermRandomKnockOut, -399, SpringLayout.EAST, pnlPerpetualMutations);
		txtPermRandomKnockOut.setColumns(10);
		pnlPerpetualMutations.add(txtPermRandomKnockOut);

		chkPermanentSpecific = new JCheckBox("Use specific genes");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, chkPermanentSpecific, 26, SpringLayout.NORTH, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, chkPermanentSpecific, 72, SpringLayout.EAST, txtPermRandomKnockOut);
		pnlPerpetualMutations.add(chkPermanentSpecific);
	}

	public class OkButtonPressed implements ActionListener {

		private JFrame parent;

		public OkButtonPressed(JFrame parent){
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			ArrayList<String> permanentKnockIn = null, permanentKnockOut = null, specificFlipGenes;
			Properties perturbationFeatures;
			int randomKI = 0, randomKO = 0;
			boolean closeView = false;
			try{
				if(chkPermanentSpecific.isSelected()){


					//Sets the permanent knock-in genes
					if(!txtPermanentKnockIn.getText().equals("")){
						permanentKnockIn = new ArrayList<String>();
						for(String gene : txtPermanentKnockIn.getText().split(",")){
							permanentKnockIn.add(gene);
						}
					}

					//Sets the permanent knock-out genes
					if(!txtPermanentKnockOut.getText().equals("")){
						permanentKnockOut = new ArrayList<String>();
						for(String gene : txtPermanentKnockOut.getText().split(",")){
							permanentKnockOut.add(gene);
						}
					}
				}else{
					randomKI = Integer.valueOf(txtPermRandomKnockIn.getText());
					if(randomKI < 0 || randomKI > currentNetwork.getNodeCount())
						throw new FeaturesException("The number of the nodes to knock-in permanently must be between 0 and " + currentNetwork.getNodeCount());
					randomKO = Integer.valueOf(txtPermRandomKnockOut.getText());
					if(randomKO < 0 || randomKO > currentNetwork.getNodeCount())
						throw new FeaturesException("The number of the nodes to knock-out permanently must be between 0 and " + currentNetwork.getNodeCount());
					if(randomKI + randomKO > currentNetwork.getNodeCount())
						throw new FeaturesException("The sum of the nodes to knock-in and knock-out must be between 0 and " + currentNetwork.getNodeCount());
				}

				//Set the perturbation features
				perturbationFeatures = new Properties();
				perturbationFeatures.setProperty(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY, SimulationFeaturesConstants.YES);
				//Flip mutations
				if(cmbPerturbationType.getSelectedIndex() == 0){
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.FLIP_MUTATIONS);
					perturbationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB, txtNumberFlipNodes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB, txtMinFlipTimes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB, txtMaxFlipTimes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.RATIO_OF_STATES_TO_PERTURB, txtRatioOfStatesToPerturb.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP, txtNumberOfExperiments.getText());

					if(chckbxUseSpecifiedGenes.isSelected()){
						specificFlipGenes = new ArrayList<String>();
						for(String gene : txtSpecificFlipGenes.getText().split(","))
							specificFlipGenes.add(gene);
						perturbationFeatures.put(SimulationFeaturesConstants.SPECIFIC_PERTURB_NODES, specificFlipGenes);
					}

				}else{
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.KNOCKIN_KNOCKOUT_MUTATIONS);
					perturbationFeatures.setProperty(SimulationFeaturesConstants.KNOCKIN_NODES, txtKnockInNodes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION, txtMinKnockInTimes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION, txtMaxKnockInTimes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.KNOCKOUT_NODES, txtKnockOutNodes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION, txtMinKnockOutTimes.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION, txtMaxKnockOutTimes.getText());
					//Set, if required, the specific nodes names for the knock-in and the knock-out
					if(chckbxEnableSpecificGenesKnockInKnockOut.isSelected()){
						//Knock-in
						ArrayList<String> specificKnockInNodes = new ArrayList<String>();
						for(String gene : txtKnockInSpecifics.getText().split(",")){
							specificKnockInNodes.add(gene);
						}
						perturbationFeatures.put(SimulationFeaturesConstants.SPECIFIC_KNOCK_IN_NODES,specificKnockInNodes);
						//Knock-Out
						ArrayList<String> specificKnockOutNodes = new ArrayList<String>();
						for(String gene : txtKnockOutSpecifics.getText().split(",")){
							specificKnockOutNodes.add(gene);
						}
						perturbationFeatures.put(SimulationFeaturesConstants.SPECIFIC_KNOCK_OUT_NODES,specificKnockOutNodes);
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.RATIO_OF_STATES_TO_PERTURB, txtRatioOfStatesToPerturb.getText());
					perturbationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP, txtNumberOfExperiments.getText());

				}

				closeView = true;
				DialogTaskManager dialogTaskManager = adapter.getCyServiceRegistrar().getService(DialogTaskManager.class);
				if(chkPermanentSpecific.isSelected()){
				dialogTaskManager.execute(new TaskIterator(new DynamicStatisticsComputationTask(simulation.getGraphManager(),
						simulation.getSamplingManager(), permanentKnockIn, permanentKnockOut, perturbationFeatures, 
						simulation.getNetworkId(), adapter, vizMapperManager, currentNetwork)));
				}else{
					dialogTaskManager.execute(new TaskIterator(new DynamicStatisticsComputationTask(simulation.getGraphManager(),
							simulation.getSamplingManager(), randomKI, randomKO, perturbationFeatures, 
							simulation.getNetworkId(), adapter, vizMapperManager, currentNetwork)));
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
				
			}finally{
				//Closes the view only if the exception is related to the computation
				if(closeView){
					parent.setVisible(false);
				}
			}	

		}

	}

	/**
	 * Cancel process and close form event
	 */
	public class CancelButtonPressed implements ActionListener {

		private JFrame parent;

		public CancelButtonPressed(JFrame parent){
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			this.parent.setVisible(false);
		}
	}
}
