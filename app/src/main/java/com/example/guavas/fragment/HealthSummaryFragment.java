package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guavas.R;
import com.example.guavas.adapter.FeatureCardViewAdapter;


public class HealthSummaryFragment extends Fragment {

    private int[] imageIds = {R.drawable.allergy, R.drawable.bmi, R.drawable.inheritance, R.drawable.vital};
    private String[] titles = new String[4];
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView =
                inflater.inflate(R.layout.fragment_health_summary, container, false);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
        setAdapterToView();
        setLayoutManagerToView();
        return parentView;
    }

    private void setAdapterToView(){
        titles = getResources().getStringArray(R.array.option_health_summary);
        FeatureCardViewAdapter adapter = new FeatureCardViewAdapter(titles, imageIds);
        adapter.setListener(new FeatureCardViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Fragment nextFragment;
                switch (position){
                    case 1:
                        nextFragment = VitalsFragment.newInstance(VitalsFragment.SUBMENU_BMI);
                        break;
                    case 3:
                        nextFragment = VitalsFragment.newInstance(VitalsFragment.SUBMENU_VITALS);                        break;
                    default:
                        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
                        return;
                }
                displayFragment(nextFragment);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManagerToView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void displayFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}
