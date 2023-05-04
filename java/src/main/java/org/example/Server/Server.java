package org.example.Server;
import org.example.BusinessObjects.JSONDeserializerMovie;
import org.example.dto.Movie;
import org.example.dao.MovieDaoInterface;
import org.example.dao.MySqlMovieDao;
import org.example.exceptions.DaoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class Server {

	// Instantiate a GsonBuilder and register the TypeAdapter (to adapt types!)
	// passing in the IssPositionAtTime class definition,
	// the name of the deserialization object (containing the deserialize() method)
	//
	private static Gson gsonBuilder = new GsonBuilder()
			.registerTypeAdapter(Movie.class, new JSONDeserializerMovie())
			.serializeNulls()
			.create();
	private static final Scanner keyboard = new Scanner(System.in);
	private static final MovieDaoInterface ImovieDao = new MySqlMovieDao();//"IUserDao" -> "I" stands for for
	public static void main(String[] args)
	{
		Server server = new Server();
		server.start();
	}

	public void start()
	{
		try
		{
			ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

			System.out.println("Server: Server started. Listening for connections on port 8080...");

			int clientNumber = 0;  // a number for clients that the server allocates as clients connect

			while (true)    // loop continuously to accept new client connections
			{
				Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection,
				// and open a new socket to communicate with the client
				clientNumber++;

				System.out.println("Server: Client " + clientNumber + " has connected.");

				System.out.println("Server: Port# of remote client: " + socket.getPort());
				System.out.println("Server: Port# of this server: " + socket.getLocalPort());

				Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
				t.start();                                                  // and run it in its own thread

				System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
				System.out.println("Server: Listening for further connections...");
			}
		} catch (IOException e)
		{
			System.out.println("Server: IOException: " + e);
		}
		System.out.println("Server: Server exiting, Goodbye!");
	}

	public class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
	{
		BufferedReader socketReader;
		PrintWriter socketWriter;
		Socket socket;
		int clientNumber;

		public ClientHandler(Socket clientSocket, int clientNumber)
		{
			try
			{
				InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
				this.socketReader = new BufferedReader(isReader);

				OutputStream os = clientSocket.getOutputStream();
				this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

				this.clientNumber = clientNumber;  // ID number that we are assigning to this client

				this.socket = clientSocket;  // store socket ref for closing

			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}

		@Override
		public void run()
		{
			String message;
			Gson gsonParser = new Gson();
			try
			{
				while ((message = socketReader.readLine()) != null)
				{
					System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);

					if (message.startsWith("1"))
					{
						String movies = ImovieDao.findAllMoviesJson();     // call a method in the DAO
						if( movies != null ) // null returned if userid and password not valid
						{
							socketWriter.println(movies);  // send message to client
						}//sends the movie by id to the client
						else
						{
							String error="error : No movies found!";
							socketWriter.println(error);
						}
					}
					else if (message.startsWith("2"))
					{
						int movieId=Integer.parseInt(message.substring(2));
						String movie = ImovieDao.findMoviesByIdJson(movieId);
						if( movie != null ) // null returned if userid and password not valid
						{
							socketWriter.println(movie);
						}//sends the movie by id to the client
						else
						{
							String error="error : Movie with id : "+movieId+" not found!";
							socketWriter.println(error);
						}
					}
					else if (message.startsWith("3"))
					{
						String movieJson=message.substring(2);
						Movie movie =  gsonBuilder.fromJson(movieJson, new TypeToken<Movie>(){}.getType());
						boolean inserted=ImovieDao.insertMovie(movie);
						if(inserted==true) {
							socketWriter.println("Movie has been added");
						}
						else {
							socketWriter.println("Insert failed!");
						}

					}
					else if (message.startsWith("4"))
					{
						int movieId=Integer.parseInt(message.substring(2));
						boolean deleted=ImovieDao.deleteMovieById(movieId);
						if(deleted==true) {
							socketWriter.println("Deleted movie with id : "+movieId);
						}
						else {
							socketWriter.println("Deleting failed!");
						}

					}
					else
					{
						socketWriter.println("I'm sorry I don't understand :(");
					}
				}

				socket.close();

			} catch (IOException ex)
			{
				ex.printStackTrace();
			} catch (DaoException e) {
				throw new RuntimeException(e);
			}
			System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
		}
	}
}
