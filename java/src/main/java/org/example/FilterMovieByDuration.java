package org.example;
import org.example.dto.Movie;
public class FilterMovieByDuration implements IFilter  {
	private String duration;

	public FilterMovieByDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public boolean matches(Object other) {
		Movie movie = (Movie) other;
		return movie.getDuration().equalsIgnoreCase(duration);
	}
}
