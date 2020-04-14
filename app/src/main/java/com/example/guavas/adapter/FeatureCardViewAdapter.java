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

/**
 * This class is the adapter for the recycler view that displays the submenus. Used in health summary, vitals, and BMI submenus.
 */
public class FeatureCardViewAdapter extends RecyclerView.Adapter<FeatureCardViewAdapter.ViewHolder> {

    private String[] featureTitle;
    private int[] featureImageId;
    private Listener listener;

    /**
     * The constructor takes in the data to be displayed.
     *
     * @param titles   the feature titles
     * @param imageIds the feature image Ids.
     */
    public FeatureCardViewAdapter(String[] titles, int[] imageIds) {
        featureTitle = titles;
        featureImageId = imageIds;
    }

    /**
     * Gets the number of data.
     *
     * @return the number of data.
     */
    @Override
    public int getItemCount() {
        return featureTitle.length;
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
    public FeatureCardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_features, parent, false);
        return new ViewHolder(cardView);
    }

    /**
     * Binds the data to the card view.
     *
     * @param holder   the <code>ViewHolder</code> that holds the card view.
     * @param position the position of the data.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        imageView.setContentDescription(featureTitle[position]);
        imageView.setImageResource(featureImageId[position]);

        TextView textView = (TextView) cardView.findViewById(R.id.text_feature_name);
        textView.setText(featureTitle[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
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

    /**
     * Interface for the activity that listens to the adapter.
     */
    public interface Listener {
        public void onClick(int position);
    }
}
