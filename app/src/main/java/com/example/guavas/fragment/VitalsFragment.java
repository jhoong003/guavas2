package com.example.guavas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guavas.R;
import com.example.guavas.adapter.FeatureCardViewAdapter;
import com.example.guavas.data.DataType;
import com.example.guavas.data.DataTypes;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VitalsFragment extends Fragment implements Subject {

    public static final int SUBMENU_BMI = 1;
    public static final int SUBMENU_VITALS = 2;
    private static final String SUBMENU_KEY = "sub";

    private String[] titles;
    private int[] imageIds;
    private int subMenu;
    private FragmentObserver observer;

    protected RecyclerView recyclerView;

    public static VitalsFragment newInstance(int subMenu) {

        Bundle args = new Bundle();
        args.putInt(SUBMENU_KEY, subMenu);
        VitalsFragment fragment = new VitalsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            subMenu = args.getInt(SUBMENU_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_vitals, container, false);
        recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        setupData();
        setAdapterToView();
        setLayoutManagerToView();
        return parent;
    }

    private void setupData(){
        if (subMenu == SUBMENU_BMI){
            titles = getResources().getStringArray(R.array.option_bmi);
            imageIds = new int[] {R.drawable.bmi, R.drawable.height, R.drawable.weight};
        }else if(subMenu == SUBMENU_VITALS){
            titles = getResources().getStringArray(R.array.option_vitals);
            imageIds = new int[] {R.drawable.blood_glucose, R.drawable.blood_pressure,
                    R.drawable.cardiogram, R.drawable.pulse_rate};
        }
    }

    private void setAdapterToView(){

        FeatureCardViewAdapter adapter = new FeatureCardViewAdapter(titles, imageIds);
        adapter.setListener(new FeatureCardViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                launchViewDetailsFragment(position, subMenu);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManagerToView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void launchViewDetailsFragment(int position, int subMenu){
        Fragment nextFragment;
        if (subMenu == SUBMENU_BMI) nextFragment = ViewDetailsHeaderFragment.newInstance(DataTypes.getBMIDataType(position));
        else nextFragment = ViewDetailsHeaderFragment.newInstance(DataTypes.getVitalDataType(position));
        notifyObserver(nextFragment);
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
