package hmin202.smart.thibaut.smartcity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import hmin202.smart.thibaut.smartcity.Model.Photos;
import hmin202.smart.thibaut.smartcity.Model.PlaceDetail;
import hmin202.smart.thibaut.smartcity.Remote.IGoogleAPIService;
import com.squareup.picasso.Picasso;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlacesActivity extends AppCompatActivity {

    ImageView photo;
    RatingBar ratingBar;
    TextView opening_hours, place_adress, place_name, phone;
    Button btnMap;
    IGoogleAPIService mService;
    PlaceDetail myPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_places);

        mService = Common.getGoogleAPIService();

        photo = (ImageView) findViewById(R.id.photo);
        ratingBar = findViewById(R.id.rating_bar);
        place_adress = findViewById(R.id.place_adress);
        place_name = findViewById(R.id.place_name);
        opening_hours = findViewById(R.id.place_open_hour);
        btnMap = findViewById(R.id.btn_show_map);
        phone = findViewById(R.id.phone);

        place_name.setText("");
        place_adress.setText("");
        opening_hours.setText("");
        phone.setText("");


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });



        if(Common.currentResult.getPhotos() != null && Common.currentResult.getPhotos().length > 0){
            Picasso.with(this)
                    .load(getPhotoOfPlace(Common.currentResult.getPhotos()[0].getPhoto_reference(), 1000))
                    .placeholder(R.drawable.ic_insert_photo_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(photo);
        }

        if (Common.currentResult.getRating() != null && !TextUtils.isEmpty(Common.currentResult.getRating())){
            ratingBar.setRating(Float.parseFloat(Common.currentResult.getRating()));
        }else {
            ratingBar.setVisibility(View.GONE);
        }

        if (Common.currentResult.getOpening_hours() != null){
            opening_hours.setText("Ouvert en ce moment : " + Common.currentResult.getOpening_hours().getOpen_now());
        }else {
            opening_hours.setVisibility(View.GONE);
        }

        mService.getDetailPlace(getPlaceDetailUrl(Common.currentResult.getPlace_id()))
                .enqueue(new Callback<PlaceDetail>() {
                    @Override
                    public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {
                        myPlace = response.body();
                        assert myPlace != null;
                        place_adress.setText(myPlace.getResult().getFormatted_address());
                        place_name.setText(myPlace.getResult().getName());
                        phone.setText(myPlace.getResult().getInternational_phone_number());
                        Log.i("tel : ", String.valueOf(phone.getText()));
                        phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + String.valueOf(phone.getText())));
                                startActivity(intent);
                            }
                        });
                        }

                    @Override
                    public void onFailure(Call<PlaceDetail> call, Throwable t) {

                    }
                });

    }

    private String getPlaceDetailUrl(String place_id) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid="+place_id);
        url.append("&key="+getResources().getString(R.string.browser_key));
        return url.toString();
    }

    private String getPhotoOfPlace(String photos, int maxWidth) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        Log.i("photo : ", photos );
        url.append("?maxwidth=400");
        url.append("&photoreference="+photos);
        url.append("&key="+getResources().getString(R.string.browser_key));
        return url.toString();
    }




}
