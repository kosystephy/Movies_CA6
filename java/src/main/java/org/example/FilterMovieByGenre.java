package org.example;
import org.example.dto.Movie;
public class FilterMovieByGenre implements IFilter{
	private String genre;

	public FilterMovieByGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public boolean matches(Object other) {
		Movie movie = (Movie) other;
		return movie.getGenre().equalsIgnoreCase(genre);
	}
}
