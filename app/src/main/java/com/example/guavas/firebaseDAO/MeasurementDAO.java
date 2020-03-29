package com.example.guavas.firebaseDAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.guavas.data.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MeasurementDAO implements MedsDAO {

    private Context context;
    private DataType dataType;
    private String key;

    public MeasurementDAO(Context userContext, DataType dataType){
        context = userContext;
        this.dataType = dataType;
        initKey();
    }

    private void initKey(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account == null){
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            key = preferences.getString("phoneNumber", null);
        }else{
            key = account.getDisplayName();
        }
    }

    @Override
    public Optional get(long id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void save(Object o) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(key);
        if (o instanceof MedicalRecord) {
            MedicalRecord record = (MedicalRecord) o;

            MedicalRecord medicalRecord = new MedicalRecord(record.getTime(), record.getMeasurement());

            String randomKey = reference.push().getKey();
            reference.child(dataType.getDataTypeName()).child(randomKey).setValue(medicalRecord);
        }else{
            if (o instanceof Integer){
                reference.child(dataType.getDataTypeName()).setValue((Integer)o);
            }
        }
    }

    @Override
    public void update(Object o, String[] params) {

    }

    @Override
    public void delete(Object o) {
        final MedicalRecord recordToRemove = (MedicalRecord) o;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(key).child(dataType.getDataTypeName());

        Query query = reference.orderByChild("time").equalTo(recordToRemove.getTime());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    if (record.getMeasurement() == recordToRemove.getMeasurement()) {
                        snapshot.getRef().removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("MeasurementDAO", "Successfully removed from database");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("MeasurementDAO", "Failed to remove from database\n" + e.getMessage());
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MeasurementDAO", databaseError.getDetails());
            }
        });
    }
}
