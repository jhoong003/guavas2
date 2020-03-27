package com.example.guavas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guavas.Entity.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Objects;

/**
 * This Activity controls Profile update feature.
 * It display data retrieve from the database
 * It inserts & update data into the database
 *
 * @author zane_
 */
public class ProfileEditActivity extends AppCompatActivity {
    String gender, phone;
    EditText txtFirstName, txtLastName, txtDay, txtMonth, txtYear, txtHeight, txtWeight, txtEmail;
    RadioGroup radioGrpGender;
    RadioButton radioBtnM, radioBtnF;
    Button btnSave;
    UserProfile userProfile = new UserProfile();
    UserProfile updateUserProfile = new UserProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        phone = bundle.getString("phone");

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);

        radioGrpGender = findViewById(R.id.radioGrpGender);
        radioBtnM = findViewById(R.id.radioBtnGenderM);
        radioBtnF = findViewById(R.id.radioBtnGenderF);

        txtDay = findViewById(R.id.txtDay);
        txtMonth = findViewById(R.id.txtMonth);
        txtYear = findViewById(R.id.txtYear);
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtEmail = findViewById(R.id.txtEmail);

        btnSave = findViewById(R.id.btnSave);

//        //GET DATA FROM DATABASE VIA UserProfile MODEL
//        userProfile = userProfile.getUserProfile(phone);
//
//        //DISPLAY THE DATA
//        if(userProfile != null){
//            txtFirstName.setText(userProfile.getFirstName());
//            txtLastName.setText(userProfile.getLastName());
//
//            gender = userProfile.getGender();
//            if(gender.equals("Male")){
//                radioGrpGender.check(R.id.radioBtnGenderM);
//            }else if(gender.equals("Female")){
//                radioGrpGender.check(R.id.radioBtnGenderF);
//            }
//
//            txtDay.setText(userProfile.getDobD());
//            txtMonth.setText(userProfile.getDobM());
//            txtYear.setText(userProfile.getDobY());
//            txtHeight.setText((int) userProfile.getHeight());
//            txtWeight.setText((int) userProfile.getWeight());
//            txtEmail.setText(userProfile.getEmail());
//        }

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

                //boolean updateResult = updateUserProfile.updateUserProfile(phone, updateUserProfile);
//                if(updateResult == false){
//                    Toast.makeText(ProfileEditActivity.this, "Profile updated unsuccessful", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(ProfileEditActivity.this, "Profile updated success", Toast.LENGTH_LONG).show();
//                }
            }
        });

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
                /*if(dataSnapshot.exists()){
                    maxid=(dataSnapshot.getChildrenCount());
                }*/

                if (dataSnapshot.exists()) {
                    String fname = dataSnapshot.child("firstName").getValue().toString();
                    String lname = dataSnapshot.child("lastName").getValue().toString();
                    gender = dataSnapshot.child("gender").getValue().toString();
                    String dd = dataSnapshot.child("dobD").getValue().toString();
                    String dm = dataSnapshot.child("dobM").getValue().toString();
                    String dy = dataSnapshot.child("dobY").getValue().toString();
                    String height = dataSnapshot.child("height").getValue().toString();
                    String weight = dataSnapshot.child("weight").getValue().toString();
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
                    txtHeight.setText(height);
                    txtWeight.setText(weight);
                    txtEmail.setText(email);
                }
            }

            public void onCancelled(@NotNull DatabaseError databaseError) {

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

            int height = Integer.parseInt(txtHeight.getText().toString().trim());
            updateUserProfile.setHeight(height);
            int weight = Integer.parseInt(txtWeight.getText().toString().trim());
            updateUserProfile.setWeight(weight);

            updateUserProfile.setEmail(txtEmail.getText().toString().trim());

            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("UserProfile");
            reff.child(phone).setValue(updateUserProfile);
            Toast.makeText(ProfileEditActivity.this, "Profile updated success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ProfileEditActivity.this, "Please fill in required fields", Toast.LENGTH_LONG).show();
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
        } else if (txtHeight.getText().toString().length() == 0) {
            txtHeight.setError("Height cannot be empty");
            return false;
        } else if (txtWeight.getText().toString().length() == 0) {
            txtWeight.setError("Weight cannot be empty");
            return false;
        } else if (txtEmail.getText().toString().length() == 0) {
            txtEmail.setError("Email cannot be empty");
            return false;
        }

        return true;
    }
}