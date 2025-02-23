package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<String> genres;

    public Movie(String title, String description, List<String> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        List<String> g1 = new ArrayList<>();
        g1.add("DRAMA");g1.add("ROMANCE");
        movies.add(new Movie("Life Is Beautiful","When an open-minded Jewish librarian...",g1));
        List<String> g2 = new ArrayList<>();
        g2.add("CRIME");g2.add("DRAMA");g2.add("MYSTERY");
        movies.add(new Movie("The Usual Suspects","A sole survivor tells of the twisty events...",g2));
        List<String> g3 = new ArrayList<>();
        g3.add("COMEDY");g3.add("FAMILY");g3.add("ANIMATION");
        movies.add(new Movie("Puss in Boots","An outlaw cat, his childhood egg-friend...",g3));
        List<String> g4 = new ArrayList<>();
        g4.add("ANIMATION");g4.add("DRAMA");g4.add("ACTION");
        movies.add(new Movie("Avatar","A paraplegic Marine dispatched to the moon Pandora...",g4));
        List<String> g5 = new ArrayList<>();
        g5.add("DRAMA");g5.add("ROMANCE");g5.add("BIOGRAPHY");
        movies.add(new Movie("The Wolf of Wall Street","Based on the true story of Jordan Belfort...",g5));
        return movies;
    }
}