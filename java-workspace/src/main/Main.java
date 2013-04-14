package main;

import graph.Graph;
import graph.Vertices;

public class Main
{
	public static void main(String[] args)
	{
		Graph graph = new Graph(System.in);
		final Vertices clique = graph.getMaximumClique(true);
		System.out.println(clique);
	}
}
