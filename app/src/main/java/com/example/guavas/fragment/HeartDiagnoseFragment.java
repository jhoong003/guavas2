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
import android.widget.ToggleButton;

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

public class HeartDiagnoseFragment extends Fragment implements Subject, View.OnClickListener{

    private float[] factors = new float[12];
    private String key;

    private FragmentObserver observer;

    private View parent;
    private ToggleButton factor1, factor5, factor6;
    private EditText factor2, factor4, factor7, factor8, factor9, factor10, factor11, factor12;


    public HeartDiagnoseFragment() {
        // Required empty public constructor
    }

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
        parent = inflater.inflate(R.layout.fragment_heart_diagnose, container, false);
        Button button = parent.findViewById(R.id.button_submit);
        button.setOnClickListener(this);

        initView();
        smartFillForm();

        return parent;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) send();
    }

    private void initView(){
        factor1 = (ToggleButton) parent.findViewById(R.id.Hfactor1);
        factor2 = (EditText) parent.findViewById(R.id.Hfactor2);
        factor4 = (EditText) parent.findViewById(R.id.Hfactor4);
        factor5 = (ToggleButton) parent.findViewById(R.id.Hfactor5);
        factor6 = (ToggleButton) parent.findViewById(R.id.Hfactor6);
        factor7 = (EditText) parent.findViewById(R.id.Hfactor7);
        factor8 = (EditText) parent.findViewById(R.id.Hfactor8);
        factor9 = (EditText) parent.findViewById(R.id.Hfactor9);
        factor10 = (EditText) parent.findViewById(R.id.Hfactor10);
        factor11= (EditText) parent.findViewById(R.id.Hfactor11);
        factor12 = (EditText) parent.findViewById(R.id.Hfactor12);
    }

    private void send(){
        factors[0] = factor1.isChecked() ? 1 : 0;
        factors[1] =Float.parseFloat(factor2.getText().toString());
        factors[3] =Float.parseFloat(factor4.getText().toString());
        factors[2] = factors[3] == 0 ? 0 : 1;
        factors[4] = factor5.isChecked() ? 1 : 0;
        factors[5] = factor6.isChecked() ? 1 : 0;
        factors[6] =Float.parseFloat(factor7.getText().toString());
        factors[7] =Float.parseFloat(factor8.getText().toString());
        factors[8] =Float.parseFloat(factor9.getText().toString());
        factors[9] =Float.parseFloat(factor10.getText().toString());
        factors[10] =Float.parseFloat(factor11.getText().toString());
        factors[11] =Float.parseFloat(factor12.getText().toString());

        ChronicDiagnosisResultFragment resultFragment =
                ChronicDiagnosisResultFragment.newInstance(factors, "Heart Disease", "Heart.tflite");
        notifyObserver(resultFragment);
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

                    factor2.setText(Integer.toString(age));

                    String gender = dataSnapshot.child("gender").getValue().toString();
                    boolean genderBool = gender.equals("Male");

                    factor1.setChecked(genderBool);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartDiagFragment", databaseError.getDetails());
            }
        });
    }

    private void smartFillFromSummary(){
        DatabaseReference refBMI = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Body-Mass Index");
        DatabaseReference refAlcohol = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Alcohol Consumption");
        DatabaseReference refSystolic = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Blood Pressure (Systolic)");
        DatabaseReference refDiastolic = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Blood Pressure (Diastolic)");
        DatabaseReference refHeart = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Heart Rate");
        DatabaseReference refGlucose = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Blood Glucose");
        DatabaseReference refStroke = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Stroke");
        DatabaseReference refHyper = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Hypertension");
        DatabaseReference refCigarette = FirebaseDatabase.getInstance().getReference()
                .child(key).child("Cigarrete per Day");

        refAlcohol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor7.setText(Float.toString(entries.get(entries.size()-1).getY()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
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
                factor8.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

        refDiastolic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor9.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
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
                factor10.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

        refHeart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor11.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

        refGlucose.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                ArrayList<Entry> entries = new DailyDataProcessor().processData(records);
                factor12.setText(Float.toString(entries.get(entries.size()-1).getY()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

        refStroke.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean state = Integer.parseInt(dataSnapshot.getValue().toString()) == 1;
                    factor5.setChecked(state);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

        refHyper.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean state = Integer.parseInt(dataSnapshot.getValue().toString()) == 1;
                    factor6.setChecked(state);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });

        refCigarette.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String count = dataSnapshot.getValue().toString();
                    factor4.setText(count);
                }else{
                    factor4.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("HeartFragment", databaseError.getDetails());
            }
        });
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
