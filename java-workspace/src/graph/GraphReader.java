package graph;

import java.io.InputStream;
import java.util.Scanner;

public class GraphReader
{
	public static boolean[][] readAdjacencyMatrix(InputStream stream)
	{
		boolean[][] adjacencyMatrix = new boolean[0][0];
		final Scanner scanner = new Scanner(stream);
		try
		{
			scanner.next();
			scanner.next();
			scanner.next();
			int n = scanner.nextInt();
			scanner.next();
			adjacencyMatrix = new boolean[n][n];
			for (int y = 0; y < n; y++)
			{
				for (int x = 0; x < n; x++)
				{
					final boolean connected = (scanner.nextInt() != 0);
					adjacencyMatrix[x][y] = connected;
				}
			}
		}
		finally
		{
			scanner.close();
		}
		return adjacencyMatrix;
	}
}
