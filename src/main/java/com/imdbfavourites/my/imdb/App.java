package com.imdbfavourites.my.imdb;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;
import com.imdbfavourites.my.imdb.movies.AvailableMovieContainer;
import com.imdbfavourites.my.imdb.movies.ExistingMovieContainer;
import com.imdbfavourites.my.imdb.util.DatabaseException;
import com.imdbfavourites.my.imdb.util.DatabaseUtility;
import com.omdbapi.Omdb;
import com.omdbapi.SearchResult;

///http://code.google.com/p/charts4j/
//http://sourceforge.net/p/omdbapi4j/wiki/Home/
//http://zetcode.com/db/mysqljava/

public class App extends Applet {
	private Random random;

	@Override
	public void init() {
		super.init();
		// this.setSize(new Dimension(800, 300));
		setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(java.awt.Color.WHITE);
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		addMenus();

		random = new Random();

		Slice s1 = Slice.newSlice(248, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.ACTION.name());
		Slice s2 = Slice.newSlice(214, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.ADVENTURE.name());
		Slice s3 = Slice.newSlice(73, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.ANIMATION.name());
		Slice s4 = Slice.newSlice(11, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.BIOGRAPHY.name());
		Slice s5 = Slice.newSlice(283, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.COMEDY.name());

		Slice s6 = Slice.newSlice(113, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.CRIME.name());
		Slice s7 = Slice.newSlice(142, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.DRAMA.name());
		Slice s8 = Slice.newSlice(115, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.FAMILY.name());
		Slice s9 = Slice.newSlice(144, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.FANTASY.name());
		Slice s10 = Slice.newSlice(9, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.HISTORY.name());

		Slice s11 = Slice.newSlice(48, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.HORROR.name());
		Slice s12 = Slice.newSlice(9, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.MUSIC.name());
		Slice s13 = Slice.newSlice(6, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.MUSICAL.name());
		Slice s14 = Slice.newSlice(75, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.MYSTERY.name());
		Slice s15 = Slice.newSlice(87, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.ROMANCE.name());

		Slice s16 = Slice.newSlice(155, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.SCI_FI.name());
		Slice s17 = Slice.newSlice(6, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.SPORT.name());
		Slice s18 = Slice.newSlice(187, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.THRILLER.name());
		Slice s19 = Slice.newSlice(9, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.WAR.name());
		Slice s20 = Slice.newSlice(7, Color.newColor(
				Integer.toHexString(getRandomColor().getRGB()).substring(2),
				100), Genre.WESTERN.name());

		PieChart chart = GCharts.newPieChart(s1, s2, s3, s4, s5, s6, s7, s8,
				s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20);
		Map<String, String> parameters = chart.getParameters();
		// [chl, chs (size), cht (title), chd, chco]
		// chart.setTitle("2D Pie Chart", Color.BLACK, 16);
		chart.setSize(650, 400);

		try {
			mainPanel.add(
					new JLabel(new ImageIcon(ImageIO.read(new URL(chart
							.toURLString())))), BorderLayout.EAST);
			mainPanel.add(
					new JLabel("<html>"
							+ parameters.get("chl").replace("|", "<br />")
							+ "</html>"), BorderLayout.WEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.add(mainPanel);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				resize(new Dimension(900, 500));
			}
		});
	}

	private void addMenus() {
		AppletMenuBar menubar = new AppletMenuBar();
		menubar.setForeground(java.awt.Color.BLACK);
		menubar.setHighlightColor(java.awt.Color.RED);
		menubar.setFont(new Font("helvetica", Font.BOLD, 12));
		this.add(menubar, BorderLayout.NORTH);

		PopupMenu exit = new PopupMenu();
		exit.add("Exit");
		PopupMenu new_movie = new PopupMenu();
		new_movie.add("New movie");
		PopupMenu all_movies = new PopupMenu();
		all_movies.add("All movies");

		exit.addActionListener(new ExitApplicationAction());

		new_movie.addActionListener(new NewMovieAction());

		all_movies.addActionListener(new ViewAllMyMoviesAction());

		menubar.addMenu("File", exit);
		menubar.addMenu("Edit", new_movie);
		menubar.addMenu("View", all_movies);
	}

	private java.awt.Color getRandomColor() {
		float hue = random.nextFloat();
		float saturation = (random.nextInt(2000) + 1000) / 10000f;
		float luminance = 0.9f;

		return java.awt.Color.getHSBColor(hue, saturation, luminance);
	}

	private class NewMovieAction implements ActionListener {
		private List<SearchResult> results;

		@Override
		public void actionPerformed(ActionEvent e) {
			Omdb omdb = null;
			String name = JOptionPane
					.showInputDialog("Enter the name of the movie");
			try {
				omdb = new Omdb();
				results = omdb.search(name);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (!results.isEmpty())
				new AvailableMoviesSelector(results);
		}

	}

	private class ViewAllMyMoviesAction implements ActionListener {
		private Thread loadingExistingMoviesThread;

		@Override
		public void actionPerformed(ActionEvent e) {
			final List<LocalMovieObject> existingMovies = MovieDatabaseUtility
					.getAllMoviesFromDatabase();

			final JPanel availableMoviesPanel = new JPanel();
			availableMoviesPanel.setLayout(new BoxLayout(availableMoviesPanel,
					BoxLayout.PAGE_AXIS));

			// stuff it in a scrollpane with a controlled size.
			final JScrollPane scrollPane = new JScrollPane(availableMoviesPanel);
			scrollPane.setPreferredSize(new Dimension(App.this.getWidth() - 70,
					App.this.getHeight() - 70));

			loadingExistingMoviesThread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (LocalMovieObject movie : existingMovies) {
						if (!loadingExistingMoviesThread.isInterrupted()) {
							availableMoviesPanel
									.add(new ExistingMovieContainer(movie));
							availableMoviesPanel.updateUI();
						}
					}
				}
			});
			loadingExistingMoviesThread.start();

			// pass the scrollpane to the joptionpane.
			int selection = JOptionPane.showConfirmDialog(App.this, scrollPane,
					"Choose from found movies", JOptionPane.CLOSED_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			if (selection == JOptionPane.OK_OPTION) {
				loadingExistingMoviesThread.interrupt();
			}
		}
	}

	private class ExitApplicationAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class AvailableMoviesSelector implements ChangeListener {
		private JPanel availableMoviesPanel;
		private AvailableMovieContainer currentlySelectedComponent;

		public AvailableMoviesSelector(List<SearchResult> results) {
			availableMoviesPanel = new JPanel();
			availableMoviesPanel.setLayout(new BoxLayout(availableMoviesPanel,
					BoxLayout.PAGE_AXIS));

			for (SearchResult result : results) {
				availableMoviesPanel.add(new AvailableMovieContainer(this,
						result));
			}

			// stuff it in a scrollpane with a controlled size.
			JScrollPane scrollPane = new JScrollPane(availableMoviesPanel);
			scrollPane.setPreferredSize(new Dimension(App.this.getWidth() - 70,
					App.this.getHeight() - 70));

			// pass the scrollpane to the joptionpane.
			int selection = JOptionPane.showConfirmDialog(App.this, scrollPane,
					"Choose from found movies", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			if (selection == JOptionPane.OK_OPTION) {
				// TODO: Handle added movie
				if (this.currentlySelectedComponent != null) {
					System.out.println(this.currentlySelectedComponent
							.getMovie().toString());
					MovieDatabaseUtility
							.addNewMovie(this.currentlySelectedComponent
									.getMovie());
				} else {
					System.out.println("nothing was selected");
				}
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			for (Component component : availableMoviesPanel.getComponents()) {
				if (component instanceof AvailableMovieContainer) {
					for (int i = 0; i < ((AvailableMovieContainer) component)
							.getComponents().length; i++) {
						System.out
								.println(((AvailableMovieContainer) component)
										.getComponent(i).getClass().getName());
						if (((AvailableMovieContainer) component)
								.getComponent(i).getClass().getName()
								.equals(JRadioButton.class.getName())) {
							System.out.println("found radio button");
							if (!e.getSource().equals(
									((AvailableMovieContainer) component)
											.getComponent(i))) {
								((JRadioButton) ((AvailableMovieContainer) component)
										.getComponent(i)).setSelected(false);
							} else {
								currentlySelectedComponent = (AvailableMovieContainer) component;
							}
						}
					}
				}
			}
		}

	}
}