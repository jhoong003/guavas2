package com.example.guavas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guavas.data.IllDetail;
import com.example.guavas.R;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> /*implements Filterable*/ {


    private ArrayList<IllDetail> DiseaseList;
    private ArrayList<IllDetail> DiseaseListDup;      //Duplicate the array of hospital cards
    private OnItemClickListener mListener;           //Initialise the interface


    // Constructor
    public DiseaseAdapter(ArrayList<IllDetail> List, OnItemClickListener mListener){
        this.DiseaseList=List;
        this.mListener=mListener;
        DiseaseListDup = new ArrayList<>(List);
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
    public static class DiseaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //private ImageView imgURL;
        private TextView diseaseName;
        OnItemClickListener onItemClickListener;


        // Constructor
        public DiseaseViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            //imgURL=itemView.findViewById(R.id.cardImage);
            diseaseName=itemView.findViewById(R.id.cardTitle);
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
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_card_view, parent, false);
        DiseaseViewHolder evh = new DiseaseViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position) {
        IllDetail currentItem=DiseaseList.get(position);
        //holder.imgURL.setImageResource(currentItem.getImgURL());
        holder.diseaseName.setText(currentItem.getName());


    }

    @Override
    public int getItemCount() {

        // Return the number of items in the hospitalList
        return DiseaseList.size();
    }

    public ArrayList<IllDetail> updateDiseaseList (ArrayList<IllDetail> newDiseaseList){
        DiseaseList =new ArrayList<IllDetail>() ;
        DiseaseList.addAll(newDiseaseList);
        notifyDataSetChanged();
        return newDiseaseList;

    }

    public IllDetail getItemAtIndex(int idx){
        return DiseaseList.get(idx);
    }
}
