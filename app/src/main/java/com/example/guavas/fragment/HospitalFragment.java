package com.example.guavas.fragment;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.guavas.R;
import com.example.guavas.adapter.HospitalAdapter;
import com.example.guavas.Hospital;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HospitalFragment extends Fragment implements Subject,
        HospitalAdapter.OnItemClickListener,
        SearchView.OnQueryTextListener{

    public static final String URL = "imageUrl";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String TELEPHONE = "number";
    public static final int IMGURL = 0;

    private static final String TAG = "HospitalActivity";

    private View parent;
    private ActionBar toolbar;
    private RecyclerView mRecyclerView;
    private HospitalAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Hospital> hospitalsList = new ArrayList<>();
    ArrayList<Hospital> searchedList = new ArrayList<>();

    private FragmentObserver observer;

    public HospitalFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_hospital, container, false);

        toolbar = (ActionBar) ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setTitle("Hospital");
        toolbar.setDisplayHomeAsUpEnabled(true);

        prepareHospitalData();
        setUpRecyclerView();

        return parent;
    }

    //Prepare the data to be displayed on the recycler view

    private void prepareHospitalData(){
        final Resources resources = getActivity().getApplicationContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.hosp);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        hospitalsList = new ArrayList<>();
        while (true){
            try {
                if ((line = reader.readLine()) == null) {
                    break;
                }
            } catch (IOException e) {
                Log.wtf("Hospital Fragment", "Is the hospital.txt data not in the resource file?!");
                e.printStackTrace();
            }
            assert line != null;
            line = line.trim();
            //System.out.println(line);
            String[] string = line.split("\\|");
            /*for (String a: string){
                System.out.println(a.trim());
            }*/
            int id = resources.getIdentifier(string[4], "drawable", getActivity().getApplicationContext().getPackageName());
            System.out.println(string[4]);
            System.out.println(id);
            hospitalsList.add(new Hospital(string[0].trim(), string[1].trim(), string[2].trim(), string[3].trim(), id));
        }

        //Collections.sort(hospitalsList, Hospital.nameComparator);
        //mAdapter.notifyDataSetChanged();
    }

    //Set Up the recycler view
    private void setUpRecyclerView(){
        mRecyclerView = parent.findViewById(R.id.recyclerView);
        // ensure that the recycle view does not change in size
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Hospital> hospitalsCopy = new ArrayList<>();
        hospitalsCopy.addAll(hospitalsList);
        mAdapter = new HospitalAdapter(hospitalsCopy, this);

        //Bind the adapter to the recycle view
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.getIcon().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(getResources().getColor(android.R.color.white), BlendModeCompat.SRC_IN));
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Hospital> newList = new ArrayList<>();
        for (Hospital item : hospitalsList){
            if(item.getName().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }
        /*choice 1*/
        searchedList = mAdapter.updateList((ArrayList<Hospital>) newList);
        /*choice 2*/
        //mAdapter.updateList(newList);

        return true;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: clicked");

        /*for (Hospital item : hospitalsList){
            System.out.println(item.getName());
            System.out.println("stupid shit");
        }*/
        Hospital clickedItem;
        /*choice 1*/
        if(searchedList.isEmpty()){
            clickedItem = hospitalsList.get(position);
        }
        else {
            clickedItem = searchedList.get(position);
        }
        /*choice 2*/
        //Hospital clickedItem = mAdapter.getItemAtIndex(position);
        HospitalInfoFragment fragment = HospitalInfoFragment.newInstance(

                clickedItem.getName(),
                clickedItem.getAddress(),
                clickedItem.getDescription(),
                clickedItem.getNumber(),
                clickedItem.getImgURL()
        );

        notifyObserver(fragment);
    }

    @Override
    public void register(FragmentObserver observer) {
        this.observer = observer;
    }

    @Override
    public void unregister() {
        observer = null;
    }

    @Override
    public void notifyObserver(Fragment fragment) {
        observer.updateContainerWithFragment(fragment);
    }
}
