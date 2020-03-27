package com.example.guavas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.guavas.adapter.HospitalAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HospitalActivity extends AppCompatActivity implements HospitalAdapter.OnItemClickListener, View.OnClickListener, SearchView.OnQueryTextListener{
    public static final String URL = "imageUrl";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String TELEPHONE = "number";

    private static final String TAG = "HospitalActivity";
    private Toolbar toolbar;
    private TextView back;
    private BottomNavigationView bottomNav;
    protected RecyclerView mRecyclerView;
    private HospitalAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Hospital> hospitalsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hospital);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView)toolbar.findViewById(R.id.toolbarTitle);
        textView.setText("Hospital");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);

        setUpRecyclerView();

        try {
            prepareHospitalData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        back = findViewById(R.id.Back);
        back.setOnClickListener(this);

        bottomNav=findViewById(R.id.bottom_Nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottomNav.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Back:
                Intent i1 = new Intent(HospitalActivity.this, SearchActivity.class);
                startActivity(i1);
                break;

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                    switch (menuItem.getItemId()) {
                        case R.id.navigation_profile:
                            Intent i1 = new Intent(HospitalActivity.this, ProfileActivity.class);
                            startActivity(i1);
                            break;
                        case R.id.navigation_search:
                            break;
                        case R.id.navigation_diagnosis:
                            Intent i2 = new Intent(HospitalActivity.this, DiagnoseMain.class);
                            startActivity(i2);
                            break;
                        case R.id.navigation_health_summary:
                            Intent i4 = new Intent(HospitalActivity.this, MainActivity.class);
                            startActivity(i4);
                            break;
                    }
                    return true;
                }
            };


    //Prepare the data to be displayed on the recycler view
    private void prepareHospitalData() throws FileNotFoundException {
        final Resources resources = getApplicationContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.hosp);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while (true){
            try {
                if (!((line = reader.readLine())!=null)) {
                    break;
                }
            } catch (IOException e) {
                System.out.println("No data");
                e.printStackTrace();
            }
            assert line != null;
            line = line.trim();
            System.out.println(line);
            String[] string = line.split("\\|");
            for (String a: string){
                System.out.println(a.trim());
            }
            hospitalsList.add(new Hospital(string[0].trim(), string[1].trim(), string[2].trim(), string[3].trim(), R.drawable.ic_hospital));
        }
        mAdapter.notifyDataSetChanged();
    }
    //Set Up the recycler view
    private void setUpRecyclerView(){
        mRecyclerView=findViewById(R.id.recyclerView);
        // ensure that the recycle view does not change in size
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter = new HospitalAdapter(hospitalsList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Bind the adapter to the recycle view
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Hospital> newList = new ArrayList<>();

        for (Hospital item:hospitalsList){
            if(item.getName().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }

        mAdapter.updateList((ArrayList<Hospital>) newList);
        /*mAdapter.getFilter().filter(newText);*/

        return true;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: clicked");
        Intent intent = new Intent(this, HospitalInfoActivity.class);
        Hospital clickedItem = hospitalsList.get(position);
        intent.putExtra(NAME, clickedItem.getName());
        intent.putExtra(ADDRESS, clickedItem.getAddress());
        intent.putExtra(DESCRIPTION, clickedItem.getDescription());
        intent.putExtra(TELEPHONE, clickedItem.getNumber());
        intent.putExtra(URL, clickedItem.getImgURL());
        startActivity(intent);
    }

}
