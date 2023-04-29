package org.example.dao;


import org.example.IFilter;
import org.example.dto.Movie;

import org.example.exceptions.DaoException;
//import com.google.gson.Gson;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlMovieDao extends MySqlDao implements MovieDaoInterface
{

	@Override
	public List<Movie> findAllMovie() throws DaoException
	{

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		List<Movie> movieList = new ArrayList<>();
			try
		{
				 connection = this.getConnection();
			String getMoviesQuery = "SELECT * FROM movie";
			 ps = connection.prepareStatement(getMoviesQuery);
			 rs = ps.executeQuery();

				while(rs.next())
				{
					int movieID=rs.getInt("MovieID");
					String title = rs.getString("Title");
									String producer  = rs.getString("Producer");
					String release_date = rs.getString("Release_Date");
					String type = rs.getString("Type");
					String genre = rs.getString("Genre");
					String duration = rs.getString("Duration");
					double ratings = rs.getDouble("Ratings");

//					List<String> ingredients = getIngredientSet(movie_ID);

					movieList.add(new Movie(movieID, title,producer, release_date, type,genre,duration, ratings));
				}

		}
		catch (SQLException e)
		{
			throw new DaoException("findAllMovie() " + e.getMessage());
		}finally
			{
				try
				{
					if (rs != null)
					{
						rs.close();
					}
					if (ps != null)
					{
						ps.close();
					}
					if (connection != null)
					{
						freeConnection(connection);
					}
				} catch (SQLException e)
				{
					throw new DaoException("findAllMovie() " + e.getMessage());
				}
			}

		return movieList;
	}



	public Movie findMovieByID(int movieID) throws DaoException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Movie movie = null;


		try
		{
				 connection = this.getConnection();
			String getMovieQuery = "SELECT * FROM movie WHERE movieID = ?";
			preparedStatement = connection.prepareStatement(getMovieQuery);
			preparedStatement.setInt(1, movieID);


			 rs = preparedStatement.executeQuery();
							if(rs.next())
				{
					movieID=rs.getInt("MovieID");
					String title = rs.getString("Title");
					String producer  = rs.getString("Producer");
					String release_date = rs.getString("Release_Date");
					String type = rs.getString("Type");
					String genre = rs.getString("Genre");
					String duration = rs.getString("Duration");
					double ratings = rs.getDouble("Ratings");

//					List<String> ingredients = getIngredientSet(movieID);

					movie = new Movie(movieID, title,producer, release_date, type,genre,duration, ratings);
				}
			}

		catch(SQLException e)
		{
			throw new DaoException("findMovieById() " + e.getMessage());
		}finally {
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (preparedStatement != null)
				{
					preparedStatement.close();
				}
				if (connection != null)
				{
					freeConnection(connection);
				}
			} catch (SQLException e)
			{
				throw new DaoException("findMovieById() " + e.getMessage());
			}
		}

		return movie;
	}



	@Override
	public boolean deleteMovieById(int movieID) throws DaoException
	{
		Connection connection = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = this.getConnection();

			String getDeleteQuery = "DELETE FROM movie WHERE movieID = ?";

			ps = connection.prepareStatement(getDeleteQuery);
			ps.setInt(1, movieID);

			ps.executeUpdate();

		}


		catch(SQLException e)
		{
			throw new DaoException("deleteMovieById() " + e.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(ps != null)
				{
					ps.close();
				}
				if(connection != null)
				{
					this.freeConnection(connection);
				}
			}
			catch(SQLException e)
			{
				throw new DaoException("findAllMovies() " + e.getMessage());
			}
		}

		return true;
	}


	public boolean insertMovie(Movie movie) throws DaoException
	{
		int movieID= movie.getMovie_ID();
		String title = movie.getTitle();
		String producer = movie.getProducer();
		String release_Date = movie.getRelease_date();
		String type = movie.getType();
		String genre = movie.getGenre();
		String duration = movie.getDuration();
		double ratings = movie.getRatings();


		// Check if movie name already exists.
		boolean movieExists = checkMovieExists(movie.getTitle());
		if(movieExists)
		{
			System.out.println("\nInsertion error- A movie with the name " + movie.getTitle() + " already exists in the system.");
			return false;
		}
		// If it doesn't exist, proceed with method.

		// Add a new record in the movie table
		insertMovieTableRecord(movieID,title,producer,release_Date,type,genre,duration, ratings);



		return true;
	}


	public boolean checkMovieExists(String title) throws DaoException
	{
		String checkMovieExistsQuery = "SELECT * FROM movie WHERE title = ?";

		try(Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(checkMovieExistsQuery))
		{
			ps.setString(1, title);

			try(ResultSet rs = ps.executeQuery())
			{
				// If there are no elements in the ResultSet, it means the movies does not exist.
				return rs.next();
			}

		}
		catch (SQLException e)
		{
			throw new DaoException("checkMovieExists() " + e.getMessage());
		}
	}


	public void insertMovieTableRecord(int movie_ID, String title, String producer, String release_date, String type, String genre, String duration, double ratings) throws DaoException
	{
		String insertMovieQuery = "INSERT INTO movie VALUES(?, ?, ?, ?,?,?,?,?)";

		try(Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(insertMovieQuery))
		{
			ps.setInt(1, movie_ID);
					ps.setString(2, title);
			ps.setString(3, producer);
			ps.setString(4, release_date);
			ps.setString(5, type);
			ps.setString(6, genre);
			ps.setString(7, duration);
			ps.setDouble(8, ratings);
			ps.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DaoException("insertMovieTableRecord() " + e.getMessage());
		}
	}

	public List<Movie> filterMovies(IFilter filter) throws DaoException
	{
		List<Movie> filteredList = new ArrayList<>();

		try
		{
			List<Movie> allMovie = findAllMovie();
			for(Movie movie : allMovie)
			{
				if(filter.matches(movie))
				{
					filteredList.add(movie);
				}
			}
		}
		catch (DaoException daoe)
		{
			System.out.println("filterArtists() " + daoe.getMessage());
		}

		return filteredList;
	}

}
