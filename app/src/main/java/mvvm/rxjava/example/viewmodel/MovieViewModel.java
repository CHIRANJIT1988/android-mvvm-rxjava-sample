package mvvm.rxjava.example.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mvvm.rxjava.example.data.ApiClient;
import mvvm.rxjava.example.data.ApiService;
import mvvm.rxjava.example.model.Movie;


public class MovieViewModel extends java.util.Observable
{

    private Context context;

    public ObservableInt progress;
    public ObservableInt recycler;

    private List<Movie> movieList;

    private ApiService apiService;


    public MovieViewModel(Context context)
    {
        this.context = context;

        this.progress = new ObservableInt(View.GONE);
        this.recycler = new ObservableInt(View.GONE);

        this.movieList = new ArrayList<>();

        this.apiService = ApiClient.getClient().create(ApiService.class);
    }


    public void onClickFabLoad(View view)
    {
        this.progress.set(View.VISIBLE);
        fetchAllMovies();
    }


    public List<Movie> getMovieList()
    {
        return movieList;
    }


    /*private void fetchAllMovies()
    {
        CompositeDisposable disposable = new CompositeDisposable();
        ConnectableObservable<List<Movie>> movieObservable = getMovieObservable().replay();

        disposable.add(movieObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Movie>>() {

                    @Override
                    public void onNext(@NonNull List<Movie> movies)
                    {
                        movieList.clear();
                        movieList.addAll(movies);

                        progress.set(View.GONE);
                        recycler.set(View.VISIBLE);

                        setChanged();
                        notifyObservers();
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onComplete()
                    {

                    }
                }));

        movieObservable.connect();
    }*/


    /**
     * Subscribe to observable
     */
    private void fetchAllMovies()
    {
        getMovieObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMovieObserver());
    }

    /**
     * Making Retrofit call to get observable
     */
    private Observable<List<Movie>> getMovieObservable()
    {
        return apiService.getMovies()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * Making call to get observer
     * @return
     */
    private DisposableObserver<List<Movie>> getMovieObserver() {

        return new DisposableObserver<List<Movie>> ()
        {
            @Override
            public void onNext(@NonNull List<Movie> movies)
            {
                movieList.clear();
                movieList.addAll(movies);

                progress.set(View.GONE);
                recycler.set(View.VISIBLE);

                setChanged();
                notifyObservers();
            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onComplete()
            {

            }
        };
    }
}