package info.bijon.googpemapapi;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Bj on 22-10-17.
 */

public class ReciveData extends AppCompatActivity implements ValueEventListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference= firebaseDatabase.getReference();
    private DatabaseReference latitudeData = databaseReference.child("Latitude");
    private DatabaseReference longtudeData = databaseReference.child("longtude");
    private DatabaseReference textData = databaseReference.child("text");
String latitude, langtude, text;
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue(String.class)!=null) {
            String key = dataSnapshot.getKey();

            if(key.equals("Latitude")){
                String latitudeValue = dataSnapshot.getValue(String.class);
                this.latitude=latitudeValue;
            }

            else if(key.equals("longtude")){
                String langtudeValue = dataSnapshot.getValue(String.class);
                this.langtude=langtudeValue;
            }

            else if(key.equals("text")){
                String textValue = dataSnapshot.getValue(String.class);
                this.text=textValue;
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

    public String getLatitude (){
            String latitudeVal = this.latitude;
        return latitudeVal;
    }

    public   String getLangtude(){
        String langtudeVal=this.langtude;
        return langtudeVal;
    }
    public String getTextValue(){
        String textVal;
        if (text!=null){
            textVal = this.text;
        }
        else {
            textVal = "Data from ReciveData";
        }
        return textVal;
    }
}
