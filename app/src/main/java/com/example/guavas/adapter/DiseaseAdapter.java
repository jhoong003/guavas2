package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guavas.data.entity.IllDetail;
import com.example.guavas.R;

import java.util.ArrayList;

/**
 * This class is the adapter for the recycler view that displays the list of diseases.
 */
public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> /*implements Filterable*/ {

    private ArrayList<IllDetail> diseaseList;
    private ArrayList<IllDetail> DiseaseListDup;      //Duplicate the array of hospital cards
    private OnItemClickListener mListener;           //Initialise the interface

    /**
     * The constructor takes in the data to be displayed, and a listener.
     *
     * @param List      the list of diseases.
     * @param mListener the listener.
     */
    public DiseaseAdapter(ArrayList<IllDetail> List, OnItemClickListener mListener) {
        this.diseaseList = List;
        this.mListener = mListener;
        DiseaseListDup = new ArrayList<>(List);
    }

    /**
     * Inflates the card view layout.
     *
     * @param parent   the parent view group.
     * @param viewType the view type.
     * @return the DiseaseViewHolder containing the card.
     */
    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_disease, parent, false);
        DiseaseViewHolder evh = new DiseaseViewHolder(v, mListener);
        return evh;
    }

    /**
     * Binds the data to the view holder.
     *
     * @param holder   the view holder.
     * @param position the position of the view holder.
     */
    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position) {
        IllDetail currentItem = diseaseList.get(position);
        holder.diseaseName.setText(currentItem.getName());
    }

    /**
     * Returns the number of items in the diseaseList.
     *
     * @return the number of items in the diseaseList.
     */
    @Override
    public int getItemCount() {
        //
        return diseaseList.size();
    }

    /**
     * Change the list of diseases to be displayed.
     *
     * @param newDiseaseList the new list to display.
     * @return the new list to display.
     */
    public ArrayList<IllDetail> updateDiseaseList(ArrayList<IllDetail> newDiseaseList) {
        diseaseList = new ArrayList<IllDetail>();
        diseaseList.addAll(newDiseaseList);
        notifyDataSetChanged();
        return newDiseaseList;

    }

    /**
     * Interface class to communicate with the activity class. It will pass the position of the click item
     */
    public interface OnItemClickListener {
        /**
         * Pass the position of the click item to the listener
         */
        void onItemClick(int position);
    }

    /**
     * The class specific ViewHolder.
     */
    public static class DiseaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView diseaseName;
        OnItemClickListener onItemClickListener;

        /**
         * This constructor takes a card view and a listener.
         *
         * @param itemView            the card view.
         * @param onItemClickListener the listener activity class.
         */
        public DiseaseViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            diseaseName = itemView.findViewById(R.id.cardTitle);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }

        /**
         * Pass the position of the clicked item
         *
         * @param v the clicked item.
         */
        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }

    }
}
