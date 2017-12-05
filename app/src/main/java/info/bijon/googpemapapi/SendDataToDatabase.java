package info.bijon.googpemapapi;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendDataToDatabase extends AppCompatActivity implements ValueEventListener {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference= firebaseDatabase.getReference();
    private DatabaseReference latitudeData = databaseReference.child("Latitude");
    private DatabaseReference longtudeData = databaseReference.child("longtude");
    private DatabaseReference textValue = databaseReference.child("text");
    private  String locationAddress;
    GPSTracker gps;
    double latitude, longtude;

    String latitudeValue, langtudeVal;
    TextView adressTV;
    EditText ettext;
    Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data_to_database);
        sendBtn = (Button) findViewById(R.id.button4);
        ettext = (EditText) findViewById(R.id.editText);
        gps = new GPSTracker(SendDataToDatabase.this);
        adressTV = (TextView) findViewById(R.id.textView);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textValue.setValue( ettext.getText().toString());
            }
        });
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longtude = gps.getLongitude();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longtude, Toast.LENGTH_LONG).show();
        }else{

            gps.showSettingsAlert();
        }
        latitudeValue = String.valueOf(latitude);
        langtudeVal = String.valueOf(longtude);

        latitude = gps.getLatitude();
        longtude = gps.getLongitude();
        latitudeData.setValue(latitudeValue);
        longtudeData.setValue(langtudeVal);
        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(latitude, longtude,
                getApplicationContext(), new GeocoderHandler());


    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {


    }



    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        latitudeData.addValueEventListener(this);
        longtudeData.addValueEventListener(this);
        textValue.addValueEventListener(this);
    }

    private  class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            adressTV.setText(locationAddress);
        }
    }

}
