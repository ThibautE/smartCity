package hmin202.smart.thibaut.smartcity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private FirebaseAuth mAuth;
    ImageView imageView;
    TextView textName, textEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        /* Info google auth */

        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageViewProfile);
        textName = findViewById(R.id.textViewNameProfile);
        textEmail = findViewById(R.id.textViewEmailProfile);

        FirebaseUser user = mAuth.getCurrentUser();
        //Glide.with(this)
        //        .load(user.getPhotoUrl())
        //        .into(imageView);

        textName.setText(user.getDisplayName());
        textEmail.setText(user.getEmail());

        findViewById(R.id.disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                onStart();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void goToInteretActivity(View view){
        Intent intent = new Intent(this, InteretActivity.class);
        startActivity(intent);
    }

}
