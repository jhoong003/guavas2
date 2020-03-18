package com.example.guavas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_Nav);

    }

    // Interface--Class
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(MenuItem item){
                    Fragment selectedFragment=null;
                    switch(item.getItemId()){
                        case R.id.ProfileFragment:
                            selectedFragment = new profile();
                            break;
                        case R.id.healthSummaryFragment:
                            selectedFragment = new healthSummary();
                            break;
                        case R.id.SearchFragment:
                            selectedFragment = new search();
                            break;
                        case R.id.DiagnosisFragment:
                            selectedFragment = new diagnosis();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                    return true;

                }

            };
}
