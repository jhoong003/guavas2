package com.example.guavas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.lockwood.memorizingpager.NavigationHistory;

import java.util.HashMap;
import java.util.Stack;

/**
 * The main class for navigating between fragments
 */

public class NavigationActivity extends AppCompatActivity{

    private final String HISTORY_KEY="history";

    private BottomNavigationView navigationView;

    private NavigationHistory history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        if (savedInstanceState == null) history = new NavigationHistory();
        else history = savedInstanceState.getParcelable(HISTORY_KEY);

        setupBottomNavigationView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(HISTORY_KEY, history);
    }

    private void setupBottomNavigationView() {
        navigationView = (BottomNavigationView)findViewById(R.id.bottom_Nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment nextFragment = null;

                switch(item.getItemId()){
                    case R.id.navigation_diagnosis:
                        nextFragment = new DiagnoseMainFragment();
                        break;
                    case R.id.navigation_health_summary:
                        nextFragment = new HealthSummaryFragment();
                        break;
                    case R.id.navigation_profile:
                        Intent intent = new Intent(NavigationActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_search:
                        Intent intent2 = new Intent(NavigationActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        return true;
                }
                showFragment(nextFragment);
                history.pushItem(item.getItemId());
                return true;
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

    @Override
    public void onBackPressed() {
        if (!history.isEmpty()){
            if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                finishAffinity();
            }
            System.out.println("pop from history: "+history.getLastSelectedItem());
            navigationView.setSelectedItemId(history.popLastSelected());
        }else{
            super.onBackPressed();
        }
    }
}
