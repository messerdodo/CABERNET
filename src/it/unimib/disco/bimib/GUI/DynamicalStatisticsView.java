/**
 * This view is used in order to show the dynamical statistics likes attractors distribution, ...
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2013
 */

package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.CABERNET.SimulationsContainer;
import it.unimib.disco.bimib.Exceptions.InputTypeException;
import it.unimib.disco.bimib.Exceptions.NotExistingNodeException;
import it.unimib.disco.bimib.Exceptions.ParamDefinitionException;
import it.unimib.disco.bimib.IO.Output;
import it.unimib.disco.bimib.Statistics.DynamicalStatistics;

//System imports
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;

//MATH3 imports
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

//JFreeChart imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;




public class DynamicalStatisticsView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimulationsContainer simulations;
	private DynamicalStatistics dynStats;
	private DescriptiveStatistics attractorsLengthsStatistics;
	private DescriptiveStatistics basinOfAttractionStatistics;

	private JPanel contentPane;
	private JPanel pnlAttractorsLengths;
	private JPanel pnlAttractorsLengthsStatistics;
	private JTextField txtAttractorsLengthsBins;
	private JCheckBox chckbxAttLenAllNetworks;
	private ChartPanel pnlAttractorsLengthsChart;
	private JLabel lblAttLenMean;
	private JLabel lblAttLenStdDev;
	private JLabel lblAttLenMedian;
	private JLabel lblAttLenGeoMean;
	private JLabel lblAttLenMinValue;
	private JLabel lblAttLenMaxValue;
	private JLabel lblAttLenMidRange;
	private JLabel lblAttLenKurtosisIndex;
	private JLabel lblAttLenObservations;
	private JPanel pnlBasinOfAttraction;
	private JPanel panel;
	private JLabel lblBins_1;
	private JTextField txtBasinBins;
	private JCheckBox chckbxBasinAllNetworks;
	private Component horizontalStrut;
	private JButton btnBasinUpdateChart;
	private JButton btnBasinExportCsv;
	private Component horizontalStrut_1;
	private JPanel pnlBasinStatistics;
	private JLabel lblBasinMean;
	private JLabel lblBasinStdDev;
	private JLabel lblBasinGeoMean;
	private JLabel lblBasinMinValue;
	private JLabel lblBasinMaxValue;
	private JLabel lblBasinMidRange;
	private JLabel lblBasinMedian;
	private JLabel lblBasinKurtosisIndex;
	private JLabel lblBasinObservations;
	private ChartPanel pnlBasinOfAttractionChart;
	private JPanel pnlOscillatingNodes;
	private ChartPanel pnlOscillatingNodesChart;
	

	/**
	 * Create the frame.
	 * @throws InputTypeException 
	 * @throws NotExistingNodeException 
	 * @throws ParamDefinitionException 
	 */
	public DynamicalStatisticsView(String currentNetwork, final SimulationsContainer simulations) throws ParamDefinitionException, NotExistingNodeException, InputTypeException {
		this.simulations = simulations;
		//Current network dynamical statistics
		dynStats = new DynamicalStatistics(this.simulations.getSimulation(currentNetwork).getSamplingManager());
		ArrayList<Integer> currentNetworkAttractorsLengths = dynStats.getAttractorsLength(true);
		//Populates the attractors lengths statistics object
		attractorsLengthsStatistics = new DescriptiveStatistics();
		for(Integer obs : currentNetworkAttractorsLengths)
			attractorsLengthsStatistics.addValue(obs);
		//Populates the basin of attraction statistics object
		basinOfAttractionStatistics = new DescriptiveStatistics();
		ArrayList<Integer> currentNetworkBasinOfAttraction = dynStats.getBasinOfAttraction(true);
		for(Integer obs : currentNetworkBasinOfAttraction)
			basinOfAttractionStatistics.addValue(obs);

		setBounds(100, 100, 681, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		pnlAttractorsLengths = new JPanel();
		tabbedPane.addTab("Attractors Lengths", null, pnlAttractorsLengths, null);
		pnlAttractorsLengths.setLayout(new BorderLayout(0, 0));

		JPanel pnlControlsAttractorsLengths = new JPanel();
		pnlAttractorsLengths.add(pnlControlsAttractorsLengths, BorderLayout.NORTH);
		FlowLayout fl_pnlControlsAttractorsLengths = new FlowLayout(FlowLayout.LEFT, 5, 5);
		pnlControlsAttractorsLengths.setLayout(fl_pnlControlsAttractorsLengths);

		JLabel lblBins = new JLabel("Bins:");
		pnlControlsAttractorsLengths.add(lblBins);

		txtAttractorsLengthsBins = new JTextField();
		txtAttractorsLengthsBins.setText("10");
		pnlControlsAttractorsLengths.add(txtAttractorsLengthsBins);
		txtAttractorsLengthsBins.setColumns(10);

		chckbxAttLenAllNetworks = new JCheckBox("All networks");
		pnlControlsAttractorsLengths.add(chckbxAttLenAllNetworks);

		JButton btnUpdateAttractorsLengthsChart = new JButton("Update Chart");
		btnUpdateAttractorsLengthsChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DynamicalStatistics dymStatsAux;
				DescriptiveStatistics statAux;
				ArrayList<Integer> attractorsLengths;
				int bins;
				pnlAttractorsLengths.remove(pnlAttractorsLengthsChart);
				try {
					try{
						bins = Integer.valueOf(txtAttractorsLengthsBins.getText());
					}catch(NumberFormatException numEx){
						bins = 10;
					}
					if(chckbxAttLenAllNetworks.isSelected()){
						attractorsLengths = new ArrayList<Integer>();
						for(String simId : simulations.getSimulationsId()){
							dymStatsAux = new DynamicalStatistics(simulations.getSimulation(simId).getSamplingManager());
							attractorsLengths.addAll(dymStatsAux.getAttractorsLength(true));
						}
						//Populates the statistical object with the attractors lengths of all the networks
						statAux = new DescriptiveStatistics();
						for(Integer obs : attractorsLengths){
							statAux.addValue(obs);
						}
					}else{
						attractorsLengths = dynStats.getAttractorsLength();
						statAux = attractorsLengthsStatistics;
					}
					
					//Shows the statistics
					lblAttLenMean.setText("Mean: " + String.format("%.4f", statAux.getMean()));
					lblAttLenStdDev.setText("Std dev: " + String.format("%.4f", statAux.getStandardDeviation()));
					lblAttLenMedian.setText("Median: " + String.format("%.4f", statAux.getPercentile(50)));
					lblAttLenGeoMean.setText("Geo-Mean: " + String.format("%.4f", statAux.getGeometricMean()));
					lblAttLenMinValue.setText("Min value: " + statAux.getMin());
					lblAttLenMaxValue.setText("Max value: " + statAux.getMax());
					lblAttLenMidRange.setText("Mid range: " + String.format("%.4f", (statAux.getMax() - statAux.getMin())/2.0));
					lblAttLenKurtosisIndex.setText("Kurtosis index: " + String.format("%.4f", statAux.getKurtosis()));
					lblAttLenObservations.setText("Observations: " + statAux.getN());
					
					HistogramDataset attractorsLengthsDataset = (HistogramDataset) prepareHistogramDataset(attractorsLengths, bins, "AttractorsLengths");
					pnlAttractorsLengthsChart = new ChartPanel(
							createHistChart("Attractors lengths distribution", attractorsLengthsDataset, 
									"Attractors lengths", "Frequency"));
					pnlAttractorsLengths.add(pnlAttractorsLengthsChart, BorderLayout.CENTER);
					pnlAttractorsLengths.updateUI();
					
				} catch (Exception ex) {
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				}
			}
		});
		
		horizontalStrut = Box.createHorizontalStrut(80);
		pnlControlsAttractorsLengths.add(horizontalStrut);
		pnlControlsAttractorsLengths.add(btnUpdateAttractorsLengthsChart);

		JButton btnExportCsvFile = new JButton("Export CSV file");
		btnExportCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					DynamicalStatistics dymStatsAux;
					ArrayList<Integer> attractorsLengths;
					String outputPath;
					//In response to a button click:
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						outputPath = fc.getSelectedFile().getParent();
						outputPath = outputPath + "/attractors_lengths.csv";
						//Get all the values of lengths if the checkbox is selected
						if(chckbxAttLenAllNetworks.isSelected()){
							attractorsLengths = new ArrayList<Integer>();
							for(String simId : simulations.getSimulationsId()){
								dymStatsAux = new DynamicalStatistics(simulations.getSimulation(simId).getSamplingManager());
								attractorsLengths.addAll(dymStatsAux.getAttractorsLength(true));
							}
						}else{
							attractorsLengths = dynStats.getAttractorsLength();
						}
						//Stores the attractors lengths in the selected path
						Output.saveAttractorsLengths(outputPath, attractorsLengths);
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				}
			}
		});
		pnlControlsAttractorsLengths.add(btnExportCsvFile);

		Integer bins = Integer.valueOf(this.txtAttractorsLengthsBins.getText());
		HistogramDataset attractorsLengthsDataset = (HistogramDataset) this.prepareHistogramDataset(currentNetworkAttractorsLengths, bins, "AttractorsLengths");
		pnlAttractorsLengthsChart = new ChartPanel(
				this.createHistChart("Attractors lengths distribution", attractorsLengthsDataset, 
						"Attractors lengths", "Frequency"));
		pnlAttractorsLengths.add(pnlAttractorsLengthsChart, BorderLayout.CENTER);

		pnlAttractorsLengthsStatistics = new JPanel();
		pnlAttractorsLengthsStatistics.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlAttractorsLengths.add(pnlAttractorsLengthsStatistics, BorderLayout.EAST);
		pnlAttractorsLengthsStatistics.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblAttLenMean = new JLabel("Mean: " + String.format("%.4f", attractorsLengthsStatistics.getMean()));
		pnlAttractorsLengthsStatistics.add(lblAttLenMean);
		
		lblAttLenStdDev = new JLabel("Std dev: " + String.format("%.4f", attractorsLengthsStatistics.getStandardDeviation()));
		pnlAttractorsLengthsStatistics.add(lblAttLenStdDev);
		
		lblAttLenMedian = new JLabel("Median: " + String.format("%.4f", attractorsLengthsStatistics.getPercentile(50)));
		pnlAttractorsLengthsStatistics.add(lblAttLenMedian);
		
		lblAttLenGeoMean = new JLabel("Geo-Mean: " + String.format("%.4f", attractorsLengthsStatistics.getGeometricMean()));
		pnlAttractorsLengthsStatistics.add(lblAttLenGeoMean);
		
		lblAttLenMinValue = new JLabel("Min value: " + attractorsLengthsStatistics.getMin());
		pnlAttractorsLengthsStatistics.add(lblAttLenMinValue);
		
		lblAttLenMaxValue = new JLabel("Max value: " + attractorsLengthsStatistics.getMax());
		pnlAttractorsLengthsStatistics.add(lblAttLenMaxValue);
		
		lblAttLenMidRange = new JLabel("Mid range: " + String.format("%.4f", (attractorsLengthsStatistics.getMax() - attractorsLengthsStatistics.getMin())/2.0));
		pnlAttractorsLengthsStatistics.add(lblAttLenMidRange);
		
		lblAttLenKurtosisIndex = new JLabel("Kurtosis index: " + String.format("%.4f", attractorsLengthsStatistics.getKurtosis()));
		pnlAttractorsLengthsStatistics.add(lblAttLenKurtosisIndex);
		
		lblAttLenObservations = new JLabel("Observations: " + attractorsLengthsStatistics.getN());
		pnlAttractorsLengthsStatistics.add(lblAttLenObservations);
		
		pnlBasinOfAttraction = new JPanel();
		tabbedPane.addTab("Basin of Attraction", null, pnlBasinOfAttraction, null);
		pnlBasinOfAttraction.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlBasinOfAttraction.add(panel, BorderLayout.NORTH);
		
		lblBins_1 = new JLabel("Bins:");
		panel.add(lblBins_1);
		
		txtBasinBins = new JTextField();
		txtBasinBins.setText("10");
		panel.add(txtBasinBins);
		txtBasinBins.setColumns(10);
		
		chckbxBasinAllNetworks = new JCheckBox("All networks");
		panel.add(chckbxBasinAllNetworks);
		
		horizontalStrut_1 = Box.createHorizontalStrut(80);
		panel.add(horizontalStrut_1);
		
		btnBasinUpdateChart = new JButton("Update Chart");
		btnBasinUpdateChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DynamicalStatistics dymStatsAux;
				DescriptiveStatistics statAux;
				ArrayList<Integer> basinOfAttraction;
				int bins;
				pnlBasinOfAttraction.remove(pnlBasinOfAttractionChart);
				try {
					try{
						bins = Integer.valueOf(txtBasinBins.getText());
					}catch(NumberFormatException numEx){
						bins = 10;
					}
					if(chckbxBasinAllNetworks.isSelected()){
						basinOfAttraction = new ArrayList<Integer>();
						for(String simId : simulations.getSimulationsId()){
							dymStatsAux = new DynamicalStatistics(simulations.getSimulation(simId).getSamplingManager());
							basinOfAttraction.addAll(dymStatsAux.getBasinOfAttraction(true));
						}
						//Populates the statistical object with the attractors lengths of all the networks
						statAux = new DescriptiveStatistics();
						for(Integer obs : basinOfAttraction){
							statAux.addValue(obs);
						}
					}else{
						basinOfAttraction = dynStats.getBasinOfAttraction();
						statAux = basinOfAttractionStatistics;
					}
					//pnlBasinOfAttractionChart
					//Shows the statistics
					lblBasinMean.setText("Mean: " + String.format("%.4f", statAux.getMean()));
					lblBasinStdDev.setText("Std dev: " + String.format("%.4f", statAux.getStandardDeviation()));
					lblBasinMedian.setText("Median: " + String.format("%.4f", statAux.getPercentile(50)));
					lblBasinGeoMean.setText("Geo-Mean: " + String.format("%.4f", statAux.getGeometricMean()));
					lblBasinMinValue.setText("Min value: " + statAux.getMin());
					lblBasinMaxValue.setText("Max value: " + statAux.getMax());
					lblBasinMidRange.setText("Mid range: " + String.format("%.4f", (statAux.getMax() - statAux.getMin())/2.0));
					lblBasinKurtosisIndex.setText("Kurtosis index: " + String.format("%.4f", statAux.getKurtosis()));
					lblBasinObservations.setText("Observations: " + statAux.getN());
					
					HistogramDataset basinOfAttractionDataset = (HistogramDataset) prepareHistogramDataset(basinOfAttraction, bins, "basinOfAttraction");
					pnlBasinOfAttractionChart = new ChartPanel(
							createHistChart("Basins of attraction distribution", basinOfAttractionDataset, 
									"Basin of attraction dimension", "Frequency"));
					pnlBasinOfAttraction.add(pnlBasinOfAttractionChart, BorderLayout.CENTER);
					pnlBasinOfAttraction.updateUI();
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				}
			}
		});
		panel.add(btnBasinUpdateChart);
		
		btnBasinExportCsv = new JButton("Export CSV file");
		btnBasinExportCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					DynamicalStatistics dymStatsAux;
					ArrayList<Integer> basinsOfAttraction;
					String outputPath;
					//In response to a button click:
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						outputPath = fc.getSelectedFile().getPath();
						outputPath = outputPath + "/basins_of_attraction.csv";
						//Get all the values of basins of attraction if the checkbox is selected
						if(chckbxBasinAllNetworks.isSelected()){
							basinsOfAttraction = new ArrayList<Integer>();
							for(String simId : simulations.getSimulationsId()){
								dymStatsAux = new DynamicalStatistics(simulations.getSimulation(simId).getSamplingManager());
								basinsOfAttraction.addAll(dymStatsAux.getBasinOfAttraction(true));
							}
						}else{
							basinsOfAttraction = dynStats.getBasinOfAttraction();
						}
						//Stores the basins of attraction in the selected path
						Output.saveBasinOfAttractionFile(outputPath, basinsOfAttraction);
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage().equals("") ? ex : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, null);
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				
				}
			}
		});
		panel.add(btnBasinExportCsv);
		
		pnlBasinStatistics = new JPanel();
		pnlBasinStatistics.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlBasinOfAttraction.add(pnlBasinStatistics, BorderLayout.EAST);
		pnlBasinStatistics.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblBasinMean = new JLabel("Mean: " + String.format("%.4f", basinOfAttractionStatistics.getMean()));
		pnlBasinStatistics.add(lblBasinMean);
		
		lblBasinStdDev = new JLabel("Std dev: " + String.format("%.4f", basinOfAttractionStatistics.getStandardDeviation()));
		pnlBasinStatistics.add(lblBasinStdDev);
		
		lblBasinMedian = new JLabel("Median: " + String.format("%.4f", basinOfAttractionStatistics.getPercentile(50)));
		pnlBasinStatistics.add(lblBasinMedian);
		
		lblBasinGeoMean = new JLabel("Geo mean: " + String.format("%.4f", basinOfAttractionStatistics.getGeometricMean()));
		pnlBasinStatistics.add(lblBasinGeoMean);
		
		lblBasinMinValue = new JLabel("Min value: " + basinOfAttractionStatistics.getMin());
		pnlBasinStatistics.add(lblBasinMinValue);
		
		lblBasinMaxValue = new JLabel("Max value: " + basinOfAttractionStatistics.getMax());
		pnlBasinStatistics.add(lblBasinMaxValue);
		
		lblBasinMidRange = new JLabel("Mid range: " + String.format("%.4f", (basinOfAttractionStatistics.getMax() - basinOfAttractionStatistics.getMin()) / 2.0));
		pnlBasinStatistics.add(lblBasinMidRange);
		
		lblBasinKurtosisIndex = new JLabel("Kurtosis index: " + String.format("%.4f", basinOfAttractionStatistics.getKurtosis()));
		pnlBasinStatistics.add(lblBasinKurtosisIndex);
		
		lblBasinObservations = new JLabel("Observations: " + basinOfAttractionStatistics.getN());
		pnlBasinStatistics.add(lblBasinObservations);
		
		int basinBins = Integer.valueOf(txtBasinBins.getText());
		HistogramDataset basinOfAttractionDataset = (HistogramDataset) this.prepareHistogramDataset(currentNetworkBasinOfAttraction, basinBins, "BasinOfAttraction");
		
		pnlBasinOfAttractionChart = new ChartPanel(
				this.createHistChart("Basins of attraction distribution", basinOfAttractionDataset, 
						"Basin of attraction dimension", "Frequency"));
		pnlBasinOfAttraction.add(pnlBasinOfAttractionChart, BorderLayout.CENTER);
		
		pnlOscillatingNodes = new JPanel();
		tabbedPane.addTab("Oscillating Nodes Ratio", null, pnlOscillatingNodes, null);
		pnlOscillatingNodes.setLayout(new BorderLayout(0, 0));
		
		PieDataset oscillatingDataset = this.preparePieDataset(this.dynStats.getOscillatingNodesRatio());
		pnlOscillatingNodesChart = new ChartPanel(this.createPieChart("Oscillating nodes ratio", oscillatingDataset));
		pnlOscillatingNodes.add(pnlOscillatingNodesChart, BorderLayout.CENTER);

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
	private IntervalXYDataset prepareHistogramDataset(ArrayList<Integer> observationsArray, int bins, String seriesName){
		HistogramDataset dataset = new HistogramDataset();
		double[] observations = new double[observationsArray.size()];
		for(int i = 0; i < observationsArray.size(); i++){
			observations[i] = observationsArray.get(i).doubleValue();
		}
		dataset.addSeries(seriesName, observations, bins);
		return dataset;
	}

	/**
	 * This method returns a Pie chart
	 * @param title: Title of the chart
	 * @param dataset: dataset for the chart
	 * @return A Pie dataset.
	 */
	private JFreeChart createPieChart(String title, PieDataset dataset){
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSimpleLabels(true);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
	            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
	    plot.setLabelGenerator(gen);
		return chart;        
	}
	
	/**
	 * This method prepares and returns a Pie dataset
	 * @param value: The value for the dataset
	 * @return a Pie dataset.
	 */
	private PieDataset preparePieDataset(double value){
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Oscillating nodes", value);
		dataset.setValue("Static nodes", (1.0 - value));
		return dataset;
	}

}
