package shrikant.com.upcomingmovie.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shrikant.com.upcomingmovie.Adapter.ViewPageAdapter;
import shrikant.com.upcomingmovie.Config.Config;
import shrikant.com.upcomingmovie.R;

public class MovieDetails extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ArrayList<String> image=new ArrayList<>();
    ProgressBar pb;
    CardView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        pb = (ProgressBar) findViewById(R.id.progressbar);
        desc = (CardView) findViewById(R.id.desc);
        desc.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
        Bundle b=getIntent().getExtras();
        int id=b.getInt("id");
        if (b.isEmpty()){
            Toast.makeText(this, "Movie Unknown", Toast.LENGTH_SHORT).show();
        }else
        VollyRequest("https://api.themoviedb.org/3/movie/"+id+"?api_key=b7cd3340a794e5a2f35e3abb820b497f");
    }

    private void VollyRequest(String url)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pb.setVisibility(View.GONE);
                       try {
                            JSONObject j=new JSONObject(response);
                           SettingText(j);
                        } catch (JSONException e)
                       {
                           Toast.makeText(MovieDetails.this, "Server error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(MovieDetails.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("api_key",new Config().getApiKey());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SettingText(JSONObject j) throws JSONException
    {
        desc.setVisibility(View.VISIBLE);
        TextView title=(TextView)findViewById(R.id.movieTitle);
        TextView overview=(TextView)findViewById(R.id.overview);
        RatingBar popularity=(RatingBar)findViewById(R.id.popularity);
        title.setText(j.getString("title"));
        overview.setText(j.getString("overview"));

        popularity.setRating((float) (j.getDouble("vote_average")/2));
        image.add(j.getString("poster_path"));
        ViewPagerMothod();

    }

    private void ViewPagerMothod()
    {

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this,image);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}