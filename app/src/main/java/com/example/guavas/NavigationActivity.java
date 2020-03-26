package com.example.guavas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.guavas.fragment.DiagnoseMainFragment;
import com.example.guavas.fragment.HealthSummaryFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The main class for navigating between fragments
 */

public class NavigationActivity extends AppCompatActivity{

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        BottomNavigationView navigationView = (BottomNavigationView)findViewById(R.id.bottom_Nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_diagnosis:
                        DiagnoseMainFragment diagnoseMainFragment = new DiagnoseMainFragment();
                        showFragment(diagnoseMainFragment);
                        return true;
                    case R.id.navigation_health_summary:
                        HealthSummaryFragment healthSummaryFragment = new HealthSummaryFragment();
                        showFragment(healthSummaryFragment);
                        return true;
                    case R.id.navigation_profile:
                        Intent intent = new Intent(NavigationActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_search:
                        //Inject your search fragment here
                        return true;
                }
                return false;
            }
        });
        //Move to health summary page on start
        navigationView.setSelectedItemId(R.id.navigation_health_summary);
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
