package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import java.util.ArrayList;

/**
 * A fragment for the Search page.
 */
public class SearchFragment extends Fragment implements Subject, View.OnClickListener {

    private FragmentObserver observer;

    public SearchFragment() {
    }

    /**
     * Inflates layout and setup the fragment.
     *
     * @param inflater           the inflater.
     * @param container          the container.
     * @param savedInstanceState the saved state.
     * @return the user interface.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_search, container, false);
        CardView hospitalButton = parent.findViewById(R.id.hospitalBut);
        CardView diseaseButton = parent.findViewById(R.id.diseaseBut);
        hospitalButton.setOnClickListener(this);
        diseaseButton.setOnClickListener(this);
        return parent;
    }

    /**
     * Sets up buttons to go to other fragment.
     * @param v the clicked view.
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.hospitalBut:
                HospitalFragment hospitalFragment = new HospitalFragment();
                notifyObserver(hospitalFragment);
                break;
            case R.id.diseaseBut:
                DiseaseFragment diseaseFragment = new DiseaseFragment();
                notifyObserver(diseaseFragment);
                break;
        }
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
