package hmin202.smart.thibaut.smartcity;

import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class NewsActivity extends AppCompatActivity {

    NewsFragment newsFragment;
    WeatherFragment weatherFragment;
    TextView tv;
    int cpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsFragment = new NewsFragment();
        weatherFragment = new WeatherFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,newsFragment);
        transaction.commit();
        tv = findViewById(R.id.buttonNews);
        tv.setText("Afficher la meteo");
        cpt = 0;

        //changement font
        TextView tv = findViewById(R.id.buttonNews);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Font/justice.ttf");
        tv.setTypeface(tf);
    }

    public void openFragment(View view){
        tv = findViewById(R.id.buttonNews);
        if(cpt == 0){
            tv.setText("Afficher les news");
            FragmentTransaction transaction= getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,weatherFragment);
            transaction.commit();
            cpt = 1;
        } else if(cpt == 1){
            tv.setText("Afficher la meteo");
            FragmentTransaction transaction= getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,newsFragment);
            transaction.commit();
            cpt = 0;
        }

    }

}



