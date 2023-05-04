package org.example.Client;
import org.example.BusinessObjects.JSONDeserializerMovie;
import org.example.dto.Movie;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Client {
	private static Gson gsonBuilder = new GsonBuilder()
			.registerTypeAdapter(Movie.class, new JSONDeserializerMovie())
			.serializeNulls()
			.create();
	public static void main(String[] args)
	{
		Client client = new Client();
		client.start();
	}

	public void start()
	{
		Gson gsonParser = new Gson();
		Scanner in = new Scanner(System.in);
		try {
			Socket socket = new Socket("localhost", 8080);  // connect to server socket
			System.out.println("Client: Port# of this client : " + socket.getLocalPort());
			System.out.println("Client: Port# of Server :" + socket.getPort() );

			System.out.println("Client message: The Client is running and has connected to the server");

			boolean continueRunning = true;
			while(continueRunning) {
				printMenu();
				String command = in.nextLine();

				OutputStream os = socket.getOutputStream();
				PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers

//                socketWriter.println(command);
				if (command.startsWith("1"))   //we expect the server to return a time
				{

					socketWriter.println(command);
				} else if (command.startsWith("2")) {
					System.out.println("Please enter the Id of the Movie : ");
					int movieId = in.nextInt();
					socketWriter.println(command + " " + movieId);
					in.nextLine();
				} else if (command.startsWith("3")) {
					System.out.println("Please enter the details of the Movie:");
					System.out.println("Movie Id:");
					int movieId = in.nextInt();
					System.out.println("Title:");
					String title = in.nextLine();
					System.out.println("Producer:");
					String producer = in.nextLine();
					System.out.println("Release_Date:");
					String release_date = in.nextLine();
					System.out.println("Type:");
					String type = in.nextLine();
					System.out.println("Genre:");
					String genre = in.nextLine();
					System.out.println("Duration:");
					String duration = in.nextLine();
					System.out.println("Rating out of 5:");
					double ratings = in.nextDouble();

					Movie a = new Movie(movieId, title,producer, release_date, type,genre,duration, ratings);
					String movieJson = gsonParser.toJson(a);
					socketWriter.println(command + " " + movieJson);
					in.nextLine();
				}
				if (command.startsWith("4"))
				{
					System.out.println("Please enter the Id of the Movie you wish  to delete : ");
					int movieId = in.nextInt();
					socketWriter.println(command + " " + movieId);
					in.nextLine();
				}


				Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply


				if (command.startsWith("1"))   //we expect the server to return a time
				{
					String movieStringJson = socketReader.nextLine();
					if (movieStringJson.startsWith("error")) {
						System.out.println(movieStringJson);
						System.out.println("Client message: Displaying All Movies: ");

						Movie[] movies = gsonBuilder.fromJson(movieStringJson, Movie[].class);
						List<Movie> movieList = new ArrayList<>(Arrays.asList(movies));
						for (Movie movie : movieList) {
							System.out.println(movie);
						}

					}
				} else if (command.startsWith("2")) {

					String movieByIdJson = socketReader.nextLine();

					if (movieByIdJson.startsWith("error")) {
						System.out.println(movieByIdJson);
					} else {

						Movie movie = gsonBuilder.fromJson(movieByIdJson, new TypeToken<Movie>() {
						}.getType());
						System.out.println("Client message: Displaying Movie By ID: " + movie);
					}
				} else if (command.startsWith("3")) {
					String message = socketReader.nextLine();
					System.out.println("Client message: " + message);
				} else if (command.startsWith("4")) {
					String message = socketReader.nextLine();
					System.out.println("Client message: " + message);
				}
				else if (command.startsWith("0")) {
					System.out.println("Client message: Exiting..");
					socketWriter.close();
					socketReader.close();
					socket.close();
					continueRunning=false;
				}
				else                            // the user has entered the Echo command or an invalid command
				{
					String input = socketReader.nextLine();
					System.out.println("Client message: Response from server: \"" + input + "\"");
				}

			}

		} catch (IOException e) {
			System.out.println("Client message: IOException: "+e);
		}

	}
	private static void printMenu() {
		System.out.println("Menu Options");
		System.out.println("Please select an option");
		System.out.println("1-Display all movies");
		System.out.println("2-Find movies by Id");
		System.out.println("3-Insert Movies");
		System.out.println("4-Delete Movies");
	}

}
