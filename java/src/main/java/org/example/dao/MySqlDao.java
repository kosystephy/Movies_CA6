package org.example.dao;

import org.example.constants.DatabaseDetails;
import org.example.exceptions.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao
{
	private final String databaseURL;

	public MySqlDao(String databaseURL)
	{
		this.databaseURL = databaseURL;
	}

	public Connection getConnection() throws DaoException
	{
		String driver = DatabaseDetails.DRIVER;

		String username = DatabaseDetails.DB_USERNAME;
		String password = "";

		Connection connection = null;

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(this.databaseURL, username, password);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Failed to find the driver class " + e.getMessage());
			System.exit(1);
		}
		catch(SQLException e)
		{
			System.out.println("Connection failed " + e.getMessage());
			System.exit(2);
		}

		return connection;
	}

	public void freeConnection(Connection connection) throws DaoException
	{
		try
		{
			if(connection != null)
			{
				connection.close();
				connection = null;
			}
		}
		catch(SQLException e)
		{
			System.out.println("Failed to free the connection " + e.getMessage());
			System.exit(1);
		}
	}
}
