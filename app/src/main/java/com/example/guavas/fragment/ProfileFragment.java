package com.example.guavas.fragment;

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

import com.example.guavas.R;
import com.example.guavas.StartActivity;
import com.example.guavas.controller.DailyDataProcessor;
import com.example.guavas.data.model.MedicalRecord;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;
import com.github.mikephil.charting.data.Entry;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileFragment extends Fragment implements Subject {

    private String phoneNumber;
    private int google=0;
    private GoogleSignInClient mGoogleSignInClient;
    private SharedPreferences preferences;
    private Button view_support;
    private View parent;
    private TextView mobileNumber;
    private TextView lblFirstname, lblLastname, lblAge, lblHeight, lblWeight;
    private DatabaseReference reff, reffHeight, reffWeight;

    private FragmentObserver observer;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get saved phone number
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null){
            phoneNumber = account.getDisplayName();//get google user id
        }else {
             preferences = getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
            phoneNumber = preferences.getString("phoneNumber", null);//get phone number
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_profile, container,false);
        //PROFILE
        displayProfile();
        //NAVIGATE to Edit Profile
        Button btnEditProfile = parent.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the fragment
                ProfileEditFragment fragment = ProfileEditFragment.newInstance(phoneNumber);
                //Fire that fragment
                notifyObserver(fragment);
            }
        });

        //to support activity
        view_support = parent.findViewById(R.id.viewsupport);
        view_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportFragment fragment = SupportFragment.newInstance(phoneNumber);
                notifyObserver(fragment);
            }
        });

        //SIGN OUT
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(account != null) google = 1;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        parent.findViewById(R.id.signout_btn).setOnClickListener(new View.OnClickListener() {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if(google == 1){
                    signOut();
                    Intent intent = new Intent(getActivity(), StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
        return parent;
    }

    private void displayProfile(){
        mobileNumber = parent.findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

        reff = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(phoneNumber);
        reffHeight = FirebaseDatabase.getInstance().getReference().child(phoneNumber).child("Height");
        reffWeight = FirebaseDatabase.getInstance().getReference().child(phoneNumber).child("Weight");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()) {
                    String fname, lname, dy;
                    lblFirstname = parent.findViewById(R.id.lblFirstname);
                    lblLastname = parent.findViewById(R.id.lblLastname);
                    lblAge = parent.findViewById(R.id.lblAge);

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

                        int curYr = Calendar.getInstance().get(Calendar.YEAR);
                        int year = Integer.parseInt(dy);
                        int age = curYr-year;

                        lblAge.setText(age + " years old");
                    }
                }
            }
            public void onCancelled(@NotNull DatabaseError databaseError){
                Log.e("ProfileFragment", databaseError.getDetails());
            }
        });

        reffHeight.addValueEventListener(new ValueEventListener() {
            ArrayList<MedicalRecord> heights = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lblHeight = parent.findViewById(R.id.lblHeight);

                if (dataSnapshot.getChildrenCount() == 0){
                    lblHeight.setText(getResources().getString(R.string.no_recorded_data_height));
                }else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                        heights.add(record);
                    }
                    DailyDataProcessor processor = new DailyDataProcessor();
                    ArrayList<Entry> entries = processor.processData(heights);
                    double height = entries.get(entries.size() - 1).getY();

                    lblHeight.setText(String.format(Locale.getDefault(), "%.2f (cm)", height));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileFragment", databaseError.getDetails());
            }
        });

        reffWeight.addValueEventListener(new ValueEventListener() {
            ArrayList<MedicalRecord> weights = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lblWeight = parent.findViewById(R.id.lblWeight);

                if (dataSnapshot.getChildrenCount() == 0){
                    lblWeight.setText(getResources().getString(R.string.no_recorded_data_weight));
                }else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MedicalRecord record = snapshot.getValue(MedicalRecord.class);
                        weights.add(record);
                    }
                    DailyDataProcessor processor = new DailyDataProcessor();
                    ArrayList<Entry> entries = processor.processData(weights);
                    double weight = entries.get(entries.size() - 1).getY();

                    lblWeight.setText(String.format(Locale.getDefault(), "%.2f (kg)", weight));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileFragment", databaseError.getDetails());
            }
        });
    }

    private void signOut() {
        if (preferences != null) preferences.edit().clear().apply();
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
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
