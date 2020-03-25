package com.example.guavas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guavas.R;
import com.example.guavas.adapter.FeatureCardViewAdapter;
import com.example.guavas.data.DataTypes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VitalsFragment extends Fragment {
    private String[] titles = new String[4];
    private int[] imageIds= {R.drawable.blood_glucose, R.drawable.blood_pressure,
                             R.drawable.cardiogram, R.drawable.pulse_rate};
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_vitals, container, false);
        recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        setAdapterToView();
        setLayoutManagerToView();
        return parent;
    }


    private void setAdapterToView(){
        titles = getResources().getStringArray(R.array.option_vitals);
        FeatureCardViewAdapter adapter = new FeatureCardViewAdapter(titles, imageIds);
        adapter.setListener(new FeatureCardViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                launchViewDetailsFragment(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManagerToView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void launchViewDetailsFragment(int position){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_vital, ViewDetailsHeaderFragment.newInstance(DataTypes.get(position)));
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}
