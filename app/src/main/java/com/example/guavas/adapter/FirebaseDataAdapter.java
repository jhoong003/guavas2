package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.DataType;
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
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseDataAdapter extends FirebaseRecyclerAdapter<MedicalRecord, FirebaseDataAdapter.ViewHolder> {

    private DataType dataType;
    private Listener listener;

    public FirebaseDataAdapter(FirebaseRecyclerOptions<MedicalRecord> options, DataType dataType){
        super(options);
        this.dataType = dataType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, final MedicalRecord record) {
        CardView cardView = holder.cardView;
        final double measurement = record.getMeasurement();

        if (measurement >= dataType.getMinNormal() && measurement <= dataType.getMaxNormal())
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.normal_measurement));
        else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.abnormal_measurement));
        }

        TextView dataTextView = cardView.findViewById(R.id.text_measurement);
        dataTextView.setText(String.format(Locale.getDefault(), "%.2f %s", measurement, dataType.getMeasurementUnit()));

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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener{
        public void launchDialog(double measurement, long timeInMillis, DataType dataType);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView view){
            super(view);
            cardView = view;
        }

    }
}
