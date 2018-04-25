package com.example.thibaut.smartcity;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.thibaut.smartcity.DB.MainDB;
import com.example.thibaut.smartcity.DB.PersonDB;
import com.example.thibaut.smartcity.function.WeatherFunction;

import static android.content.ContentValues.TAG;

public class WeatherFragment extends Fragment {

    Handler handler;

    TextView cityTextView;
    TextView dateTextView;
    TextView weatherTextView;
    TextView humidityTextView;
    TextView temperatureTextView;
    ImageView weatherImageView;
    private String currentCity;

    public WeatherFragment() {
        handler = new Handler();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityTextView = rootView.findViewById(R.id.city);
        weatherTextView = rootView.findViewById(R.id.weather);
        humidityTextView = rootView.findViewById(R.id.humidity);
        temperatureTextView = rootView.findViewById(R.id.temperature);
        weatherImageView = rootView.findViewById(R.id.weathericon);
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainDB myDatabaseHandler = new MainDB(getActivity());
        SQLiteDatabase db = myDatabaseHandler.getReadableDatabase();
        String[] projection = {
                PersonDB.FeedEntry.COLUMN_CITY
        };
        Cursor cursor = db.query(
                PersonDB.FeedEntry.TABLE_NAME,
                projection,
                null, //Where clause
                null, //Where clause
                null, //Don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        this.currentCity = "Montpellier";

        if (cursor.moveToFirst()) {
            this.currentCity = cursor.getString(cursor.getColumnIndex(PersonDB.FeedEntry.COLUMN_CITY));
        }

        cursor.close();
        updateWeatherData(this.currentCity);
    }

    public void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = WeatherFunction.getJSON(getActivity(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.weatherError), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    public void renderWeather(JSONObject json) {
        try {
            cityTextView.setText(this.currentCity);
            //SimpleDateFormat datef = new SimpleDateFormat("dd MMMM YYYY", Locale.FRANCE);
            //String date = datef.format(new Date(json.getLong("dt") * 1000));
            //dateTextView.setText(date);   --------- AFFICHER LA DATE DANS SA TEXTVIEW
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherIcon(int id, long sunrise, long sunset) {
        String icon = "";
        String iconM = "";
        long currentTime = new Date().getTime();
        if (currentTime >= sunrise && currentTime < sunset) //récupération code jour/nuit
            iconM = "Journée";
        else
            iconM = "Nuit";

        if(id>=200 && id<300){ //récupération code météo
            icon = "orage";
        } else if(id>=300 && id<400){
            icon = "bruine";
        } else if(id>=500 && id<600){
            icon = "pluie";
        } else if(id>=600 && id<700){
            icon = "neige";
        } else if(id>=700 && id<800){
            icon = "brouillard";
        } else if(id>=800 && id<900){
            icon = "beau";
        } else if(id>=900 && id<1000) {
            icon = "orage";
        }

        weatherTextView.setText(iconM + ", " + icon); //ajoute la météo correspondante dans la textView
        weatherImageView.setImageResource(getResources().getIdentifier(icon, "drawable", "com.example.thibaut.smartcity")); //est censé ajouter l'image correspondante a la météo dans l imageview
    }
}