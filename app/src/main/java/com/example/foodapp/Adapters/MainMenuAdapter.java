package com.example.foodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.MainMenuItems;
import com.example.foodapp.R;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {
    private ArrayList<MainMenuItems> myMenuList;
    private OnClickListener myListener;

    //Takes in arraylist and listner to add to private variables
    public MainMenuAdapter(ArrayList<MainMenuItems> items, OnClickListener onClickListener){
        this.myMenuList = items;
        this.myListener = onClickListener;
    }
    //The override is in the Activity Class
    public interface  OnClickListener{
        void onItemClick(int position);
    }
    //Need to Implement View.OnClickListener, RecyclerView does not come with it
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView1;
        public TextView myTextView2;
        OnClickListener onClickListener;

        //This allows the Adapter to make changes to the TextViews in the XML file
        public ViewHolder(View itemView, OnClickListener listener) {
            super(itemView);
            myTextView1 = itemView.findViewById(R.id.textView);
            myTextView2 = itemView.findViewById(R.id.textView2);
            onClickListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }

    //Gets the context, in this case the main_menu_items, to be used as the holder for the
    //Recycler view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_items, parent, false);
        ViewHolder mmvh = new ViewHolder(v, myListener);
        return mmvh;
    }

    //Uses the information int eh myMenuList ArrayList, and fills in the TextViews for each card
    //With the information
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainMenuItems currentItem = myMenuList.get(position);

        holder.myTextView1.setText(currentItem.getText1());
        holder.myTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return myMenuList.size();
    }
}