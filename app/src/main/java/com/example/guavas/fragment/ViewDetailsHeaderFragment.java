package com.example.guavas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.entity.DataType;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * This fragment is the header of the chosen medical data type. It consists of the title and the tabs.
 */
public class ViewDetailsHeaderFragment extends Fragment {
    private static final String DATATYPE_KEY = "DA";

    private View parent;
    private ViewPager pager;

    private DataType dataType;

    /**
     * Gets a new instance of the fragment.
     *
     * @param dataType the chosen medical data type.
     * @return a new instance of the fragment.
     */
    public static ViewDetailsHeaderFragment newInstance(DataType dataType) {
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY, dataType);
        ViewDetailsHeaderFragment fragment = new ViewDetailsHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sets up the data from the saved system state.
     *
     * @param savedInstanceState the saved system state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) dataType = args.getParcelable(DATATYPE_KEY);
    }

    /**
     * Inflates layout and setup the fragment.
     *
     * @param inflater           the inflater.
     * @param container          the container.
     * @param savedInstanceState the saved state.
     * @return the user interface.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_view_details_header, container, false);
        setupFragmentTitle();
        setupViewPager();
        return parent;
    }

    /**
     * Sets up the title text.
     */
    private void setupFragmentTitle() {
        TextView textView = parent.findViewById(R.id.text_title);
        textView.setText(dataType.getDataTypeName());
    }

    /**
     * Sets up the ViewPager.
     */
    private void setupViewPager() {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        pager = (ViewPager) parent.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        attachViewPagerToTab();
    }

    /**
     * Sets up the tab.
     */
    private void attachViewPagerToTab() {
        TabLayout tabLayout = (TabLayout) parent.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
    }

    /**
     * This class is the ViewPager class used in the fragment.
     */
    private class SectionPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 4;

        /**
         * The constructor.
         *
         * @param fm the fragment manager.
         */
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Number of tabs.
         *
         * @return 4.
         */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        /**
         * Gets a fragment at the position.
         *
         * @param position the position of the tab.
         * @return the fragment.
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ViewDetailsContentFragment.newInstance(dataType, position);
        }

        /**
         * Sets the title of the tabs.
         *
         * @param position the position of the tab.
         * @return the title of the tabs.
         */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "W";
                case 2:
                    return "M";
                case 3:
                    return "Y";
                default:
                    return "D";
            }
        }
    }
}
