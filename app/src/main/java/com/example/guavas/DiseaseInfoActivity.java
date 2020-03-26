package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import static com.example.guavas.DiseaseActivity.NAME;
import static com.example.guavas.DiseaseActivity.DESCRIPTION;
import static com.example.guavas.DiseaseActivity.PREVENTION;


public class DiseaseInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView back;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_disease_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra(NAME);
        String prevention = intent.getStringExtra(PREVENTION);
        String description = intent.getStringExtra(DESCRIPTION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView)toolbar.findViewById(R.id.toolbarTitle);
        textView.setText("Information");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        TextView dname = findViewById(R.id.name);
        TextView dprevention = findViewById(R.id.prevention);
        TextView ddescription = findViewById(R.id.description);

        dname.setText(name);
        dprevention.setText(prevention);
        ddescription.setText(description);

        back = findViewById(R.id.Back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Back:
                Intent i2 = new Intent(DiseaseInfoActivity.this, DiseaseActivity.class);
                startActivity(i2);
                break;
        }
    }
}
