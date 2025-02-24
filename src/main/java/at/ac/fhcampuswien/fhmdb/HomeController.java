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
import java.util.Comparator;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private boolean ascending = true;

    public static List<Movie> filterMovies(List<Movie> movies, String query, Genre genre) {
        return movies.stream()
                .filter(m -> (query == null || query.isEmpty() ||
                        m.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        m.getDescription().toLowerCase().contains(query.toLowerCase())))
                .filter(m -> genre == null || m.getGenres().contains(genre))
                .toList();
    }

    public static List<Movie> sortMovies(List<Movie> movies, boolean ascending) {
        movies.sort(ascending ?
                Comparator.comparing(Movie::getTitle) :
                Comparator.comparing(Movie::getTitle).reversed());
        return movies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // "Filter" button
        searchBtn.setOnAction(actionEvent -> {
            String searchQuery = searchField.getText() == null ? "" : searchField.getText().trim();
            Genre selectedGenre = genreComboBox.getValue();
            observableMovies.clear();
            observableMovies.addAll(filterMovies(allMovies, searchQuery, selectedGenre));
        });

        // "Sort" button
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                observableMovies.setAll(sortMovies(new ArrayList<>(observableMovies), true));
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.setAll(sortMovies(new ArrayList<>(observableMovies), false));
                sortBtn.setText("Sort (asc)");
            }
        });
    }
}