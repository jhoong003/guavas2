package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guavas.R;
import com.example.guavas.data.entity.Hospital;

import java.util.ArrayList;

/**
 * This class is the adapter for the recycler view that displays the list of hospitals.
 */
public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> /*implements Filterable*/ {

    private ArrayList<Hospital> hospitalList;
    private ArrayList<Hospital> hospitalListDup;      //Duplicate the array of hospital cards
    private OnItemClickListener mListener;           //Initialise the interface


    /**
     * The constructor takes in the data to be displayed, and a listener.
     *
     * @param hospitalList the list of hospitals.
     * @param mListener    the listener.
     */
    public HospitalAdapter(ArrayList<Hospital> hospitalList, OnItemClickListener mListener) {
        this.hospitalList = hospitalList;
        this.mListener = mListener;
        hospitalListDup = new ArrayList<>(hospitalList);
    }

    /**
     * Inflates the card view layout.
     *
     * @param parent   the parent view group.
     * @param viewType the view type.
     * @return the HospitalViewHolder containing the card.
     */
    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_hospital, parent, false);
        HospitalViewHolder evh = new HospitalViewHolder(v, mListener);
        return evh;
    }

    /**
     * Binds the data to the view holder.
     *
     * @param holder   the view holder.
     * @param position the position of the view holder.
     */
    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital currentItem = hospitalList.get(position);
        holder.imgURL.setImageResource(currentItem.getImgURL());
        holder.hospitalName.setText(currentItem.getName());
    }

    /**
     * Returns the number of items in the hospitalList.
     *
     * @return the number of items in the hospitalList.
     */
    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    /**
     * Change the list of hospitals to be displayed.
     *
     * @param newHospitalList the new list to display.
     * @return the new list to display.
     */
    public ArrayList<Hospital> updateList(ArrayList<Hospital> newHospitalList) {
        hospitalList = new ArrayList<Hospital>();
        hospitalList.addAll(newHospitalList);
        notifyDataSetChanged();
        return newHospitalList;
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
    public static class HospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgURL;
        private TextView hospitalName;
        OnItemClickListener onItemClickListener;

        /**
         * This constructor takes a card view and a listener.
         *
         * @param itemView            the card view.
         * @param onItemClickListener the listener activity class.
         */
        public HospitalViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imgURL = itemView.findViewById(R.id.cardImage);
            hospitalName = itemView.findViewById(R.id.cardTitle);
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
