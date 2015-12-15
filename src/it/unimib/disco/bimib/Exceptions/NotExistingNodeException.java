/**
 * NotExistingNodeException class.
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @author Giorgia Previtali (g.previtali6@campus.unimib.it)
 * @group BIMIB @ Disco (Department of Information Technology, Systems and Communication) of Milan University - Bicocca  
 * @year 2013
 */
package it.unimib.disco.bimib.Exceptions;


public class NotExistingNodeException extends Exception {


	private static final long serialVersionUID = 1L;

	public NotExistingNodeException() {
		super("The node doesn't exist");

	}

	public NotExistingNodeException(String exception) {
		super(exception);
	}



}

