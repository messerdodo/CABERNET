package it.unimib.disco.bimib.Tes;

import java.io.IOException;
import java.util.ArrayList;

import it.unimib.disco.bimib.Exceptions.InputFormatException;
import it.unimib.disco.bimib.Exceptions.TesTreeException;
import it.unimib.disco.bimib.IO.Input;

public class TESTest {

	public static void main(String[] args) throws IOException, NullPointerException, 
	InputFormatException, NumberFormatException, TesTreeException, InterruptedException {
		
		System.out.println("***TEST Tree Generation***");
		ArrayList<String[]> readTree = Input.readTree("/Users/messerdodo/Desktop/testTree.txt");
		TesTree givenTree = TesManager.createTesTreeFromFile(readTree);
		System.out.println("Red tree");
		givenTree.print();
		double atm[][] = new double[3][3];
		atm[0][0] = 0.88;
		atm[0][1] = 0.12;
		atm[0][2] = 0.0;
		atm[1][0] = 0.24;
		atm[1][1] = 0.75;
		atm[1][2] = 0.0;
		atm[2][0] = 0.34;
		atm[2][1] = 0.09;
		atm[2][2] = 0.56;
		Object[] attractors = {"0000000000", "0000000001", "0000000010"};
		double[] thresholds = {0.0, 0.5, 0.80};
		TesTree tree = new TesTree(thresholds, atm, attractors);
		System.out.println("Generated tree");
		tree.print();
		
		
		//TES Count TEST
		/*System.out.println("***TES Count TEST***");
		double[][] adj_mat = Input.readAtm("/Users/messerdodo/Desktop/Networks/network_2/atm.csv");
		for(double threshold = 0.0; threshold <= 1.0; threshold+= 0.01){
			for(int i = 0; i < adj_mat.length; i++){
				for(int k = 0; k < adj_mat.length; k++){
					if(adj_mat[i][k] <= threshold)
						adj_mat[i][k] = 0;
				}
			}
			System.out.println(threshold + " --> " + TesManager.getTesNumber(adj_mat, threshold));
		}

		//TES Adjacency matrix TEST
		//adj_mat = Input.readAtm("/Users/messerdodo/Desktop/Networks/network_2/atm.csv");
		double t = 0.5;
		for(double threshold = 0.0; threshold <= 1.0; threshold+= 0.01){
			for(int i = 0; i < atm.length; i++){
				for(int k = 0; k < atm.length; k++){
					if(atm[i][k] <= t)
						atm[i][k] = 0;
				}
			}
		}
		double[][] tesMatrix = TesManager.getTesGraph(atm);
		for(int i = 0; i < tesMatrix.length; i++){
			for(int k = 0; k < tesMatrix.length; k++){
				System.out.print(tesMatrix[i][k] + " ");
			}
			System.out.println();
		}*/
		
		ArrayList<String[]> t1 = Input.readTree("/Users/messerdodo/Desktop/t1.txt");
		TesTree gt1 = TesManager.createTesTreeFromFile(t1);
		ArrayList<String[]> t2 = Input.readTree("/Users/messerdodo/Desktop/t2.txt");
		TesTree gt2 = TesManager.createTesTreeFromFile(t2);
		
		System.out.println("GT1 depth: " + gt1.getTreeDeppness());
		System.out.println("GT2 depth: " + gt2.getTreeDeppness());
		gt1.print();
		gt2.print();
		System.out.println("Comparison: " + (gt2.tesTreeCompare(gt2, 8, 0.12) ? "YES" : "NO") );
	
		
	}
	
	
}
