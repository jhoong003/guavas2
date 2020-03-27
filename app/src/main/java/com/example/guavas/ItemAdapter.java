package com.example.guavas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> implements Filterable {
    private ArrayList<ItemList> Item ;
    private ArrayList<ItemList> ItemFull ;

    private OnItemClickListener listener;

    private Filter exampleFitler = new Filter(){
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

    //Constructor
    public ItemAdapter(ArrayList<ItemList> items){
        Item = items;
        ItemFull = new ArrayList<ItemList>(items);
    }

    //Bawaan
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardboi,parent,false);
        ItemHolder IH = new ItemHolder(v,this.listener);
        return IH;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ItemList inputItem = Item.get(position);

        holder.Text1.setText(inputItem.getText1());
        holder.Text2.setText(inputItem.getText2());

    }

    @Override
    public int getItemCount() {
        return Item.size();
    }

    @Override
    public Filter getFilter() {

        return exampleFitler;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        public TextView Text1;
        public TextView Text2;

        public ItemHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            Text1 = itemView.findViewById(R.id.Text1);
            Text2 = itemView.findViewById(R.id.Text2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!= null){
                        int position = getAdapterPosition();
                        listener.OnItemClick(position);
                    }
                }
            });
        }
    }

    //Onclick costumize
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

}
