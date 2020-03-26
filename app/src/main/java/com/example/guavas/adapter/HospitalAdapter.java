package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.guavas.R;
import com.example.guavas.hospital;
import java.util.ArrayList;
public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> /*implements Filterable*/ {


    private ArrayList<hospital> hospitalList;
    private ArrayList<hospital> hospitalListDup;      //Duplicate the array of hospital cards
    private OnItemClickListener mListener;           //Initialise the interface


    // Constructor
    public HospitalAdapter(ArrayList<hospital> hospitalList, OnItemClickListener mListener){
        this.hospitalList=hospitalList;
        this.mListener=mListener;
        hospitalListDup = new ArrayList<>(hospitalList);
    }

    // Create Interface class to communicate with the activity class
    // it will pass the position of the click item
    public interface OnItemClickListener{
        //Pass the position of the click item to the listener
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    // HospitalViewAdapter class
    public static class HospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgURL;
        private TextView hospitalName;
        OnItemClickListener onItemClickListener;


        // Constructor
        public HospitalViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imgURL=itemView.findViewById(R.id.cardImage);
            hospitalName=itemView.findViewById(R.id.cardTitle);
            this.onItemClickListener=onItemClickListener;
            itemView.setOnClickListener(this);


        }
        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }

    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_card_view, parent, false);
        HospitalViewHolder evh = new HospitalViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        hospital currentItem=hospitalList.get(position);
        holder.imgURL.setImageResource(currentItem.getImgURL());
        holder.hospitalName.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the hospitalList
        return hospitalList.size();
    }

    public void updateList (ArrayList<hospital> newHospitalList){
        hospitalList =new ArrayList<hospital>() ;
        hospitalList.addAll(newHospitalList);
        notifyDataSetChanged();
    }
}
