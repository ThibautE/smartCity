package com.example.thibaut.smartcity;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class NewsActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private WeatherFragment weatherFragment;
    private NewsFragment newsFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsFragment = new NewsFragment();
        weatherFragment = new WeatherFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,weatherFragment);
        transaction.commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void openWeatherFragment(View view){
        findViewById(R.id.newsButton).setEnabled(false);
        findViewById(R.id.weatherButton).setEnabled(true);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,weatherFragment);
        transaction.commit();
    }
    public void openNewsFragment(View view){
        findViewById(R.id.newsButton).setEnabled(false);
        findViewById(R.id.weatherButton).setEnabled(true);
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
    }
}
