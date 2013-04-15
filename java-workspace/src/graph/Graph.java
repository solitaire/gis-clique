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
		final Vertices r = new Vertices();
		final Vertices p = new Vertices();
		for (int v = 0; v < n; v++)
		{
			p.add(v);
		}
		final Vertices x = new Vertices();
		if (faster)
		{
			bronKerbosh2(r, p, x);
		}
		else
		{
			bronKerbosh1(r, p, x);
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
	 * @param r Potential clique.
	 * @param p Candidates.
	 * @param x Excluded, already taken as candidates. */
	private void bronKerbosh1(Vertices r, Vertices p, Vertices x)
	{
		if (p.isEmpty() && x.isEmpty() && (maximumClique.size() < r.size()))
		{
			maximumClique = new Vertices(r);
			return;
		}

		final Integer[] candidates = p.toArray(new Integer[0]);
		for (final int v : candidates)
		{
			p.remove(v);

			final Vertices rNew = new Vertices(r);
			rNew.add(v);

			final Vertices pNew = new Vertices(p);
			pNew.retainAll(neighbors[v]);

			final Vertices xNew = new Vertices(x);
			xNew.retainAll(neighbors[v]);

			bronKerbosh1(rNew, pNew, xNew);

			x.add(v);
		}
	}

	/** Bron-Kerbosh version 2.
	 * @param r Potential clique.
	 * @param p Candidates.
	 * @param x Excluded, already taken as candidates. */
	private void bronKerbosh2(Vertices r, Vertices p, Vertices x)
	{
		if (p.isEmpty() && x.isEmpty() && (maximumClique.size() < r.size()))
		{
			maximumClique = new Vertices(r);
			return;
		}

		// Choose pivot. Pivot is a vertex in the (P union X) set with the highest vertex degree.
		int pivot = -1;
		int maxDegree = -1;
		final Vertices union = new Vertices(p);
		union.addAll(x);
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
		final Vertices candidates = new Vertices(p);
		candidates.removeAll(neighbors[pivot]);

		for (final int v : candidates)
		{
			p.remove(v);

			final Vertices rNew = new Vertices(r);
			rNew.add(v);

			final Vertices pNew = new Vertices(p);
			pNew.retainAll(neighbors[v]);

			final Vertices xNew = new Vertices(x);
			xNew.retainAll(neighbors[v]);

			bronKerbosh1(rNew, pNew, xNew);

			x.add(v);
		}
	}
}
