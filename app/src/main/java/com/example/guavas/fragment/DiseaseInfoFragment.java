package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import static com.example.guavas.DiseaseActivity.NAME;
import static com.example.guavas.DiseaseActivity.DESCRIPTION;
import static com.example.guavas.DiseaseActivity.PREVENTION;

public class DiseaseInfoFragment extends Fragment implements Subject {

    private FragmentObserver observer;

    public DiseaseInfoFragment() {
    }

    public static DiseaseInfoFragment newInstance(String name, String prevention, String desc) {

        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(PREVENTION, prevention);
        args.putString(DESCRIPTION, desc);
        DiseaseInfoFragment fragment = new DiseaseInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_disease_info, container, false);

        ActionBar toolbar = (ActionBar) ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setTitle("Information");
        toolbar.setDisplayHomeAsUpEnabled(true);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString(NAME);
            String prevention = args.getString(PREVENTION);
            String description = args.getString(DESCRIPTION);

            TextView dname = parent.findViewById(R.id.name);
            TextView dprevention = parent.findViewById(R.id.prevention);
            TextView ddescription = parent.findViewById(R.id.description);

            dname.setText(name);
            dprevention.setText(prevention);
            ddescription.setText(description);
        }

        return parent;
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