package com.example.guavas.fragment;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import java.util.ArrayList;
import java.util.List;

public class HospitalFragment extends Fragment implements Subject,
        HospitalAdapter.OnItemClickListener,
        SearchView.OnQueryTextListener{

    public static final String URL = "imageUrl";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String TELEPHONE = "number";

    private static final String TAG = "HospitalActivity";

    private View parent;
    private ActionBar toolbar;
    protected RecyclerView mRecyclerView;
    private HospitalAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Hospital> hospitalsList = new ArrayList<>();

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

        setUpRecyclerView();

        try {
            prepareHospitalData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return parent;
    }

    //Prepare the data to be displayed on the recycler view
    private void prepareHospitalData() throws FileNotFoundException {
        final Resources resources = getActivity().getApplicationContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.hosp);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while (true){
            try {
                if ((line = reader.readLine()) == null) {
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
        mRecyclerView = parent.findViewById(R.id.recyclerView);
        // ensure that the recycle view does not change in size
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        mAdapter = new HospitalAdapter(hospitalsList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Bind the adapter to the recycle view
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
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
        List<Hospital> newList = new ArrayList<>();

        for (Hospital item:hospitalsList){
            if(item.getName().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }

        mAdapter.updateList((ArrayList<Hospital>) newList);

        return true;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: clicked");
        Hospital clickedItem = hospitalsList.get(position);
        HospitalInfoFragment fragment = HospitalInfoFragment.newInstance(
                clickedItem.getName(),
                clickedItem.getAddress(),
                clickedItem.getDescription(),
                clickedItem.getNumber()
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
