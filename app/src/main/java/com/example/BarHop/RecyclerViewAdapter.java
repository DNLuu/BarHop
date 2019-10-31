package com.example.BarHop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


// This class renders a list of items dynamically based on the data that the class is given
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";

    // Dummy data of bar names
    // RecyclerView will render a list of bars form the bar names in the arrayList
    private List<String> barNames = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context, List<String> barNames) {
        this.context = context;
        this.barNames = barNames;
//        this.images = images;
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
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, barNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // sets the text of the item stored in the list at the corresponding position
        holder.barName.setText(barNames.get(position));
    }

    @Override
    public int getItemCount() {
        return barNames.size();
    }

    // Holds each individual list entry in memory,
    // gets ready to add next entry
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView barName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            barName = itemView.findViewById(R.id.name_text);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
