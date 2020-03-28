package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import static com.example.guavas.fragment.HospitalFragment.NAME;
import static com.example.guavas.fragment.HospitalFragment.ADDRESS;
import static com.example.guavas.fragment.HospitalFragment.TELEPHONE;
import static com.example.guavas.fragment.HospitalFragment.DESCRIPTION;

public class HospitalInfoFragment extends Fragment implements Subject {

    private static final String TAG = "HospitalInfoActivity";

    private View parent;

    private FragmentObserver observer;

    public HospitalInfoFragment() {
    }

    public static HospitalInfoFragment newInstance(String name, String address, String desc, String telephone) {

        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(ADDRESS, address);
        args.putString(TELEPHONE, telephone);
        args.putString(DESCRIPTION, desc);
        HospitalInfoFragment fragment = new HospitalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_hospital_info, container, false);

        ActionBar toolbar = (ActionBar) ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setTitle("Information");
        toolbar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView = parent.findViewById(R.id.image);
        TextView hospital_name = parent.findViewById(R.id.name);
        TextView hospital_description = parent.findViewById(R.id.description);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString(NAME);
            String address = args.getString(ADDRESS);
            String number = args.getString(TELEPHONE);
            String description = args.getString(DESCRIPTION);

            imageView.setImageResource(R.drawable.ic_hospital);
            hospital_name.setText(name);
            hospital_description.setText(description);
        }
        return parent;
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
