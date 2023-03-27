package org.example.constants;

import org.example.dto.Movie;



public class DatabaseDetails
{
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_USERNAME = "root";
	public static final String MAIN_DB_URL = "jdbc:mysql://localhost:3306/movies_db"; // To be used in the actual project
	public static final String TEST_DB_URL = "jdbc:mysql://localhost:3306/movies_test_db"; // Only to be used for JUnit tests

//	public static final Movie MOVIE_TEST_DATA = new Movie((
//
//					new Movie(1, "Avatar", "Joy Thomas", "2009-12-10","movie","science fiction","01:58:12",3.3),
//
//					new Movie(2, "Titanic", "Kate Grant", "1997-11-18","movie","romance","02:01:45",4.5),
//
//
//			new Movie	(3, "The Avengers","Gavin Doyle", "2012-04-25","series","super-hero","02:30:12",3.8),
//			new Movie (4,"Jurassic World", "Susan Grey", "2015-06-09","movie","action","01:55:10",4.0),
//			new Movie (5, "Furious 7", "William McCourt", "2015-04-01","series","action/thriller","02:10:22",4.5),
//			new Movie	(6, "Avengers: Age of Ultron", "Ronald White", "2015-04-22","movie","super-hero","02:20:12",3.9),
//			new Movie	(7, "Frozen", "Faith Hope", "2013-11-27","movie","fantasy","01:49:58",4.6),
//					new Movie(8, "Iron Man 3", "Hofner Icon", "2013-04-18","movie","super-hero","02:32:15",4.3),
//			new Movie (9, "Minions", "John Lurthwig", "2015-06-17","movie","comedy","01:41:12",4.0),
//			new Movie (10, "Grey Anatomy","Sarah Garaham", "2016-04-27","series","drama","00:48:15",4.5),
//			new Movie	(11, "The Vikings", "Cian Malik", "2011-06-28","series","historical drama","1:00:12",4.6),
//			new Movie (12, "Squid game", "Kim Lee Jung", "2003-12-01","series","drama","59:20:15",3.8),
//			new Movie (13, "Alice in Wonderland", "Faith Hope", "2012-10-25","movie","adventure","01:15:12",4.2),
//			new Movie (14, "Toy Story 3", "John Lurthwig", "2014-06-25","movie","comedy","02:01:12",4.4),
//			new Movie(15, "Transformers: Age of Extinction","William McCourt", "2012-07-16","movie","science-fiction","02:03:12",4.1));
//


}