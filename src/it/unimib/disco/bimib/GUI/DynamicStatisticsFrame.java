/**
 * This view is used in order to set the perturbations that have to be applied to the system.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */
package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
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

import java.awt.FlowLayout;

import javax.swing.JOptionPane;
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
	private JTextField txtMinFlipTimes;
	private JTextField txtMaxFlipTimes;
	private JTextField txtNumberFlipNodes;
	private JTextArea txtSpecificFlipGenes;
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
	private JCheckBox chkAllNetworks;
	private JTextField txtPerturbExp;

	//Cytoscape app objects
	private CySwingAppAdapter adapter;
	private CyNetwork currentNetwork;

	//CABERNET Objects
	private SimulationsContainer simulations;
	private VizMapperManager vizMapperManager;



	public DynamicStatisticsFrame(CySwingAppAdapter adapter, SimulationsContainer simulations, CyNetwork currentNetwork, 
			VizMapperManager vizMapperManager){

		//Creates the view
		this();

		this.adapter = adapter;
		this.simulations = simulations;
		this.currentNetwork = currentNetwork;
		this.vizMapperManager = vizMapperManager;

	}

	/**
	 * Create the frame.
	 */
	public DynamicStatisticsFrame() {

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

		JPanel pnlTemporaryMutations = new JPanel();
		contentPane.add(pnlTemporaryMutations, BorderLayout.CENTER);
		pnlTemporaryMutations.setLayout(new BorderLayout(0, 0));

		JPanel pnlCommonInfo = new JPanel();
		pnlTemporaryMutations.add(pnlCommonInfo, BorderLayout.NORTH);

		JLabel lblPerturbationsType = new JLabel("Perturbations type:");

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

		chkAllNetworks = new JCheckBox("Repeat for all the networks");
		cmbPerturbationType.setModel(new DefaultComboBoxModel<String>(new String[] {"Node Flip: 1—> 0, 0—> 1", "Node KnockIn-KnockOut"}));

		JLabel lblNumberOfPerturb = new JLabel("Number of perturb experiments:");

		txtPerturbExp = new JTextField();
		txtPerturbExp.setHorizontalAlignment(SwingConstants.CENTER);
		txtPerturbExp.setText("1");
		txtPerturbExp.setColumns(10);
		GroupLayout gl_pnlCommonInfo = new GroupLayout(pnlCommonInfo);
		gl_pnlCommonInfo.setHorizontalGroup(
				gl_pnlCommonInfo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCommonInfo.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlCommonInfo.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_pnlCommonInfo.createSequentialGroup()
										.addComponent(lblPerturbationsType, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
										.addComponent(cmbPerturbationType, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_pnlCommonInfo.createSequentialGroup()
												.addComponent(lblNumberOfPerturb)
												.addGap(18)
												.addComponent(txtPerturbExp, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
												.addComponent(chkAllNetworks, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE))
												.addContainerGap())
				);
		gl_pnlCommonInfo.setVerticalGroup(
				gl_pnlCommonInfo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCommonInfo.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_pnlCommonInfo.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNumberOfPerturb)
								.addComponent(txtPerturbExp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(chkAllNetworks, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_pnlCommonInfo.createParallelGroup(Alignment.BASELINE)
										.addComponent(cmbPerturbationType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPerturbationsType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
				);
		pnlCommonInfo.setLayout(gl_pnlCommonInfo);

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
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, txtSpecificFlipGenes, 100, SpringLayout.NORTH, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, txtSpecificFlipGenes, 0, SpringLayout.WEST, lblPerturbationDuration);
		sl_pnlFlips.putConstraint(SpringLayout.SOUTH, txtSpecificFlipGenes, -64, SpringLayout.SOUTH, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.EAST, txtSpecificFlipGenes, 0, SpringLayout.EAST, txtMaxFlipTimes);
		txtSpecificFlipGenes.setEnabled(false);
		pnlFlips.add(txtSpecificFlipGenes);

		JLabel lblSetTheSpecific = new JLabel("Specify the name of the nodes to perturb, separated by a comma symbol");
		sl_pnlFlips.putConstraint(SpringLayout.WEST, lblSetTheSpecific, 0, SpringLayout.WEST, lblPerturbationDuration);
		sl_pnlFlips.putConstraint(SpringLayout.SOUTH, lblSetTheSpecific, -6, SpringLayout.NORTH, txtSpecificFlipGenes);
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
	}


	public class OkButtonPressed implements ActionListener {

		private JFrame parent;

		public OkButtonPressed(JFrame parent){
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			Properties perturbationFeatures = new Properties();
			ArrayList<String> specificFlipGenes;
			int randomKI, randomKO, randomFlip;
			perturbationFeatures.setProperty(SimulationFeaturesConstants.COMPUTE_AVALANCHES_AND_SENSITIVITY, 
					SimulationFeaturesConstants.YES);

			try{
				if(Integer.valueOf(txtPerturbExp.getText()) <= 0)
					throw new NumberFormatException("The number of perturbation experiments must be greater than 0.");
				perturbationFeatures.put(SimulationFeaturesConstants.HOW_MANY_PERTURB_EXP, txtPerturbExp.getText());

				if(cmbPerturbationType.getSelectedIndex() == 0){
					//Flip mutations
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, SimulationFeaturesConstants.FLIP_MUTATIONS);

					if(Integer.valueOf(txtMinFlipTimes.getText()) <= 0){
						throw new NumberFormatException("The minimim flip times must be greater than 0.");
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MIN_DURATION_OF_PERTURB, txtMinFlipTimes.getText());
					if(Integer.valueOf(txtMaxFlipTimes.getText()) <= 0){
						throw new NumberFormatException("The maximum flip times must be greater than 0.");
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MAX_DURATION_OF_PERTURB, txtMaxFlipTimes.getText());

					if(chckbxUseSpecifiedGenes.isSelected()){
						if(txtSpecificFlipGenes.getText().length() == 0){
							throw new FeaturesException("At least one gene name must be specified.");
						}
						specificFlipGenes = new ArrayList<String>();
						for(String gene : txtSpecificFlipGenes.getText().split(","))
							specificFlipGenes.add(gene);
						perturbationFeatures.put(SimulationFeaturesConstants.SPECIFIC_PERTURB_NODES, specificFlipGenes);
					}else{
						randomFlip = Integer.valueOf(txtNumberFlipNodes.getText());
						if(randomFlip <= 0 && randomFlip > currentNetwork.getNodeCount()){
							throw new NumberFormatException("The number of nodes to flip must be greater than 0 and less or equal then the nodes in the network.");
						}
						perturbationFeatures.setProperty(SimulationFeaturesConstants.HOW_MANY_NODES_TO_PERTURB, txtNumberFlipNodes.getText());


					}
				}else{
					//Knock-in/Knock-out mutations
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MUTATION_TYPE, 
							SimulationFeaturesConstants.KNOCKIN_KNOCKOUT_MUTATIONS);

					//Sets, if required, the specific nodes names for the knock-in and the knock-out

					if(Integer.valueOf(txtMinKnockInTimes.getText()) <= 0){
						throw new FeaturesException("The minimum knock-in times must be greater than 0");
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKIN_DURATION, txtMinKnockInTimes.getText());

					if(Integer.valueOf(txtMaxKnockInTimes.getText()) <= 0 && Integer.valueOf(txtMaxKnockInTimes.getText()) != -1){
						throw new FeaturesException("The maximum knock-in times must be greater than 0 or -1 for no limits.");
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKIN_DURATION, txtMaxKnockInTimes.getText());

					if(Integer.valueOf(txtMinKnockOutTimes.getText()) <= 0){
						throw new FeaturesException("The minimum knock-out times must be greater than 0");
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MIN_KNOCKOUT_DURATION, txtMinKnockOutTimes.getText());
					if(Integer.valueOf(txtMaxKnockOutTimes.getText()) <= 0 && Integer.valueOf(txtMaxKnockOutTimes.getText()) != -1){
						throw new FeaturesException("The maximum knock-out times must be greater than 0 or -1 for no limits.");
					}
					perturbationFeatures.setProperty(SimulationFeaturesConstants.MAX_KNOCKOUT_DURATION, txtMaxKnockOutTimes.getText());


					if(chckbxEnableSpecificGenesKnockInKnockOut.isSelected()){
						if(txtKnockInSpecifics.getText().length() == 0 &&
								txtKnockOutSpecifics.getText().length() == 0){
							throw new FeaturesException("At least one gene name must be specified.");
						}

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

					}else{

						randomKI = Integer.valueOf(txtKnockInNodes.getText());
						randomKO = Integer.valueOf(txtKnockOutNodes.getText());
						if(randomKI < 0)
							throw new FeaturesException("The number of nodes to knock-in must be greater than 0");
						if(randomKO <= 0)
							throw new FeaturesException("The number of nodes to knock-out must be greater than 0");
						if(randomKI + randomKO > currentNetwork.getNodeCount())
							throw new FeaturesException("The sum of knock-in and knock-out nodes must be less or equal than the nodes in the network.");
						perturbationFeatures.setProperty(SimulationFeaturesConstants.KNOCKIN_NODES, txtKnockInNodes.getText());
						perturbationFeatures.setProperty(SimulationFeaturesConstants.KNOCKOUT_NODES, txtKnockOutNodes.getText());
					}
				}

				DialogTaskManager dialogTaskManager = adapter.getCyServiceRegistrar().getService(DialogTaskManager.class);
				SimulationsContainer sims;
				String selectedNetwork = currentNetwork.getRow(currentNetwork).get(CyNetwork.NAME, String.class);
				if(!chkAllNetworks.isSelected()){
					sims = new SimulationsContainer();
					sims.addSimulation(selectedNetwork, simulations.getSimulation(selectedNetwork));
				}else{
					sims = simulations;
				}

				dialogTaskManager.execute(new TaskIterator(new DynamicStatisticsComputationTask(sims, perturbationFeatures, 
						adapter, vizMapperManager, currentNetwork)));
				this.parent.setVisible(false);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? 
						ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
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