package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private List<Movie> allMovies;

    @BeforeEach
    void setUp() {
        allMovies = Movie.initializeMovies();  // Load test movies
    }

    @Test
    void filterMovies_withNonExistingQuery_shouldReturnEmptyList() {
        List<Movie> result = HomeController.filterMovies(allMovies, "Nonexistent Movie", null);
        assertTrue(result.isEmpty());
    }

    @Test
    void filterMovies_byTitle_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "Life", null);
        assertEquals(1, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
    }

    @Test
    void filterMovies_byTitleAndGenre_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "Life", Genre.DRAMA);
        assertEquals(1, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
    }

    @Test
    void filterMovies_byDescription_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "Jewish", null);
        assertEquals(1, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
    }

    @Test
    void filterMovies_byGenre_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "", Genre.DRAMA);
        assertTrue(result.size() > 0);
    }

    @Test
    void filterMovies_withEmptyQueryAndNullGenre_shouldReturnAllMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "", null);
        assertEquals(allMovies.size(), result.size());
    }

    @Test
    void filterMovies_byAnotherTitle_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "Inception", null);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void filterMovies_byGenre_withSpecificGenre_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies(allMovies, "", Genre.COMEDY);
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(movie -> movie.getTitle().equals("Puss in Boots") || movie.getTitle().equals("500 Days of Summer")));
    }

    @Test
    void sortMovies_ascending_shouldBeAlphabetical() {
        List<Movie> sortedMovies = HomeController.sortMovies(new ArrayList<>(allMovies), true);
        assertEquals("500 Days of Summer", sortedMovies.get(0).getTitle());
        assertEquals("The Wolf of Wall Street", sortedMovies.get(sortedMovies.size() - 1).getTitle());
    }

    @Test
    void sortMovies_descending_shouldBeReverseAlphabetical() {
        List<Movie> sortedMovies = HomeController.sortMovies(new ArrayList<>(allMovies), false);
        assertEquals("The Wolf of Wall Street", sortedMovies.get(0).getTitle());
        assertEquals("500 Days of Summer", sortedMovies.get(sortedMovies.size() - 1).getTitle());
    }
}
