package com.example.guavas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guavas.Entity.UserProfile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
 * This Activity controls Profile display and Log out feature
 *
 * @author zane_
 */
public class ProfileActivity extends AppCompatActivity {

    String phoneNumber;
    TextView mobileNumber;
    int google=0;
    GoogleSignInClient mGoogleSignInClient;

    TextView lblFirstname, lblLastname, lblAge, lblHeight, lblWeight;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get saved phone number
        SharedPreferences prefs =  getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", NULL);
        //phoneNumber = "+6588888888";


        //PROFILE
        displayProfile();

        //NAVIGATE to Edit Profile
        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ProfileEditActivity.class);
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



        //SIGN OUT
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) google = 1;
        findViewById(R.id.signout_btn).setOnClickListener(new View.OnClickListener() {

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            @Override
            public void onClick(View v) {
                if(currentUser != null){
                    FirebaseAuth.getInstance().signOut();
                }
                else if(google == 1){
                    signOut();
                }

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    toSearchActivity();
                    return true;
                case R.id.navigation_diagnosis:
                    toDiagnosisActivity();
                    return true;
                case R.id.navigation_health_summary:
                    toHealthSummaryActivity();
                    return true;
            }
            return false;
        }
    };

    private void toSearchActivity () {
        Intent startIntent = new Intent(ProfileActivity.this, SearchActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toDiagnosisActivity () {
        Intent startIntent = new Intent(ProfileActivity.this, DiagnosisActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toHealthSummaryActivity () {
        Intent startIntent = new Intent(ProfileActivity.this, HealthSummaryActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void displayProfile(){
        mobileNumber = findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

        lblFirstname = findViewById(R.id.lblFirstname);
        lblLastname = findViewById(R.id.lblLastname);
        lblAge = findViewById(R.id.lblAge);
        lblHeight = findViewById(R.id.lblHeight);
        lblWeight = findViewById(R.id.lblWeight);

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
                    lblFirstname = findViewById(R.id.lblFirstname);
                    lblLastname = findViewById(R.id.lblLastname);
                    lblAge = findViewById(R.id.lblAge);
                    lblHeight = findViewById(R.id.lblHeight);
                    lblWeight = findViewById(R.id.lblWeight);

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