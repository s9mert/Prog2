package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;
    private int releaseYear;
    private double rating;
    private List<String> mainCast;
    private String director;

    public Movie(String title, String description,List<Genre> genres, int releaseYear, double rating, List<String> mainCast, String director) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.mainCast = mainCast;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public String getDirector() {
        return director;
    }
}

