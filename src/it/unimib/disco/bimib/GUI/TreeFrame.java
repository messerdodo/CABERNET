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

	public TreeFrame(final AtmManager atmManager, final SamplingManager samplingManager, 
			String networkId, final CyNetwork currentNetwork, final CySwingAppAdapter adapter,
			CyApplicationManager appManager) {
		setAlwaysOnTop(true);
		super.setSize(350, 160);
		setType(Type.UTILITY);
		setResizable(false);
		this.txtDepthValue.setVisible(false);
		
		this.cytoscapeBridge = new NetworkManagment(adapter, appManager);
		
		this.setTitle("Network: " + networkId);

		JLabel lblTreeDepth = new JLabel("Tree depth:");

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

		txtDepthValue = new JTextField();
		txtDepthValue.setHorizontalAlignment(SwingConstants.CENTER);
		txtDepthValue.setText("1");

		rdbtnLogn = new JRadioButton("Log2(n)");
		rdbtnLogn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDepthValue.setVisible(false);
			}
		});
		groupDepth.add(rdbtnLogn);
		rdbtnLogn.setSelected(true);

		btnCompute = new JButton("Compute");
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reqDepth = 0, value;
				int n = samplingManager.getAttractorFinder().getAttractorsNumber();
				try{
					if(rdbtnAbsolute.isSelected()){
						reqDepth = Integer.valueOf(txtDepthValue.getText());
						
					}else if(rdbtnRelative.isSelected()){
						value = Integer.valueOf(txtDepthValue.getText());
						if(value <= 0 || value > 1.0)
							throw new NumberFormatException("The value must be between 0 and 1.");
						reqDepth = (int) Math.floor(Integer.valueOf(txtDepthValue.getText()) * n);
					}else{
						reqDepth = (int) Math.floor(Math.log10(n) / Math.log10(2));
					}
					
					if(reqDepth > n)
						throw new NumberFormatException("The required depth must be smaller or equal than " + n);
					if(reqDepth <= 0)
						throw new NumberFormatException("The required depth must be greater than 0");
					
					// Get a Cytoscape service 'DialogTaskManager' in CyActivator class
					DialogTaskManager dialogTaskManager = adapter.getCyServiceRegistrar().getService(DialogTaskManager.class);
					dialogTaskManager.execute(new TaskIterator(new FindTreeTask(reqDepth, atmManager, samplingManager,
							cytoscapeBridge, currentNetwork)));
					System.out.println("Tesk completed");
				}catch(Exception nfe){
					String message = (String) (nfe.getMessage().equals("") ? nfe : nfe.getMessage());
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
				}finally{
					setVisible(false);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnAbsolute)
							.addPreferredGap(ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
							.addComponent(txtDepthValue, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(27))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnLogn, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(199, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnRelative)
							.addContainerGap())))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTreeDepth)
					.addContainerGap(285, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(255, Short.MAX_VALUE)
					.addComponent(btnCompute)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTreeDepth)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnAbsolute)
						.addComponent(txtDepthValue, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnRelative)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnLogn)
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(btnCompute)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}


}
