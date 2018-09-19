package mvvm.rxjava.example.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import mvvm.rxjava.example.R;
import mvvm.rxjava.example.databinding.ActivityMovieListBinding;
import mvvm.rxjava.example.view.adapter.MovieRecyclerAdapter;
import mvvm.rxjava.example.viewmodel.MovieViewModel;

import java.util.Observable;
import java.util.Observer;


public class MovieListActivity extends AppCompatActivity implements Observer
{
    private ActivityMovieListBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);

        MovieViewModel movieViewModel = new MovieViewModel(this);

        this.setupObserver(movieViewModel);
        this.initMovieRecyclerView(binding.movieList);
        this.binding.setMovieViewModel(movieViewModel);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupObserver(Observable observable)
    {
        observable.addObserver(this);
    }

    /**
     * Initialize movie adapter
     * @param recyclerView
     */
    private void initMovieRecyclerView(RecyclerView recyclerView)
    {
        MovieRecyclerAdapter adapter = new MovieRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object o)
    {
        if (observable instanceof MovieViewModel)
        {
            MovieRecyclerAdapter adapter = (MovieRecyclerAdapter) binding.movieList.getAdapter();
            MovieViewModel movieViewModel = (MovieViewModel) observable;
            adapter.setMovieList(movieViewModel.getMovieList());

            Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
        }
    }
}