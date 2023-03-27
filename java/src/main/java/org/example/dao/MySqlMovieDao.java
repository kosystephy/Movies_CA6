package org.example.dao;


//import org.example.IFilter;
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

	public MySqlMovieDao(String databaseURL) {
		super(databaseURL);
	}


	/**
	 * Gets all recipes that are currently stored in the database.
	 *
	 * @return A List of all the recipes in the database
	 * @throws DaoException Extends SQLException
	 */
	@Override
	public List<Movie> findAllMovie() throws DaoException
	{
		List<Movie> movie = new ArrayList<>();

		String getMoviesQuery = "SELECT * FROM movie";

		try(Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(getMoviesQuery))
		{
			try(ResultSet rs = ps.executeQuery())
			{
				while(rs.next())
				{
					int movieID=rs.getInt("Movie_ID");
					String title = rs.getString("Title");
									String producer  = rs.getString("Producer");
					String release_date = rs.getString("Release Date");
					String type = rs.getString("Type");
					String genre = rs.getString("Genre");
					String duration = rs.getString("Duration");
					double ratings = rs.getDouble("Ratings");

//					List<String> ingredients = getIngredientSet(movie_ID);

					movie.add(new Movie(movieID, title,producer, release_date, type,genre,duration, ratings));
				}
			}
		}
		catch (SQLException e)
		{
			throw new DaoException("findAllMovies() " + e.getMessage());
		}

		return movie;
	}



	public Movie findMovieByID(int movieID) throws DaoException
	{
		Movie movie = null;
		String getMovieQuery = "SELECT * FROM movie WHERE movieID = ?";

		try(Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(getMovieQuery))
		{
			ps.setInt(1, movieID);

			try(ResultSet rs = ps.executeQuery())
			{
				while(rs.next())
				{
					int ID=rs.getInt("Movie_ID");
					String title = rs.getString("Title");
					String producer  = rs.getString("Producer");
					String release_date = rs.getString("Release Date");
					String type = rs.getString("Type");
					String genre = rs.getString("Genre");
					String duration = rs.getString("Duration");
					double ratings = rs.getDouble("Ratings");

//					List<String> ingredients = getIngredientSet(movie_ID);

					movie = new Movie(movieID, title,producer, release_date, type,genre,duration, ratings);
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("findMovieById() " + e.getMessage());
		}

		return movie;
	}



	@Override
	public boolean deleteMovieById(int movieID) throws DaoException
	{
		Connection connection = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			connection = this.getConnection();

			String checkRecipeExistsQuery = "SELECT * FROM recipe WHERE movieID = ?";
			ps = connection.prepareStatement(checkRecipeExistsQuery);
			ps.setInt(1, movieID);

			rs = ps.executeQuery();

			// An empty result set means there are no rows in the database with the specified recipe name.
			if(!rs.next())
			{
				return false;
			}

			// Before deleting the recipe from the recipe table, we must delete its child records
			// from the recipe_ingredient table

//			String deleteChildRecordsQuery = "DELETE FROM movies WHERE movieID = ?";
//			ps = con.prepareStatement(deleteChildRecordsQuery);
//			ps.setInt(1, movieID);
//			ps.executeUpdate();

			// Now, we can delete the parent row from the recipe table
			String deleteMovieQuery = "DELETE FROM movie WHERE movieID = ?";
			ps = connection.prepareStatement(deleteMovieQuery);
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

//	/**
//	 * Adds a new recipe to the database, along with the ingredients associated with it.
//	 * Before adding new records to the recipe table, it checks if a recipe with the same name already exists.
//	 * It does the same before adding the ingredients, as there is no need to store duplicates of ingredients.
//	 * It also adds rows in the recipe_ingredient table, connecting the recipe to the ingredients.
//	 *
//	 * @param recipe A Recipe Object, the details of which we are entering into the database.
//	 * @return true if the insertion is successful, otherwise false(when a recipe with the same name already exists.)
//	 * @throws DaoException Extends SQLException
//	 */

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


		// Check if recipe name already exists.
		boolean movieExists = checkMovieExists(movie.getTitle());
		if(movieExists)
		{
			System.out.println("\nInsertion error- A recipe with the name " + movie.getTitle() + " already exists in the system.");
			return false;
		}
		// If it doesn't exist, proceed with method.

		// Add a new record in the recipe table
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
				// If there are no elements in the ResultSet, it means the recipe does not exist.
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
		String insertRecipeQuery = "INSERT INTO movie VALUES(?, ?, ?, ?,?,?,?,?)";

		try(Connection con = this.getConnection();
			PreparedStatement ps = con.prepareStatement(insertRecipeQuery))
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

}
