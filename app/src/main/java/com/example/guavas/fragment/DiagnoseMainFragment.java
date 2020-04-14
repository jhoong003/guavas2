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
 * A fragment that is the main menu for the diagnose section.
 */
public class DiagnoseMainFragment extends Fragment implements Subject {

    private FragmentObserver observer;

    public DiagnoseMainFragment() {
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

        View parent = inflater.inflate(R.layout.fragment_diagnose_main, container, false);
        Button commonButton = parent.findViewById(R.id.common);
        Button chronicButton = parent.findViewById(R.id.chronic);
        commonButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                notifyObserver(new DiagnoseCommonFragment());
            }
        });

        chronicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyObserver(new DiagnoseChronicFragment());
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
