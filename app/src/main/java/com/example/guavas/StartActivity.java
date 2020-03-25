package com.example.guavas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button registerBtn = findViewById(R.id.start_sign_up_btn);

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent reg_intent = new Intent(StartActivity.this, SigninActivity.class);
                startActivity(reg_intent);
            }
        });

    }
}