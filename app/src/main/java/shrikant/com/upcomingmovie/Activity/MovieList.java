package shrikant.com.upcomingmovie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shrikant.com.upcomingmovie.Adapter.Adapterclass;
import shrikant.com.upcomingmovie.Config.Config;
import shrikant.com.upcomingmovie.Model_Class.Movie;
import shrikant.com.upcomingmovie.R;

public class MovieList extends AppCompatActivity
{
ArrayList<Movie> result;
    ListView movieListView;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
            pb=(ProgressBar)findViewById(R.id.pd);
            pb.setVisibility(View.VISIBLE);
            movieListView=(ListView)findViewById(R.id.list) ;
            Volly(new Config().getUpcomingMovieList());
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Movie m=result.get(position);
                startActivity(new Intent(getApplicationContext(),MovieDetails.class).putExtra("id",m.getId()));



            }
        });
    }

    private void Volly(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                                JsonParse(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(MovieList.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String,String> param=new HashMap<>();
                param.put("api_key",new Config().getApiKey());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void JsonParse(String response)
    {
        Movie m;
        result=new ArrayList<>();
        if (!response.isEmpty())
        try
        {
                JSONObject j=new JSONObject(response);
                JSONArray ja=j.getJSONArray("results");
            for (int i = 0; i < ja.length(); i++)
            {
                JSONObject jo = ja.getJSONObject(i);
                m=new Movie();
                m.setId(Integer.parseInt(jo.getString("id")));
                m.setTitle(jo.getString("title"));
                m.setAdult(Boolean.parseBoolean(jo.getString("adult")));
                m.setReleseDate(jo.getString("release_date"));
                m.setPosterPath(jo.getString("poster_path"));
                result.add(m);
            }
            pb.setVisibility(View.GONE);
            movieListView.setAdapter(new Adapterclass(this,result));

        }catch (JSONException e)
        {
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }



}
