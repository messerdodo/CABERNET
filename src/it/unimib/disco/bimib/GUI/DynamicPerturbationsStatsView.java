package it.unimib.disco.bimib.GUI;

//GESTODifferent imports
import it.unimib.disco.bimib.IO.Output;
import it.unimib.disco.bimib.Statistics.DynamicPerturbationsStatistics;

//System imports
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

//JFreeChart imports
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
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

	private double[] avalanchesObs;
	private JPanel contentPane;
	private JTextField txtAvalanchesBin;
	private JPanel avalanchesPanel;
	private ChartPanel avalchesChartPanel;
	private JPanel pnlSensitivity;
	private ChartPanel sensitivityChartPanel;


	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public DynamicPerturbationsStatsView(final DynamicPerturbationsStatistics statsToView, final ArrayList<String> genesNames) throws Exception {

		//Gets the avalanches dataset
		this.avalanchesObs = new double[statsToView.getAvalanches().size()];
		for(int i = 0; i < statsToView.getAvalanches().size(); i++)
			this.avalanchesObs[i] = statsToView.getAvalanches().get(i);

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

		JLabel lblAvalanchesMean = new JLabel("Mean: " + String.format("%.4f", avalanchesStats.getMean()));
		pnlAvalanchesStatistics.add(lblAvalanchesMean);


		JLabel lblAvalanchesStdDev = new JLabel("Std deviation: " + String.format("%.4f", avalanchesStats.getStandardDeviation()));
		pnlAvalanchesStatistics.add(lblAvalanchesStdDev);

		JLabel lblAvalanchesMedian = new JLabel("Median: " + String.format("%.4f", avalanchesStats.getPercentile(50)));
		pnlAvalanchesStatistics.add(lblAvalanchesMedian);

		JLabel lblAvalanchesGeomean = new JLabel("Geo-Mean: " + String.format("%.4f", avalanchesStats.getGeometricMean()));
		pnlAvalanchesStatistics.add(lblAvalanchesGeomean);

		JLabel lblAvalanchesMinValue = new JLabel("Min-Value: " + String.format("%.4f", avalanchesStats.getMin()));
		pnlAvalanchesStatistics.add(lblAvalanchesMinValue);

		JLabel lblAvalanchesMaxValue = new JLabel("Max-Value: " + String.format("%.4f", avalanchesStats.getMax()));
		pnlAvalanchesStatistics.add(lblAvalanchesMaxValue);

		JLabel lblAvalanchesMidrange = new JLabel("Mid-Range: " + String.format("%.4f", (avalanchesStats.getMax() - avalanchesStats.getMin())/2.0));
		pnlAvalanchesStatistics.add(lblAvalanchesMidrange);

		JLabel lblAvalanchesCurtosi = new JLabel("Kurtosis index: " + String.format("%.4f", avalanchesStats.getKurtosis()));
		pnlAvalanchesStatistics.add(lblAvalanchesCurtosi);

		JLabel lblAvalanchesObservations = new JLabel("Observations: " + avalanchesStats.getN());
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
					String outputPath;
					//In response to a button click:
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						outputPath = fc.getSelectedFile().getPath();
						Output.saveAvalachesDistribution(outputPath, statsToView.getAvalanchesDistribution());
					}
				}catch(Exception ex){
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

		DescriptiveStatistics sensitivityStats = new DescriptiveStatistics();
		for(double obs : statsToView.getSensitivity())
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
					String outputPath;
					//In response to a button click:
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
						outputPath = fc.getSelectedFile().getPath();
						Output.saveSensitivity(outputPath , genesNames, statsToView.getSensitivity());
					}
				}catch(Exception ex){
					System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
				}
			}
		});
		pnlSensitivityStatisticsControl.add(btnExportCsvFile_1);

		JPanel pnlSesitivityStatistics = new JPanel();
		pnlSesitivityStatistics.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlSensitivity.add(pnlSesitivityStatistics, BorderLayout.EAST);
		pnlSesitivityStatistics.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSensitivityMean = new JLabel("Mean: " + String.format("%.4f", sensitivityStats.getMean()));
		pnlSesitivityStatistics.add(lblSensitivityMean);

		JLabel lblSensitivityStdDeviation = new JLabel("Std deviation: " + String.format("%.4f", sensitivityStats.getStandardDeviation()));
		pnlSesitivityStatistics.add(lblSensitivityStdDeviation);

		JLabel lblSensitivityMedian = new JLabel("Median: " + String.format("%.4f", sensitivityStats.getPercentile(50)));
		pnlSesitivityStatistics.add(lblSensitivityMedian);

		JLabel lblSensitivityGeoMean = new JLabel("Geo-Mean: " + String.format("%.4f", sensitivityStats.getGeometricMean()));
		pnlSesitivityStatistics.add(lblSensitivityGeoMean);

		JLabel lblSensitivityMinValue = new JLabel("Min-Value: " + String.format("%.4f", sensitivityStats.getMin()));
		pnlSesitivityStatistics.add(lblSensitivityMinValue);

		JLabel lblSensitivityMaxValue = new JLabel("Max-value: " + String.format("%.4f", sensitivityStats.getMax()));
		pnlSesitivityStatistics.add(lblSensitivityMaxValue);

		JLabel lblSensitivityMidrange = new JLabel("Mid-Range: " + String.format("%.4f", (sensitivityStats.getMax() - sensitivityStats.getMin())/2.0));
		pnlSesitivityStatistics.add(lblSensitivityMidrange);

		JLabel lblSensitivityKurtosisIndex = new JLabel("Kurtosis index: " + String.format("%.4f", sensitivityStats.getKurtosis()));
		pnlSesitivityStatistics.add(lblSensitivityKurtosisIndex);

		JLabel lblSensitivityObservations = new JLabel("Observations: " + sensitivityStats.getN());
		pnlSesitivityStatistics.add(lblSensitivityObservations);

		//Creates and shows the avalanche distribution chart
		DefaultCategoryDataset sensitivityDataset = this.prepareCategoryDataset(genesNames, statsToView.getSensitivity());
		sensitivityChartPanel = new ChartPanel(this.createBarChart("Sensitivity", sensitivityDataset, "Genes names", "Sensitivity"));
		pnlSensitivity.add(sensitivityChartPanel, BorderLayout.CENTER);

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
	private DefaultCategoryDataset prepareCategoryDataset(ArrayList<String> xValues, int[] yValues) throws Exception{
		//Checks the dataset dimensions
		if(xValues.size() != yValues.length)
			throw new Exception("X and Y values must be the same length");
		//Creates and populates the dataset object
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < xValues.size(); i++){
			dataset.addValue(Double.valueOf(yValues[i]), Double.valueOf(yValues[i]), xValues.get(i));
		}
		return dataset;
	}
}
