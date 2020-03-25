package com.example.guavas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.DataType;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewDetailsHeaderFragment extends Fragment {
    private static final String DATATYPE_KEY = "DA";

    private View parent;
    private ViewPager pager;

    private DataType dataType;

    public static ViewDetailsHeaderFragment newInstance(DataType dataType) {
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY,dataType);
        ViewDetailsHeaderFragment fragment = new ViewDetailsHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) dataType = args.getParcelable(DATATYPE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_view_details_header, container, false);
        setupFragmentTitle();
        setupViewPager();
        return parent;
    }

    private void setupFragmentTitle(){
        TextView textView = parent.findViewById(R.id.text_title);
        textView.setText(dataType.getDataTypeName());
    }
    private void setupViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        pager = (ViewPager) parent.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        attachViewPagerToTab();
    }

    private void attachViewPagerToTab(){
        TabLayout tabLayout = (TabLayout)parent.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 4;

        public SectionPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ViewDetailsContentFragment.newInstance(dataType, position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 1: return "W";
                case 2: return "M";
                case 3: return "Y";
                default: return "D";
            }
        }
    }
}
