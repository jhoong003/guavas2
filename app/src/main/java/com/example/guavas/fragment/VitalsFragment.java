package com.example.guavas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guavas.R;
import com.example.guavas.adapter.FeatureCardViewAdapter;
import com.example.guavas.data.database.DataTypes;
import com.example.guavas.data.database.SubMenus;
import com.example.guavas.data.entity.SubMenu;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment that displays the lists of medical data type to be chosen. Used in Vitals and BMI.
 */
public class VitalsFragment extends Fragment implements Subject {

    public static final int SUBMENU_BMI = 0;
    public static final int SUBMENU_VITALS = 1;
    private static final String SUBMENU_KEY = "sub";

    private String[] titles;
    private int[] imageIds;
    private int subMenuId;
    private FragmentObserver observer;

    private RecyclerView recyclerView;

    /**
     * Gets a new instance of this fragment.
     *
     * @param subMenu the sub menu type.
     * @return a new instance of this fragment.
     */
    public static VitalsFragment newInstance(int subMenu) {

        Bundle args = new Bundle();
        args.putInt(SUBMENU_KEY, subMenu);
        VitalsFragment fragment = new VitalsFragment();
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
        if (args != null) {
            subMenuId = args.getInt(SUBMENU_KEY);
        }
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
        View parent = inflater.inflate(R.layout.fragment_vitals, container, false);
        recyclerView = (RecyclerView) parent.findViewById(R.id.recycler_view);
        setupData();
        setAdapterToView();
        setLayoutManagerToView();
        return parent;
    }

    /**
     * Sets up the list of medical data type.
     */
    private void setupData() {
        SubMenus subMenus = new SubMenus(getActivity());
        SubMenu subMenu = null;
        if (subMenuId == SUBMENU_BMI) subMenu = subMenus.getBmiSubMenu();
        else if (subMenuId == SUBMENU_VITALS) subMenu = subMenus.getVitalSubMenu();

        titles = subMenu.getTitles();
        imageIds = subMenu.getImageIds();
    }

    /**
     * Attach an adapter to the recycler view.
     */
    private void setAdapterToView() {

        FeatureCardViewAdapter adapter = new FeatureCardViewAdapter(titles, imageIds);
        adapter.setListener(new FeatureCardViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                launchViewDetailsFragment(position, subMenuId);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Attach a layout manager to the recycler view.
     */
    private void setLayoutManagerToView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Launch the details of the clicked medical data type.
     *
     * @param position the position of the clicked item.
     * @param subMenu  the sub menu type.
     */
    private void launchViewDetailsFragment(int position, int subMenu) {
        Fragment nextFragment;
        if (subMenu == SUBMENU_BMI)
            nextFragment = ViewDetailsHeaderFragment.newInstance(DataTypes.getBMIDataType(position));
        else
            nextFragment = ViewDetailsHeaderFragment.newInstance(DataTypes.getVitalDataType(position));
        notifyObserver(nextFragment);
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
