package shrikant.com.upcomingmovie.Adapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import shrikant.com.upcomingmovie.R;

/**
 * Created by ShrikantIngole on 20-01-2018.
 */

public class ViewPageAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
//    private Integer [] images = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};
    private ArrayList<String> poster;
    public ViewPageAdapter(Context context,ArrayList<String> poster)
    {
        this.poster=poster;
        this.context = context;
    }

    @Override
    public int getCount() {
        return poster.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_image, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        //imageView.setImageResource(images[position]);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w300/"+poster.get(position))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .resize(100, 100).centerCrop()
                .into(imageView);



        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}