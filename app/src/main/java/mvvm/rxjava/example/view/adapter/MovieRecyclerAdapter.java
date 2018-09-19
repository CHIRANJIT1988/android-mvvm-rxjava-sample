package mvvm.rxjava.example.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mvvm.rxjava.example.R;
import mvvm.rxjava.example.model.Movie;
import mvvm.rxjava.example.databinding.ItemMovieBinding;
import mvvm.rxjava.example.viewmodel.ItemMovieViewModel;


public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder>
{
    private List<Movie> movieList;


    public MovieRecyclerAdapter()
    {
        this.movieList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position)
    {
        holder.bindMovie(movieList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return movieList.size();
    }

    public void setMovieList(List<Movie> movieList)
    {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        private ItemMovieBinding binding;

        MovieViewHolder(ItemMovieBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }


        void bindMovie(Movie movie)
        {
            if (binding.getMovieItemVM() == null)
            {
                binding.setMovieItemVM(new ItemMovieViewModel(itemView.getContext(), movie));
            }

            else
            {
                binding.getMovieItemVM().setMovie(movie);
            }
        }
    }
}