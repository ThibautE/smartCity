package hmin202.smart.thibaut.smartcity;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import hmin202.smart.thibaut.smartcity.DB.MainDB;
import hmin202.smart.thibaut.smartcity.DB.CityDB;
import hmin202.smart.thibaut.smartcity.function.WeatherFunction;

public class WeatherFragment extends Fragment {

    TextView cityTV;
    TextView weatherTV;
    TextView humidityTV;
    TextView temperatureTV;
    ImageView weatherIV;
    String ville;

    public WeatherFragment(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityTV = rootView.findViewById(R.id.city);
        weatherTV = rootView.findViewById(R.id.weather);
        humidityTV = rootView.findViewById(R.id.humidity);
        temperatureTV = rootView.findViewById(R.id.temperature);
        weatherIV = rootView.findViewById(R.id.weathericon);
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainDB myDatabaseHandler = new MainDB(getActivity());
        SQLiteDatabase db = myDatabaseHandler.getReadableDatabase();
        String[] projection = {
                CityDB.FeedEntry.COLUMN_CITY
        };
        Cursor cursor = db.query(
                CityDB.FeedEntry.TABLE_NAME,
                projection,
                null, //Where clause
                null, //Where clause
                null, //Don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        this.ville = "";

        if (cursor.moveToFirst()) {
            this.ville = cursor.getString(cursor.getColumnIndex(CityDB.FeedEntry.COLUMN_CITY));
        }

        cursor.close();
        updateWeatherData(this.ville);
    }

    public void updateWeatherData(final String city) {
        Handler handler = new Handler();
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
                            weather(json);
                        }
                    });
                }
            }
        }.start();
    }

    public void weather(JSONObject json) {
        try {
            cityTV.setText(this.ville);
            JSONObject jsonObject = json.getJSONArray("weather").getJSONObject(0);
            setWeatherIcon(jsonObject.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherIcon(int id, long couche, long leve) {
        String icon = "";
        String iconM = "";
        long currentTime = new Date().getTime();
        if (currentTime >= couche && currentTime < leve) //récupération code jour/nuit
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

        weatherTV.setText(iconM + ", " + icon); //ajoute la météo correspondante dans la textView
        weatherIV.setImageResource(getResources().getIdentifier(icon, "drawable", "hmin202.smart.thibaut.smartcity")); //est censé ajouter l'image correspondante a la météo dans l imageview
    }
}