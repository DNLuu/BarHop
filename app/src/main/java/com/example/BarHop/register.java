package com.example.BarHop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class register extends Fragment {
    Button back, signUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register, null);

        signUp = (Button)view.findViewById(R.id.btn_signup);

        signUp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do sign up stuff;
                Toast.makeText(getContext(), "Account registered.", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, new login());

                fragmentTransaction.commit();
            }
        }));



        back = (Button)view.findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_main, new login());

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void closeFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
