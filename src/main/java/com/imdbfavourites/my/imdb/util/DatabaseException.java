package com.imdbfavourites.my.imdb.util;

import java.sql.SQLException;

public class DatabaseException extends Exception {
	private SQLException exception;

	public DatabaseException(SQLException ex) {
		super(ex);
		this.exception = ex;
	}
	
	public DatabaseException(String message) {
		super(message);
		this.exception = new SQLException(message);
	}

	public String getMessage() {
		return this.exception.getMessage();
	}
}
