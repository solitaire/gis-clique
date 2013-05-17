package graph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphReader
{
	public static boolean[][] readAdjacencyMatrix(InputStream stream)
	{
		boolean[][] adjacencyMatrix = new boolean[0][0];
		final ArrayList<Boolean> input = new ArrayList<Boolean>();
		final Scanner scanner = new Scanner(stream);
		try
		{
			while (scanner.hasNext())
			{
				input.add(scanner.nextInt() != 0);
			}
		}
		finally
		{
			scanner.close();
		}
		final int n = (int) Math.sqrt(input.size());
		adjacencyMatrix = new boolean[n][n];
		for (int y = 0; y < n; y++)
		{
			for (int x = 0; x < n; x++)
			{
				adjacencyMatrix[x][y] = input.get((n * y) + x);
			}
		}
		return adjacencyMatrix;
	}
}
