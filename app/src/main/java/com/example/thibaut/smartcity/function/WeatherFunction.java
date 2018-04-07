package com.example.thibaut.smartcity.function;

import android.content.Context;

import com.example.thibaut.smartcity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class WeatherFunction {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s,FR";

    public static JSONObject getJSON(Context context, String city){
        try{
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city)); //Ajout de la ville dans l'url
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", context.getString(R.string.weatherAPIKey)); //Ajout la clef de l'api contenu dans les ressources

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp;

            while((tmp=reader.readLine())!= null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getInt("cod") != 200)
                return null;
            return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
