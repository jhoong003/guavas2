package com.example.guavas.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guavas.R;
import com.example.guavas.controller.DailyDataProcessor;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;
import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DiabetesDiagnosisFragment extends Fragment implements Subject, View.OnClickListener {

    private float[] factors = new float[7];
    private FragmentObserver observer;
    private String key;

    private View parent;
    private EditText factor1, factor2, factor3, factor4, factor5, factor6, factor7;

    public DiabetesDiagnosisFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account == null){
            SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            key = preferences.getString("phoneNumber", null);
        }else{
            key = account.getDisplayName();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_diabetes_diagnosis, container, false);

        initViews();
        smartFillForm();

        return parent;
    }

    private void initViews(){
        factor1 = (EditText) parent.findViewById(R.id.factor1);
        factor2 = (EditText) parent.findViewById(R.id.factor2);
        factor3 = (EditText) parent.findViewById(R.id.factor3);
        factor4 = (EditText) parent.findViewById(R.id.factor4);
        factor5 = (EditText) parent.findViewById(R.id.factor5);
        factor6 = (EditText) parent.findViewById(R.id.factor6);
        factor7 = (EditText) parent.findViewById(R.id.factor7);

        Button button = parent.findViewById(R.id.button_submit);
        button.setOnClickListener(this);
    }

    private void smartFillForm(){
        smartFillFromProfile();
        smartFillFromSummary();
    }

    private void smartFillFromProfile(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserProfile").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Calendar curDate = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                    int year = Integer.parseInt(dataSnapshot.child("dobY").getValue().toString());
                    int month = Integer.parseInt(dataSnapshot.child("dobM").getValue().toString());
                    int day = Integer.parseInt(dataSnapshot.child("dobD").getValue().toString());
                    int age = curDate.get(Calendar.YEAR)-year;

                    if (month < curDate.get(Calendar.MONTH)){
                        age--;
                    }else if (month == curDate.get(Calendar.MONTH)){
                        if (day < curDate.get(Calendar.DAY_OF_MONTH)){
                            age--;
                        }
                    }

                    factor7.setText(Integer.toString(age));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiabetesFragment", databaseError.getDetails());
            }
        });
    }

    private void smartFillFromSummary(){
        DatabaseReference refGlucose = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Blood Glucose");
        DatabaseReference refSystolic = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Blood Pressure (Systolic)");
        DatabaseReference refTricep = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Tricep Skin Thickness");
        DatabaseReference refInsulin = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Insulin");
        DatabaseReference refBMI = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Body-Mass Index");
        DatabaseReference refPregnancy = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Pregnancy Count");

        refGlucose.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor2.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiabetesFragment", databaseError.getDetails());
            }
        });

        refSystolic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor3.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiabetesFragment", databaseError.getDetails());
            }
        });

        refTricep.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor4.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiabetesFragment", databaseError.getDetails());
            }
        });

        refInsulin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor5.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiabetesFragment", databaseError.getDetails());
            }
        });

        refBMI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor6.setText(Float.toString(entries.get(entries.size()-1).getY()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DiabetesFragment", databaseError.getDetails());
            }
        });

        refPregnancy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String count = dataSnapshot.getValue().toString();
                    factor1.setText(count);
                }else{
                    factor1.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) send();
    }

    private void send(){
        factors[0] =Float.parseFloat(factor1.getText().toString());
        factors[1] =Float.parseFloat(factor2.getText().toString());
        factors[2] =Float.parseFloat(factor3.getText().toString());
        factors[3] =Float.parseFloat(factor4.getText().toString());
        factors[4] =Float.parseFloat(factor5.getText().toString());
        factors[5] =Float.parseFloat(factor6.getText().toString());
        factors[6]= Float.parseFloat(factor7.getText().toString());

        ChronicDiagnosisResultFragment fragment =
                ChronicDiagnosisResultFragment.newInstance(factors, "Diabetes", "Diabetes.tflite");
        notifyObserver(fragment);
    }

    @Override
    public void register(FragmentObserver observer) {
        this.observer = observer;
    }

    @Override
    public void unregister() {
        observer = null;
    }

    @Override
    public void notifyObserver(Fragment fragment) {
        observer.updateContainerWithFragment(fragment);
    }
}
