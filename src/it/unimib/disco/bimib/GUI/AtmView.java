/**
 * This view is used in order to show the ATM matrix.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2013
 */

package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Middleware.NetworkManagment;
import it.unimib.disco.bimib.Middleware.ViewManager;
import it.unimib.disco.bimib.Middleware.VizMapperManager;
import it.unimib.disco.bimib.Sampling.SamplingManager;


import it.unimib.disco.bimib.Utility.NumberFormat;

//Java Swing and AWT imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;



//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;



public class AtmView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int PRECISION = 2;
	
	private JPanel contentPane;
	private JTextField txtThreshold;
	private JScrollPane pnlATMTable;
	private JButton btnShowAtmGraph;
	private JTable tblATM;
	private DefaultTableModel atmModel;
	private JCheckBox chkCollapsed;
	
	private SamplingManager samplingManager;
	private AtmManager atmManager;
	private final CySwingAppAdapter adapter;
	private final CyApplicationManager appManager;
	private VizMapperManager vizMapperManager;
	private CyNetwork currentNetwork;
	
	/**
	 * Create the frame.
	 */
	public AtmView(AtmManager atmManager, SamplingManager samplingManager, CySwingAppAdapter adapter, 
			CyApplicationManager appManager, String networkId, VizMapperManager vizMapperManager, 
			CyNetwork currentNetwork) {
		setTitle("ATM (network_" + networkId + ")");
		setResizable(false);
		this.atmManager = atmManager;
		this.samplingManager = samplingManager;
		this.adapter = adapter;
		this.appManager = appManager;
		this.vizMapperManager = vizMapperManager;
		this.currentNetwork = currentNetwork;
		
		setBounds(100, 100, 459, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		SpringLayout springLayout = new SpringLayout();
		contentPane.setLayout(springLayout);
		setContentPane(contentPane);
		
		pnlATMTable = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, pnlATMTable, 10, SpringLayout.NORTH, this.contentPane);
		springLayout.putConstraint(SpringLayout.WEST, pnlATMTable, 10, SpringLayout.WEST, this.contentPane);
		springLayout.putConstraint(SpringLayout.SOUTH, pnlATMTable, 235, SpringLayout.NORTH, this.contentPane);
		springLayout.putConstraint(SpringLayout.EAST, pnlATMTable, 440, SpringLayout.WEST, this.contentPane);
		contentPane.add(pnlATMTable);
		
		this.atmModel = new DefaultTableModel();
		double[][] atm = this.atmManager.getAtm().copyAtm();
		String[] atmHeaders = new String[atm.length];
		for(int i = 0; i < atm.length; i++)
			atmHeaders[i] = "A" + (i + 1);
		this.atmModel.setColumnIdentifiers(atmHeaders);
		Double[] row;
		//Populates the atm table
		for(int i = 0; i < atm.length; i++){
			row = new Double[atm.length];
			for(int j = 0; j < atm.length; j++)
				row[j] = NumberFormat.toPrecision(atm[i][j], PRECISION);
			this.atmModel.addRow(row);
		}
		this.tblATM = new JTable();
		tblATM.setRowSelectionAllowed(false);
		this.tblATM.setModel(this.atmModel);
		this.pnlATMTable.setViewportView(tblATM);
		
		JLabel lblNewLabel = new JLabel("Threshold:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 13, SpringLayout.SOUTH, pnlATMTable);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, this.contentPane);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 84, SpringLayout.WEST, this.contentPane);
		contentPane.add(lblNewLabel);
		
		txtThreshold = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, txtThreshold, 80, SpringLayout.EAST, lblNewLabel);
		txtThreshold.setHorizontalAlignment(SwingConstants.CENTER);
		txtThreshold.setText("0.0");
		springLayout.putConstraint(SpringLayout.NORTH, txtThreshold, 6, SpringLayout.SOUTH, pnlATMTable);
		springLayout.putConstraint(SpringLayout.WEST, txtThreshold, 6, SpringLayout.EAST, lblNewLabel);
		contentPane.add(txtThreshold);
		txtThreshold.setColumns(10);
		
		btnShowAtmGraph = new JButton("TES network");
		springLayout.putConstraint(SpringLayout.NORTH, btnShowAtmGraph, 6, SpringLayout.SOUTH, pnlATMTable);
		springLayout.putConstraint(SpringLayout.WEST, btnShowAtmGraph, 154, SpringLayout.EAST, txtThreshold);
		springLayout.putConstraint(SpringLayout.EAST, btnShowAtmGraph, -10, SpringLayout.EAST, contentPane);
		btnShowAtmGraph.addActionListener(new ShowTESGraph(networkId));
		contentPane.add(btnShowAtmGraph);
		
		chkCollapsed = new JCheckBox("Collapsed view");
		springLayout.putConstraint(SpringLayout.NORTH, chkCollapsed, -4, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, chkCollapsed, 6, SpringLayout.EAST, txtThreshold);
		contentPane.add(chkCollapsed);
		
		
	}
	
	public class ShowTESGraph implements ActionListener{

		private String networkId;
		
		public ShowTESGraph(String networkId){
			this.networkId = networkId;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			NetworkManagment middleware = new NetworkManagment(adapter, appManager);
			ViewManager viewManager = new ViewManager(adapter);
			Double threshold = Double.valueOf(txtThreshold.getText());
			CyNetwork network;
			threshold = (threshold < 0) ? 0 : (threshold > 1) ? 1 : threshold;
			try {
				if(chkCollapsed.isSelected()){
					network = middleware.createCollapsedTesGraph(atmManager, networkId, threshold, currentNetwork);
					//Show the network in a new view.
					viewManager.createView(network, vizMapperManager.getStyle("CABERNET Collapsed TES"));
				}else{
					network = middleware.createTesGraph(samplingManager.getAttractorFinder(), atmManager, networkId, 
							threshold, currentNetwork);
					//Show the network in a new view.
					viewManager.createView(network, vizMapperManager.getStyle("CABERNET TES"));
				}
				
			} catch (Exception exception){
				String message = (String) (exception.getMessage().equals("") ? exception : exception.getMessage());
				JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			
		}
	}
}
