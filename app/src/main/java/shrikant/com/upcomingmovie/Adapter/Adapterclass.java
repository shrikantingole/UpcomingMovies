package shrikant.com.upcomingmovie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import shrikant.com.upcomingmovie.Model_Class.Movie;
import shrikant.com.upcomingmovie.R;

/**
 * Created by ShrikantIngole on 19-01-2018.
 */
public class Adapterclass extends BaseAdapter
{
    private static ArrayList<Movie> statusDataList;
Context c;
    private LayoutInflater mInflater;

    public Adapterclass(Context context, ArrayList<Movie> results)
    {
        c=context;
        statusDataList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return statusDataList.size();
    }

    public Object getItem(int position) {
        return statusDataList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.movie_item, null);
            holder = new ViewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.movieTitle);
            holder.ReleseDate = (TextView) convertView.findViewById(R.id.relesedate);
            holder.Adult = (TextView) convertView.findViewById(R.id.adult);
            holder.Poster=(ImageView)convertView.findViewById(R.id.poster);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        String title= statusDataList.get(position).getTitle();
        String releseDate= statusDataList.get(position).getReleseDate();
        boolean adult= statusDataList.get(position).isAdult();
        String poster= statusDataList.get(position).getPosterPath();

        holder.Title.setText(title);
        holder.ReleseDate.setText(releseDate);
        if (adult)
        holder.Adult.setText("(A)");
        else
        holder.Adult.setText("(U/A)");

        Picasso.with(c).load("https://image.tmdb.org/t/p/w300"+poster).placeholder(R.drawable.loading)// Place holder image from drawable folder
                .error(R.drawable.error)
                .resize(100, 100).centerCrop()
                .into(holder.Poster);
        return convertView;
    }

    private static class ViewHolder {
        TextView Title,ReleseDate,Adult;
        ImageView Poster;
    }
}