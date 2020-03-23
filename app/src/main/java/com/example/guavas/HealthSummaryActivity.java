package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HealthSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthsummary);
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
                case R.id.navigation_profile:
                    toProfileActivity();
                    return true;
            }
            return false;
        }
    };

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.navigation_health_summary) {
            //FirebaseAuth.getInstance().signOut();
            toHealthSummaryActivity();
        } else if (item.getItemId() == R.id.navigation_search) {
            toSearchActivity();
        }
        else if (item.getItemId() == R.id.navigation_diagnosis) {
            toDiagnosisActivity();
        }
        else if (item.getItemId() == R.id.navigation_me) {
            toProfileActivity();
        }
        return true;
    }*/

    private void toSearchActivity () {
        Intent startIntent = new Intent(HealthSummaryActivity.this, SearchActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toDiagnosisActivity () {
        Intent startIntent = new Intent(HealthSummaryActivity.this, DiagnosisActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toProfileActivity () {
        Intent startIntent = new Intent(HealthSummaryActivity.this, ProfileActivity.class);
        startActivity(startIntent);
        finish();
    }
}
