package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.guavas.R;
import com.example.guavas.data.entity.ItemList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the adapter for the recycler view that displays the symptoms.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> implements Filterable {
    private ArrayList<ItemList> Item;
    private ArrayList<ItemList> ItemFull;

    private OnItemClickListener listener;

    private Filter exampleFitler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemList> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(ItemFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemList item : ItemFull) {
                    if (item.getText1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Item.clear();
            Item.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * The constructor takes in a list of symptoms.
     *
     * @param items the list of symptoms.
     */
    public ItemAdapter(ArrayList<ItemList> items) {
        Item = items;
        ItemFull = new ArrayList<ItemList>(items);
    }

    /**
     * Inflates the card view layout.
     *
     * @param parent   the parent view group.
     * @param viewType the view type.
     * @return the <code>ViewHolder</code> holding the card view.
     */
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_symptoms, parent, false);
        ItemHolder IH = new ItemHolder(v, this.listener);
        return IH;
    }

    /**
     * Binds the data to the card view.
     *
     * @param holder   the <code>ViewHolder</code> that holds the card view.
     * @param position the position of the data.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ItemList inputItem = Item.get(position);

        holder.Text1.setText(inputItem.getText1());
        holder.Text2.setText(inputItem.getText2());

    }

    /**
     * Gets the number of data.
     *
     * @return the number of data.
     */
    @Override
    public int getItemCount() {
        return Item.size();
    }

    /**
     * Gets the filter of this class.
     *
     * @return the filter of this class.
     */
    @Override
    public Filter getFilter() {

        return exampleFitler;
    }

    /**
     * Sets an activity as a listener.
     *
     * @param listener the activity that listens.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * The class specific ViewHolder.
     */
    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView Text1;
        public TextView Text2;

        /**
         * The constructor a view and a listener.
         *
         * @param itemView the card view.
         * @param listener the listener.
         */
        public ItemHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            Text1 = itemView.findViewById(R.id.Text1);
            Text2 = itemView.findViewById(R.id.Text2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.OnItemClick(position);
                    }
                }
            });
        }
    }

    /**
     * Interface for the activity that listens to the adapter.
     */
    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

}
