package shrikant.com.upcomingmovie;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import shrikant.com.upcomingmovie.Activity.MovieList;

public class Startup_Page extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_page);

        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                    startActivity(new Intent(getApplicationContext(), MovieList.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 100);
    }
}
