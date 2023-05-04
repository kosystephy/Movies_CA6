package org.example.BusinessObjects;

import org.example.dto.Movie;

import java.util.Comparator;

public class MovieRatingComparator implements Comparator<Movie> {

	int num;
	@Override
	public int compare(Movie m1, Movie m2) {
		if(m1.getRatings() <m2.getRatings()) {
			num = -1;
		}
		else if (m1.getRatings() ==  m2.getRatings()) {
			num = 0;
		} else {
			num = 1;
		}
		return num;
	}

}
