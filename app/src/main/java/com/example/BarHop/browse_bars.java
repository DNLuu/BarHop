package com.example.BarHop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class browse_bars extends Fragment {
    private List<String> barNames = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_browse_bars,null);
        populateBarLists();

        RecyclerView recyclerView = view.findViewById(R.id.bar_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), barNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

        return view;
    }


    private void populateBarLists() {
        barNames.add("520 Bar & Grill");
        barNames.add("Earl's Kitchen + Bar");
        barNames.add("Cork and Tap");
        barNames.add("Tavern Hall");
        barNames.add("Cypress Lounge & Wine Bar");
    }
}
