package com.imdbfavourites.my.imdb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtility {
	private final String url = "jdbc:postgresql://localhost:5432/movies";
	private final String user = "master";
	private final String password = "";
	private Connection connection;
	private static DatabaseUtility instance;

	public static DatabaseUtility getInstance() {
		if (instance == null) {
			try {
				instance = new DatabaseUtility();
			} catch (DatabaseException e) {
				System.out.println("EXPECTION: DB-Initialization: "
						+ e.getMessage());
			}
		}

		return instance;
	}

	private DatabaseUtility() throws DatabaseException {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}

	public String getVersion() throws DatabaseException {
		Statement st;
		try {
			st = connection.createStatement();

			ResultSet rs = st.executeQuery("SELECT VERSION()");

			if (rs.next())
				return rs.getString(1);

		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-getVersion(): "
					+ e.getMessage());
		}

		return null;
	}

	public int insertRecordIntoDB(String dbTable, String columns, String values)
			throws DatabaseException {
		Statement st;
		int result = -1;
		try {
			st = connection.createStatement();
			System.out.println("insert into " + dbTable + " (" + columns
					+ ") values (" + values + ")");
			result = st.executeUpdate("insert into " + dbTable + " (" + columns
					+ ") values (" + values + ")", 0);
			ResultSet generatedKeys = st.getGeneratedKeys();
			generatedKeys.next();
			result = Integer.parseInt(generatedKeys.getString(1));
		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-insertRecord: "
					+ e.getMessage());
		}

		return result;
	}

	public ResultSet searchDatabaseTable(String table, String column,
			String matcher) throws DatabaseException {
		Statement st;
		ResultSet result = null;
		try {
			st = connection.createStatement();
			result = st.executeQuery("select * from " + table + " where "
					+ column + " = '" + matcher.toLowerCase() + "'");
		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-insertRecord: "
					+ e.getMessage());
		}

		return result;

	}

	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public ResultSet retrieveTable(String table) throws DatabaseException {
		Statement st;
		ResultSet result = null;
		try {
			st = connection.createStatement();
			result = st.executeQuery("select * from " + table);
		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-insertRecord: "
					+ e.getMessage());
		}
		return result;
	}

	public ResultSet retrieveSpecificMovieWriters(String movieID) throws DatabaseException{
		Statement st;
		ResultSet result = null;
		try {
			st = connection.createStatement();
			result = st.executeQuery("select writer from movie_writers where movie_id='"+movieID+"'");
		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-insertRecord: "
					+ e.getMessage());
		}
		return result;
	}

	public ResultSet retrieveSpecificMovieGenres(String movieID) throws DatabaseException{
		Statement st;
		ResultSet result = null;
		try {
			st = connection.createStatement();
			result = st.executeQuery("select genre_id from genre where movie_id='"+movieID+"'");
		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-insertRecord: "
					+ e.getMessage());
		}
		return result;
	}

	public String retrieveGenreByID(String genreID) throws DatabaseException{
		Statement st;
		ResultSet result = null;
		String genre = null;
		try {
			st = connection.createStatement();
			result = st.executeQuery("select name from movie_genres where id='"+genreID+"'");
			if(result.next()){
				genre = result.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException("EXPECTION: DB-insertRecord: "
					+ e.getMessage());
		}
		return genre;
	}
}
