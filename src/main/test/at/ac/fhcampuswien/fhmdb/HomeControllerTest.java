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
        allMovies = List.of(
                new Movie("Inception", "A dream within a dream", List.of(Genre.ACTION, Genre.SCIENCE_FICTION), 2010, 8.8,
                        List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"), "Christopher Nolan"),
                new Movie("The Dark Knight", "Batman fights Joker", List.of(Genre.ACTION, Genre.DRAMA), 2008, 9.0,
                        List.of("Christian Bale", "Heath Ledger", "Morgan Freeman"), "Christopher Nolan"),
                new Movie("Life Is Beautiful", "A Jewish father protects his son in a concentration camp", List.of(Genre.DRAMA), 1997, 8.6,
                        List.of("Roberto Benigni", "Nicoletta Braschi"), "Roberto Benigni"),
                new Movie("Titanic", "A love story on a sinking ship", List.of(Genre.ROMANCE, Genre.DRAMA), 1997, 7.8,
                        List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane"), "James Cameron"),
                new Movie("The Wolf of Wall Street", "Financial crime drama", List.of(Genre.DRAMA, Genre.COMEDY), 2013, 8.2,
                        List.of("Leonardo DiCaprio", "Jonah Hill", "Margot Robbie"), "Martin Scorsese")
        );
    }

    // ======= TESTS FÜR DIE STREAM-METHODEN =======

    @Test
    void getMostPopularActor_ReturnsCorrectActor() {
        String mostPopularActor = HomeController.getMostPopularActor(allMovies);
        assertEquals("Leonardo DiCaprio", mostPopularActor);
    }

    @Test
    void getMostPopularActor_ReturnsEmptyIfNoMovies() {
        String mostPopularActor = HomeController.getMostPopularActor(new ArrayList<>());
        assertEquals("No actor found", mostPopularActor);
    }

    @Test
    void getLongestMovieTitle_ReturnsCorrectLength() {
        int longestTitleLength = HomeController.getLongestMovieTitle(allMovies);
        assertEquals(23, longestTitleLength);  // "Life Is Beautiful" has 23 characters
    }

    @Test
    void getLongestMovieTitle_ReturnsZeroIfNoMovies() {
        int longestTitleLength = HomeController.getLongestMovieTitle(new ArrayList<>());
        assertEquals(0, longestTitleLength);
    }

    @Test
    void countMoviesFrom_ReturnsCorrectCount() {
        long nolanMovies = HomeController.countMoviesFrom(allMovies, "Christopher Nolan");
        assertEquals(2, nolanMovies);
    }

    @Test
    void countMoviesFrom_ReturnsZeroForUnknownDirector() {
        long unknownDirectorMovies = HomeController.countMoviesFrom(allMovies, "Quentin Tarantino");
        assertEquals(0, unknownDirectorMovies);
    }

    @Test
    void getMoviesBetweenYears_ReturnsCorrectMovies() {
        List<Movie> moviesBetween1995And2010 = HomeController.getMoviesBetweenYears(allMovies, 1995, 2010);
        assertEquals(4, moviesBetween1995And2010.size());
    }

    @Test
    void getMoviesBetweenYears_ReturnsEmptyListIfNoMatch() {
        List<Movie> moviesBetween1800And1900 = HomeController.getMoviesBetweenYears(allMovies, 1800, 1900);
        assertTrue(moviesBetween1800And1900.isEmpty());
    }

    // ======= ALTE TESTS =======

    @Test
    void filterMovies_withNonExistingQuery_shouldReturnEmptyList() {
        List<Movie> result = HomeController.filterMovies("", Genre.DOCUMENTARY, 0, 0);
        assertTrue(result.isEmpty());
    }

    @Test
    void filterMovies_byTitle_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies("Life is", null, 0, 0);
        assertEquals(1, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
    }

    @Test
    void filterMovies_byTitleAndGenre_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies("Life is", Genre.DRAMA, 0, 0);
        assertEquals(1, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
    }

    @Test
    void filterMovies_byDescription_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies("Jewish", null, 0, 0);
        assertEquals(2, result.size());
        assertEquals("Schindler's List", result.get(0).getTitle());
        assertEquals("Life Is Beautiful", result.get(1).getTitle());
    }

    @Test
    void filterMovies_byGenre_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies("", Genre.DRAMA, 0, 0);
        assertFalse(result.isEmpty());
    }

    @Test
    void filterMovies_withEmptyQueryAndNullGenre_shouldReturnAllMovies() {
        List<Movie> result = HomeController.filterMovies("", null, 0, 0);
        assertEquals(31, result.size());
    }

    @Test
    void filterMovies_byAnotherTitle_shouldReturnCorrectMovies() {
        List<Movie> result = HomeController.filterMovies("Inception", null, 0, 0);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void sortMovies_ascending_shouldBeAlphabetical() {
        List<Movie> sortedMovies = HomeController.sortMovies(new ArrayList<>(allMovies), true);
        assertEquals("Inception", sortedMovies.get(0).getTitle());
        assertEquals("Life Is Beautiful", sortedMovies.get(1).getTitle());
        assertEquals("The Dark Knight", sortedMovies.get(2).getTitle());
        assertEquals("The Wolf of Wall Street", sortedMovies.get(3).getTitle());
        assertEquals("Titanic", sortedMovies.get(sortedMovies.size() - 1).getTitle());
    }

    @Test
    void sortMovies_descending_shouldBeReverseAlphabetical() {
        List<Movie> sortedMovies = HomeController.sortMovies(new ArrayList<>(allMovies), false);
        assertEquals("Titanic", sortedMovies.get(0).getTitle());
        assertEquals("The Wolf of Wall Street", sortedMovies.get(1).getTitle());
        assertEquals("The Dark Knight", sortedMovies.get(2).getTitle());
        assertEquals("Life Is Beautiful", sortedMovies.get(3).getTitle());
        assertEquals("Inception", sortedMovies.get(sortedMovies.size() - 1).getTitle());

    }
}
