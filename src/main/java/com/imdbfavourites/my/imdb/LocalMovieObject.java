package com.imdbfavourites.my.imdb;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class LocalMovieObject {
	private String imdbID;
	private String title;
	private String year;
	private List<String> genres;
	private float imdbRating;
	private String plot;
	private String director;
	private List<String> writers;

	public LocalMovieObject(String imdbID, String title, String year,
			List<String> genres, float imdbRating, String plot,
			String director, List<String> writers) {
		this.imdbID = imdbID;
		this.title = title;
		this.year = year;
		this.genres = genres;
		this.imdbRating = imdbRating;
		this.plot = plot;
		this.director = director;
		this.writers = writers;
	}

	public String getIMDbID() {
		return this.imdbID;
	}

	public void setIMDbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<String> getGenres() {
		return this.genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public float getImdbRating() {
		return this.imdbRating;
	}

	public void setImdbRating(float imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getPlot() {
		return this.plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<String> getWriters() {
		return this.writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	@Override
	public String toString() {
		return "imdb ID: " + this.imdbID + " | Title: " + this.title
				+ " | Year: " + this.year + " | Genres: "
				+ Arrays.toString(this.genres.toArray()) + " | IMDb rating: "
				+ this.imdbRating + " | Plot: " + this.plot + " | Director: "
				+ this.director + " | Writers: "
				+ Arrays.toString(this.writers.toArray());
	}
}
