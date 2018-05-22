package hmin202.smart.thibaut.smartcity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NewsFunctionAPI extends AsyncTask<String, Void, JSONObject> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    String news;

    NewsFunctionAPI(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String categorie = "general";
        if(categorie.equals("general")) {
            news = "https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt&language=fr";
        }else if(categorie.equals("business")){
            news = "https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt&language=fr";
        }

        URL url = null;
        try {
            url = new URL(String.format(news, params[0]));
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        connection.addRequestProperty("x-api-key", this.context.getString(R.string.newsAPI));
        InputStream in = null;

        try {
            in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer json = new StringBuffer(1024);
            String tmp;

            while ((tmp = reader.readLine()) != null) {
                json.append(tmp).append("\n");
            }

            connection.disconnect();
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if (data.getString("status").equals("ok")) {
                return data;
            } else {
                return null;
            }

        } catch (IOException | JSONException e1) {
            e1.printStackTrace();
        }


        return null;
    }
}