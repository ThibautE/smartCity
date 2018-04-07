package com.example.thibaut.smartcity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;

public class NewsActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private WeatherFragment weatherFragment;
    private NewsFragment newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsFragment = new NewsFragment();
        weatherFragment = new WeatherFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,weatherFragment);
        transaction.commit();
    }

    public void openWeatherFragment(View view){
        findViewById(R.id.newsButton).setEnabled(false);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,weatherFragment);
        transaction.commit();
    }
    public void openNewsFragment(View view){
        findViewById(R.id.newsButton).setEnabled(false);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
    }
}
