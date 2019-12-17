package com.example.foodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.FoodItemCard;
import com.example.foodapp.R;

import java.util.ArrayList;

public class foodItemAdapter extends RecyclerView.Adapter<foodItemAdapter.ViewHolder> {
    private ArrayList<FoodItemCard> myFoodList;
    private OnClickListener myListener;

    public foodItemAdapter(ArrayList<FoodItemCard> items, OnClickListener onClickListener){
        this.myFoodList = items;
        this.myListener = onClickListener;
    }

    public interface  OnClickListener{
        void onItemClick(int position);
    }
    //Need to Implement View.OnClickListener, RecyclerView does not come with it
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView1;
        public TextView myTextView2;
        public TextView myTextView3;
        OnClickListener onClickListener;


        public ViewHolder(View itemView, OnClickListener listener) {
            super(itemView);
            myTextView1 = itemView.findViewById(R.id.itemTextView);
            myTextView2 = itemView.findViewById(R.id.expirationDateTextView);
            myTextView3 = itemView.findViewById(R.id.glutenFreeView);
            onClickListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_items, parent, false);
        ViewHolder mmvh = new ViewHolder(v, myListener);
        return mmvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodItemCard currentItem = myFoodList.get(position);

        holder.myTextView1.setText(currentItem.getFoodName());
        holder.myTextView2.setText(currentItem.getExpirationDate());
        holder.myTextView3.setText(currentItem.getIsGlutenFree());
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }
}
