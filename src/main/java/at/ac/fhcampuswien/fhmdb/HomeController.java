package at.ac.fhcampuswien.fhmdb;

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
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private static List<Movie> filterMovies(List<Movie> movies, String searchQuery, String selectedGenre) {
        List<Movie> filtered = new ArrayList<>();
        String q = searchQuery == null ? "" : searchQuery.toLowerCase().trim();
        String g = selectedGenre == null ? "" : selectedGenre.toUpperCase().trim();
        for(Movie m : movies){
            boolean matchesQuery = q.isEmpty() || m.getTitle().toLowerCase().contains(q) || m.getDescription().toLowerCase().contains(q);
            boolean matchesGenre = g.isEmpty() || m.getGenres().stream().anyMatch(x -> x.equalsIgnoreCase(g));
            if(matchesQuery && matchesGenre){
                filtered.add(m);
            }
        }
        return filtered;
    }

    private static List<Movie> sortMovies(List<Movie> movies, boolean ascending) {
        movies.sort((m1, m2) -> ascending
                ? m1.getTitle().compareToIgnoreCase(m2.getTitle())
                : m2.getTitle().compareToIgnoreCase(m1.getTitle()));
        return movies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll("ACTION","ADVENTURE","ANIMATION","BIOGRAPHY","COMEDY","CRIME","DRAMA","DOCUMENTARY","FAMILY","FANTASY","HISTORY","HORROR","MUSICAL","MYSTERY","ROMANCE","SCIENCE_FICTION","SPORT","THRILLER","WAR","WESTERN");

        searchBtn.setOnAction(actionEvent -> {
            String searchQuery = searchField.getText() == null ? "" : searchField.getText().trim();
            String selectedGenre = genreComboBox.getValue() == null ? "" : genreComboBox.getValue().toString().trim();
            observableMovies.clear();
            observableMovies.addAll(filterMovies(allMovies, searchQuery, selectedGenre));
        });

        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                observableMovies.setAll(sortMovies(new ArrayList<>(observableMovies), true));
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.setAll(sortMovies(new ArrayList<>(observableMovies), false));
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    public static List<Movie> filterMoviesTestHelper(List<Movie> movies, String searchQuery, String selectedGenre) {
        return filterMovies(movies, searchQuery, selectedGenre);
    }

    public static List<Movie> sortMoviesTestHelper(List<Movie> movies, boolean ascending) {
        return sortMovies(movies, ascending);
    }
}