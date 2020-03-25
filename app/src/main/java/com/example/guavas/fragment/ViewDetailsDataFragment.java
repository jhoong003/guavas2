package com.example.guavas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guavas.R;
import com.example.guavas.ShowAllActivity;
import com.example.guavas.adapter.DataCardViewAdapter;
import com.example.guavas.data.DataType;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDetailsDataFragment extends Fragment implements View.OnClickListener{
    private View parent;
    private RecyclerView recyclerView;

    private static final String ENTRY_KEY = "Entry key";
    private static final String DATATYPE_KEY ="Type key";

    private CharSequence[] dateTime;
    private double[] data;
    private DataType dataType;
    private ArrayList<Entry> entries;
    private Listener listener;

    public ViewDetailsDataFragment(){

    }

    public static ViewDetailsDataFragment newInstance(DataType dataType, ArrayList<Entry> entries){
        ViewDetailsDataFragment fragment = new ViewDetailsDataFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY, dataType);
        args.putParcelableArrayList(ENTRY_KEY, entries);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            dataType = args.getParcelable(DATATYPE_KEY);
            entries = args.getParcelableArrayList(ENTRY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_view_details_data, container, false);
        convertEntriesToArray();
        setupRecyclerViewAdapter();
        setupRecyclerViewLayout();
        setupShowAllButtonClick();
        return parent;
    }

    private void convertEntriesToArray(){
        dateTime = new CharSequence[entries.size()];
        data = new double[entries.size()];
        int i = 0;

        for (Entry e : entries){
            dateTime[i] = listener.getAxisLabel(e.getX(), null);
            data[i] = e.getY();
            i++;
        }
    }

    private void setupRecyclerViewAdapter(){
        recyclerView = parent.findViewById(R.id.recycler_view);
        DataCardViewAdapter adapter = new DataCardViewAdapter(data, dateTime, dataType);
        recyclerView.setAdapter(adapter);
    }

    private void setupRecyclerViewLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupShowAllButtonClick(){
        Button button = parent.findViewById(R.id.button_show_all);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickShowAll();
    }

    private void onClickShowAll(){
        Intent intent = new Intent(getContext(), ShowAllActivity.class);
        intent.putExtra(ShowAllActivity.DATATYPE_KEY, dataType);
        startActivity(intent);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener{
        public String getAxisLabel(float value, AxisBase axis);
    }
}
