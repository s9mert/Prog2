package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MovieAPI {

    private static final String BASE_URL = "https://prog2.fh-campuswien.ac.at/movies";

    public static List<Movie> getMovies (String query, String genre, String releaseYear, String ratingFrom){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();

        if (query != null) {
            urlBuilder.addQueryParameter("query", query); //url?query=Film1
        }
        if (genre != null) {
            urlBuilder.addQueryParameter("genre", genre); //url?genre=Action
        }
        if (releaseYear != null) {
            urlBuilder.addQueryParameter("releaseYear", releaseYear); //url?releaseYear=2000
        }
        if (ratingFrom != null) {
            urlBuilder.addQueryParameter("ratingFrom", ratingFrom); //url?ratingFrom=5
        }

        String url = urlBuilder.build().toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "http.agent")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return Collections.emptyList();
            }
            String json = response.body().string();
            Gson gson = new Gson();
            Type listType = new TypeToken<Collection<Movie>>(){}.getType();
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



