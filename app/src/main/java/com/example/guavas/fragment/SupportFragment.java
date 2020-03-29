package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.guavas.ExpandableList;
import com.example.guavas.controller.FAQDataManager;
import com.example.guavas.firebaseDAO.MedsDAO;
import com.example.guavas.firebaseDAO.RatingDAO;
import com.example.guavas.R;
import com.example.guavas.data.model.UserRatingData;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SupportFragment extends Fragment implements Subject {

    private static final String PHONE_KEY ="phone";

    private FragmentObserver observer;

    private View parent;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private RatingBar ratingBar;
    private Button submitRating;
    private MedsDAO ratingDAO;
    private UserRatingData userRatingData;

    public SupportFragment() {
    }

    public static SupportFragment newInstance(String phone) {

        Bundle args = new Bundle();
        args.putString(PHONE_KEY, phone);
        SupportFragment fragment = new SupportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ratingDAO = (MedsDAO) new RatingDAO();
        userRatingData = new UserRatingData();

        Bundle args = getArguments();
        if (args != null){
            //Get user id
            userRatingData.setUserID(args.getString(PHONE_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_support, container, false);

        setFAQ();
        setRatingBar();
        setSubmitRating();

        return parent;
    }

    private void setFAQ() {
        expandableListView = (ExpandableListView) parent.findViewById(R.id.expandableListView);
        expandableListDetail = FAQDataManager.getFAQData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        Collections.sort(expandableListTitle);
        expandableListAdapter = new ExpandableList(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                showToastToUser(expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                return false;
            }
        });
    }

    private void setRatingBar() {
        ratingBar = (RatingBar) parent.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                userRatingData.setUserRating(v);
                showToastToUser("You rated " + v);
            }
        });
    }

    private void setSubmitRating() {
        submitRating = (Button) parent.findViewById(R.id.submitrating);
        submitRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showToastToUser("Thank you for rating our app!");

                    // Save to Firebase
                    ratingDAO.save(userRatingData);

                    //Go back to profile's home page
                    notifyObserver(new ProfileFragment());
                }
            }
        );
    }

    private void showToastToUser(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void register(FragmentObserver observer) {
        this.observer = observer;
    }

    @Override
    public void unregister() {
        observer = null;
    }

    @Override
    public void notifyObserver(Fragment fragment) {
        observer.updateContainerWithFragment(fragment);
    }
}
