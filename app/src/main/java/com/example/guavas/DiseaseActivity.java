package com.example.guavas;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.guavas.adapter.DiseaseAdapter;
import com.example.guavas.data.IllDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Deprecated (Migrated to fragment)
public class DiseaseActivity extends AppCompatActivity implements View.OnClickListener, DiseaseAdapter.OnItemClickListener,  SearchView.OnQueryTextListener {
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String PREVENTION = "prevention";

    private static final String TAG = "DiseaseActivity";

    private Toolbar toolbar;
    private TextView back;
    private SearchView searchView;
    private BottomNavigationView bottomNav;


    protected RecyclerView mRecyclerView;
    private DiseaseAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    ArrayList<IllDetail> DiseaseList = new ArrayList<IllDetail>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_disease);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView)toolbar.findViewById(R.id.toolbarTitle);
        textView.setText("Disease");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        setUpRecyclerView();
        prepareDiseaseData();

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
                Intent i1 = new Intent(DiseaseActivity.this, SearchActivity.class);
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
                            Intent i1 = new Intent(DiseaseActivity.this, ProfileActivity.class);
                            startActivity(i1);
                            break;
                        case R.id.navigation_search:
                            break;
                        case R.id.navigation_diagnosis:
                            Intent i2 = new Intent(DiseaseActivity.this, DiagnoseMain.class);
                            startActivity(i2);
                            break;
                        case R.id.navigation_health_summary:
                            Intent i4 = new Intent(DiseaseActivity.this, MainActivity.class);
                            startActivity(i4);
                            break;
                    }

                return true;
                }
            };


    //Prepare the data to be displayed on the recycler view
    private void prepareDiseaseData() {
        final Resources resources = getApplicationContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.disease);
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
            line = line.trim();
            System.out.println(line);
            String[] br = line.split("\\|");
            for (String a: br){
                System.out.println(a.trim());
            }
            if(br.length>1) {
                DiseaseList.add(new IllDetail(br[0], br[1], br[2]));
            }
        }
        Collections.sort(DiseaseList, IllDetail.nameComparator);

        mAdapter.notifyDataSetChanged();
    }
    //Set Up the recycler view
    private void setUpRecyclerView(){
        mRecyclerView=findViewById(R.id.recyclerView);
        // ensure that the recycle view does not change in size
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter = new DiseaseAdapter(DiseaseList, this);
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
        List<IllDetail> newList = new ArrayList<>();

        for (IllDetail item:DiseaseList){
            if(item.getName().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }

        mAdapter.updateDiseaseList((ArrayList<IllDetail>) newList);
        /*mAdapter.getFilter().filter(newText);*/

        return true;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: clicked");
        Intent intent = new Intent(this, DiseaseInfoActivity.class);
        IllDetail clickedItem = DiseaseList.get(position);
        intent.putExtra(NAME, clickedItem.getName());
        intent.putExtra(PREVENTION, clickedItem.getPrevention());
        intent.putExtra(DESCRIPTION, clickedItem.getDescription());
        startActivity(intent);
    }
}
