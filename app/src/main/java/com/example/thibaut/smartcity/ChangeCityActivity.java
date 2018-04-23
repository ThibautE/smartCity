package com.example.thibaut.smartcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;


import com.example.thibaut.smartcity.DB.*;

public class ChangeCityActivity extends AppCompatActivity implements OnConnectionFailedListener {

    private GoogleApiClient myGoogleApiClient;
    private MainDB myDatabaseHandler;
    private String newCity = "";
    private String currentCity;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        myDatabaseHandler = new MainDB(ChangeCityActivity.this);

        //Init Ville
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
        if(cursor.moveToFirst()==true){
            currentCity = cursor.getString(cursor.getColumnIndex(PersonDB.FeedEntry.COLUMN_CITY));
        };
        cursor.close();

        //TextView
        ((TextView) findViewById(R.id.text_you_are)).append(currentCity);

        //Autocomplete
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).setCountry("FR").build();

        myGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlaceAutocompleteFragment autocompleteFragment =  (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setFilter(autocompleteFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                newCity = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                newCity = "";
            }
        });
    }
    public void onConnectionFailed (ConnectionResult result){
        Log.d("PROBLEM","ça marche pas là");
    }
    public void close(View view){
        ChangeCityActivity.this.finish();
    }
    public void validate(View view){
        PlaceAutocompleteFragment autocompleteFragment =  (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setText("");
        if(!newCity.equals("")){
            SQLiteDatabase db = myDatabaseHandler.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PersonDB.FeedEntry.COLUMN_CITY,newCity);
            db.update(PersonDB.FeedEntry.TABLE_NAME, values,null,null);
            currentCity = newCity;
            ((TextView) findViewById(R.id.text_you_are)).setText(R.string.you_are);
            ((TextView) findViewById(R.id.text_you_are)).append(currentCity);
        }
    }
    public void onDestroy(){
        myDatabaseHandler.close();
        super.onDestroy();
    }
}