package com.example.guavas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Calendar;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private String phoneNumber;
    private int google=0;
    private GoogleSignInClient mGoogleSignInClient;

    private View parent;
    private TextView lblFirstname, lblLastname, lblAge, lblHeight, lblWeight;
    private TextView mobileNumber;
    private DatabaseReference reff;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        phoneNumber = preferences.getString("phoneNumber", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_profile, container, false);
        setupButtons();
        displayProfile();

        return parent;
    }

    private void setupButtons(){
        Button btnEditProfile = parent.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(this);
        Button btnSignOut = parent.findViewById(R.id.signout_btn);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signout_btn){
            onClickSignOut();
        }else if (v.getId() == R.id.btnEditProfile){
            Toast.makeText(getContext(), "Go to edit profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProfile(){
        mobileNumber = parent.findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

        reff = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(phoneNumber);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()) {
                    String fname, lname, dy, height, weight;
                    lblFirstname = parent.findViewById(R.id.lblFirstname);
                    lblLastname = parent.findViewById(R.id.lblLastname);
                    lblAge = parent.findViewById(R.id.lblAge);
                    lblHeight = parent.findViewById(R.id.lblHeight);
                    lblWeight = parent.findViewById(R.id.lblWeight);

                    if(dataSnapshot.child("firstName").getValue() != null){
                        fname = dataSnapshot.child("firstName").getValue().toString();
                        lblFirstname.setText(fname);
                    }

                    if(dataSnapshot.child("lastName").getValue() != null){
                        lname = dataSnapshot.child("lastName").getValue().toString();
                        lblLastname.setText(lname);
                    }

                    if(dataSnapshot.child("dobY").getValue() != null){
                        dy = dataSnapshot.child("dobY").getValue().toString();

                        //String dob = dd+"/"+dm+"/"+dy;
                        //http://www.deboma.com/article/mobile-development/22/android-datepicker-and-age-calculation

                        int curYr = Calendar.getInstance().get(Calendar.YEAR);
                        int year = Integer.parseInt(dy);
                        int age = curYr-year;
                        //Toast.makeText(ProfileViewActivity.this, "this is age"+age, Toast.LENGTH_LONG).show();

                        lblAge.setText(age + " years old");
                    }

                    if(dataSnapshot.child("height").getValue() != null){
                        height = dataSnapshot.child("height").getValue().toString();
                        lblHeight.setText(height+"(cm)");
                    }

                    if(dataSnapshot.child("weight").getValue() != null){
                        weight = dataSnapshot.child("weight").getValue().toString();
                        lblWeight.setText(weight+ "(kg)");
                    }
                }
            }
            public void onCancelled(@NotNull DatabaseError databaseError){
                Log.e("ProfileFragment", databaseError.getDetails());
            }
        });
    }

    private void onClickSignOut(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null) google = 1;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        parent.findViewById(R.id.signout_btn).setOnClickListener(new View.OnClickListener() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext(), StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if(google == 1){
                    signOut();
                    Intent intent = new Intent(getContext(), StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void signOut() {
        /*if(mGoogleSignInClient == null){
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);}*/
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }
}
