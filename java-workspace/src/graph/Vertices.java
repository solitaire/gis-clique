package graph;

import java.util.Iterator;
import java.util.TreeSet;

public class Vertices extends TreeSet<Integer>
{
	private static final long serialVersionUID = 1L;

	public Vertices()
	{
		super();
	}

	/** Copy constructor. */
	public Vertices(Vertices v)
	{
		super(v);
	}

	@Override
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		final Iterator<Integer> it = iterator();
		if (it.hasNext())
		{
			result.append(it.next());
		}
		while (it.hasNext())
		{
			result.append(" ");
			result.append(it.next());
		}
		return result.toString();
	}
}
