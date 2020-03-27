package com.example.guavas.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.guavas.R;
import com.example.guavas.controller.DataProcessor;
import com.example.guavas.data.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.factory.DataProcessorFactory;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ViewDetailsContentFragment extends Fragment implements ViewDetailsGraphFragment.Listener,
        ViewDetailsDataFragment.Listener{

    private static final String POSITION_KEY = "Pos";
    private static final String DATATYPE_KEY ="data";

    private DataType dataType;
    private int position;
    private DataProcessor dataProcessor;
    private ArrayList<Entry> entries;
    private FragmentManager fragmentManager;

    private ProgressBar progressBar;

    public static ViewDetailsContentFragment newInstance(DataType dataType, int position){
        System.out.println(position);
        ViewDetailsContentFragment fragment = new ViewDetailsContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY,dataType);
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            dataType = args.getParcelable(DATATYPE_KEY);
            position = args.getInt(POSITION_KEY);
            dataProcessor = DataProcessorFactory.getDataProcessor(position);
            fragmentManager = getChildFragmentManager();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrieveDatabase();
        View parent = inflater.inflate(R.layout.fragment_view_detail_content, container, false);
        progressBar = (ProgressBar) parent.findViewById(R.id.progress_bar);
        return parent;
    }

    private void retrieveDatabase(){
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        String userPhone = preferences.getString("phoneNumber", null);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(userPhone).child(dataType.getDataTypeName());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MedicalRecord> records = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                    records.add(record);
                }

                entries = dataProcessor.processData(records);
                progressBar.setVisibility(View.GONE);
                if (isAdded()) setupChildFragment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Error loading from database in class ViewDetailsContentFragment!");
            }
        });
    }

    private void setupChildFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ViewDetailsGraphFragment graphFragment = ViewDetailsGraphFragment.newInstance(dataType, entries);
        graphFragment.setListener(this);
        transaction.replace(R.id.graph_container, graphFragment);
        ViewDetailsDataFragment dataFragment = ViewDetailsDataFragment.newInstance(dataType, entries);
        dataFragment.setListener(this);
        transaction.replace(R.id.data_container, dataFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public float getGranularity() {
        return dataProcessor.getGranularity();
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return dataProcessor.getAxisLabel(value, axis);
    }
}
