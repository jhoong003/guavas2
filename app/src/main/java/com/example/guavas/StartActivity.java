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
        Button loginBtn = findViewById(R.id.start_sign_in_btn);
        Button forgetBtn = findViewById(R.id.forgetpassword);

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent reg_intent = new Intent(StartActivity.this, SignupActivity.class);
                startActivity(reg_intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent login_intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(login_intent);
            }
        });

        forgetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent forget_intent = new Intent(StartActivity.this, ForgetActivity.class);
                startActivity(forget_intent);
            }
        });
    }
}