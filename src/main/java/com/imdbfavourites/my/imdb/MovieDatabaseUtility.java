package com.imdbfavourites.my.imdb;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.imdbfavourites.my.imdb.util.DatabaseException;
import com.imdbfavourites.my.imdb.util.DatabaseUtility;

public class MovieDatabaseUtility {

	public static void addNewMovie(LocalMovieObject movie) {
		int movieID = addMovieIntoDB(movie);
		addWritersIntoDB(movieID, movie);
		addGenreIntoDB(movieID, movie);
	}

	private static int addMovieIntoDB(LocalMovieObject movie) {
		int result = -1;
		try {
			System.out.println(movie.getIMDbID());
			result = DatabaseUtility
					.getInstance()
					.insertRecordIntoDB(
							"movies",
							"imdbID, movie_title, production_year, imdb_rating, plot, director",
							"'" + movie.getIMDbID() + "', " + "'"
									+ movie.getTitle().replace("'", "") + "', " + "'"
									+ movie.getYear() + "', " + "'"
									+ movie.getImdbRating() + "', " + "'"
									+ movie.getPlot().replace("'", "") + "', " + "'"
									+ movie.getDirector().replace("'", "") + "'");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static void addGenreIntoDB(int movieID, LocalMovieObject movie) {
		try {
			for (String genre : movie.getGenres()) {
				ResultSet result = DatabaseUtility.getInstance()
						.searchDatabaseTable("movie_genres", "name", genre);
				if (result.next()) {
					DatabaseUtility.getInstance().insertRecordIntoDB(
							"genre",
							"movie_id, genre_id",
							"'" + movieID + "', " + "'" + result.getString(1)
									+ "'");
				}
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void addWritersIntoDB(int movieID, LocalMovieObject movie) {
		try {
			for (String writer : movie.getWriters())
				DatabaseUtility.getInstance().insertRecordIntoDB(
						"movie_writers", "movie_id, writer",
						"'" + movieID + "', " + "'" + writer.replace("'", "") + "'");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static List<LocalMovieObject> getAllMoviesFromDatabase() {
		List<LocalMovieObject> existingMovies = new ArrayList<LocalMovieObject>();
		List<String> movieWriters = null;
		List<String> movieGenres = null;
		try {
			ResultSet moviesTableResult = DatabaseUtility.getInstance()
					.retrieveTable("movies");
			while (moviesTableResult.next()) {
				ResultSet movieWritersResult = DatabaseUtility.getInstance()
						.retrieveSpecificMovieWriters(
								moviesTableResult.getString(1));
				movieWriters = new ArrayList<String>();
				while (movieWritersResult.next()) {
					movieWriters.add(movieWritersResult.getString(1));
				}

				ResultSet movieGenresIDsResult = DatabaseUtility.getInstance()
						.retrieveSpecificMovieGenres(
								moviesTableResult.getString(1));
				movieGenres = new ArrayList<String>();
				while (movieGenresIDsResult.next()) {
					String movieGenre = DatabaseUtility.getInstance()
							.retrieveGenreByID(
									movieGenresIDsResult.getString(1));
					movieGenres.add(movieGenre);
				}

				LocalMovieObject existingMovie = new LocalMovieObject(
						moviesTableResult.getString(2),
						moviesTableResult.getString(3),
						moviesTableResult.getString(4), movieGenres,
						Float.valueOf(moviesTableResult.getString(5)),
						moviesTableResult.getString(6),
						moviesTableResult.getString(7), movieWriters);
				existingMovies.add(existingMovie);
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return existingMovies;
	}
}
