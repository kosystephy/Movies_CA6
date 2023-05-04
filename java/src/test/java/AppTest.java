
import org.example.BusinessObjects.FilterMovieByDuration;
import org.example.BusinessObjects.FilterMovieByGenre;
import org.example.BusinessObjects.FilterMovieByRating;
import org.example.BusinessObjects.IFilter;
import org.example.dto.Movie;
import org.example.dao.MovieDaoInterface;
import org.example.exceptions.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class AppTest {

	private static MovieDaoInterface daoMock;

	@Before
	public void setUp() throws Exception {
		daoMock=mock(MovieDaoInterface.class);

	}

	@After
	public void tearDown() throws Exception {

		daoMock=null;
	}

	@Test
	public void testFindAllMovie() throws DaoException {
		List<Movie> expectedMovies = new ArrayList<>();
		expectedMovies.add(new Movie(1, "Movie1", "Producer1", "2009-12-10","type1","Genre1", "01:58:12", 4.5));
		expectedMovies.add(new Movie(2, "Movie2", "Producer2",  "2010-11-11","type2","Genre2","02:50:12", 4.0));
		expectedMovies.add(new Movie(3, "Movie3", "Producer3", "2013-04-05", "type3","Genre3", "01:58:00", 3.5));

		// Mock the DAO's findAllArtists() method to return the expected artists
		when(daoMock.findAllMovie()).thenReturn(expectedMovies);

		List<Movie> actualMovies = daoMock.findAllMovie();
//        String actual = "";
//        for (Artist a : actualArtists) {
//            actual +=a.toString() + "\n";
//        }
//        String expected = "Artist{id=1, name='Artist1', country='Country1', genre='Genre1', active_since=2000, biography='Biography1', rating=4.5}\n" +
//                          "Artist{id=2, name='Artist2', country='Country2', genre='Genre2', active_since=1995, biography='Biography2', rating=4.0}\n" +
//                          "Artist{id=3, name='Artist3', country='Country3', genre='Genre3', active_since=2010, biography='Biography3', rating=3.5}\n" +
//                           "";

		assertEquals(expectedMovies,actualMovies);
	}
	@Test
	public void testFindMovieById() throws DaoException {
		List<Movie> expectedMovies = new ArrayList<>();
		expectedMovies.add(new Movie(1, "Movie1", "Producer1", "2009-12-10","type1","Genre1", "01:58:12", 4.5));
		expectedMovies.add(new Movie(2, "Movie2", "Producer2",  "2010-11-11","type2","Genre2","02:50:12", 4.0));
		expectedMovies.add(new Movie(3, "Movie3", "Producer3", "2013-04-05", "type3","Genre3", "01:58:00", 3.5));

		// Mock the DAO's findAllArtists() method to return the expected artists
		when(daoMock.findMovieByID(1)).thenReturn(expectedMovies.get(0));

		Movie actualMovie = daoMock.findMovieByID(1);
		String actual =actualMovie.toString();

		String expected = "Movie{movie_ID=1, title='Title1', producer='Producer1', release_date='2009-12-10',type='type1',genre='genre1', duration='01:58:12', rating=4.5}";

		assertEquals(expected,actual);
	}
	@Test
	public void testDeleteMovieById() throws DaoException {
		int movieID=1;
		boolean expectedResult=true;
		// Mock the DAO's deleteArtistById() method to return the expected result
		when(daoMock.deleteMovieById(movieID)).thenReturn(expectedResult);

		boolean actualResult = daoMock.deleteMovieById(movieID);

		assertEquals(expectedResult, actualResult);
	}
	@Test
	public void testInsertMovie() throws DaoException {
		Movie movie = new Movie(1, "Movie1", "Producer1", "2009-12-10","type1","Genre1", "01:58:12", 4.5);
		when(daoMock.insertMovie(movie)).thenReturn(true);

		boolean inserted = daoMock.insertMovie(movie);

		assertTrue(inserted);
	}


	@Test
	public void testFilterMovieByGenre() throws DaoException {
		IFilter filter=new FilterMovieByGenre("Genre1");
		List<Movie> moviesExpected = new ArrayList<>();
		moviesExpected.add(new Movie(1, "Movie1", "Producer1", "2009-12-10","type1","Genre1", "01:58:12" ,4.5));

		when(daoMock.filterMovies(filter)).thenReturn(moviesExpected);
		List<Movie> moviesActual=daoMock.filterMovies(filter);

		assertEquals(moviesExpected,moviesActual);

	}
	@Test
	public void testFilterMoviesByRating() throws DaoException {
		IFilter filter=new FilterMovieByRating(3.5,4.5);
		List<Movie> expectedMovies = new ArrayList<>();
		expectedMovies.add(new Movie(1, "Movie1", "Producer1", "2009-12-10","type1","Genre1", "01:58:12", 4.5));
		expectedMovies.add(new Movie(2, "Movie2", "Producer2",  "2010-11-11","type2","Genre2","02:50:12", 4.0));
		expectedMovies.add(new Movie(3, "Movie3", "Producer3", "2013-04-05", "type3","Genre3", "01:58:00", 3.5));
		when(daoMock.filterMovies(filter)).thenReturn(expectedMovies);
		List<Movie> moviesActual=daoMock.filterMovies(filter);

		assertEquals(expectedMovies,moviesActual);

	}
	@Test
	public void testFilterMovieByDuration() throws DaoException {
		IFilter filter=new FilterMovieByDuration("01:58:12");
		List<Movie> expectedMovies = new ArrayList<>();
		expectedMovies.add(new Movie(1, "Movie1", "Producer1", "2009-12-10","type1","Genre1", "01:58:12" ,4.5));
		expectedMovies.add(new Movie(2, "Movie2", "Producer2",  "2010-11-11","type2","Genre2","02:50:12", 4.0));
		expectedMovies.add(new Movie(3, "Movie3", "Producer3", "2013-04-05", "type3","Genre3", "01:58:00", 3.5));
		when(daoMock.filterMovies(filter)).thenReturn(expectedMovies);
		List<Movie> moviesActual=daoMock.filterMovies(filter);

		assertEquals(expectedMovies,moviesActual);

	}
	@Test
	public void testFindAllMoviesJson() throws DaoException {
		String expected = "Movie{movie_ID=1, title='Title1', producer='Producer1', release_date='2009-12-10',type='type1',genre='genre1', duration='01:58:12', rating=4.5}"+
				"Movie{movie_ID=2, title='Title2', producer='Producer2', release_date='2010-11-11',type='type2',genre='genre2', duration='02:50:12', rating=4.0}" +
				"Movie{movie_ID=3, title='Title3', producer='Producer3', release_date='2013-04-05',type='type3',genre='genre3', duration='01:58:00', rating=3.5}" +
				"";
		// Mock the DAO's findArtistById() method to return the example Artist object
		when(daoMock.findAllMoviesJson()).thenReturn(expected);

		// Call the DAO's findArtistByIdJson() method with the ID of the example Artist object
		String json = daoMock.findAllMoviesJson();

		// Assert that the deserialized Artist object matches the example Artist object
		assertEquals(expected,json);
	}
	@Test
	public void testFindMovieByIdJson() throws DaoException {
		// Create an example Artist object
		String expected = "Movie{movie_ID=1, title='Title1', producer='Producer1', release_date='2009-12-10',type='type1',genre='genre1', duration='01:58:12', rating=4.5}";
		// Mock the DAO's findArtistById() method to return the example Artist object
		when(daoMock.findMoviesByIdJson(1)).thenReturn(String.valueOf(expected));

		// Call the DAO's findArtistByIdJson() method with the ID of the example Artist object
		String json = daoMock.findMoviesByIdJson(1);

		// Assert that the deserialized Artist object matches the example Artist object
		assertEquals(expected,json);


	}}