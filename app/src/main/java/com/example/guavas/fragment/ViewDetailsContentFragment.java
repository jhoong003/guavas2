package com.example.guavas.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.guavas.R;
import com.example.guavas.controller.DataProcessor;
import com.example.guavas.data.entity.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.factory.DataProcessorFactory;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

/**
 * A fragment that displays the content of a medical data type. The content include the graph and the list of data.
 *
 * @see ViewDetailsDataFragment
 * @see ViewDetailsGraphFragment
 */
public class ViewDetailsContentFragment extends Fragment implements ViewDetailsGraphFragment.Listener,
        ViewDetailsDataFragment.Listener {

    private static final String POSITION_KEY = "Pos";
    private static final String DATATYPE_KEY = "data";

    private DataType dataType;
    private DataProcessor dataProcessor;
    private ArrayList<Entry> entries;
    private FragmentManager fragmentManager;

    private ProgressBar progressBar;

    /**
     * Gets a new instance of this fragment.
     *
     * @param dataType the chosen medical data type.
     * @param position the position of the tab.
     * @return a new instance of this fragment.
     */
    public static ViewDetailsContentFragment newInstance(DataType dataType, int position) {
        System.out.println(position);
        ViewDetailsContentFragment fragment = new ViewDetailsContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY, dataType);
        args.putInt(POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sets up the data from the saved system state.
     *
     * @param savedInstanceState the saved system state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            dataType = args.getParcelable(DATATYPE_KEY);
            int position = args.getInt(POSITION_KEY);
            dataProcessor = DataProcessorFactory.getDataProcessor(position);
            fragmentManager = getChildFragmentManager();
        }
    }

    /**
     * Inflates layout and setup the fragment.
     *
     * @param inflater           the inflater.
     * @param container          the container.
     * @param savedInstanceState the saved state.
     * @return the user interface.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrieveDatabase();
        View parent = inflater.inflate(R.layout.fragment_view_detail_content, container, false);
        progressBar = (ProgressBar) parent.findViewById(R.id.progress_bar);
        return parent;
    }

    /**
     * Retrieves data from the database.
     */
    private void retrieveDatabase() {
        String key;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account == null) {
            SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            key = preferences.getString("phoneNumber", null);
        } else {
            key = account.getDisplayName();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(key).child(dataType.getDataTypeName());

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

    /**
     * Sets up the graph and the list of data.
     */
    private void setupChildFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ViewDetailsGraphFragment graphFragment = ViewDetailsGraphFragment.newInstance(dataType, entries);
        graphFragment.setListener(this);
        transaction.replace(R.id.graph_container, graphFragment);
        ViewDetailsDataFragment dataFragment = ViewDetailsDataFragment.newInstance(dataType, entries);
        dataFragment.setListener(this);
        transaction.replace(R.id.data_container, dataFragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * Gets the granularity of the graph.
     *
     * @return the granularity.
     */
    @Override
    public float getGranularity() {
        return dataProcessor.getGranularity();
    }

    /**
     * Gets the label of the x-coordinates of the graph.
     *
     * @param value the value the label belongs to.
     * @param axis  the x-axis.
     * @return the label.
     */
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return dataProcessor.getAxisLabel(value, axis);
    }
}
