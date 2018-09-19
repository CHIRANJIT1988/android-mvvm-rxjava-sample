package mvvm.rxjava.example.data;

import java.util.List;

import io.reactivex.Single;
import mvvm.rxjava.example.model.Movie;
import retrofit2.http.GET;


public interface ApiService {

    @GET("demos/marvel/")
    Single<List<Movie>> getMovies();
}