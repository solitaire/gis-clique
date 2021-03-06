package graph;

import java.io.InputStream;

public class Graph
{
	/** Number of vertices. */
	private final int n;
	/** Graph representation. */
	private final boolean[][] adjacencyMatrix;
	/** All neighbors for given vertex v. */
	private final Vertices[] neighbors;
	/** Recent maximum clique. */
	private Vertices maximumClique;
	/** Recent potential clique. */
	private Vertices compsub;

	public Graph(InputStream stream)
	{
		adjacencyMatrix = GraphReader.readAdjacencyMatrix(stream);
		n = adjacencyMatrix.length;
		neighbors = new Vertices[n];
		for (int y = 0; y < n; y++)
		{
			neighbors[y] = new Vertices();
			for (int x = 0; x < n; x++)
			{
				if (adjacencyMatrix[x][y])
				{
					neighbors[y].add(x);
				}
			}
		}
	}

	/** @param faster True, if version 2 of Bron-Kerbosh algorithm should be used. Otherwise, version 1 will be used.
	 * @return A maximum clique. */
	public Vertices getMaximumClique(boolean faster)
	{
		maximumClique = new Vertices();
		compsub = new Vertices();
		final Vertices candidates = new Vertices();
		for (int v = 0; v < n; v++)
		{
			candidates.add(v);
		}
		final Vertices not = new Vertices();
		if (faster)
		{
			bronKerbosh2(candidates, not);
		}
		else
		{
			bronKerbosh1(candidates, not);
		}
		return maximumClique;
	}

	@Override
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		final String lineSeparator = System.getProperty("line.separator");
		for (int y = 0; y < n; y++)
		{
			for (int x = 0; x < n; x++)
			{
				result.append((adjacencyMatrix[x][y]) ? 1 : 0);
				result.append(" ");
			}
			result.append(lineSeparator);
		}
		return result.toString();
	}

	/** Bron-Kerbosh version 1.
	 * @param candidates Set of candidates.
	 * @param not Set of excluded vertices, already taken as candidates. */
	private void bronKerbosh1(Vertices candidates, Vertices not)
	{
		if (candidates.isEmpty() && not.isEmpty())
		{
			if (maximumClique.size() < compsub.size())
			{
				maximumClique = new Vertices(compsub);
			}
			return;
		}

		final Integer[] candidatesToCheck = candidates.toArray(new Integer[0]);
		for (final int v : candidatesToCheck)
		{
			compsub.add(v);

			candidates.remove(v);
			final Vertices newCandidates = new Vertices(candidates);
			newCandidates.retainAll(neighbors[v]);

			final Vertices newNot = new Vertices(not);
			newNot.retainAll(neighbors[v]);

			bronKerbosh1(newCandidates, newNot);

			compsub.remove(v);
			not.add(v);
		}
	}

	/** Bron-Kerbosh version 2.
	 * @param candidates Set of candidates.
	 * @param not Set of excluded vertices, already taken as candidates. */
	private void bronKerbosh2(Vertices candidates, Vertices not)
	{
		if (candidates.isEmpty() && not.isEmpty())
		{
			if (maximumClique.size() < compsub.size())
			{
				maximumClique = new Vertices(compsub);
			}
			return;
		}

		// Choose pivot. Pivot is a vertex in the (P union X) set with the highest vertex degree.
		int pivot = -1;
		int maxDegree = -1;
		final Vertices union = new Vertices(candidates);
		union.addAll(not);
		for (final int v : union)
		{
			final int degree = neighbors[v].size();
			if (maxDegree < degree)
			{
				maxDegree = degree;
				pivot = v;
			}
		}

		// Remove pivot's neighbors from candidates.
		final Vertices candidatesToCheck = new Vertices(candidates);
		candidatesToCheck.removeAll(neighbors[pivot]);

		for (final int v : candidatesToCheck)
		{
			compsub.add(v);

			candidates.remove(v);
			final Vertices newCandidates = new Vertices(candidates);
			newCandidates.retainAll(neighbors[v]);

			final Vertices newNot = new Vertices(not);
			newNot.retainAll(neighbors[v]);

			bronKerbosh2(newCandidates, newNot);

			compsub.remove(v);
			not.add(v);
		}
	}
}
