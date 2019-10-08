package com.example.logindemo;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends Fragment {

    TextView textView;
    Button exit, login;
    EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login,null);

        et=(EditText) view.findViewById(R.id.inut_pwd);
        exit=(Button)view.findViewById(R.id.btn_exit);
        login=(Button)view.findViewById(R.id.btn_login);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String received;
                received="1";
                Fragment fr=null;
                if (received.equals("0")) {
                    fr = new listStudent();
                    FragmentManager fm2 = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm2.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_main, fr);
                    fragmentTransaction.commit();
                } else if (received.equals("1")) {
                    Toast.makeText(getContext(), "Wrong ID and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}