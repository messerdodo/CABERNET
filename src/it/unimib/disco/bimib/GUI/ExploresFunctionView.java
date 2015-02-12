/**
 * This view is used in order to show the details of the selected function.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */

package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.Functions.CanalizedFunction;
import it.unimib.disco.bimib.Functions.Function;

//System imports
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;

public class ExploresFunctionView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExploresFunctionView frame = new ExploresFunctionView(1, null, null);
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
	public ExploresFunctionView(int currentNode, ArrayList<String> nodesNames, Function function) {
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnlFunctionInfo = new JPanel();
		tabbedPane.addTab("Function summary", null, pnlFunctionInfo, null);
		tabbedPane.setEnabledAt(0, true);
		SpringLayout sl_pnlFunctionInfo = new SpringLayout();
		pnlFunctionInfo.setLayout(sl_pnlFunctionInfo);
		
		JLabel lblFunctionType = new JLabel("Function type: " + function.getType());
		pnlFunctionInfo.add(lblFunctionType);
		
		JLabel lblGeneName = new JLabel("Gene name: " + nodesNames.get(currentNode));
		sl_pnlFunctionInfo.putConstraint(SpringLayout.WEST, lblGeneName, 10, SpringLayout.WEST, pnlFunctionInfo);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.SOUTH, lblGeneName, -196, SpringLayout.SOUTH, pnlFunctionInfo);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.NORTH, lblFunctionType, 6, SpringLayout.SOUTH, lblGeneName);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.WEST, lblFunctionType, 0, SpringLayout.WEST, lblGeneName);
		pnlFunctionInfo.add(lblGeneName);
		
		JList<String> inputsGenesList = new JList<String>();
		sl_pnlFunctionInfo.putConstraint(SpringLayout.NORTH, inputsGenesList, 97, SpringLayout.NORTH, pnlFunctionInfo);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.WEST, inputsGenesList, 0, SpringLayout.WEST, lblFunctionType);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.SOUTH, inputsGenesList, -10, SpringLayout.SOUTH, pnlFunctionInfo);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.EAST, inputsGenesList, 249, SpringLayout.EAST, lblGeneName);
		
		DefaultListModel<String> inputsList = new DefaultListModel<String>();
		ArrayList<Integer> usedInputs;
		//For canalizing function shows only the useful inputs
		if(function instanceof CanalizedFunction){
			usedInputs = new ArrayList<Integer>();
			for(int i : ((CanalizedFunction) function).getUsefullInputs())
				usedInputs.add(i);
		}else{
			//For not canalizing function all the inputs are useful
			usedInputs = function.getInputs();
		}
		
		//Shows the input genes names
		for(Integer node : function.getInputs()){
			inputsList.addElement(nodesNames.get(node));
		}
		
		inputsGenesList.setModel(inputsList);
		pnlFunctionInfo.add(inputsGenesList);
		
		JLabel lblNewLabel = new JLabel("Used inputs:");
		sl_pnlFunctionInfo.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblFunctionType);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.SOUTH, lblNewLabel, -6, SpringLayout.NORTH, inputsGenesList);
		pnlFunctionInfo.add(lblNewLabel);

		JLabel lblBias = new JLabel("Bias value: " + function.getBias());
		sl_pnlFunctionInfo.putConstraint(SpringLayout.NORTH, lblBias, 6, SpringLayout.SOUTH, lblFunctionType);
		sl_pnlFunctionInfo.putConstraint(SpringLayout.WEST, lblBias, 0, SpringLayout.WEST, lblFunctionType);
		pnlFunctionInfo.add(lblBias);
		
		JPanel pnlFunctionTable = new JPanel();
		tabbedPane.addTab("Function table", null, pnlFunctionTable, null);
		pnlFunctionTable.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlFunctionTable.add(scrollPane);
		//Shows the function table adding each inputs-output couple
		table = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel();
		
		tableModel.setColumnIdentifiers(new String[]{"Inputs", "Output"});
		//Sorts the input keys
		HashMap<String, String> functionTable = function.getTable();
		SortedSet<String> sortedKeys = new TreeSet<String>();
		sortedKeys.addAll(functionTable.keySet());
		
		for(String inputs : sortedKeys){
			tableModel.addRow(new String[]{inputs, functionTable.get(inputs)});
		}
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		tabbedPane.setEnabledAt(1, true);
	}
}
