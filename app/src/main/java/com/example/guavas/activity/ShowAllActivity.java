package com.example.guavas.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.guavas.R;
import com.example.guavas.adapter.FirebaseDataAdapter;
import com.example.guavas.controller.BMICalculator;
import com.example.guavas.data.entity.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.firebaseDAO.MeasurementDAO;
import com.example.guavas.fragment.AddMeasurementFragment;
import com.example.guavas.fragment.RemoveMeasurementDialogFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is the activity which the user can see all the recorded data. It is also the page that provides the feature
 * to add a new record.
 */
public class ShowAllActivity extends AppCompatActivity implements AddMeasurementFragment.Listener,
        FirebaseDataAdapter.Listener {

    private RecyclerView recyclerView;

    private AddMeasurementFragment fragment;
    private DataType dataType;
    private FirebaseDataAdapter adapter;
    private String key;

    private int compoundIndex;
    private ArrayList<Pair<Double, Long>> compoundData;

    public static final String DATATYPE_KEY = "data type key";

    /**
     * Start listening to database events when the activity starts.
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * Sets up the whole activity page.
     *
     * @param savedInstanceState the saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        getDatabaseKey();
        setupDataType();
        setupFAB();
        setupToolbar();
        setupRecyclerView();
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    /**
     * Stop listening to database events when the activity stopped.
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /**
     * Gets the user ID for the current user.
     */
    private void getDatabaseKey() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            key = preferences.getString("phoneNumber", null);
        } else {
            key = account.getDisplayName();
        }
    }

    /**
     * Gets the chosen medical data type.
     */
    private void setupDataType() {
        dataType = getIntent().getParcelableExtra(DATATYPE_KEY);
        if (dataType.isCompound()) {
            compoundIndex = 0;
            compoundData = new ArrayList<>();
        }
    }

    /**
     * Sets up the floating action button. If the chosen medical type is not editable, the button will not be displayed.
     */
    private void setupFAB() {
        if (!dataType.isEditable()) {
            FloatingActionButton button = findViewById(R.id.fab_show_all);
            button.setVisibility(View.GONE);
        }
    }

    /**
     * Sets up the recycler view so that it displays list of data fetched from the database.
     */
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        fetchData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    /**
     * Retrieves data from the database.
     */
    private void fetchData() {
        Query query = FirebaseDatabase.getInstance().getReference().child(key).child(dataType.getDataTypeName()).orderByChild("time");
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

    /**
     * Displays the form when the user clicks on the "+" floating action button.
     *
     * @param view the floating action button.
     */
    public void onClickAddData(View view) {
        if (fragment == null && dataType.isCompound()) {
            compoundIndex = 0;
            compoundData.clear();
        }

        if (!dataType.isCompound())
            fragment = AddMeasurementFragment.newInstance(dataType);
        else {
            fragment = AddMeasurementFragment.newInstance(dataType.getCompoundAtIndex(compoundIndex));
        }
        fragment.setListener(this);

        showFragment(fragment);

    }

    /**
     * Displays a fragment to the user.
     *
     * @param fragment the fragment to the display.
     */
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    /**
     * Saves the new medical record to the database when the user clicked the check button.
     *
     * @param view         the clicked View.
     * @param measurement  the submitted measurement.
     * @param timeInMillis the submitted time in milliseconds.
     */
    @Override
    public void onClickDone(View view, double measurement, long timeInMillis) {
        if (formIsValid()) {
            getSupportFragmentManager().popBackStackImmediate();
            if (dataType.isCompound()) {
                Pair<Double, Long> pair = new Pair<>(measurement, timeInMillis);
                compoundData.add(pair);
                compoundIndex++;
                if (compoundIndex < dataType.getNumOfCompound()) onClickAddData(view);
                else {
                    addBMIToDatabase(timeInMillis);
                    fragment = null;
                }
            } else {
                addToDatabase(measurement, timeInMillis);
                fragment = null;
                onClickClose();
            }

        } else {
            Toast.makeText(fragment.getContext(), "Please enter measurement", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if the form is valid. A form is valid if the form is not empty, and the time of record is less than
     * or equal to the current time.
     *
     * @return <code>true</code> if the form is valid
     * @see AddMeasurementFragment#isValidForm()
     */
    private boolean formIsValid() {
        return fragment.isValidForm();
    }

    /**
     * Close the form when the "X" button is pressed.
     */
    @Override
    public void onClickClose() {
        if (dataType.isCompound()) {
            fragment = null;
            compoundData.clear();
        }

        getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Tries to display a Dialog to the user. Only editable data type is allowed.
     *
     * @param measurement  the value of the record.
     * @param timeInMillis the time of the record.
     * @param dataType     the chosen medical record type.
     */
    public void launchDialog(double measurement, long timeInMillis, DataType dataType) {
        if (dataType.isEditable()) showDialog(measurement, timeInMillis, dataType);
        else showSnackbar("This health section cannot be edited individually");
    }

    /**
     * Displays a Dialog to the user.
     *
     * @param measurement  the value of record.
     * @param timeInMillis the time of record.
     * @param dataType     the chosen medical record type.
     */
    private void showDialog(double measurement, long timeInMillis, DataType dataType) {
        RemoveMeasurementDialogFragment dialogFragment =
                RemoveMeasurementDialogFragment.newInstance(measurement, timeInMillis, dataType);
        dialogFragment.show(getSupportFragmentManager(), null);
    }

    /**
     * Saves a medical record to the database.
     *
     * @param measurement  the value to save.
     * @param timeInMillis the time of record.
     */
    private void addToDatabase(double measurement, long timeInMillis) {

        MeasurementDAO dao = new MeasurementDAO(this, dataType);
        dao.save(new MedicalRecord(timeInMillis, measurement));
    }

    /**
     * Adds BMI data to the database.
     *
     * @param timeInMillis the time of record.
     */
    private void addBMIToDatabase(long timeInMillis) {

        for (int i = 0; i < dataType.getNumOfCompound(); i++) {
            MeasurementDAO dao = new MeasurementDAO(this, dataType.getCompoundAtIndex(i));
            dao.save(new MedicalRecord(compoundData.get(i).second, compoundData.get(i).first));
        }
        MeasurementDAO dao = new MeasurementDAO(this, dataType);
        dao.save(new MedicalRecord(timeInMillis, BMICalculator.calculate(compoundData)));

    }

    /**
     * Displays a snackbar containing a message for a short duration.
     *
     * @param message the message to display.
     */
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Sets the action bar. The title will be the same as the chosen feature, and the up button will be enabled.
     */
    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(dataType.getDataTypeName());
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Sets up button to behave like back button.
     *
     * @param item the selected option item.
     * @return <code>true</code> if the event is handled.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Up button behaves like back button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
