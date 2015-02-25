/**
 * This view is used in order to show the perturbations statistics likes avalanches and sensitivity.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2013
 */

package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.IO.Output;

//System imports
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;

//Math3 imports
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

//JFreeChart imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class DynamicPerturbationsStatsView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, ArrayList<Integer>> avalanches;
	private HashMap<String, int[]> sensitivity;
	private double[] avalanchesObs;
	private final ArrayList<String> geneNames;

	private JPanel contentPane;
	private JTextField txtAvalanchesBin;
	private JPanel avalanchesPanel;
	private ChartPanel avalchesChartPanel;
	private JPanel pnlSensitivity;
	private ChartPanel sensitivityChartPanel;
	private JComboBox<String> cmbSelectedNetworks;
	private JLabel lblAvalanchesMean;	
	private JLabel lblAvalanchesStdDev;	
	private JLabel lblAvalanchesMedian;
	private JLabel lblAvalanchesGeomean;	
	private JLabel lblAvalanchesMinValue;	
	private JLabel lblAvalanchesMaxValue;
	private JLabel lblAvalanchesMidrange;
	private JLabel lblAvalanchesKurtosis;
	private JLabel lblAvalanchesObservations;
	private JLabel lblSensitivityMean;
	private JLabel lblSensitivityStdDeviation;	
	private JLabel lblSensitivityMedian;
	private JLabel lblSensitivityGeoMean;
	private JLabel lblSensitivityMinValue;	
	private JLabel lblSensitivityMaxValue;
	private JLabel lblSensitivityMidrange;	
	private JLabel lblSensitivityKurtosisIndex;
	private JLabel lblSensitivityObservations;



	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public DynamicPerturbationsStatsView(final HashMap<String, ArrayList<Integer>> avalanches,
			final HashMap<String, int[]> sensitivity, final ArrayList<String> geneNames, String selectedNetwork) throws Exception {

		this.avalanches = avalanches;
		this.sensitivity = sensitivity;
		this.geneNames = geneNames;
		
		//Gets the avalanches dataset
		this.avalanchesObs = new double[avalanches.get(selectedNetwork).size()];
		for(int i = 0; i < avalanches.get(selectedNetwork).size(); i++)
			this.avalanchesObs[i] = avalanches.get(selectedNetwork).get(i);

		setTitle("Dynamic Perturbations Statistics");
		setBounds(100, 100, 648, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		avalanchesPanel = new JPanel();
		tabbedPane.addTab("Avalanches distribution", null, avalanchesPanel, null);
		avalanchesPanel.setLayout(new BorderLayout(0, 0));

		JPanel avalanchesDistributionControlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) avalanchesDistributionControlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		avalanchesPanel.add(avalanchesDistributionControlPanel, BorderLayout.NORTH);

		JLabel lblBins = new JLabel("Bins:");
		avalanchesDistributionControlPanel.add(lblBins);

		txtAvalanchesBin = new JTextField();
		txtAvalanchesBin.setText("10");
		avalanchesDistributionControlPanel.add(txtAvalanchesBin);
		txtAvalanchesBin.setColumns(10);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		avalanchesDistributionControlPanel.add(horizontalStrut);

		JButton btnUpdateAlanchesChart = new JButton("Update chart");
		btnUpdateAlanchesChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Updates the chart
				Integer bins = Integer.valueOf(txtAvalanchesBin.getText());
				avalanchesPanel.remove(avalchesChartPanel);
				HistogramDataset avalanchesDataset = (HistogramDataset) prepareHistogramDataset(avalanchesObs, bins, "Avalanches");
				avalchesChartPanel = new ChartPanel(createHistChart("Avalanches distribution", avalanchesDataset, "Avalanches dimension", "Frequency"));
				avalanchesPanel.add(avalchesChartPanel, BorderLayout.CENTER);
				avalanchesPanel.updateUI();
			}
		});

		JPanel pnlAvalanchesStatistics = new JPanel();
		pnlAvalanchesStatistics.setBorder(new EmptyBorder(5, 5, 5, 5));
		avalanchesPanel.add(pnlAvalanchesStatistics, BorderLayout.EAST);
		pnlAvalanchesStatistics.setLayout(new GridLayout(0, 1, 0, 0));

		// Get a DescriptiveStatistics instance
		DescriptiveStatistics avalanchesStats = new DescriptiveStatistics();
		for(double obs : this.avalanchesObs)
			avalanchesStats.addValue(obs);

		lblAvalanchesMean = new JLabel("Mean: " + String.format("%.4f", avalanchesStats.getMean()));
		pnlAvalanchesStatistics.add(lblAvalanchesMean);

		lblAvalanchesStdDev = new JLabel("Std deviation: " + String.format("%.4f", avalanchesStats.getStandardDeviation()));
		pnlAvalanchesStatistics.add(lblAvalanchesStdDev);

		lblAvalanchesMedian = new JLabel("Median: " + String.format("%.4f", avalanchesStats.getPercentile(50)));
		pnlAvalanchesStatistics.add(lblAvalanchesMedian);

		lblAvalanchesGeomean = new JLabel("Geo-Mean: " + String.format("%.4f", avalanchesStats.getGeometricMean()));
		pnlAvalanchesStatistics.add(lblAvalanchesGeomean);

		lblAvalanchesMinValue = new JLabel("Min-Value: " + String.format("%.4f", avalanchesStats.getMin()));
		pnlAvalanchesStatistics.add(lblAvalanchesMinValue);

		lblAvalanchesMaxValue = new JLabel("Max-Value: " + String.format("%.4f", avalanchesStats.getMax()));
		pnlAvalanchesStatistics.add(lblAvalanchesMaxValue);

		lblAvalanchesMidrange = new JLabel("Mid-Range: " + String.format("%.4f", (avalanchesStats.getMax() - avalanchesStats.getMin())/2.0));
		pnlAvalanchesStatistics.add(lblAvalanchesMidrange);

		lblAvalanchesKurtosis = new JLabel("Kurtosis index: " + String.format("%.4f", avalanchesStats.getKurtosis()));
		pnlAvalanchesStatistics.add(lblAvalanchesKurtosis);

		lblAvalanchesObservations = new JLabel("Observations: " + avalanchesStats.getN());
		pnlAvalanchesStatistics.add(lblAvalanchesObservations);


		//Creates and shows the avalanche distribution chart
		avalanchesDistributionControlPanel.add(btnUpdateAlanchesChart);

		Component horizontalStrut_1 = Box.createHorizontalStrut(130);
		avalanchesDistributionControlPanel.add(horizontalStrut_1);

		JButton btnExportCsvFile = new JButton("Export CSV file");
		btnExportCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Saves the avalanches observations and distribution
				try{
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					String outputPath;

					//In response to a button click:
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						outputPath = fc.getSelectedFile().getPath();
						outputPath = outputPath + "/avalanches.csv";
						Output.saveAvalaches(outputPath, avalanches); 
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				}
			}
		});

		avalanchesDistributionControlPanel.add(btnExportCsvFile);
		Integer bins = Integer.valueOf(txtAvalanchesBin.getText());
		HistogramDataset avalanchesDataset = (HistogramDataset) this.prepareHistogramDataset(this.avalanchesObs, bins, "Avalanches");
		avalchesChartPanel = new ChartPanel(this.createHistChart("Avalanches distribution", avalanchesDataset, "Avalanches dimension", "Frequency"));
		avalanchesPanel.add(avalchesChartPanel, BorderLayout.CENTER);

		pnlSensitivity = new JPanel();
		tabbedPane.addTab("Sensitivity", null, pnlSensitivity, null);
		pnlSensitivity.setLayout(new BorderLayout(0, 0));

		//Statistics object 
		DescriptiveStatistics sensitivityStats = new DescriptiveStatistics();
		for(double obs : sensitivity.get(selectedNetwork))
			sensitivityStats.addValue(obs);

		JPanel pnlSensitivityStatisticsControl = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnlSensitivityStatisticsControl.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		pnlSensitivity.add(pnlSensitivityStatisticsControl, BorderLayout.NORTH);

		JButton btnExportCsvFile_1 = new JButton("Export CSV file");
		btnExportCsvFile_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Saves the sensitivity csv file
				try{
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					String outputPath;
					//In response to a button click:
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						outputPath = fc.getSelectedFile().getPath();
						outputPath = outputPath + "/sensitivity.csv";
						Output.saveSensitivity(outputPath, geneNames, sensitivity);
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				}
			}
		});
		pnlSensitivityStatisticsControl.add(btnExportCsvFile_1);

		JPanel pnlSesitivityStatistics = new JPanel();
		pnlSesitivityStatistics.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlSensitivity.add(pnlSesitivityStatistics, BorderLayout.EAST);
		pnlSesitivityStatistics.setLayout(new GridLayout(0, 1, 0, 0));

		lblSensitivityMean = new JLabel("Mean: " + String.format("%.4f", sensitivityStats.getMean()));
		pnlSesitivityStatistics.add(lblSensitivityMean);

		lblSensitivityStdDeviation = new JLabel("Std deviation: " + String.format("%.4f", sensitivityStats.getStandardDeviation()));
		pnlSesitivityStatistics.add(lblSensitivityStdDeviation);

		lblSensitivityMedian = new JLabel("Median: " + String.format("%.4f", sensitivityStats.getPercentile(50)));
		pnlSesitivityStatistics.add(lblSensitivityMedian);

		lblSensitivityGeoMean = new JLabel("Geo-Mean: " + String.format("%.4f", sensitivityStats.getGeometricMean()));
		pnlSesitivityStatistics.add(lblSensitivityGeoMean);

		lblSensitivityMinValue = new JLabel("Min-Value: " + String.format("%.4f", sensitivityStats.getMin()));
		pnlSesitivityStatistics.add(lblSensitivityMinValue);

		lblSensitivityMaxValue = new JLabel("Max-value: " + String.format("%.4f", sensitivityStats.getMax()));
		pnlSesitivityStatistics.add(lblSensitivityMaxValue);

		lblSensitivityMidrange = new JLabel("Mid-Range: " + String.format("%.4f", (sensitivityStats.getMax() - sensitivityStats.getMin())/2.0));
		pnlSesitivityStatistics.add(lblSensitivityMidrange);

		lblSensitivityKurtosisIndex = new JLabel("Kurtosis index: " + String.format("%.4f", sensitivityStats.getKurtosis()));
		pnlSesitivityStatistics.add(lblSensitivityKurtosisIndex);

		lblSensitivityObservations = new JLabel("Observations: " + sensitivityStats.getN());
		pnlSesitivityStatistics.add(lblSensitivityObservations);

		//Creates and shows the sensitivity distribution chart
		DefaultCategoryDataset sensitivityDataset = this.prepareCategoryDataset(this.geneNames, sensitivity.get(selectedNetwork), "Sensitivity");
		sensitivityChartPanel = new ChartPanel(this.createBarChart("Sensitivity", sensitivityDataset, "Genes names", "Sensitivity"));
		pnlSensitivity.add(sensitivityChartPanel, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblDataset = new JLabel("Dataset:");

		//Defines and populates the network selection combobox.
		cmbSelectedNetworks = new JComboBox<String>();
		cmbSelectedNetworks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateStatistics();
				} catch (Exception ex) {
					String message = (String) (ex.getMessage().equals("") ? ex : ex.getMessage());
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		});
		DefaultComboBoxModel<String> selectedNetworkModel = new DefaultComboBoxModel<String>();
		selectedNetworkModel.addElement("All");
		for(String simId : avalanches.keySet()){
			selectedNetworkModel.addElement(simId);
		}
		cmbSelectedNetworks.setModel(selectedNetworkModel);
		cmbSelectedNetworks.setSelectedItem(selectedNetwork);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblDataset)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cmbSelectedNetworks, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
						.addGap(209))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDataset)
								.addComponent(cmbSelectedNetworks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				);
		panel.setLayout(gl_panel);

	}

	/**
	 * This method returns a JFreeChart histogram chart object.
	 * @param chartTitle: Title of the chart
	 * @param dataset: the dataset to process
	 * @param xLabelsTitle: Title for the X axes
	 * @param yLabelsTitle: Title for the Y axes
	 * @return
	 */
	public JFreeChart createHistChart(String chartTitle, IntervalXYDataset dataset, String xLabelsTitle, String yLabelsTitle){
		JFreeChart hist = ChartFactory.createHistogram(chartTitle, xLabelsTitle, yLabelsTitle, 
				dataset, PlotOrientation.VERTICAL, false, false, false);
		return hist;
	}

	/**
	 * This method creates and populates an IntervallXYDataset object.
	 * @param observations: observations
	 * @param bins: Number of bins
	 * @param seriesName: Name of the series.
	 * @return a IntervallXYDataset object.
	 */
	private IntervalXYDataset prepareHistogramDataset(double[] observations, int bins, String seriesName){
		HistogramDataset dataset = new HistogramDataset();
		dataset.addSeries(seriesName, observations, bins);
		return dataset;
	}

	/**
	 * This method returns a JFreeChart bar chart object.
	 * @param chartTitle: Title of the chart
	 * @param dataset: A category dataset.
	 * @param xLabelsTitle: Title for the X axis
	 * @param yLabelsTitle: Title for the Y axis
	 * @return
	 */
	public JFreeChart createBarChart(String chartTitle, CategoryDataset dataset, String xLabelsTitle, String yLabelsTitle){
		//Creates the bar chart
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle,
				xLabelsTitle, yLabelsTitle,
				dataset,PlotOrientation.VERTICAL,
				false, false, false);
		//Sets the x tick labels angle (45 degrees)
		CategoryPlot plot = barChart.getCategoryPlot();
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		return barChart;
	}


	/**
	 * This method returns a CategoryDataset populated with the passed data.
	 * @param xValues: X values
	 * @param yValues: Y values
	 * @return The CategoryDataset dataset object
	 * @throws Exception
	 */
	private DefaultCategoryDataset prepareCategoryDataset(ArrayList<String> xValues, int[] yValues, String seriesName) throws Exception{
		//Checks the dataset dimensions
		if(xValues.size() != yValues.length)
			throw new Exception("X and Y values must be the same length");
		//Creates and populates the dataset object
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < xValues.size(); i++){
			dataset.addValue(Double.valueOf(yValues[i]), seriesName, xValues.get(i));
		}
		return dataset;
	}

	private void updateStatistics() throws Exception{

		String selectedNetwork = this.cmbSelectedNetworks.getSelectedItem().toString();
		DescriptiveStatistics avalanchesStats = new DescriptiveStatistics();
		DescriptiveStatistics sensitivityStats = new DescriptiveStatistics();
		int[] sensitivityObs;
		if(!selectedNetwork.equals("All")){
			//Gets the avalanches dataset
			this.avalanchesObs = new double[avalanches.get(selectedNetwork).size()];
			for(int i = 0; i < avalanches.get(selectedNetwork).size(); i++)
				this.avalanchesObs[i] = avalanches.get(selectedNetwork).get(i);
			//Avalanches statistics
			for(double obs : this.avalanchesObs)
				avalanchesStats.addValue(obs);
			//Sensitivity statistics
			sensitivityObs = sensitivity.get(selectedNetwork);
			for(double obs : sensitivity.get(selectedNetwork))
				sensitivityStats.addValue(obs);
		}else{
			int obsSize = 0;
			for(String simId : this.avalanches.keySet()){
				obsSize = obsSize + avalanches.get(simId).size();
			}
			this.avalanchesObs = new double[obsSize];
			int i = 0;
			for(String simId : this.avalanches.keySet()){
				for(Integer simAvalanches : this.avalanches.get(simId)){
					this.avalanchesObs[i] = simAvalanches;
					i = i + 1;
				}
			}
			//Avalanches statistics
			for(double obs : this.avalanchesObs)
				avalanchesStats.addValue(obs);

			sensitivityObs = new int[geneNames.size()];
			for(int j = 0; j < geneNames.size(); j++)
				sensitivityObs[j] = 0;
			
			for(String simId : this.avalanches.keySet()){
				
				for(int j = 0; j < sensitivity.get(simId).length; j++){
					sensitivityStats.addValue(sensitivity.get(simId)[j]);
					sensitivityObs[j] = sensitivityObs[j] + sensitivity.get(simId)[j];
				}
			}
		}

		//Avalanches
		lblAvalanchesMean.setText("Mean: " + String.format("%.4f", avalanchesStats.getMean()));
		lblAvalanchesStdDev.setText("Std deviation: " + String.format("%.4f", avalanchesStats.getStandardDeviation()));
		lblAvalanchesMedian.setText("Median: " + String.format("%.4f", avalanchesStats.getPercentile(50)));
		lblAvalanchesGeomean.setText("Geo-Mean: " + String.format("%.4f", avalanchesStats.getGeometricMean()));
		lblAvalanchesMinValue.setText("Min-Value: " + String.format("%.4f", avalanchesStats.getMin()));
		lblAvalanchesMaxValue.setText("Max-Value: " + String.format("%.4f", avalanchesStats.getMax()));
		lblAvalanchesMidrange.setText("Mid-Range: " + String.format("%.4f", (avalanchesStats.getMax() - avalanchesStats.getMin())/2.0));
		lblAvalanchesKurtosis.setText("Kurtosis index: " + String.format("%.4f", avalanchesStats.getKurtosis()));
		lblAvalanchesObservations.setText("Observations: " + avalanchesStats.getN());

		//Avalanches chart
		Integer bins = Integer.valueOf(txtAvalanchesBin.getText());
		avalanchesPanel.remove(avalchesChartPanel);
		HistogramDataset avalanchesDataset = (HistogramDataset) prepareHistogramDataset(avalanchesObs, bins, "Avalanches");
		avalchesChartPanel = new ChartPanel(createHistChart("Avalanches distribution", avalanchesDataset, "Avalanches dimension", "Frequency"));
		avalanchesPanel.add(avalchesChartPanel, BorderLayout.CENTER);
		avalanchesPanel.updateUI();

		//Sensitivity
		lblSensitivityMean.setText("Mean: " + String.format("%.4f", sensitivityStats.getMean()));
		lblSensitivityStdDeviation.setText("Std deviation: " + String.format("%.4f", sensitivityStats.getStandardDeviation()));
		lblSensitivityMedian.setText("Median: " + String.format("%.4f", sensitivityStats.getPercentile(50)));
		lblSensitivityGeoMean.setText("Geo-Mean: " + String.format("%.4f", sensitivityStats.getGeometricMean()));
		lblSensitivityMinValue.setText("Min-Value: " + String.format("%.4f", sensitivityStats.getMin()));
		lblSensitivityMaxValue.setText("Max-value: " + String.format("%.4f", sensitivityStats.getMax()));
		lblSensitivityMidrange.setText("Mid-Range: " + String.format("%.4f", (sensitivityStats.getMax() - sensitivityStats.getMin())/2.0));
		lblSensitivityKurtosisIndex.setText("Kurtosis index: " + String.format("%.4f", sensitivityStats.getKurtosis()));
		lblSensitivityObservations.setText("Observations: " + sensitivityStats.getN());
		
		//Sensitivity chart
		pnlSensitivity.remove(sensitivityChartPanel);
		DefaultCategoryDataset sensitivityDataset = this.prepareCategoryDataset(this.geneNames, sensitivityObs, "Sensitivity");
		sensitivityChartPanel = new ChartPanel(this.createBarChart("Sensitivity", sensitivityDataset, "Genes names", "Sensitivity"));
		pnlSensitivity.add(sensitivityChartPanel, BorderLayout.CENTER);
		pnlSensitivity.updateUI();
	}
}
