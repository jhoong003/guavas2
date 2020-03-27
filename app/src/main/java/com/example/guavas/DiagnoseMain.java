package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

//Deprecated (Migrated to fragment)
public class DiagnoseMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_main_act);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button commonButton = findViewById(R.id.common);
        Button chronicButton = findViewById(R.id.chronic);
        commonButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(DiagnoseMain.this,Diagnose_common.class);

                startActivity(intent);
            }
        });

        chronicButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent SetIntent = new Intent(DiagnoseMain.this, Diagnose_chronic.class);
                startActivity(SetIntent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }
}
