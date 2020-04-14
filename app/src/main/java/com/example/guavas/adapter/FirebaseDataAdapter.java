package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.entity.DataType;
import com.example.guavas.data.model.MedicalRecord;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This class is the adapter for the recycler view that displays the medical data.
 */
public class FirebaseDataAdapter extends FirebaseRecyclerAdapter<MedicalRecord, FirebaseDataAdapter.ViewHolder> {

    private DataType dataType;
    private Listener listener;

    /**
     * The constructor takes a FirebaseRecyclerOptions and the chosen data type.
     *
     * @param options  the FirebaseRecyclerOptions.
     * @param dataType the chosen data type.
     */
    public FirebaseDataAdapter(FirebaseRecyclerOptions<MedicalRecord> options, DataType dataType) {
        super(options);
        this.dataType = dataType;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_measurement, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the card view.
     *
     * @param holder   the <code>ViewHolder</code> that holds the card view.
     * @param position the position of the data.
     * @param record   the <code>MedicalRecord</code> object holding the data.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, final MedicalRecord record) {
        CardView cardView = holder.cardView;
        final double measurement = record.getMeasurement();

        // Color based on the measurement
        if (measurement >= dataType.getMinNormal() && measurement <= dataType.getMaxNormal())
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.normal_measurement));
        else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.abnormal_measurement));
        }

        // Set the measurement text
        TextView dataTextView = cardView.findViewById(R.id.text_measurement);
        dataTextView.setText(String.format(Locale.getDefault(), "%.2f %s", measurement, dataType.getMeasurementUnit()));

        // Set the date and time text
        TextView timeTextView = cardView.findViewById(R.id.text_datetime);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(record.getTime());
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT);
        timeTextView.setText(format.format(calendar.getTime()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.launchDialog(measurement, record.getTime(), dataType);
            }
        });
    }

    /**
     * Sets an activity as a listener.
     *
     * @param listener the activity that listens.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Interface for the activity that listens to the adapter.
     */
    public interface Listener {
        public void launchDialog(double measurement, long timeInMillis, DataType dataType);
    }

    /**
     * The class specific ViewHolder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        /**
         * The constructor takes a card view.
         *
         * @param view the card view.
         */
        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }

    }
}
