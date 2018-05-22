package hmin202.smart.thibaut.smartcity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import hmin202.smart.thibaut.smartcity.function.NewsFunction;

public class NewsAdapter extends ArrayAdapter<NewsFunction> {

    public NewsAdapter(@NonNull Context context, List<NewsFunction> listNews) {
        super(context, 0, listNews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news, parent, false);
        }

        NewsViewHolder newsViewHolder = (NewsViewHolder) convertView.getTag();


        if (newsViewHolder == null) {
            newsViewHolder = new NewsViewHolder();
            newsViewHolder.title = convertView.findViewById(R.id.newsTitle);
            newsViewHolder.description = convertView.findViewById(R.id.newsDescription);
            newsViewHolder.image = convertView.findViewById(R.id.newsImage);

            convertView.setTag(newsViewHolder);
        }

        NewsFunction news = getItem(position);
        if (news != null) {
            newsViewHolder.title.setText(news.getTitle());
            newsViewHolder.description.setText(news.getDescription());
            Bitmap img = null;
            try {
                img = new GetImageBitmapTask().execute(news.getUrlImage()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (img != null) {
                newsViewHolder.image.setImageBitmap(img);
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



    private class NewsViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
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