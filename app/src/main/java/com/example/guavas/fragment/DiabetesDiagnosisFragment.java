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
import com.example.guavas.diabetesResult;
import com.example.guavas.diabetesfactor;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

public class DiabetesDiagnosisFragment extends Fragment implements Subject, View.OnClickListener {

    private float[] factors = new float[7];
    private FragmentObserver observer;

    private View parent;

    public DiabetesDiagnosisFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_diabetes_diagnosis, container, false);
        Button button = parent.findViewById(R.id.button_submit);
        button.setOnClickListener(this);
        return parent;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) send();
    }

    public void send(){
        final EditText factor1 = (EditText) parent.findViewById(R.id.factor1);
        factors[0] =Float.parseFloat(factor1.getText().toString());
        final EditText factor2 = (EditText) parent.findViewById(R.id.factor2);
        factors[1] =Float.parseFloat(factor2.getText().toString());
        final EditText factor3 = (EditText) parent.findViewById(R.id.factor3);
        factors[2] =Float.parseFloat(factor3.getText().toString());
        final EditText factor4 = (EditText) parent.findViewById(R.id.factor4);
        factors[3] =Float.parseFloat(factor4.getText().toString());
        final EditText factor5 = (EditText) parent.findViewById(R.id.factor5);
        factors[4] =Float.parseFloat(factor5.getText().toString());
        final EditText factor6 = (EditText) parent.findViewById(R.id.factor6);
        factors[5] =Float.parseFloat(factor6.getText().toString());
        final EditText factor7 = (EditText) parent.findViewById(R.id.factor7);
        factors[6]= Float.parseFloat(factor7.getText().toString());

        ChronicDiagnosisResultFragment fragment =
                ChronicDiagnosisResultFragment.newInstance(factors, "Diabetes", "Diabetes.tflite");
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
