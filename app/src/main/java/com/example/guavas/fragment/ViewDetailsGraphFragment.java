package com.example.guavas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guavas.R;
import com.example.guavas.data.entity.DataType;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * This fragment is responsible for the creation of the line chart
 */
public class ViewDetailsGraphFragment extends Fragment {

    public static final String DATATYPE_KEY = "datatype";
    public static final String ENTRY_KEY = "entry key";

    private DataType dataType;
    private ArrayList<Entry> entries;
    private Listener listener;

    private View parent;

    public ViewDetailsGraphFragment() {
    }

    /**
     * Gets a new instance of the fragment.
     *
     * @param dataType the chosen medical data type.
     * @param entries  the entries of the data.
     * @return a new instance of the fragment.
     */
    public static ViewDetailsGraphFragment newInstance(DataType dataType, ArrayList<Entry> entries) {
        ViewDetailsGraphFragment fragment = new ViewDetailsGraphFragment();
        Bundle args = new Bundle();
        args.putParcelable(DATATYPE_KEY, dataType);
        args.putParcelableArrayList(ENTRY_KEY, entries);
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
            dataType = args.getParcelable(DATATYPE_KEY);
            entries = args.getParcelableArrayList(ENTRY_KEY);
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
        parent = inflater.inflate(R.layout.fragment_view_details_graph, container, false);
        setupGraph();
        return parent;
    }

    /**
     * Attach the data to the graph.
     */
    private void setupGraph() {
        LineChart mChart = parent.findViewById(R.id.line_chart);
        mChart.getDescription().setEnabled(false);
        mChart.setDragEnabled(true);
        mChart.zoom(1.2f, 1f, mChart.getCenter().getX(), mChart.getCenter().getY());
        adjustGraphAxis(mChart);

        LineDataSet dataSet = new LineDataSet(entries, dataType.getDataTypeName());
        LineData lineData = new LineData(dataSet);
        mChart.setData(lineData);

        mChart.invalidate();
    }

    /**
     * Sets up the graph axis.
     *
     * @param chart the line chart.
     */
    private void adjustGraphAxis(LineChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(listener.getGranularity());
        if (!entries.isEmpty()) {
            xAxis.setAxisMinimum(entries.get(0).getX());
            xAxis.setAxisMaximum(entries.get(entries.size() - 1).getX());
        }
        xAxis.setLabelCount(entries.size(), false);
        xAxis.setValueFormatter(new XAxisFormatter());

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setEnabled(false);
        yAxis = chart.getAxisRight();
        yAxis.setEnabled(false);
    }

    /**
     * Connects with <code>ViewDetailsContentFragment</code>.
     *
     * @param listener <code>ViewDetailsContentFragment</code>.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Interface to connect with <code>ViewDetailsContentFragment</code>.
     */
    public interface Listener {
        public float getGranularity();

        public String getAxisLabel(float value, AxisBase axis);
    }

    /**
     * A custom x-axis formatter.
     */
    private class XAxisFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return listener.getAxisLabel(value, axis);
        }
    }
}
