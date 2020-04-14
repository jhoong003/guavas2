package com.example.guavas.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * This class is the main activity of the project. Upon starting the application, it will go to this main activity.
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    /**
     * Directs user to Start Activity if the user is not signed in, else directs user to Navigation Activity
     *
     * @param savedInstanceState the saved state.
     * @see StartActivity
     * @see NavigationActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check network connection
        if (!isNetworkConnected()) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
        }

        // Authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // If user is NOT signed in, go to Start Activity else to Navigation Activity
        if (currentUser == null && account == null) {
            toStartActivity();
        } else {
            toNavigationActivity();
        }
    }

    /**
     * When the application starts, inform the user if the device is not connected to the internet.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!isNetworkConnected()) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Go to Start Activity
     */
    private void toStartActivity() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    /**
     * Go to Navigation Activity
     */
    private void toNavigationActivity() {
        Intent startIntent = new Intent(MainActivity.this, NavigationActivity.class);
        startActivity(startIntent);
        finish();
    }

    /**
     * Checks if the device is connected to the internet.
     *
     * @return <code>true</code> if the device is connected to the internet.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}

