package org.example;
import java.util.HashSet;

public class MovieCache {
	private HashSet<Integer> movieIds;

	public MovieCache(){
		this.movieIds=new HashSet<>();
	}
	public boolean contains (int movieId)
	{
				return movieIds.contains(movieId);
			}
			public void add(int movieId)
			{
				movieIds.add(movieId);
			}
			public void remove(int movieId)
			{
				movieIds.remove(movieId);
			}
}
