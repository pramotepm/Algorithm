package net.mikelab.algorithm.pagerank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PageRank {
	/*
	 * linkedlist : webpage-i <interger> ==> webpage <j> with score <double>
	 * maxSize : number of webpage in this graph
	 */
	private static HashMap<Integer, HashMap<Integer, Double>> linkedlist;
	private static int maxSize;
	private static HashSet<Integer> zeroOutdegree;

	private static void getZeroOutdegree() {
		HashSet<Integer> hasOutlink = new HashSet<Integer>();
		for (Map.Entry<Integer, HashMap<Integer, Double>> t1 : linkedlist.entrySet())
			if (!hasOutlink.contains(t1.getKey()) && t1.getValue().size() > 0)
				hasOutlink.add(t1.getKey());
		for (int i = 0; i < maxSize; i++)
			if (!hasOutlink.contains(i))
				zeroOutdegree.add(i);
	}

	private static double diff(double[] a, double[] b) {
		double sum = 0;
		for (int i = 0; i < maxSize; i++)
			sum += Math.pow(Math.abs(a[i] - b[i]) ,2);
		return Math.sqrt(sum);
	}

	public static double[] cal(HashMap<Integer, HashMap<Integer, Double>> linkedList, int Size) {
		linkedlist = linkedList;
		maxSize = Size;
		zeroOutdegree = new HashSet<Integer>();
		getZeroOutdegree();

		System.out.println("ZERO OUTLINK SIZE: " + zeroOutdegree.size());

		double[] source = new double[maxSize];
		for (int i = 0; i < maxSize; i++)
			source[i] = (double) 1.0 / maxSize;

		double[] dest;
		double threshold = 1E-15;
		double c = 0.85;
		double err = 0;
		do {
			dest = new double[maxSize];
			for (int i = 0; i < maxSize; i++)
				dest[i] = 0;
			for (Map.Entry<Integer, HashMap<Integer, Double>> t1 : linkedlist.entrySet()) {
				Integer iID = t1.getKey();
				for (Map.Entry<Integer, Double> t2 : t1.getValue().entrySet()) {
					Integer jID = t2.getKey();
					dest[jID] += source[iID] * ((double) 1.0 / t1.getValue().size());
				}
			}
			double zeroInlinkScoreSum = 0;
			for (Integer temp : zeroOutdegree)
				zeroInlinkScoreSum += source[temp] * ((double) 1.0 / maxSize);
			for (int j = 0; j < maxSize; j++)
				dest[j] += zeroInlinkScoreSum;
			for (int i = 0; i < maxSize; i++)
				dest[i] = c * dest[i] + (1.0 - c) / maxSize;
			err = diff(source, dest);
			source = dest;
			//System.out.println("Error " + err);
		} while (err > threshold);
		return source;
	}
}