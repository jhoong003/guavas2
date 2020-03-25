package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guavas.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FeatureCardViewAdapter extends RecyclerView.Adapter<FeatureCardViewAdapter.ViewHolder> {

    private String[] featureTitle;
    private int[] featureImageId;
    private Listener listener;

    public FeatureCardViewAdapter(String[] titles, int[] imageIds){
        featureTitle = titles;
        featureImageId = imageIds;
    }

    @Override
    public int getItemCount() {
        return featureTitle.length;
    }

    @NonNull
    @Override
    public FeatureCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        imageView.setContentDescription(featureTitle[position]);
        imageView.setImageResource(featureImageId[position]);

        TextView textView = (TextView) cardView.findViewById(R.id.text_feature_name);
        textView.setText(featureTitle[position]);

        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cv){
            super(cv);
            cardView = cv;
        }

    }

    public interface Listener{
        public void onClick(int position);
    }
}
