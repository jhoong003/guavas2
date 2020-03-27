package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class diabetesfactor extends AppCompatActivity {
    private float[] Factors = new float[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diabetesform);
    }

    public void Send(View view){
        final EditText factor1 = (EditText) findViewById(R.id.factor1);
        Factors[0] =Integer.parseInt(factor1.getText().toString());
        final EditText factor2 = (EditText) findViewById(R.id.factor2);
        Factors[1] =Integer.parseInt(factor2.getText().toString());
        final EditText factor3 = (EditText) findViewById(R.id.factor3);
        Factors[2] =Integer.parseInt(factor3.getText().toString());
        final EditText factor4 = (EditText) findViewById(R.id.factor4);
        Factors[3] =Integer.parseInt(factor4.getText().toString());
        final EditText factor5 = (EditText) findViewById(R.id.factor5);
        Factors[4] =Integer.parseInt(factor5.getText().toString());
        final EditText factor6 = (EditText) findViewById(R.id.factor6);
        Factors[5] =Integer.parseInt(factor6.getText().toString());
        final EditText factor7 = (EditText) findViewById(R.id.factor7);
        Factors[6]= Integer.parseInt(factor7.getText().toString());
        Intent intent = new Intent(diabetesfactor.this,diabetesResult.class);
        intent.putExtra("values",Factors);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }
}
