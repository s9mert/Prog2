package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn; // Button to trigger search

    @FXML
    public TextField searchField; // Input field for search query

    @FXML
    public TextField releaseYearField;

    @FXML
    public TextField ratingFromField;

    @FXML
    public JFXListView<Movie> movieListView; // List view to display movies

    @FXML
    public JFXComboBox<Genre> genreComboBox; // Dropdown to select genre filter

    @FXML
    public JFXButton sortBtn; // Button to trigger sorting

    @FXML
    public JFXButton resetBtn;

    public List<Movie> allMovies = new ArrayList<>();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private boolean ascending = true; // Track sorting order

    // Method to filter movies based on search query and selected genre
    public static List<Movie> filterMovies(String query, Genre genre, int releaseYear, double ratingFrom) {
        // Retrieve all movies using the MovieAPI with the given genre, releaseYear, and rating filter
        List<Movie> allMovies = MovieAPI.getMovies(
                null,
                genre == null ? null : genre.toString(),
                releaseYear == 0 ? null : String.valueOf(releaseYear),
                ratingFrom == 0 ? null : String.valueOf(ratingFrom)
        );

        // If query is empty or null, return all movies without filtering on title or description
        if (query == null || query.trim().isEmpty()) {
            return allMovies;
        }

        // Filter movies based on the query (matching both title and description)
        String lowerCaseQuery = query.trim().toLowerCase();
        return allMovies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        movie.getDescription().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }


    // Method to sort movies alphabetically
    public static List<Movie> sortMovies(List<Movie> movies, boolean ascending) {
        movies.sort(ascending ?
                Comparator.comparing(Movie::getTitle) :
                Comparator.comparing(Movie::getTitle).reversed());
        return movies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize movie list
        try {
            allMovies = MovieAPI.getMovies(null, null, null, null);
        } catch (Exception e) {
            allMovies = new ArrayList<>();
        }
        observableMovies.addAll(allMovies);
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        // Initialize genre dropdown
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // Filter button action
        searchBtn.setOnAction(actionEvent -> {
            String searchQuery = searchField.getText() == null ? "" : searchField.getText().trim();
            Genre selectedGenre = genreComboBox.getValue();
            observableMovies.clear();
            observableMovies.addAll(filterMovies(searchQuery, selectedGenre, releaseYearField.getText().isEmpty() ? 0 : Integer.parseInt(releaseYearField.getText()), ratingFromField.getText().isEmpty() ? 0 : Double.parseDouble(ratingFromField.getText())));
        });

        // Sort button action
        sortBtn.setOnAction(actionEvent -> {
            if (ascending) {
                observableMovies.setAll(sortMovies(new ArrayList<>(observableMovies), true));
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.setAll(sortMovies(new ArrayList<>(observableMovies), false));
                sortBtn.setText("Sort (asc)");
            }
            ascending = !ascending;
        });

        // Reset-Button: Setzt Filter und Suchfeld zurück
        resetBtn.setOnAction(actionEvent -> {
            searchField.clear();  // Löscht das Suchfeld
            genreComboBox.setValue(null);  // Setzt Genre auf "null" zurück
            releaseYearField.clear();  // Löscht das Erscheinungsjahr
            ratingFromField.clear();  // Löscht die Bewertung
            // Zeigt alle Filme wieder an
            observableMovies.setAll(allMovies);  // Alle Filme anzeigen
        });
    }

    public static String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No actor found");
    }

    public static int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    public static long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirector().equalsIgnoreCase(director))
                .count();
    }

    public static List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
}
