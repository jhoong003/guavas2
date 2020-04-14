package com.example.guavas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.guavas.R;
import com.example.guavas.fragment.DiagnoseMainFragment;
import com.example.guavas.fragment.HealthSummaryFragment;
import com.example.guavas.fragment.ProfileFragment;
import com.example.guavas.fragment.SearchFragment;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

/**
 * This is the class for navigating between fragments. The class listens for the need to change the fragment.
 */
public class NavigationActivity extends AppCompatActivity implements FragmentObserver {

    private final String HISTORY_KEY = "history";

    private BottomNavigationView navigationView;

    private Stack<Integer> backHistory;
    private int prevItemOrder;


    /**
     * Initialize the history of navigation which is saved in <code>backHistory</code>. Then, setup the bottom navigation
     * view for use.
     *
     * @param savedInstanceState the saved system state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (savedInstanceState != null)
            backHistory = (Stack<Integer>) savedInstanceState.getSerializable(HISTORY_KEY);
        else backHistory = new Stack<>();
        setupBottomNavigationView();
    }

    /**
     * If the activity is to be recreated, save the history of navigation.
     *
     * @param outState the bundle holding the system state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(HISTORY_KEY, backHistory);
    }

    /**
     * Sets up the bottom navigation view of the activity.
     */
    private void setupBottomNavigationView() {
        navigationView = findViewById(R.id.bottom_Nav);

        //Make responds to selection
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment nextFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_diagnosis:
                        nextFragment = new DiagnoseMainFragment();
                        break;
                    case R.id.navigation_health_summary:
                        nextFragment = new HealthSummaryFragment();
                        break;
                    case R.id.navigation_profile:
                        nextFragment = new ProfileFragment();
                        break;
                    case R.id.navigation_search:
                        nextFragment = new SearchFragment();
                        break;
                }
                backHistory.push(item.getOrder());
                prevItemOrder = item.getOrder();
                showFragment(nextFragment);
                return true;
            }
        });

        navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                //Do nothing if the same page is reselected
            }
        });

        //Move to profile page on start
        navigationView.setSelectedItemId(R.id.navigation_profile);
    }

    /**
     * Updates the old fragment with the new fragment.
     *
     * @param fragment the new fragment to display.
     */
    @Override
    public void updateContainerWithFragment(Fragment fragment) {
        backHistory.push(prevItemOrder);
        showFragment(fragment);
    }

    /**
     * Displays the new fragment on the screen.
     *
     * @param fragment the new fragment to display.
     */
    private void showFragment(Fragment fragment) {
        //Register this class to listen if a change of fragment is needed
        if (fragment instanceof Subject) registerToSubject((Subject) fragment);
        setActionBarDefault();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Reset the action bar of the activity. The title will be set to "MEDS" and the up button is eliminated.
     */
    private void setActionBarDefault() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MEDS");
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    /**
     * Registers to the subject so that this class listens to the subject.
     *
     * @param subject the subject to listen to.
     */
    private void registerToSubject(Subject subject) {
        subject.register(this);
    }

    /**
     * When the back button is pressed, pop the top item in the <code>backHistory</code>.
     * If there is only 1 item, close the application.
     */
    @Override
    public void onBackPressed() {
        setActionBarDefault();
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finishAffinity();
        } else {
            super.onBackPressed();
            backHistory.pop();
            int prevPos = backHistory.peek();
            navigationView.getMenu().getItem(prevPos).setChecked(true);
        }
    }

    /**
     * Sets up button to behave like back button.
     *
     * @param item the selected option item.
     * @return <code>true</code> if the event is handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Up button behaves like back button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
