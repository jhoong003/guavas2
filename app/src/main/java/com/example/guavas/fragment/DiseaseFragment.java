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
import android.widget.TextView;

import com.example.guavas.data.IllDetail;
import com.example.guavas.R;
import com.example.guavas.adapter.DiseaseAdapter;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiseaseFragment extends Fragment implements Subject,
        DiseaseAdapter.OnItemClickListener,
        SearchView.OnQueryTextListener{

    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String PREVENTION = "prevention";

    private static final String TAG = "DiseaseActivity";

    private TextView back;
    private SearchView searchView;

    protected RecyclerView mRecyclerView;
    private DiseaseAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private View parent;

    private FragmentObserver observer;

    private ArrayList<IllDetail> DiseaseList = new ArrayList<IllDetail>();

    public DiseaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_disease, container, false);

        ActionBar toolbar = (ActionBar) ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setTitle("Disease");
        toolbar.setDisplayHomeAsUpEnabled(true);

        setUpRecyclerView();
        prepareDiseaseData();

        return parent;
    }

    //Prepare the data to be displayed on the recycler view
    private void prepareDiseaseData() {
        final Resources resources = getActivity().getApplicationContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.disease);
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
        mRecyclerView=parent.findViewById(R.id.recyclerView);
        // ensure that the recycle view does not change in size
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(getContext());
        mAdapter = new DiseaseAdapter(DiseaseList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
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
        List<IllDetail> newList = new ArrayList<>();

        for (IllDetail item:DiseaseList){
            if(item.getName().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }

        mAdapter.updateDiseaseList((ArrayList<IllDetail>) newList);

        return true;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: clicked");
        IllDetail clickedItem = DiseaseList.get(position);
        DiseaseInfoFragment fragment = DiseaseInfoFragment.newInstance(clickedItem.getName(),
                clickedItem.getPrevention(),
                clickedItem.getDescription()
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
