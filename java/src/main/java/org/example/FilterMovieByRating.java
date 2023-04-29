package org.example;
import org.example.dto.Movie;
public class FilterMovieByRating implements IFilter {
	private double rating,maxRating;

	public FilterMovieByRating(double rating,double maxRating)
	{
		this.rating = rating;
		this.maxRating = maxRating;

	}

	@Override
	public boolean matches(Object object)
	{
		Movie movie = (Movie) object;

		return movie.getRatings() >= rating && movie.getRatings() <= maxRating;
	}
}
