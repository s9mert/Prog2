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
}
