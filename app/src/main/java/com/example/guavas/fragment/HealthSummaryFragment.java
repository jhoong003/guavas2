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
import com.example.guavas.data.SubMenus;
import com.example.guavas.data.model.SubMenu;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;


public class HealthSummaryFragment extends Fragment implements Subject {

    private RecyclerView recyclerView;

    private FragmentObserver observer;

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
        SubMenu subMenu = new SubMenus(getActivity()).getHealthSummarySubMenu();
        FeatureCardViewAdapter adapter = new FeatureCardViewAdapter(subMenu.getTitles(), subMenu.getImageIds());
        adapter.setListener(new FeatureCardViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Fragment nextFragment;
                switch (position){
                    case 0:
                        nextFragment = VitalsFragment.newInstance(VitalsFragment.SUBMENU_BMI);
                        break;
                    case 1:
                        nextFragment = VitalsFragment.newInstance(VitalsFragment.SUBMENU_VITALS);
                        break;
                    default:
                        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
                        return;
                }
                notifyObserver(nextFragment);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManagerToView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
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
