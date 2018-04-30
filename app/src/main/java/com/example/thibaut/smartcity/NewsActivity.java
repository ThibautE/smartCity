package com.example.thibaut.smartcity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


public class NewsActivity extends AppCompatActivity {

    NewsFragment newsFragment;
    WeatherFragment weatherFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //setSupportActionBar((Toolbar) findViewById(R.id.news_menu));
        newsFragment = new NewsFragment();
        weatherFragment = new WeatherFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
    }
    public void openWeatherFragment(View view){
        findViewById(R.id.buttonWeather).setEnabled(false);
        findViewById(R.id.buttonNews).setEnabled(true);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,weatherFragment);
        transaction.commit();
    }
    public void openNewsFragment(View view){
        findViewById(R.id.buttonNews).setEnabled(false);
        findViewById(R.id.buttonWeather).setEnabled(true);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
    }

}