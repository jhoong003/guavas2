package com.example.guavas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.guavas.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity is the main page before the user is signed in.
 */
public class StartActivity extends AppCompatActivity {

    /**
     * Sets up the user interface. The button directs user to the Sign In Activity
     *
     * @param savedInstanceState the saved system state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button registerBtn = findViewById(R.id.start_sign_up_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(StartActivity.this, SigninActivity.class);
                startActivity(reg_intent);
            }
        });
    }
}