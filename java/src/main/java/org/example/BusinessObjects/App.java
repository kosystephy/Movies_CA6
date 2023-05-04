package org.example.BusinessObjects;



import org.example.dao.MySqlMovieDao;
import org.example.dao.MovieDaoInterface;
import org.example.dto.Movie;
import org.example.exceptions.DaoException;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class App {
	private static final Scanner keyboard = new Scanner(System.in);
	private static final MovieDaoInterface ImovieDao = new MySqlMovieDao();

	public static void main(String[] args) {
//		MovieDaoInterface ImovieDao = new MySqlMovieDao();  //"IUserDao" -> "I" stands for for

		App app = new App();
		app.start();
		Movie movie;
	}

	public void start() {

		MovieDaoInterface ImovieDao = new MySqlMovieDao();

		try {
			System.out.println("Input an option to perform an action");
			int select = -1;
			do {
				System.out.println("1-Find all movies");
				System.out.println("2-Find movies by Id");
				System.out.println("3-Delete movies by Id");
				System.out.println("4-Insert movies");
				System.out.println("5-Filter movies");
				boolean exit = false;
				System.out.println("Enter your selection");
				select = keyboard.nextInt();
				switch (select) {
					case 0:

						System.out.println("Movies"); //ending of program
						break;
					case 1:
						findMovie();
						break;
					case 2:
						findMovieById();
						break;
					case 3:
						deletedMovieById();
						break;
					case 4:
						insertMovie();
						break;
					case 5:
						filterMovies();
						break;
					default:
						System.out.println("Invalid selection.Please make another Selection");
				}
			}

			while (select != 0);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	private static void printFilterMenuInstructions() {

		System.out.println("Filter Option");
		System.out.println("Please Select an option");
		System.out.println("0. Exit");
		System.out.println("1. Filter Movies By Duration");
		System.out.println("2. Filter Movies By Genre");
		System.out.println(" 3. Filter movies By Rating");


	}
		public static void findMovie () throws DaoException {
			MovieDaoInterface ImovieDao = new MySqlMovieDao();
			System.out.println("\nCall findAllMovies()");
			List<Movie> movie = ImovieDao.findAllMovie();

			if (movie.isEmpty())
				System.out.println("There are no Movies");
			else {
				for (Movie movies : movie)
					System.out.println("Movie: " + movies.toString());
			}

		}
		public void findMovieById ()  throws DaoException{
			MovieDaoInterface ImovieDao = new MySqlMovieDao();
				System.out.println("Enter the movie Id you wish to Find");
				int movieID = keyboard.nextInt();

				Movie movie2 = ImovieDao.findMovieByID(movieID);

				if (movie2 != null) // null returned if userid and password not valid
					System.out.println("Movie found: " + movie2);
				else
					System.out.println("Movie with that id was not found");

				// test dao - with an invalid username (i.e. not in database)

			}


		public  void  deletedMovieById() throws DaoException{
			MovieDaoInterface ImovieDao = new MySqlMovieDao();

				System.out.println("Enter the movie Id you wish to Delete");
				int movieID = keyboard.nextInt();

				boolean movie2 = ImovieDao.deleteMovieById(movieID);
				if(movie2==true)
					System.out.println("Movie has sucessfully been deleted");
                  else
	System.out.println("Movie was not deleted as it does not exit");

		}
		public void insertMovie () throws DaoException{
			MovieDaoInterface ImovieDao = new MySqlMovieDao();


				System.out.println("Please enter the details you wish to insert");
			System.out.println("MovieID");
			int movieID=keyboard.nextInt();
				System.out.println("Title");
				String title=keyboard.nextLine();
				System.out.println("Producer:");
			String producer=keyboard.nextLine();
				System.out.println("Release_date:");
			String release_date=keyboard.nextLine();
				System.out.println("Type:");
			String type=keyboard.nextLine();
				System.out.println("Genre:");
			String genre=keyboard.nextLine();
				System.out.println("Duration:");
			String duration=keyboard.nextLine();
				System.out.println("Ratings:");
			double ratings=keyboard.nextDouble();

				Movie movie1 = new Movie(movieID,title,producer,release_date,type,genre,duration,ratings);
				boolean movie3 = ImovieDao.insertMovie(movie1);     // call a method in the DAO

				if (movie3==false)
					System.out.println("There are no Movies");
				else {

						System.out.println("the movie1 was added: " + movie1.toString());
				}

			}

	private  void filterMovies()
	{
		List<Movie> filteredMovies = new ArrayList<>();

		boolean  valid = false;

		while(!valid)
		{
			printFilterMenuInstructions();
			int choice = keyboard.nextInt();

			switch (choice)
			{
				case 1:
					filteredMovies= filterByDuartion();
					printFilteredList(filteredMovies);
					break;
				case 2:
					filteredMovies = filterByGenre();
					printFilteredList(filteredMovies);
					break;
				case 3:
					filteredMovies = filterByRating();
					printFilteredList(filteredMovies);
					break;

				case 0:
					System.out.println("Thanks for filtering");
					valid = true;
					break;
				default:
					System.out.println("Invalid input. Try again.");
					break;
			}

		}

	}
	private static List<Movie> filterByDuartion()
	{
		keyboard.nextLine();
		System.out.println("Enter Duaration: ");
		String duration=keyboard.nextLine();

		List<Movie> filteredMovies = null;

		try
		{
			filteredMovies = ImovieDao.filterMovies(new FilterMovieByDuration(duration));

		}
		catch(DaoException e)
		{
			System.out.println("filterByName() " + e.getMessage());
		}
		return filteredMovies;
	}

	private static List<Movie> filterByGenre()
	{
		keyboard.nextLine();
		System.out.println("\nEnter Genre: ");
		String genre=keyboard.nextLine();

		List<Movie> filteredMovies = null;

		try
		{
			filteredMovies = ImovieDao.filterMovies(new FilterMovieByGenre(genre));

		}
		catch(DaoException e)
		{
			System.out.println("filterByGenre() " + e.getMessage());
		}

		return filteredMovies;
	}

	private static List<Movie> filterByRating()
	{
		keyboard.nextLine();
		System.out.println("Enter  rating(1-5): ");
		double rating =keyboard.nextDouble();
		System.out.println("\nEnter maximum rating(" + rating + " - 5): ");
		double maxRating =keyboard.nextDouble();


		List<Movie> filteredMovies = null;

		try
		{
			filteredMovies = ImovieDao.filterMovies(new FilterMovieByRating(rating,maxRating));
			MovieRatingComparator movieRatingComparator=new MovieRatingComparator();
			Collections.sort(filteredMovies,movieRatingComparator);
		}
		catch(DaoException e)
		{
			System.out.println("filterByRating() " + e.getMessage());
		}

		return filteredMovies;
	}


	private static void printFilteredList(List<Movie> movieList) {
		if(movieList.isEmpty()){
			System.out.println("Wrong input please check again.");
		}
		else {
			System.out.println("Displaying filtered movies: ");
			for (Movie movie : movieList) {
				System.out.println(movie);
				System.out.println();
			}
		}
	}
		}


