package net.mikelab.algorithm.test;

import net.mikelab.algorithm.pagerank.TraditionalPageRankDriver;

public class test {
	public static void main(String[] args) {
		TraditionalPageRankDriver d = new TraditionalPageRankDriver();
		d.readFromFile("/san01/home/thanaphol/boat/project/TemporalPageRank/boomData/temp.txt");
		// d.printGraph();
		for (double x : d.getPageRankScore())
			System.out.println(x);
	}
}