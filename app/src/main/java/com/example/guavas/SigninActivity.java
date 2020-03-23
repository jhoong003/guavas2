package com.example.guavas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

/**
 * This activity controls the sign in feautrue.
 * It displays data retrieve from the
 */

public class SigninActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;
    private TextInputEditText Phone;
    //private TextInputLayout Username;
    private TextInputLayout Password;
    private ProgressDialog RegisterProgress;

    //google sign in
    SignInButton signInButton;
    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;
    //final ProgressBar loadingProgressBar = findViewById(R.id.loading);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Phone = findViewById(R.id.phone);
        TextInputEditText countryCode = findViewById(R.id.CountryCode);
        //Password = findViewById(R.id.password);
        Button registerBtn = findViewById(R.id.signup_btn); //phone signin

        signInButton = findViewById(R.id.login_btn2); //google signin

        RegisterProgress = new ProgressDialog(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String phone = Objects.requireNonNull(Objects.requireNonNull(Phone).getText()).toString();
                //String username = Objects.requireNonNull(Username.getEditText()).getText().toString();
                //String password = Objects.requireNonNull(Password.getEditText()).getText().toString();
                String countryCode = "+65";

                if (TextUtils.isEmpty(phone)) Phone.setError("Phone cannot be empty");
                //else if (TextUtils.isEmpty(password)) Password.setError("Password cannot be empty");
                else if (!isPhoneValid(phone)) Phone.setError("Phone is invalid");
                else {
                    RegisterProgress.setTitle(R.string.registering);
                    RegisterProgress.setCanceledOnTouchOutside(false);
                    RegisterProgress.show();
                    //registerNewUser(phone, password);//username, password);
                    phone = countryCode + phone;
                    Intent ver_Intent = new Intent(SigninActivity.this, VerificationActivity.class);
                    ver_Intent.putExtra("phoneNumber", phone);
                    startActivity(ver_Intent);
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // [END on_start_sign_in]
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private boolean isPhoneValid(String phone) {
        return (phone.length() == 8) && (phone.charAt(0) == '9' || phone.charAt(0) == '8');
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(SigninActivity.this, ProfileActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // refer to the GoogleSignInStatusCodes class reference for more info.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(SigninActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }
}