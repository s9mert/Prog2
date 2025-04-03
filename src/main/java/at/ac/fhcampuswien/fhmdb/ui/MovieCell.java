package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label releaseYear = new Label();
    private final Label rating = new Label();
    private final VBox layout = new VBox(title, detail, releaseYear, rating);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Apply text styles
            title.setText(movie.getTitle());
            detail.setText(movie.getDescription() != null ? movie.getDescription() : "No description available");

            // Release Year & Rating
            releaseYear.setText("Release Year: " + (movie.getReleaseYear() > 0 ? movie.getReleaseYear() : "Unknown"));
            rating.setText("Rating: " + (movie.getRating() > 0 ? String.format("%.1f", movie.getRating()) : "N/A"));

            // Set colors
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            releaseYear.setStyle("-fx-text-fill: lightgray;");
            rating.setStyle("-fx-text-fill: lightgray;");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // Set layout properties
            title.setStyle("-fx-font-size: 20px;");
            detail.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.setSpacing(10);

            setGraphic(layout);
        }
    }
}


