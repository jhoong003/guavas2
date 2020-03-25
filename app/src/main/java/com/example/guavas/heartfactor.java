package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class heartfactor extends AppCompatActivity {
    private float[] Factors = new float[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartfactor);
    }

    public void HSend(View view){
        final EditText factor1 = (EditText) findViewById(R.id.Hfactor1);
        final EditText factor2 = (EditText) findViewById(R.id.Hfactor2);
        final EditText factor3 = (EditText) findViewById(R.id.Hfactor3);
        final EditText factor4 = (EditText) findViewById(R.id.Hfactor4);
        final EditText factor5 = (EditText) findViewById(R.id.Hfactor5);
        final EditText factor6 = (EditText) findViewById(R.id.Hfactor6);
        final EditText factor7 = (EditText) findViewById(R.id.Hfactor7);
        final EditText factor8 = (EditText) findViewById(R.id.Hfactor8);
        final EditText factor9 = (EditText) findViewById(R.id.Hfactor9);
        final EditText factor10 = (EditText) findViewById(R.id.Hfactor10);
        final EditText factor11= (EditText) findViewById(R.id.Hfactor11);
        final EditText factor12 = (EditText) findViewById(R.id.Hfactor12);
        Factors[0] =Integer.parseInt(factor1.getText().toString());
        Factors[1] =Integer.parseInt(factor2.getText().toString());
        Factors[2] =Integer.parseInt(factor3.getText().toString());
        Factors[3] =Integer.parseInt(factor4.getText().toString());
        Factors[4] =Integer.parseInt(factor5.getText().toString());
        Factors[5] =Integer.parseInt(factor6.getText().toString());
        Factors[6] =Integer.parseInt(factor7.getText().toString());
        Factors[7] =Integer.parseInt(factor8.getText().toString());
        Factors[8] =Integer.parseInt(factor9.getText().toString());
        Factors[9] =Integer.parseInt(factor10.getText().toString());
        Factors[10] =Integer.parseInt(factor11.getText().toString());
        Factors[11] =Integer.parseInt(factor12.getText().toString());
        Intent intent = new Intent(heartfactor.this,heartresult.class);
        intent.putExtra("values",Factors);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }
}
