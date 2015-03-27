/**
 * Most frequently found tree dialog.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 */

package it.unimib.disco.bimib.GUI;

//CABERNET imports
import it.unimib.disco.bimib.Atms.AtmManager;
import it.unimib.disco.bimib.Middleware.NetworkManagment;
import it.unimib.disco.bimib.Sampling.SamplingManager;
import it.unimib.disco.bimib.Task.FindTreeTask;



//System imports
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Cytoscape imports
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

public class TreeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ButtonGroup groupDepth = new ButtonGroup();
	private JTextField txtDepthValue;
	private JRadioButton rdbtnAbsolute;
	private JRadioButton rdbtnRelative;
	private JRadioButton rdbtnLogn;
	private JButton btnCompute;

	private NetworkManagment cytoscapeBridge;
	private JTextField txtCutoff;
	private JTextField txtMaxChildren;
	private JTextField txtPermutationProbability;

	public TreeFrame(final AtmManager atmManager, final SamplingManager samplingManager, 
			String networkId, final CyNetwork currentNetwork, final CySwingAppAdapter adapter,
			CyApplicationManager appManager) {
		setAlwaysOnTop(true);
		super.setSize(350, 160);
		setType(Type.UTILITY);
		setResizable(false);
		
		
		this.cytoscapeBridge = new NetworkManagment(adapter, appManager);
		
		this.setTitle("Network: " + networkId);

		JLabel lblTreeDepth = new JLabel("Tree depth:");
		this.txtDepthValue = new JTextField();
		this.txtDepthValue.setHorizontalAlignment(SwingConstants.CENTER);
		this.txtDepthValue.setText("0.5");
		
		rdbtnAbsolute = new JRadioButton("Absolute");
		rdbtnAbsolute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDepthValue.setVisible(true);
			}
		});
		groupDepth.add(rdbtnAbsolute);

		rdbtnRelative = new JRadioButton("Ratio of the attractors");
		rdbtnRelative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDepthValue.setVisible(true);
			}
		});
		groupDepth.add(rdbtnRelative);

		rdbtnLogn = new JRadioButton("Log2(n)");
		rdbtnLogn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDepthValue.setVisible(false);
			}
		});
		groupDepth.add(rdbtnLogn);
		rdbtnRelative.setSelected(true);

		btnCompute = new JButton("Compute");
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reqDepth = 0;
				int cutoff;
				int maxChildren;
				double permProb;
				double value;
				int n = samplingManager.getAttractorFinder().getAttractorsNumber();
				try{
					if(rdbtnAbsolute.isSelected()){
						reqDepth = Integer.valueOf(txtDepthValue.getText());
					}else if(rdbtnRelative.isSelected()){
						value = Double.valueOf(txtDepthValue.getText());
						if(value <= 0 || value > 1.0)
							throw new NumberFormatException("The value must be between 0 and 1.");
						reqDepth = (int) Math.floor(value * n);
					}else{
						reqDepth = (int) Math.floor(Math.log10(n) / Math.log10(2));
					}
					
					if(reqDepth > n)
						throw new NumberFormatException("The required depth must be smaller or equal than " + n);
					if(reqDepth <= 0)
						throw new NumberFormatException("The required depth must be greater than 0");
					
					cutoff = Integer.valueOf(txtCutoff.getText());
					if(cutoff < -1){
						throw new NumberFormatException("The cutoff must be greater or equal than 0 or -1.");
					}
					
					maxChildren = Integer.valueOf(txtMaxChildren.getText());
					if(maxChildren < 0){
						throw new NumberFormatException("The maximum number of children for a complete test must be greater than 0.");
					}
					
					permProb = Double.valueOf(txtPermutationProbability.getText());
					if(permProb < 0 || permProb > 1){
						throw new NumberFormatException("The permutation probability must be bertwwen 0 and 1.");
					}
					
					// Get a Cytoscape service 'DialogTaskManager' in CyActivator class
					DialogTaskManager dialogTaskManager = adapter.getCyServiceRegistrar().getService(DialogTaskManager.class);
					dialogTaskManager.execute(new TaskIterator(new FindTreeTask(reqDepth, cutoff, atmManager, samplingManager,
							cytoscapeBridge, currentNetwork, maxChildren, permProb)));
					System.out.println("Tesk completed");
				}catch(Exception nfe){
					String message = (String) (nfe.getMessage().equals("") ? nfe : nfe.getMessage());
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
				}finally{
					setVisible(false);
				}
			}
		});
		
		txtCutoff = new JTextField();
		txtCutoff.setHorizontalAlignment(SwingConstants.CENTER);
		txtCutoff.setText("-1");
		txtCutoff.setColumns(10);
		
		JLabel lblMaximumTreesTo = new JLabel("Permutation probability value:");
		
		JLabel label = new JLabel("Maximum trees to test:");
		
		JLabel lblMaxChildrenFor = new JLabel("Maximum children for a complete test:");
		
		txtMaxChildren = new JTextField();
		txtMaxChildren.setText("8");
		txtMaxChildren.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaxChildren.setColumns(10);
		
		txtPermutationProbability = new JTextField();
		txtPermutationProbability.setText("0.5");
		txtPermutationProbability.setHorizontalAlignment(SwingConstants.CENTER);
		txtPermutationProbability.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblMaximumTreesTo)
									.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
									.addComponent(txtPermutationProbability, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTreeDepth)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(6)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(rdbtnRelative)
												.addComponent(rdbtnAbsolute)
												.addComponent(rdbtnLogn, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txtCutoff, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtDepthValue, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblMaxChildrenFor)
									.addGap(18)
									.addComponent(txtMaxChildren, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
							.addGap(211))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnCompute)
							.addGap(181))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblTreeDepth)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnAbsolute)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnRelative)
							.addGap(5)
							.addComponent(rdbtnLogn))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(44)
							.addComponent(txtDepthValue, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(txtCutoff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaxChildrenFor)
						.addComponent(txtMaxChildren, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaximumTreesTo)
						.addComponent(txtPermutationProbability, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCompute)
					.addGap(85))
		);
		getContentPane().setLayout(groupLayout);
	}
}
