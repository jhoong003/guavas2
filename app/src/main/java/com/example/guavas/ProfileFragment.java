package com.example.guavas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    String phoneNumber;
    TextView mobileNumber;
    int google=0;
    GoogleSignInClient mGoogleSignInClient;
    Button view_support;
    View parent;

    TextView lblFirstname, lblLastname, lblAge, lblHeight, lblWeight;
    DatabaseReference reff;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_profile, container,false);

        // get saved phone number
        SharedPreferences prefs =  getActivity().getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", NULL);
        //phoneNumber = "+6588888888";

        //PROFILE
        displayProfile();

        //NAVIGATE to Edit Profile
        Button btnEditProfile = parent.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileEditActivity.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data to bundle
                bundle.putString("phone",phoneNumber);
                //Add the bundle to the intent
                i.putExtras(bundle);
                //Fire that second activity
                startActivity(i);
            }
        });

        //to support activity
        Button view_support = parent.findViewById(R.id.viewsupport);
        view_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SupportActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
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

    private void signOut() {
        /*if(mGoogleSignInClient == null){
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);}*/
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    private void displayProfile(){
        mobileNumber = parent.findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);



//        UserProfile profile = new UserProfile();
//        profile = profile.getUserProfile(phoneNumber);
//        if(profile != null){
//            if(profile.getFirstName() != null){
//                lblFirstname.setText(profile.getFirstName());
//            }
//            if(profile.getLastName() != null){
//                lblLastname.setText(profile.getLastName());
//            }
//            if(profile.getDobY() != null){
//                int curYr = Calendar.getInstance().get(Calendar.YEAR);
//                int year = Integer.parseInt(profile.getDobY());
//                int age = curYr-year;
//                lblAge.setText(age + " years old");
//            }
//            if(profile.getHeight() != 0){
//                lblHeight.setText(profile.getHeight()+"(cm)");
//
//            }
//            if(profile.getWeight() != 0){
//                lblWeight.setText(profile.getWeight()+ "(kg)");
//            }
//        }
//

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

            }
        });
    }
}
