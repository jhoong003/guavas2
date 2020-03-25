package com.example.guavas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import com.example.guavas.MainActivity;
import com.example.guavas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        //Instantiate the navigation controller which is referencing view
        //navController = Navigation.findNavController(view);
        GridLayout gridView = findViewById(R.id.grid_layout);
        CardView hospitalButton = findViewById(R.id.hospitalBut);
        CardView diseaseButton = findViewById(R.id.diseaseBut);
        hospitalButton.setOnClickListener(this);
        diseaseButton.setOnClickListener(this);

        BottomNavigationView bottomNav=findViewById(R.id.bottom_Nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottomNav.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);

    }
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.hospitalBut:
                //selectedFragment= new hospitalFragment();
                Intent i1 = new Intent(SearchActivity.this, HospitalActivity.class);
                startActivity(i1);
                break;
            case R.id.diseaseBut:
                //selectedFragment= new diseaseFragment();
                Intent i2 = new Intent(SearchActivity.this, DiseaseActivity.class);
                startActivity(i2);
                break;
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.ProfileFragment:
                            Intent i1 = new Intent(SearchActivity.this, ProfileActivity.class);
                            startActivity(i1);
                            break;
                        case R.id.SearchFragment:
                            break;
                        case R.id.DiagnosisFragment:
                            Intent i2 = new Intent(SearchActivity.this, DiagnoseMain.class);
                            startActivity(i2);
                            break;
                        case R.id.healthSummaryFragment:
                            Intent i4 = new Intent(SearchActivity.this, MainActivity.class);
                            startActivity(i4);
                            break;
                    }


                    return true;
                }
            };
}
