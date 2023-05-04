package org.example.dao;

import org.example.BusinessObjects.IFilter;
import  org.example.exceptions.DaoException;
import org.example.dto.Movie;

import java.util.List;

public interface MovieDaoInterface
{
	// Features
	public List<Movie> findAllMovie() throws DaoException;
	public Movie findMovieByID(int movieID) throws DaoException;
	public boolean deleteMovieById(int movieID) throws DaoException;
	public void insertMovieTableRecord(int movie_ID, String title, String producer, String release_date, String type, String genre, String duration, double ratings) throws DaoException;
	public boolean checkMovieExists(String title) throws DaoException;
	public boolean insertMovie(Movie movie) throws DaoException;
	public	List<Movie> filterMovies(IFilter filter) throws DaoException;
	public String findAllMoviesJson() throws DaoException;
	public String findMoviesByIdJson(int movieId) throws DaoException;


}