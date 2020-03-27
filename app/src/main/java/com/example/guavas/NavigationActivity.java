package com.example.guavas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.guavas.fragment.DiagnoseMainFragment;
import com.example.guavas.fragment.HealthSummaryFragment;
import com.example.guavas.fragment.ProfileFragment;
import com.example.guavas.fragment.SearchFragment;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lockwood.memorizingpager.NavigationHistory;

/**
 * The main class for navigating between fragments
 */

public class NavigationActivity extends AppCompatActivity implements FragmentObserver {

    private final String HISTORY_KEY="history";

    private BottomNavigationView navigationView;

    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        setupBottomNavigationView();
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
                        nextFragment = new ProfileFragment();
                        break;
                    case R.id.navigation_search:
                        nextFragment = new SearchFragment();
                        break;
                }
                showFragment(nextFragment);
                return true;
            }
        });
        //Move to health summary page on start
        navigationView.setSelectedItemId(R.id.navigation_health_summary);
    }

    @Override
    public void updateContainerWithFragment(Fragment fragment) {
        showFragment(fragment);
    }

    private void showFragment(Fragment fragment){
        //Register this class to listen if a change of fragment is needed
        if (fragment instanceof Subject) registerToSubject((Subject) fragment);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void registerToSubject(Subject subject){
        subject.register(this);
    }

    //TODO: Fix on back press behavior
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finishAffinity();
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Up button behaves like back button
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
