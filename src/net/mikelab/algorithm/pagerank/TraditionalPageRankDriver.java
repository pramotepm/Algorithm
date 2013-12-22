package net.mikelab.algorithm.pagerank;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

public class TraditionalPageRankDriver {
	/*
	 * Haveliwala's web graph structure
	 * 
	 * <Number of Node>
	 * <Source ID 1> <Number of out link> <Destination ID 1> <Destination ID 2> <Destination ID...>
	 * <Source ID 2> ...
	 * <Source ID 3> ...
	 * .
	 * .
	 * .
	 * <Source ID N>
	 */	
	private HashMap<Integer, HashMap<Integer, Double>> web_graph_link_list = new HashMap<Integer, HashMap<Integer,Double>>();
	private int Size;
	
	public void readFromFile(String filename) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String N = br.readLine();
			Size = Integer.parseInt(N);
			String temp;
			while((temp = br.readLine()) != null) {
				String[] temp2 = temp.split(";");
				HashMap<Integer,Double> dest = new HashMap<Integer, Double>();
				double uniformScore = (double) 1 / (temp2.length - 1);
				for (int i = 1; i < temp2.length; i++)
					dest.put(Integer.parseInt(temp2[i]), uniformScore);
				web_graph_link_list.put(Integer.parseInt(temp2[0]), dest);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printGraph() {
		System.out.println(web_graph_link_list.size());
		for (Entry<Integer, HashMap<Integer, Double>> e : web_graph_link_list.entrySet()) {
			System.out.println("Source: " + e.getKey().toString());
			System.out.print("Dest: ");
			for (Entry<Integer, Double> ee : e.getValue().entrySet()) {
				System.out.print(ee.getKey().toString() + " ");
			}
			System.out.println();
		}
	}
	
	public double[] getPageRankScore() {
		return PageRank.cal(web_graph_link_list, Size);
	}
}