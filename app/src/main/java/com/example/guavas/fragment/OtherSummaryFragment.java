package com.example.guavas.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.guavas.R;
import com.example.guavas.data.entity.DataType;
import com.example.guavas.firebaseDAO.MeasurementDAO;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;
import com.example.guavas.view.PlusMinusEditText;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A fragment that displays the "Other" in the health summary section.
 */
public class OtherSummaryFragment extends Fragment implements Subject, View.OnClickListener {

    private FragmentObserver observer;

    private View parent;
    private Button homeButton, saveButton;
    private ToggleButton strokeButton, hyperButton;
    private PlusMinusEditText pregnancyEditText, cigarreteEditText;

    private TextView notAvailableText;
    private LinearLayout pregnancyLinearLayout;

    public OtherSummaryFragment() {
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
        parent = inflater.inflate(R.layout.fragment_other_summary, container, false);
        initView();
        restoreFromDatabase();
        return parent;
    }

    /**
     * Gets reference to the Views.
     */
    private void initView() {
        strokeButton = parent.findViewById(R.id.toggle_button_stroke);
        strokeButton.setOnClickListener(this);
        hyperButton = parent.findViewById(R.id.toggle_button_hypertension);
        hyperButton.setOnClickListener(this);
        pregnancyEditText = parent.findViewById(R.id.pregnancy_count);
        cigarreteEditText = parent.findViewById(R.id.cigarette_count);
        saveButton = parent.findViewById(R.id.button_save);
        saveButton.setOnClickListener(this);
        homeButton = parent.findViewById(R.id.button_home);
        homeButton.setOnClickListener(this);

        notAvailableText = parent.findViewById(R.id.text_not_available);
        pregnancyLinearLayout = parent.findViewById(R.id.linear_layout_pregnancy);
    }

    /**
     * Sets up save and home button.
     *
     * @param v the clicked button.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_home) notifyObserver(new HealthSummaryFragment());
        else if (v.getId() == R.id.button_save) {
            updateDataToDatabase();
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
            notifyObserver(new HealthSummaryFragment());
        }
    }

    /**
     * Saves data to the database.
     */
    private void updateDataToDatabase() {
        writeDatabase(strokeButton.isChecked() ? 1 : 0, new DataType("Stroke"));
        writeDatabase(hyperButton.isChecked() ? 1 : 0, new DataType("Hypertension"));
        writeDatabase(pregnancyEditText.getValue(), new DataType("Pregnancy Count"));
        writeDatabase(cigarreteEditText.getValue(), new DataType("Cigarrete per Day"));
    }

    /**
     * Retrieves data from the database.
     */
    private void restoreFromDatabase() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        String key;
        if (account == null) {
            SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            key = preferences.getString("phoneNumber", null);
        } else {
            key = account.getDisplayName();
        }

        DatabaseReference refStroke = FirebaseDatabase.getInstance().getReference(key).child("Stroke");
        DatabaseReference refHypertension = FirebaseDatabase.getInstance().getReference(key).child("Hypertension");
        DatabaseReference refPregnancy = FirebaseDatabase.getInstance().getReference(key).child("Pregnancy Count");
        DatabaseReference refCigarette = FirebaseDatabase.getInstance().getReference(key).child("Cigarrete per Day");
        DatabaseReference refGender = FirebaseDatabase.getInstance().getReference("UserProfile").child(key).child("gender");

        refStroke.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean state = Integer.parseInt(dataSnapshot.getValue().toString()) == 1;
                    strokeButton.setChecked(state);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OtherSumFragment", databaseError.getDetails());
            }
        });

        refHypertension.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean state = Integer.parseInt(dataSnapshot.getValue().toString()) == 1;
                    hyperButton.setChecked(state);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OtherSumFragment", databaseError.getDetails());
            }
        });

        refPregnancy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int count = Integer.parseInt(dataSnapshot.getValue().toString());
                    pregnancyEditText.setValue(count);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OtherSumFragment", databaseError.getDetails());
            }
        });

        refCigarette.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int count = Integer.parseInt(dataSnapshot.getValue().toString());
                    cigarreteEditText.setValue(count);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OtherSumFragment", databaseError.getDetails());
            }
        });

        refGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notAvailableText.setVisibility(View.GONE);
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue().toString().equals("Male")) {
                        notAvailableText.setVisibility(View.VISIBLE);
                        pregnancyLinearLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OtherSumFragment", databaseError.getDetails());
            }
        });
    }

    /**
     * Saves data to the database.
     *
     * @param value    the count.
     * @param dataType the medical data type.
     */
    private void writeDatabase(int value, DataType dataType) {
        MeasurementDAO dao = new MeasurementDAO(getActivity(), dataType);
        dao.save(value);
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
