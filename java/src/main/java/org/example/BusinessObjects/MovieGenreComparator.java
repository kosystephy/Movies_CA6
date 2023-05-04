package org.example.BusinessObjects;

import org.example.dto.Movie;

import java.util.Comparator;

public class MovieGenreComparator implements Comparator<Movie> {
	@Override
	public int compare(Movie m1, Movie m2) {
		return m2.getGenre() .compareToIgnoreCase( m1.getGenre());
	}
}
