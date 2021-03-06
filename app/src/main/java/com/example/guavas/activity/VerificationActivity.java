package com.example.guavas.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guavas.R;
import com.google.android.gms.tasks.Task;


//x
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * This activity is the verification page.
 */
public class VerificationActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;

    private ProgressBar progressBar;
    private TextInputEditText editText;

    /**
     * Sets up the user interface.
     *
     * @param savedInstanceState the saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.loading);
        editText = findViewById(R.id.verification_code);
        Button buttonSignIn = findViewById(R.id.confirm_btn);

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(phoneNumber);

        // save phone number
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phoneNumber", phoneNumber);
        editor.apply();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                String code = Objects.requireNonNull(editText.getText()).toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

    }

    /**
     * Verifies the inputted code.
     *
     * @param code the inputted code.
     */
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    /**
     * Verifies the inputted code with the generated code.
     *
     * @param credential the credential.
     */
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(VerificationActivity.this, "Verification successful!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(VerificationActivity.this, NavigationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                        } else {
                            Toast.makeText(VerificationActivity.this, "Verification failed.", Toast.LENGTH_LONG).show();
                            //Toast.makeText(VerificationActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * Sends the verification code to the phone number.
     *
     * @param number the phone number.
     */
    private void sendVerificationCode(String number) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                //TaskExecutors.MAIN_THREAD,
                this,
                mCallBack
        );

        progressBar.setVisibility(View.GONE);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    };
}