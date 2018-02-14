package com.imdbfavourites.my.imdb.movies;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

import com.imdbfavourites.my.imdb.LocalMovieObject;
import com.omdbapi.Movie;
import com.omdbapi.Omdb;
import com.omdbapi.OmdbConnectionErrorException;
import com.omdbapi.OmdbMovieNotFoundException;
import com.omdbapi.OmdbSyntaxErrorException;
import com.omdbapi.SearchResult;

public class AvailableMovieContainer extends JPanel {
	private ChangeListener listener;
	private JRadioButton radioButton;
	private LocalMovieObject movie;

	public AvailableMovieContainer(ChangeListener listener, SearchResult result) {
		super(new GridBagLayout());
		this.listener = listener;

		String poster = null;
		String title = null;
		String year = null;
		List<String> genres = null;
		float imdbRating = -1;
		String plot = null;
		String director = null;
		List<String> writers = null;

		try {
			Movie availableMovie = new Omdb().getById(result.getImdbID());

			if (!availableMovie.getPoster().equals("N/A")) {
				poster = availableMovie.getPosterURL().getFile();
			}
			title = result.getTitle();
			year = result.getYear();
			genres = availableMovie.getGenres();
			try {
				imdbRating = availableMovie.getImdbRating();
			} catch (Exception e) {
				imdbRating = -1;
			}
			plot = availableMovie.getPlot();
			director = availableMovie.getDirector();
			writers = availableMovie.getWriters();
		} catch (OmdbConnectionErrorException e) {
			e.printStackTrace();
		} catch (OmdbSyntaxErrorException e) {
			e.printStackTrace();
		} catch (OmdbMovieNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		this.movie = new LocalMovieObject(result.getImdbID(), title, year, genres,
				imdbRating, plot, director, writers);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets.bottom = 5;
		c.insets.top = 5;
		c.insets.left = 5;
		c.insets.right = 5;
		add(getMovieRadioButtonArea(), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.insets.bottom = 5;
		c.insets.top = 5;
		c.insets.left = 5;
		c.insets.right = 5;
		add(getMovieImageArea(poster), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		c.insets.bottom = 5;
		c.insets.top = 5;
		c.insets.left = 5;
		c.insets.right = 5;
		add(getMovieInformationArea(title, year, genres, imdbRating, plot,
				director, writers), c);

		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	private Component getMovieInformationArea(String title, String year,
			List<String> genres, float imdbRating, String plot,
			String director, List<String> writers) {

		JPanel movieInformationPanel = new JPanel();

		movieInformationPanel.setLayout(new BoxLayout(movieInformationPanel,
				BoxLayout.PAGE_AXIS));

		movieInformationPanel.add(getMovieTitleArea(title));
		movieInformationPanel.add(getMovieYearArea(year));
		movieInformationPanel.add(getMovieGenresArea(genres));
		movieInformationPanel.add(getMovieIMDbRatingArea(imdbRating));
		movieInformationPanel.add(getMoviePlotArea(plot));
		movieInformationPanel.add(getMovieDirectorArea(director));
		movieInformationPanel.add(getMovieWritersArea(writers));

		return movieInformationPanel;
	}

	private JRadioButton getMovieRadioButtonArea() {
		radioButton = new JRadioButton();
		radioButton.addChangeListener(this.listener);
		radioButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return radioButton;
	}

	private JLabel getMovieImageArea(String poster) {
		JLabel image = new JLabel("<No poster available>");
		if (poster != null) {
			Image downloadedImage = null;
			try {
				downloadedImage = ImageIO.read(new URL(
						"http://ia.media-imdb.com" + poster));
				downloadedImage = downloadedImage.getScaledInstance(
						downloadedImage.getWidth(this) - 50,
						downloadedImage.getHeight(this) - 50, Image.SCALE_FAST);
			} catch (IOException e) {
				e.printStackTrace();
			}

			image = new JLabel(new ImageIcon(downloadedImage));
			image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		return image;
	}

	private JTextField getMovieTitleArea(String title) {
		JTextField titleArea = new JTextField(title);
		titleArea.setHorizontalAlignment(JTextField.CENTER);
		// titleArea.setLineWrap(true);
		// titleArea.setWrapStyleWord(true);
		titleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return titleArea;
	}

	private JTextArea getMovieYearArea(String year) {
		JTextArea yearArea = new JTextArea(year);
		yearArea.setLineWrap(true);
		yearArea.setWrapStyleWord(true);
		yearArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return yearArea;
	}

	private JTextArea getMovieGenresArea(List<String> genres) {
		JTextArea genresArea = new JTextArea(Arrays.toString(genres.toArray()));
		genresArea.setLineWrap(true);
		genresArea.setWrapStyleWord(true);
		genresArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return genresArea;
	}

	private JTextArea getMovieIMDbRatingArea(float imdbRating) {
		JTextArea imdbRatingArea = new JTextArea("" + imdbRating);
		imdbRatingArea.setLineWrap(true);
		imdbRatingArea.setWrapStyleWord(true);
		imdbRatingArea
				.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return imdbRatingArea;
	}

	private JTextArea getMoviePlotArea(String plot) {
		JTextArea plotArea = new JTextArea(plot);
		plotArea.setLineWrap(true);
		plotArea.setWrapStyleWord(true);
		plotArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return plotArea;
	}

	private JTextArea getMovieDirectorArea(String director) {
		JTextArea directorArea = new JTextArea(director);
		directorArea.setLineWrap(true);
		directorArea.setWrapStyleWord(true);
		directorArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return directorArea;
	}

	private JTextArea getMovieWritersArea(List<String> writers) {
		JTextArea writersArea = new JTextArea(
				Arrays.toString(writers.toArray()));
		writersArea.setLineWrap(true);
		writersArea.setWrapStyleWord(true);
		writersArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return writersArea;
	}

	public boolean isRadioButtonSelected() {
		return this.radioButton.isSelected();
	}

	public LocalMovieObject getMovie() {
		return this.movie;
	}
}
