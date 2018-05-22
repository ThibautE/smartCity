package hmin202.smart.thibaut.smartcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;



import hmin202.smart.thibaut.smartcity.DB.MainDB;
import hmin202.smart.thibaut.smartcity.DB.CityDB;

public class ChangeCityActivity extends AppCompatActivity implements OnConnectionFailedListener {

    private GoogleApiClient myGoogleApiClient;
    private MainDB myDB;
    private String ville2 = "";
    private String ville;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //reccup la ville dans la bd sqlite
        myDB = new MainDB(ChangeCityActivity.this);

        SQLiteDatabase db = myDB.getReadableDatabase();
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
        if(cursor.moveToFirst()){
            ville = cursor.getString(cursor.getColumnIndex(CityDB.FeedEntry.COLUMN_CITY));
        };
        cursor.close();

        //Google API
        myGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        ((TextView) findViewById(R.id.emplacement)).append(ville);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).setCountry("FR").build();

        PlaceAutocompleteFragment autocompleteFragment =  (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.adapter);

        autocompleteFragment.setFilter(autocompleteFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                ville2 = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                ville2 = "";
            }
        });
    }

    //gestion erreur OnConnectionFailedListener
    public void onConnectionFailed (ConnectionResult result){
        Log.d("ERROR","Erreur au moment de la connexion, veuilllez réessayer plus tard");
    }

    public void close(View view){
        ChangeCityActivity.this.finish();
    }

    public void onDestroy(){
        myDB.close();
        super.onDestroy();
    }

    public void validate(View view){
        PlaceAutocompleteFragment autocompleteFragment;
        autocompleteFragment= (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.adapter);
        autocompleteFragment.setText("");
        if(ville2 != "" && ville2 != null){
            SQLiteDatabase db = myDB.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CityDB.FeedEntry.COLUMN_CITY,ville2);
            db.update(CityDB.FeedEntry.TABLE_NAME, values,null,null);
            ville = ville2;
            ((TextView) findViewById(R.id.emplacement)).setText(R.string.position);
            ((TextView) findViewById(R.id.emplacement)).append(ville);
        }
    }
}