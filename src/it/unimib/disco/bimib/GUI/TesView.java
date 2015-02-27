/**
 * This view is used in order to show the TES chart.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2014
 */

package it.unimib.disco.bimib.GUI;

//GRNSim imports
import it.unimib.disco.bimib.Atms.Atm;

import it.unimib.disco.bimib.Tes.TesManager;

//System imports
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.Component;

import javax.swing.Box;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


//JFreeChart imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class TesView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double[] thresholds;
	private int[] teses;
	private Atm atm;

	private JPanel contentPane;
	private JTextField txtStep;
	private ChartPanel pnlTesChart;


	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public TesView(Atm atm) throws Exception {

		this.atm = atm;

		setTitle("TES");
		setBounds(100, 100, 751, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pnlTesControl = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlTesControl.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(pnlTesControl, BorderLayout.NORTH);

		JLabel lblStep = new JLabel("Step:");
		pnlTesControl.add(lblStep);

		Component horizontalStrut_1 = Box.createHorizontalStrut(30);
		pnlTesControl.add(horizontalStrut_1);

		txtStep = new JTextField();
		txtStep.setText("0.01");
		pnlTesControl.add(txtStep);
		txtStep.setColumns(10);

		Component horizontalStrut = Box.createHorizontalStrut(390);
		pnlTesControl.add(horizontalStrut);

		JButton btnUpdateChart = new JButton("Update chart");
		btnUpdateChart.addActionListener(new CreateTesChart());
		pnlTesControl.add(btnUpdateChart);
		
		double thresholdStep = 0.01;
		computeTeses(thresholdStep);
		
		XYSeriesCollection dataset = prepareDataset(thresholds, teses, "Tes");
		pnlTesChart = new ChartPanel(createLineChart("", dataset, "Threshold", "Number of TESes"));
		contentPane.add(pnlTesChart, BorderLayout.CENTER);

	}


	/**
	 * This method returns a JFreeChart line chart object.
	 * @param chartTitle: Title of the chart
	 * @param dataset: A category dataset.
	 * @param xLabelsTitle: Title for the X axis
	 * @param yLabelsTitle: Title for the Y axis
	 * @return
	 */
	public JFreeChart createLineChart(String chartTitle, XYSeriesCollection dataset, String xLabelsTitle, String yLabelsTitle){
		//Creates the bar chart
		JFreeChart lineChart = ChartFactory.createXYLineChart(chartTitle, xLabelsTitle, 
				yLabelsTitle, dataset, PlotOrientation.VERTICAL, false, false, false);
		return lineChart;
	}

	/**
	 * This method returns a CategoryDataset populated with the passed data.
	 * @param xValues: X values
	 * @param yValues: Y values
	 * @return The CategoryDataset dataset object
	 * @throws Exception
	 */
	private XYSeriesCollection prepareDataset(double[] xValues, int[] yValues, String seriesName) throws Exception{
		//Checks the dataset dimensions
		if(xValues.length != yValues.length)
			throw new Exception("X and Y values must be the same length");
		XYSeries series = new XYSeries(seriesName);
	
		//Creates and populates the dataset object
		for(int i = 0; i < xValues.length; i++){
			series.add(Double.valueOf(xValues[i]), Double.valueOf(yValues[i]));
		}
		
		 XYSeriesCollection dataset = new XYSeriesCollection();
	        dataset.addSeries(series);
		return dataset;
	}

	/**
	 * This method computes the thresholds-dependent chart.
	 * @param thresholdStep: the threshold step. It must be between 0 and 1.
	 */
	private void computeTeses(double thresholdStep){

		int samplings = ((int) (1 / thresholdStep)) + 1;
		this.thresholds = new double[samplings];
		this.teses = new int[samplings];
		double threshold = 0.0;
		Atm copiedAtm = new Atm(this.atm.copyAtm());

		//Invokes the creation action
		for(int i = 0; i < samplings; i++){
			this.thresholds[i] = threshold;
			this.teses[i] = TesManager.getTesNumber(copiedAtm.atmMatrixWithDeltaRemoval(threshold), threshold);
			threshold = threshold + thresholdStep;
		}

	}

	public class CreateTesChart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				double thresholdStep = Double.valueOf(txtStep.getText());
				if(thresholdStep <= 0)
					thresholdStep = 0.01;
				else if(thresholdStep > 1)
					thresholdStep = 1.0;
				
				computeTeses(thresholdStep);
			
				contentPane.remove(pnlTesChart);
				XYSeriesCollection dataset = prepareDataset(thresholds, teses, "Tes");
				pnlTesChart = new ChartPanel(createLineChart("", dataset, "Threshold", "Number of TESes"));
				contentPane.add(pnlTesChart, BorderLayout.CENTER);
				contentPane.updateUI();
				
			}catch(Exception ex){
				System.out.println(ex.getMessage().equals("") ? ex : ex.getMessage());
			}

		}

	}
}
