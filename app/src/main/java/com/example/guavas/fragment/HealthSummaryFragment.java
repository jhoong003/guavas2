package com.example.guavas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.guavas.R;
import com.example.guavas.adapter.FeatureCardViewAdapter;
import com.example.guavas.data.database.SubMenus;
import com.example.guavas.data.entity.SubMenu;
import com.example.guavas.observer.FragmentObserver;
import com.example.guavas.observer.Subject;

/**
 * A fragment that displays the health summary fragment.
 */
public class HealthSummaryFragment extends Fragment implements Subject {

    private RecyclerView recyclerView;

    private FragmentObserver observer;

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
        View parentView =
                inflater.inflate(R.layout.fragment_health_summary, container, false);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
        setAdapterToView();
        setLayoutManagerToView();
        return parentView;
    }

    /**
     * Sets up the adapter to display the data.
     */
    private void setAdapterToView() {
        SubMenu subMenu = new SubMenus(getActivity()).getHealthSummarySubMenu();
        FeatureCardViewAdapter adapter = new FeatureCardViewAdapter(subMenu.getTitles(), subMenu.getImageIds());
        adapter.setListener(new FeatureCardViewAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Fragment nextFragment;
                switch (position) {
                    case 0:
                        nextFragment = VitalsFragment.newInstance(VitalsFragment.SUBMENU_BMI);
                        break;
                    case 1:
                        nextFragment = VitalsFragment.newInstance(VitalsFragment.SUBMENU_VITALS);
                        break;
                    case 2:
                        nextFragment = new OtherSummaryFragment();
                        break;
                    default:
                        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
                        return;
                }
                notifyObserver(nextFragment);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Sets the layout manager to the recycler view.
     */
    private void setLayoutManagerToView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
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
