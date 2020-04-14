package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

/**
 * A fragment showing the options available for chronic disease.
 */
public class DiagnoseChronicFragment extends Fragment implements Subject {

    private FragmentObserver observer;

    public DiagnoseChronicFragment() {
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
        View parent = inflater.inflate(R.layout.fragment_diagnose_chronic, container, false);

        Button Diabetes = parent.findViewById(R.id.diabetes);
        Button Heart = parent.findViewById(R.id.Heart);
        Diabetes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notifyObserver(new DiabetesDiagnosisFragment());
            }
        });
        Heart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notifyObserver(new HeartDiagnoseFragment());
            }
        });

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
