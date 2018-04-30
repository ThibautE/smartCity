package com.example.thibaut.smartcity;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thibaut.smartcity.DB.MainDB;
import com.example.thibaut.smartcity.DB.PersonDB;
import com.example.thibaut.smartcity.function.NewsFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class NewsFragment extends Fragment {

    ListView viewListNews;
    List<NewsFunction> listNews;
    String urlImage = "http://x254.co/wp-content/uploads/2017/05/News-Briefs.png";
    String currentCity = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
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
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        try {
            JSONObject json = new NewsFunctionAPI(getActivity()).execute(currentCity).get();
            if (json == null) {
                this.listNews = generateNews(0);
                Toast.makeText(getActivity(), "Erreur", Toast.LENGTH_SHORT).show();
            } else {
                this.listNews = getNewsFromJson(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        NewsAdapter adapter = new NewsAdapter(getActivity(), this.listNews);
        this.viewListNews = (ListView) rootView.findViewById(R.id.listNews);
        this.viewListNews.setAdapter(adapter);

        return rootView;
    }

    private void printNews() {
        List<NewsFunction> list = generateNews(5);

        NewsAdapter adapter = new NewsAdapter(getActivity(), list);
        viewListNews.setAdapter(adapter);
    }

    private List<NewsFunction> generateNews(int nbNews) {
        List<NewsFunction> list = new ArrayList<NewsFunction>();
        for (int i = 1; i <= nbNews; i++) {
            list.add(new NewsFunction("Titre "+i, "Actualité non disponible." , this.urlImage));
        }
        return list;
    }

    private List<NewsFunction> getNewsFromJson(JSONObject json) throws JSONException {
        List<NewsFunction> list = new ArrayList<NewsFunction>();
        JSONArray arrayNews = json.getJSONArray("articles");
        JSONObject article;
        for (int i = 0; i < arrayNews.length(); i++) {
            article = ((JSONObject) arrayNews.get(i));
            list.add(new NewsFunction(article.getString("title"), article.getString("description"), article.getString("urlToImage")));
        }
        return list;
    }

}