package info.bijon.googpemapapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements ValueEventListener {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference= firebaseDatabase.getReference();
    private DatabaseReference latitudeData = databaseReference.child("Latitude");
    private DatabaseReference longtudeData = databaseReference.child("longtude");
    private DatabaseReference textData = databaseReference.child("text");
    String latitude, langtude, text;
    double dLatitude,dLangtude;
        GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button admin = (Button) findViewById(R.id.button2);
        Button btn2 = (Button) findViewById(R.id.admin);



        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(HomeActivity.this);
                if(gps.canGetLocation()){

                    dLatitude = gps.getLatitude();
                    dLangtude = gps.getLongitude();


                }else{

                    gps.showSettingsAlert();
                }

                String myLatitude= String.valueOf(dLatitude);
                String myLongtude = String.valueOf(dLangtude);
                Intent i = new Intent(HomeActivity.this,MapsActivity.class);
                i.putExtra("data",text);
                i.putExtra("latitude", latitude);
                i.putExtra("langtude",langtude);
                i.putExtra("myLatitude",myLatitude);
                i.putExtra("myLangtude",myLongtude);
                startActivity(i);
                Toast.makeText(HomeActivity.this,"Data"+langtude+langtude,Toast.LENGTH_SHORT).show();


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SendDataToDatabase.class);
                Intent intent = new Intent();
                startActivity(i);
            }
        });


    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue(String.class)!=null) {
            String key = dataSnapshot.getKey();

            if (key.equals("Latitude")) {
                String latitudeValue = dataSnapshot.getValue(String.class);
                this.latitude = latitudeValue;
            } else if (key.equals("longtude")) {
                String langtudeValue = dataSnapshot.getValue(String.class);
                this.langtude = langtudeValue;
            } else if (key.equals("text")) {
                String textValue = dataSnapshot.getValue(String.class);
                this.text = textValue;
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        latitudeData.addValueEventListener(this);
        longtudeData.addValueEventListener(this);
        textData.addValueEventListener(this);
    }
}
