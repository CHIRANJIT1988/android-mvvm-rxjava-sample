package mvvm.rxjava.example.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import mvvm.rxjava.example.model.Movie;


public class ItemMovieViewModel extends BaseObservable
{
    private Movie movie;
    private Context context;


    public ItemMovieViewModel(Context context, Movie movie)
    {
        this.movie = movie;
        this.context = context;
    }

    public String getName()
    {
        return this.movie.getName();
    }

    public String getPublisher()
    {
        return this.movie.getPublisher();
    }

    public String getCreatedBy()
    {
        return this.movie.getCreatedBy();
    }

    public String getThumbnail()
    {
        return this.movie.getImageUrl();
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url)
    {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    public void setMovie(Movie movie)
    {
        this.movie = movie;
        notifyChange();
    }
}