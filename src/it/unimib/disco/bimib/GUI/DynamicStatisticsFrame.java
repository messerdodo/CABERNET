package it.unimib.disco.bimib.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

public class DynamicStatisticsFrame extends JFrame {

	private JPanel contentPane;
	private JPanel pnlPerturbation;
	private JPanel pnlFlips;
	private JCheckBox chckbxUseSpecifiedGenes;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtMinFlipTimes;
	private JTextField txtMaxFlipTimes;
	private JTextField textField_4;
	private JTextArea txtSpecificFlipGenes;
	private JTextArea txtPermanentKnockIn;
	private JTextArea txtPermanentKnockOut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DynamicStatisticsFrame frame = new DynamicStatisticsFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DynamicStatisticsFrame() {
		setTitle("Dynamic Statistics");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 716, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		bottomPanel.add(btnCancel);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bottomPanel.add(btnOk);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnlTemporaryMutations = new JPanel();
		tabbedPane.addTab("Temporary mutations", null, pnlTemporaryMutations, null);
		pnlTemporaryMutations.setLayout(new BorderLayout(0, 0));
		
		JPanel experimentsPanel = new JPanel();
		pnlTemporaryMutations.add(experimentsPanel, BorderLayout.SOUTH);
		experimentsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Ratio of states to perturb for each attractor:");
		experimentsPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("0.5");
		experimentsPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Number of experiments:");
		experimentsPanel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("1");
		experimentsPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel pnlMutationType = new JPanel();
		pnlTemporaryMutations.add(pnlMutationType, BorderLayout.NORTH);
		pnlMutationType.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPerturbationsType = new JLabel("Perturbations type:");
		pnlMutationType.add(lblPerturbationsType);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Flip", "Temporary"}));
		pnlMutationType.add(comboBox);
		
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
		
		textField_4 = new JTextField();
		textField_4.setText("1");
		sl_pnlFlips.putConstraint(SpringLayout.WEST, chckbxUseSpecifiedGenes, 34, SpringLayout.EAST, textField_4);
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, textField_4, 6, SpringLayout.SOUTH, lblPerturbationDuration);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, textField_4, 18, SpringLayout.EAST, lblNumberOfNodes);
		pnlFlips.add(textField_4);
		textField_4.setColumns(10);
		
		txtSpecificFlipGenes = new JTextArea();
		txtSpecificFlipGenes.setEnabled(false);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, txtSpecificFlipGenes, 10, SpringLayout.WEST, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.EAST, txtSpecificFlipGenes, 0, SpringLayout.EAST, txtMaxFlipTimes);
		pnlFlips.add(txtSpecificFlipGenes);
		
		JLabel lblSetTheSpecific = new JLabel("Set the specific genes with their names separated by a comma symbol");
		sl_pnlFlips.putConstraint(SpringLayout.NORTH, txtSpecificFlipGenes, 6, SpringLayout.SOUTH, lblSetTheSpecific);
		sl_pnlFlips.putConstraint(SpringLayout.SOUTH, txtSpecificFlipGenes, 106, SpringLayout.SOUTH, lblSetTheSpecific);
		sl_pnlFlips.putConstraint(SpringLayout.SOUTH, lblSetTheSpecific, -195, SpringLayout.SOUTH, pnlFlips);
		sl_pnlFlips.putConstraint(SpringLayout.WEST, lblSetTheSpecific, 0, SpringLayout.WEST, lblPerturbationDuration);
		pnlFlips.add(lblSetTheSpecific);
		
		JPanel pnlPerpetualMutations = new JPanel();
		tabbedPane.addTab("Permanenet mutations", null, pnlPerpetualMutations, null);
		SpringLayout sl_pnlPerpetualMutations = new SpringLayout();
		pnlPerpetualMutations.setLayout(sl_pnlPerpetualMutations);
		
		JLabel lblSetTheNames = new JLabel("Set the names of the genes to knock-in or knock out in them in a permanent way.");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, lblSetTheNames, 10, SpringLayout.NORTH, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblSetTheNames, 10, SpringLayout.WEST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(lblSetTheNames);
		
		txtPermanentKnockIn = new JTextArea();
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, txtPermanentKnockIn, 109, SpringLayout.NORTH, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, txtPermanentKnockIn, 0, SpringLayout.WEST, lblSetTheNames);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, txtPermanentKnockIn, -120, SpringLayout.SOUTH, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, txtPermanentKnockIn, 260, SpringLayout.WEST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(txtPermanentKnockIn);
		
		txtPermanentKnockOut = new JTextArea();
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.NORTH, txtPermanentKnockOut, 0, SpringLayout.NORTH, txtPermanentKnockIn);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, txtPermanentKnockOut, -260, SpringLayout.EAST, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, txtPermanentKnockOut, -120, SpringLayout.SOUTH, pnlPerpetualMutations);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, txtPermanentKnockOut, -10, SpringLayout.EAST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(txtPermanentKnockOut);
		
		JLabel lblKnockin = new JLabel("Knock-In:");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblKnockin, 0, SpringLayout.WEST, lblSetTheNames);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, lblKnockin, -6, SpringLayout.NORTH, txtPermanentKnockIn);
		pnlPerpetualMutations.add(lblKnockin);
		
		JLabel lblKnockout = new JLabel("Knock-Out:");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, lblKnockout, -6, SpringLayout.NORTH, txtPermanentKnockOut);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.EAST, lblKnockout, -186, SpringLayout.EAST, pnlPerpetualMutations);
		pnlPerpetualMutations.add(lblKnockout);
		
		JLabel lblUseTheComma = new JLabel("Use the comma symbol for separating two genes names in the boxes below.");
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.WEST, lblUseTheComma, 0, SpringLayout.WEST, lblSetTheNames);
		sl_pnlPerpetualMutations.putConstraint(SpringLayout.SOUTH, lblUseTheComma, -42, SpringLayout.NORTH, lblKnockin);
		pnlPerpetualMutations.add(lblUseTheComma);
	}
}
