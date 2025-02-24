package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description,List<Genre> genres) {
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

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();

        List<Genre> g1 = new ArrayList<>();
        g1.add(Genre.DRAMA);
        g1.add(Genre.ROMANCE);
        movies.add(new Movie("Life Is Beautiful","When an open-minded Jewish librarian...", g1));

        List<Genre> g2 = new ArrayList<>();
        g2.add(Genre.CRIME);
        g2.add(Genre.DRAMA);
        g2.add(Genre.MYSTERY);
        movies.add(new Movie("The Usual Suspects","A sole survivor tells of the twisty events...", g2));

        List<Genre> g3 = new ArrayList<>();
        g3.add(Genre.COMEDY);
        g3.add(Genre.FAMILY);
        g3.add(Genre.ANIMATION);
        movies.add(new Movie("Puss in Boots","An outlaw cat, his childhood egg-friend...", g3));

        List<Genre> g4 = new ArrayList<>();
        g4.add(Genre.ANIMATION);
        g4.add(Genre.DRAMA);
        g4.add(Genre.ACTION);
        movies.add(new Movie("Avatar","A paraplegic Marine dispatched to the moon Pandora...", g4));

        List<Genre> g5 = new ArrayList<>();
        g5.add(Genre.DRAMA);
        g5.add(Genre.ROMANCE);
        g5.add(Genre.BIOGRAPHY);
        movies.add(new Movie("The Wolf of Wall Street","Based on the true story of Jordan Belfort...", g5));

        List<Genre> g6 = new ArrayList<>();
        g6.add(Genre.SCIENCE_FICTION);
        g6.add(Genre.ACTION);
        g6.add(Genre.THRILLER);
        movies.add(new Movie("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO...", g6));

        List<Genre> g7 = new ArrayList<>();
        g7.add(Genre.HORROR);
        g7.add(Genre.THRILLER);
        movies.add(new Movie("The Shining", "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence...", g7));

        List<Genre> g8 = new ArrayList<>();
        g8.add(Genre.COMEDY);
        g8.add(Genre.DRAMA);
        g8.add(Genre.ROMANCE);
        movies.add(new Movie("500 Days of Summer", "An offbeat romantic comedy about a woman who doesn't believe true love exists, and the man who falls for her...", g8));

        List<Genre> g9 = new ArrayList<>();
        g9.add(Genre.ACTION);
        g9.add(Genre.THRILLER);
        g9.add(Genre.MYSTERY);
        movies.add(new Movie("The Dark Knight", "When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham...", g9));

        List<Genre> g10 = new ArrayList<>();
        g10.add(Genre.DRAMA);
        g10.add(Genre.BIOGRAPHY);
        g10.add(Genre.ROMANCE);
        movies.add(new Movie("The Theory of Everything", "A look at the relationship between the famous physicist Stephen Hawking and his wife...", g10));

        return movies;
    }
}

