package com.example.guavas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

public class HeartDiagnoseFragment extends Fragment implements Subject, View.OnClickListener{

    private float[] factors = new float[12];

    private FragmentObserver observer;

    private View parent;

    public HeartDiagnoseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_heart_diagnose, container, false);
        Button button = parent.findViewById(R.id.button_submit);
        button.setOnClickListener(this);
        return parent;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) send();
    }

    private void send(){
        final EditText factor1 = (EditText) parent.findViewById(R.id.Hfactor1);
        final EditText factor2 = (EditText) parent.findViewById(R.id.Hfactor2);
        final EditText factor3 = (EditText) parent.findViewById(R.id.Hfactor3);
        final EditText factor4 = (EditText) parent.findViewById(R.id.Hfactor4);
        final EditText factor5 = (EditText) parent.findViewById(R.id.Hfactor5);
        final EditText factor6 = (EditText) parent.findViewById(R.id.Hfactor6);
        final EditText factor7 = (EditText) parent.findViewById(R.id.Hfactor7);
        final EditText factor8 = (EditText) parent.findViewById(R.id.Hfactor8);
        final EditText factor9 = (EditText) parent.findViewById(R.id.Hfactor9);
        final EditText factor10 = (EditText) parent.findViewById(R.id.Hfactor10);
        final EditText factor11= (EditText) parent.findViewById(R.id.Hfactor11);
        final EditText factor12 = (EditText) parent.findViewById(R.id.Hfactor12);
        factors[0] =Integer.parseInt(factor1.getText().toString());
        factors[1] =Integer.parseInt(factor2.getText().toString());
        factors[2] =Integer.parseInt(factor3.getText().toString());
        factors[3] =Integer.parseInt(factor4.getText().toString());
        factors[4] =Integer.parseInt(factor5.getText().toString());
        factors[5] =Integer.parseInt(factor6.getText().toString());
        factors[6] =Integer.parseInt(factor7.getText().toString());
        factors[7] =Integer.parseInt(factor8.getText().toString());
        factors[8] =Integer.parseInt(factor9.getText().toString());
        factors[9] =Integer.parseInt(factor10.getText().toString());
        factors[10] =Integer.parseInt(factor11.getText().toString());
        factors[11] =Integer.parseInt(factor12.getText().toString());

        ChronicDiagnosisResultFragment resultFragment =
                ChronicDiagnosisResultFragment.newInstance(factors, "Heart Disease", "Heart.tflite");
        notifyObserver(resultFragment);

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
