package com.example.guavas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guavas.DiagnoseMain;
import com.example.guavas.Diagnose_chronic;
import com.example.guavas.Diagnose_common;
import com.example.guavas.R;

public class DiagnoseMainFragment extends Fragment {

    public DiagnoseMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parent = inflater.inflate(R.layout.fragment_diagnose_main, container, false);
        Button commonButton = parent.findViewById(R.id.common);
        Button chronicButton = parent.findViewById(R.id.chronic);
        commonButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Diagnose_common.class);
                startActivity(intent);
            }
        });

        chronicButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent SetIntent = new Intent(getContext(), Diagnose_chronic.class);
                startActivity(SetIntent);
            }
        });
        return parent;
    }
}
