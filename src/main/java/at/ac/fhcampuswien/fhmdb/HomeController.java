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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn; // Button to trigger search

    @FXML
    public TextField searchField; // Input field for search query

    @FXML
    public JFXListView<Movie> movieListView; // List view to display movies

    @FXML
    public JFXComboBox<Genre> genreComboBox; // Dropdown to select genre filter

    @FXML
    public JFXButton sortBtn; // Button to trigger sorting

    @FXML
    public JFXButton resetBtn;

    public List<Movie> allMovies = Movie.initializeMovies(); // Load all movies
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private boolean ascending = true; // Track sorting order

    // Method to filter movies based on search query and selected genre
    public static List<Movie> filterMovies(List<Movie> movies, String query, Genre genre) {
        return movies.stream()
                .filter(m -> (query == null || query.isEmpty() ||
                        m.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        m.getDescription().toLowerCase().contains(query.toLowerCase())))
                .filter(m -> genre == null || m.getGenres().contains(genre))
                .toList();
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
            observableMovies.addAll(filterMovies(allMovies, searchQuery, selectedGenre));
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

            // Zeigt alle Filme wieder an
            observableMovies.setAll(allMovies);  // Alle Filme anzeigen
        });
    }
}
