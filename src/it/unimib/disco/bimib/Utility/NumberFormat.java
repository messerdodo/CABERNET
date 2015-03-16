/**
 * This class implements the common number format methods.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2014
 * 
 */
package it.unimib.disco.bimib.Utility;

import java.math.BigDecimal;
import java.math.MathContext;

public class NumberFormat {

	/**
	 * This methods returns the given number with 'precision' significant figures.
	 * @param p is the probability of true
	 * @return a boolean random value 
	 */
	public static Double toPrecision(double number, int precision){
		BigDecimal d = new BigDecimal(number, new MathContext(precision));
		return Double.valueOf(d.toString());
	}

}
