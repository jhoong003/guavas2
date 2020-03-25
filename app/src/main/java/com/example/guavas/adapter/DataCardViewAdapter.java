package com.example.guavas.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.DataType;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

public class DataCardViewAdapter extends RecyclerView.Adapter<DataCardViewAdapter.ViewHolder> {

    private double[] data;
    private CharSequence[] dateTime;
    private DataType dataType;

    public DataCardViewAdapter(double[] data, CharSequence[] dateTime, DataType dataType){
        this.data = data;
        this.dateTime = dateTime;
        this.dataType = dataType;
    }

    @Override
    public int getItemCount() {return data.length; }

    @NonNull
    @Override
    public DataCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.data_card_view, parent, false);
        return new ViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        double measurement = data[position];

        if (measurement >= dataType.getMinNormal() && measurement <= dataType.getMaxNormal())
            cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.normal_measurement));
        else {
            float a = 0.1f;
            double differenceRate = (measurement < dataType.getMinNormal()) ?
                    dataType.getMinNormal()/measurement : measurement/dataType.getMaxNormal();
            int colorAlpha = (int) (Math.min(a + (differenceRate-1)*0.9, 1) * 255);
            cardView.setCardBackgroundColor(ColorUtils.setAlphaComponent(
                    ContextCompat.getColor(cardView.getContext(), R.color.abnormal_measurement), colorAlpha));
        }

        TextView measurementText = (TextView) cardView.findViewById(R.id.text_measurement);
        measurementText.setText(String.format(Locale.getDefault(), "%.2f %s", measurement, dataType.getMeasurementUnit()));

        TextView dateTimeText = (TextView) cardView.findViewById(R.id.text_datetime);
        dateTimeText.setText(dateTime[position]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }
    }
}
