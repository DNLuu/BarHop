package com.example.logindemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> students = new ArrayList<>();
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateStudentList();

        Fragment fr;
        fr = new login();  // page to render on login
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_main, fr);
        fragmentTransaction.commit();


    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fr = null;
        int id = item.getItemId();

        if (id == R.id.action_menu1) {
            Toast.makeText(this, "action Menu1", Toast.LENGTH_SHORT).show();
            fr = new listStudent();
        }
        if (id == R.id.action_menu2) {
            Toast.makeText(this, "action Menu2", Toast.LENGTH_SHORT).show();
            //fr = new activity for menu2();
        }

        if (id == R.id.action_logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (fr != null) {
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm2.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main, fr);
            fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }




    // Populate list w/ dummy data for testing
    // Data will come from AWS
    public void populateStudentList() {
        students.clear();
        students.add("Lee");
        students.add("James");
        students.add("Jane");
        students.add("Ted");
    }


}
