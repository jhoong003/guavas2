package com.example.guavas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.guavas.adapter.FirebaseDataAdapter;
import com.example.guavas.controller.BMICalculator;
import com.example.guavas.data.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.fragment.AddMeasurementFragment;
import com.example.guavas.fragment.RemoveMeasurementDialogFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowAllActivity extends AppCompatActivity implements AddMeasurementFragment.Listener,
        FirebaseDataAdapter.Listener{

    private RecyclerView recyclerView;

    private AddMeasurementFragment fragment;
    private DataType dataType;
    private FirebaseDataAdapter adapter;
    private String userPhone;

    private int compoundIndex;
    private ArrayList<Pair<Double, Long> > compoundData;

    public static final String DATATYPE_KEY = "data type key";

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        getUserPhone();
        setupDataType();
        setupToolbar();
        setupRecyclerView();
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void getUserPhone(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        userPhone = preferences.getString("phoneNumber", null);
    }

    private void setupDataType() {
        dataType = getIntent().getParcelableExtra(DATATYPE_KEY);
        if (dataType.isCompound()){
            compoundIndex = 0;
            compoundData = new ArrayList<>();
        }
    }

    private void setupRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        fetchData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void fetchData(){
        Query query = FirebaseDatabase.getInstance().getReference().child(userPhone).child(dataType.getDataTypeName()).orderByChild("time");
        FirebaseRecyclerOptions<MedicalRecord> options = new FirebaseRecyclerOptions.Builder<MedicalRecord>()
                .setQuery(query, new SnapshotParser<MedicalRecord>() {
                    @NonNull
                    @Override
                    public MedicalRecord parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new MedicalRecord(Long.parseLong(snapshot.child("time").getValue().toString()),
                                Double.parseDouble(snapshot.child("measurement").getValue().toString()));
                    }
                }).build();

        adapter = new FirebaseDataAdapter(options, dataType);
        adapter.setListener(this);

        recyclerView.setAdapter(adapter);
    }

    public void onClickAddData(View view){
        if (fragment == null){
            compoundIndex = 0;
            compoundData.clear();
        }

        if (!dataType.isCompound())
            fragment = AddMeasurementFragment.newInstance(dataType);
        else {
            fragment = AddMeasurementFragment.newInstance(dataType.getCompoundAtIndex(compoundIndex));
        }
        fragment.setListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onClickDone(View view, double measurement, long timeInMillis) {
        if (formIsValid()) {
            getSupportFragmentManager().popBackStackImmediate();
            if (dataType.isCompound()){
                Pair<Double, Long> pair = new Pair<>(measurement, timeInMillis);
                compoundData.add(pair);
                compoundIndex++;
                if (compoundIndex < dataType.getNumOfCompound()) onClickAddData(view);
                else{
                    for(int i=0;i<dataType.getNumOfCompound();i++){
                        addToDatabase(dataType.getCompoundAtIndex(i).getDataTypeName(),
                                compoundData.get(i).first,
                                compoundData.get(i).second);

                    }
                    addToDatabase(dataType.getDataTypeName(), BMICalculator.calculate(compoundData),timeInMillis);
                    fragment = null;
                }
            }else {
                addToDatabase(dataType.getDataTypeName(), measurement, timeInMillis);
                fragment = null;
                onClickClose();
            }

        }else{
            Toast.makeText(fragment.getContext(), "Please enter measurement", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formIsValid(){
        return fragment.isValidForm();
    }

    @Override
    public void onClickClose() {
        if (dataType.isCompound()){
            fragment = null;
            compoundData.clear();
        }

        getSupportFragmentManager().popBackStackImmediate();
    }

    public void launchDialog(double measurement, long timeInMillis, DataType dataType){
        RemoveMeasurementDialogFragment dialogFragment =
                RemoveMeasurementDialogFragment.newInstance(measurement, timeInMillis, dataType);
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    private void addToDatabase(String dataTypeName, double measurement, long timeInMillis){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(userPhone);

        MedicalRecord medicalRecord = new MedicalRecord(timeInMillis, measurement);

        String key = reference.push().getKey();
        reference.child(dataTypeName).child(key).setValue(medicalRecord)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Snackbar.make(findViewById(R.id.coordinator_layout), "Your measurement has been added.", Snackbar.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(findViewById(R.id.coordinator_layout), "Failed to add to the database! Please try again!", Snackbar.LENGTH_SHORT).show();
                }
            });
    }

    private void setupToolbar(){
        getSupportActionBar().setTitle(dataType.getDataTypeName());
    }
}
