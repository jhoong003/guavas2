package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.Objects;

import static com.example.guavas.HospitalActivity.NAME;
import static com.example.guavas.HospitalActivity.ADDRESS;
import static com.example.guavas.HospitalActivity.TELEPHONE;
import static com.example.guavas.HospitalActivity.DESCRIPTION;
import static com.example.guavas.HospitalActivity.URL;

public class HospitalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HospitalInfoActivity";

    private TextView back;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hospital_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra(NAME);
        String address = intent.getStringExtra(ADDRESS);
        String number = intent.getStringExtra(TELEPHONE);
        String description = intent.getStringExtra(DESCRIPTION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView)toolbar.findViewById(R.id.toolbarTitle);
        textView.setText("Information");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        ImageView imageView = findViewById(R.id.image);
        TextView hospital_name = findViewById(R.id.name);
        TextView hospital_description = findViewById(R.id.description);

        imageView.setImageResource(R.drawable.ic_hospital);
        hospital_name.setText(name);
        hospital_description.setText(description);

        back = findViewById(R.id.Back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Back:
                Intent i1 = new Intent(HospitalInfoActivity.this, HospitalActivity.class);
                startActivity(i1);
                break;
        }
    }
}