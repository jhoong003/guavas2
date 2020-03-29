package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guavas.data.model.UserProfile;
import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

/**
 * This Fragment controls Profile update feature.
 * It display data retrieve from the database
 * It inserts & update data into the database
 *
 * @author zane_
 */
public class ProfileEditFragment extends Fragment implements Subject {

    private static String PHONE_KEY ="key";

    private String gender, phone;
    private EditText txtFirstName, txtLastName, txtDay, txtMonth, txtYear, txtEmail;
    private RadioGroup radioGrpGender;
    private RadioButton radioBtnM, radioBtnF;
    private Button btnSave;
    private UserProfile updateUserProfile = new UserProfile();

    private FragmentObserver observer;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    public static ProfileEditFragment newInstance(String phone) {
        ProfileEditFragment fragment = new ProfileEditFragment();
        Bundle args = new Bundle();
        args.putString(PHONE_KEY, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            phone = args.getString(PHONE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        txtFirstName = parent.findViewById(R.id.txtFirstName);
        txtLastName = parent.findViewById(R.id.txtLastName);

        radioGrpGender = parent.findViewById(R.id.radioGrpGender);
        radioBtnM = parent.findViewById(R.id.radioBtnGenderM);
        radioBtnF = parent.findViewById(R.id.radioBtnGenderF);

        txtDay = parent.findViewById(R.id.txtDay);
        txtMonth = parent.findViewById(R.id.txtMonth);
        txtYear = parent.findViewById(R.id.txtYear);
        txtEmail = parent.findViewById(R.id.txtEmail);

        btnSave = parent.findViewById(R.id.btnSave);

        //DISPLAY
        displayProfile();

        //EVENT LISTENERS
        radioBtnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        radioBtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                //Go back to profile's home page
                notifyObserver(new ProfileFragment());
            }
        });

        return parent;
    }

    //Function to handle Gender Radio Button
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioBtnGenderM:
                if (checked)
                    // Gender = Male
                    gender = "Male";
                break;
            case R.id.radioBtnGenderF:
                if (checked)
                    // Gender = Female
                    gender = "Female";
                break;
        }
    }

    public void displayProfile() {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(phone);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {//retrieve from db and display
                if (dataSnapshot.exists()) {
                    String fname = dataSnapshot.child("firstName").getValue().toString();
                    String lname = dataSnapshot.child("lastName").getValue().toString();
                    gender = dataSnapshot.child("gender").getValue().toString();
                    String dd = dataSnapshot.child("dobD").getValue().toString();
                    String dm = dataSnapshot.child("dobM").getValue().toString();
                    String dy = dataSnapshot.child("dobY").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    txtFirstName.setText(fname);
                    txtLastName.setText(lname);

                    if (gender.equals("Male")) {
                        radioGrpGender.check(R.id.radioBtnGenderM);
                    } else if (gender.equals("Female")) {
                        radioGrpGender.check(R.id.radioBtnGenderF);
                    }

                    txtDay.setText(dd);
                    txtMonth.setText(dm);
                    txtYear.setText(dy);
                    txtEmail.setText(email);
                }
            }

            public void onCancelled(@NotNull DatabaseError databaseError) {
                Log.e("ProfileEditFragment", databaseError.getDetails());
            }
        });
    }

    public void updateProfile() {
        boolean validate = validateForm();

        if (validate) {
            updateUserProfile.setFirstName(txtFirstName.getText().toString().trim());
            updateUserProfile.setLastName(txtLastName.getText().toString().trim());
            updateUserProfile.setGender(gender);
            updateUserProfile.setDobD(txtDay.getText().toString().trim());
            updateUserProfile.setDobM(txtMonth.getText().toString().trim());
            updateUserProfile.setDobY(txtYear.getText().toString().trim());

            updateUserProfile.setEmail(txtEmail.getText().toString().trim());

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("UserProfile");
            reff.child(phone).setValue(updateUserProfile);
            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Please fill in required fields", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validateForm() {
        if (txtFirstName.getText().toString().length() == 0) {
            txtFirstName.setError("First Name cannot be empty");
            return false;
        } else if (txtLastName.getText().toString().length() == 0) {
            txtLastName.setError("Last Name cannot be empty");
            return false;
        } else if (txtDay.getText().toString().length() == 0) {
            txtDay.setError("DOB Day cannot be empty");
            return false;
        } else if (txtMonth.getText().toString().length() == 0) {
            txtMonth.setError("DOB Month cannot be empty");
            return false;
        } else if (txtYear.getText().toString().length() == 0) {
            txtYear.setError("DOB Year cannot be empty");
            return false;
        } else if (txtEmail.getText().toString().length() == 0) {
            txtEmail.setError("Email cannot be empty");
            return false;
        }

        return true;
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
