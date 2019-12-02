package com.example.BarHop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// This class renders a list of items dynamically based on the data that the class is given
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";

    // RecyclerView will render a list of bars form the bar names in the arrayList
    private List<Bar> bars;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Bar> bars) {
        this.context = context;
        this.bars = bars;
    }

    // Inflates view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.bar_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    // Assigns the data of each item (images, text fields, etc.)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // sets the text of the item stored in the list at the corresponding position
        Bar bar = bars.get(position);
        holder.barName.setText(bar.name);
        holder.barAddress.setText(bar.address);
        holder.barDistance.setText(bar.distance);
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }

    // Holds each individual list entry in memory,
    // gets ready to add next entry
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView barName;
        TextView barAddress;
        TextView barDistance;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            barName = itemView.findViewById(R.id.name_text);
            barAddress = itemView.findViewById(R.id.address_text);
            barDistance = itemView.findViewById(R.id.distance_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
