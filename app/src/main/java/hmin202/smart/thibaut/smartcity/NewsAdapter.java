package hmin202.smart.thibaut.smartcity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import hmin202.smart.thibaut.smartcity.function.NewsFunction;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsAdapter extends ArrayAdapter<NewsFunction> {

    public NewsAdapter(@NonNull Context context, List<NewsFunction> listNews) {
        super(context, 0, listNews);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news, parent, false);
        }

        convertView.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.LTGRAY);

        News n = (News) convertView.getTag();


        if (n == null) {
            n = new News();
            n.titre = convertView.findViewById(R.id.newsTitle);
            n.description = convertView.findViewById(R.id.newsDescription);
            n.image = convertView.findViewById(R.id.newsImage);

            convertView.setTag(n);
        }

        NewsFunction news = getItem(position);
        if (news != null) {
            n.titre.setText(news.getTitle());
            n.description.setText(news.getDescription());
            Bitmap img = null;
            try {
                img = new GetImageBitmapTask().execute(news.getUrlImage()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (img != null) {
                n.image.setImageBitmap(img);
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getItem(position).getUrl())));
            }
        });

        return convertView;
    }


    private class News {
        public ImageView image;
        public TextView titre;
        public TextView description;
    }

    private class GetImageBitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap img = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream= connection.getInputStream();
                img = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return img;
        }
    }
}