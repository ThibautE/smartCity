package hmin202.smart.thibaut.smartcity;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NewsFunctionAPI extends AsyncTask<String, Void, JSONObject> {

    private Context context;

    NewsFunctionAPI(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try{
            String NEWS_API = "https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt&language=fr";
            URL url = new URL(String.format(NEWS_API, params[0]));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.addRequestProperty("x-api-key", this.context.getString(R.string.newsAPI));
            InputStream in = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!= null) {
                json.append(tmp).append("\n");
            }
            connection.disconnect();
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if(data.getString("status").equals("ok")) {
                return data;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}