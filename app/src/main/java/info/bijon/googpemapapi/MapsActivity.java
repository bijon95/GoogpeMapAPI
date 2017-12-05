package info.bijon.googpemapapi;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ReciveData rData;
    private GPSTracker gps;

    Double latitude;
    Double longitude;
    double myLatitude,myLangtude;
    TextView tvtext , distancetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvtext = (TextView) findViewById(R.id.textView);
        distancetv = (TextView) findViewById(R.id.textView);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

         //  tvtext.setText(getIntent().getStringExtra("data"));

    //  latitude=Double.parseDouble(getIntent().getStringExtra("latitude"));
    //    longitude=Double.parseDouble(getIntent().getStringExtra("langtude"));
      //  myLatitude = getIntent().getStringExtra("myLatitude");
      //  myLangtude = getIntent().getStringExtra("myLangtude");

        myLatitude  =  24.901263 ;
        myLangtude = 91.866609;
        latitude = 24.8884084;
        longitude =91.8741362 ;




//distancetv.setText(distance(latitude,longitude,myLatitude,myLangtude,"k")+"");
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       GPSTracker gps = new GPSTracker(MapsActivity.this);

        // Add a marker in Sydney and move the camera
       LatLng sydney = new LatLng(gps.getLatitude(),gps.getLongitude());
       mMap.addMarker(new MarkerOptions().position(sydney).title("He is Here"));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       mMap.setMinZoomPreference(16);
    }
}


