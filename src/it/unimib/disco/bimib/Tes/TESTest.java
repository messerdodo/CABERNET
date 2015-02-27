package it.unimib.disco.bimib.Tes;

import java.io.IOException;

import it.unimib.disco.bimib.IO.Input;

public class TESTest {

	public static void main(String[] args) throws IOException {

		//TES Count TEST
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
		adj_mat = Input.readAtm("/Users/messerdodo/Desktop/Networks/network_2/atm.csv");
		double t = 0.56;
		for(double threshold = 0.0; threshold <= 1.0; threshold+= 0.01){
			for(int i = 0; i < adj_mat.length; i++){
				for(int k = 0; k < adj_mat.length; k++){
					if(adj_mat[i][k] <= t)
						adj_mat[i][k] = 0;
				}
			}
		}
		double[][] tesMatrix = TesManager.getTesGraph(adj_mat);
		for(int i = 0; i < tesMatrix.length; i++){
			for(int k = 0; k < tesMatrix.length; k++){
				System.out.print(tesMatrix[i][k] + " ");
			}
			System.out.println();
		}
	}
}
