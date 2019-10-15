package com.example.logindemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class listStudent extends Fragment {
    Context context;
    ListView listView;
    Button remove, cancel;

    Button addNew;
    EditText newName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_student, null);
        remove = (Button) view.findViewById(R.id.btn_remove);

        context = container.getContext();  // context in Fragment

        final ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_single_choice, MainActivity.students);
        listView = (ListView) view.findViewById(R.id.list_student);
        listView.setChoiceMode(1);      // single selection mode (radio buttons)
        listView.setAdapter(adapter);

        // remove button clicked
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position;
                position = listView.getCheckedItemPosition(); // find position of item

                if (position > 0 && position < MainActivity.students.size()) {
                    MainActivity.students.remove(position);       // remove item at position
                    listView.clearChoices();                      // clear selection
                    adapter.notifyDataSetChanged();               // refresh listview
                } else {
                    Toast.makeText(context, "No student selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newName = (EditText) view.findViewById(R.id.input_newName);   // define a text edit
        addNew = (Button) view.findViewById(R.id.btn_addNew);          // define a button to add
        addNew.setOnClickListener(new View.OnClickListener() {      // wait till tte button is clicked
            @Override
            public void onClick(View v) {
                String s = newName.getText().toString();           // read user’s type (input box)
                if (!s.equals("")) {
                    MainActivity.students.add(s);                   // add new data into the data structure in MainActivity
                    adapter.notifyDataSetChanged();                 // refresh
                    newName.setText("");                            // clear the input box
                } else {
                    Toast.makeText(context, "Name field empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}