package hmin202.smart.thibaut.smartcity;

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

import hmin202.smart.thibaut.smartcity.DB.MainDB;
import hmin202.smart.thibaut.smartcity.DB.CityDB;
import hmin202.smart.thibaut.smartcity.function.NewsFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsFragment extends Fragment {

    ListView viewListNews;
    List<NewsFunction> list;
    String urlImage = "http://x254.co/wp-content/uploads/2017/05/News-Briefs.png";
    String ville = "";
    String url;

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
                CityDB.FeedEntry.COLUMN_CITY
        };
        Cursor cursor = db.query(
                CityDB.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        this.ville = "";

        if (cursor.moveToFirst()) {
            this.ville = cursor.getString(cursor.getColumnIndex(CityDB.FeedEntry.COLUMN_CITY));
        }

        cursor.close();
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        try {
            JSONObject json = new NewsFunctionAPI(getActivity()).execute(ville).get();
            if (json == null) {
                this.list = createNews(0);
                Toast.makeText(getActivity(), "Erreur", Toast.LENGTH_SHORT).show();
            } else {
                this.list = getNewsFromJson(json);
            }
        } catch (JSONException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        NewsAdapter adapter = new NewsAdapter(getActivity(), this.list);
        this.viewListNews = (ListView) rootView.findViewById(R.id.list);
        this.viewListNews.setAdapter(adapter);

        return rootView;
    }

    private void printNews() {
        List<NewsFunction> list = createNews(5);

        NewsAdapter adapter = new NewsAdapter(getActivity(), list);
        viewListNews.setAdapter(adapter);
    }

    private List<NewsFunction> createNews(int nbNews) {
        List<NewsFunction> list = new ArrayList<NewsFunction>();
        for (int i = 1; i <= nbNews; i++) {
            list.add(new NewsFunction(this.urlImage,"Titre " + i, "Actualité non disponible.", this.url));
        }
        return list;
    }

    private List<NewsFunction> getNewsFromJson(JSONObject json) throws JSONException {
        List<NewsFunction> list = new ArrayList<NewsFunction>();
        JSONArray arrayNews = json.getJSONArray("articles");
        JSONObject article;
        for (int i = 0; i < arrayNews.length(); i++) {
            article = ((JSONObject) arrayNews.get(i));
            list.add(new NewsFunction(article.getString("urlToImage"), article.getString("title"), article.getString("description"), article.getString("url")));
        }
        return list;
    }

}