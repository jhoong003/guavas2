package com.example.guavas.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.entity.DataType;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is the adapter for the recycler view that displays medical data.
 */
public class DataCardViewAdapter extends RecyclerView.Adapter<DataCardViewAdapter.ViewHolder> {

    private double[] data;
    private CharSequence[] dateTime;
    private DataType dataType;

    /**
     * The constructor takes in the data to be displayed.
     *
     * @param data     the data.
     * @param dateTime the date and time.
     * @param dataType the medical data type.
     */
    public DataCardViewAdapter(double[] data, CharSequence[] dateTime, DataType dataType) {
        this.data = data;
        this.dateTime = dateTime;
        this.dataType = dataType;
    }

    /**
     * Gets the number of data.
     *
     * @return the number of data.
     */
    @Override
    public int getItemCount() {
        return data.length;
    }

    /**
     * Inflates the card view layout.
     *
     * @param parent   the parent view group.
     * @param viewType the view type.
     * @return the card view.
     */
    @NonNull
    @Override
    public DataCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_measurement, parent, false);
        return new ViewHolder(cardView);
    }

    /**
     * Binds the data to the card view.
     *
     * @param holder   the <code>ViewHolder</code> that holds the card view.
     * @param position the position of the data.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        double measurement = data[position];

        if (measurement >= dataType.getMinNormal() && measurement <= dataType.getMaxNormal())
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.normal_measurement));
        else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.abnormal_measurement));
        }

        TextView measurementText = (TextView) cardView.findViewById(R.id.text_measurement);
        measurementText.setText(String.format(Locale.getDefault(), "%.2f %s", measurement, dataType.getMeasurementUnit()));

        TextView dateTimeText = (TextView) cardView.findViewById(R.id.text_datetime);
        dateTimeText.setText(dateTime[position]);
    }

    /**
     * The class specific ViewHolder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        /**
         * The constructor takes a card view.
         *
         * @param cv the card view.
         */
        public ViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }
}
