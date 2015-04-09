/**
 * This class defines the Mutation interface
 * 
 * @author Andrea Paroni (a.paroni@campus.unimib.it)
 * @author Giorgia Previtali (g.previtali6@campus.unimib.it)
 * @group BIMIB @ DISCo (Department of Information Technology, Systems and Communication) of Milan University - Bicocca 
 * @year 2013
 * 
 */

package it.unimib.disco.bimib.Mutations;

//GRNSim imports
import it.unimib.disco.bimib.Atms.Atm;
import it.unimib.disco.bimib.Exceptions.*;

public interface Mutation {
	
	/**
	 * This method returns the mutated atm
	 * @return atm
	 */
	public Atm getMutatedAtm();
	
	
	public Object doMutation(Object state) throws ParamDefinitionException, NotExistingNodeException, 
		InputTypeException, InterruptedException;

	public Object doSingleFlip(Object state, int gene) throws ParamDefinitionException, 
		NotExistingNodeException, InputTypeException, InterruptedException;

}
