package cpl20182team1.com;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();

    SharedPreferences sharedPref;

    FusedLocationProviderClient client;

    String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //처음일 경우 유저키 저장
        initializeUser();

        //위치 권한 요청
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);


        Button buttonSaveLatLng = findViewById(R.id.buttonSaveLatLng);
        buttonSaveLatLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null){
                                Long currentTimeLong = System.currentTimeMillis();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
                                String date = dateFormat.format(currentTimeLong);
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
                                String time = timeFormat.format(currentTimeLong);


                                Double latitude = location.getLatitude();
                                Double longitude = location.getLongitude();

                                Toast.makeText(getApplicationContext(), date + time + "\nLatitude:" + latitude + "\nLongitude:" + longitude,Toast.LENGTH_LONG).show();

                                LocationInfo locationInfo = new LocationInfo(date, time,"test",latitude,longitude);
                                mDatabaseReference.child(userKey).child(currentTimeLong.toString()).setValue(locationInfo);
                            }

                        }
                    });
                }else{
                    requestPermission();
                }

            }
        });
    }

    private void initializeUser(){
        sharedPref = getSharedPreferences("USER_ACCOUNT", MODE_PRIVATE);

        userKey = sharedPref.getString("USER_KEY", "");
        if(userKey.equals("")){
            userKey = mDatabaseReference.push().getKey();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("USER_KEY", userKey);
            editor.apply();
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

}
