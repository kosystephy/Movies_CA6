package org.example.dto;

//
//import org.example.constants.Colors;
import org.example.core.MenuOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.core.MenuOptions.*;

public class Movie implements Comparable<Movie>, Serializable {


	private int movie_ID;
	private String title;
	private String producer;
	private String release_date;
	private String type;
	private String genre;
	private String duration;
	// A recipe's rating is the average of all ratings given by users (rated out of a 100)
	private double ratings;

	public Movie(int movie_ID, String title, String producer, String release_date, String type, String genre, String duration, double ratings) {
		this.movie_ID = movie_ID;
		this.title = title;
		this.producer = producer;
		this.release_date = release_date;
		this.type = type;
		this.genre = genre;
		this.duration = duration;
		this.ratings = ratings;
	}
	public void printDetails()
	{
		System.out.println( "MovieID: " + this.movie_ID);
		System.out.println( "Title: " + this.title);
		System.out.println( "Instructions: "+ this.producer);
		System.out.println("Rating: "+ this.release_date);
		System.out.println( "Type: " + this.type);
		System.out.println( "Genre: " + this.genre);
		System.out.println( "Duration: " + this.duration);
		System.out.println( "Ratings: " + this.ratings);
	}
	public int getMovie_ID() {
		return movie_ID;
	}

	public void setMovie_ID(int movie_ID) {
		this.movie_ID = movie_ID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public double getRatings() {
		return ratings;
	}

	public void setRatings(double ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"movie_ID=" + movie_ID +
				", title='" + title + '\'' +
				", producer='" + producer + '\'' +
				", release_date='" + release_date + '\'' +
				", type='" + type + '\'' +
				", genre='" + genre + '\'' +
				", duration='" + duration + '\'' +
				", ratings=" + ratings +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Movie movie = (Movie) o;
		return movie_ID == movie.movie_ID &&
				Double.compare(movie.ratings, ratings) == 0
				&& Objects.equals(title, movie.title)
				&& Objects.equals(producer, movie.producer)
				&& Objects.equals(release_date, movie.release_date)
				&& Objects.equals(type, movie.type)
				&& Objects.equals(genre, movie.genre)
				&& Objects.equals(duration, movie.duration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(movie_ID, title, producer, release_date, type, genre, duration, ratings);
	}

	@Override
	public int compareTo(Movie m) {
		return this.getTitle().compareToIgnoreCase(m.getTitle());
	}
}
